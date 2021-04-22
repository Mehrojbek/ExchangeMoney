package uz.pdp.appexchangemoney.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.pdp.appexchangemoney.entity.Card;
import uz.pdp.appexchangemoney.entity.Income;
import uz.pdp.appexchangemoney.entity.Outcome;
import uz.pdp.appexchangemoney.payload.ApiResponse;
import uz.pdp.appexchangemoney.payload.CardDto;
import uz.pdp.appexchangemoney.payload.TransferDto;
import uz.pdp.appexchangemoney.repository.CardRepository;
import uz.pdp.appexchangemoney.repository.IncomeRepository;
import uz.pdp.appexchangemoney.repository.OutcomeRepository;

import java.util.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class MoneyService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    OutcomeRepository outcomeRepository;


    //ADD CARD
    public ApiResponse addCard(CardDto cardDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }


        if (new Date().after(cardDto.getExpireDate()))
            return new ApiResponse("karta amal qilish muddati o'tgan",false);

        Card card = new Card();
        card.setNumber(cardDto.getNumber());
        card.setExpireDate(cardDto.getExpireDate());

        //BU TRANSFER PAYTIDA KARTA SHU USERGA TEGISHLIMI YOKI YOQMI BILISH UCHUN QILINDI
        card.setUsername(username);

        //HOZIRCHA KARTAGA 200_000 PUL SOLIB QO'YAMIZ ICHIDAGI PULNI AVTOMATIK ANIQLASH IMKONIYATI YO'QLIGI SABABLI
        card.setBalance(200000d);
        cardRepository.save(card);
        return new ApiResponse("Karta muvaffaqiyatli saqlandi",true);
    }



    //TRANSFER MONEY
    public ApiResponse moneyTransfer(TransferDto transferDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        //PUL KETADIGAN KARTA
        Optional<Card> optionalFromCard = cardRepository.findByNumber(transferDto.getFromCardNumber());
        if (!optionalFromCard.isPresent())
            return new ApiResponse("karta topilmadi", false);

        //PUL KELADIGAN KARTA
        Optional<Card> optionalToCard = cardRepository.findByNumber(transferDto.getToCardNumber());
        if (!optionalToCard.isPresent())
            return new ApiResponse("Qabul qiluvchi karta toplimadi", false);

        Card fromCard = optionalFromCard.get();
        Card toCard = optionalToCard.get();

        //KARTA ACTIVE MI
        if (!fromCard.isActive())
            return new ApiResponse("Karta active emas", false);
        if (!toCard.isActive())
            return new ApiResponse("Qabul qiluvchi karta active emas", false);

        //AMAL QILISH MUDDATI
        if (new Date().after(fromCard.getExpireDate()))
            return new ApiResponse("Karta amal qilish muddati tugagan", false);
        if (new Date().after(toCard.getExpireDate()))
            return new ApiResponse("Qabul qiluvchi karta amal qilish muddati tugagan", false);

        //KARTA SHU USER GA TEGISHLIMI
        if (!fromCard.getUsername().equals(username))
            return new ApiResponse("Bu karta sizga tegishli emas", false);

        Double balanceFromCard = fromCard.getBalance();
        Double balanceToCard = toCard.getBalance();
        Double amount = transferDto.getAmount();
        double commissionAmount = amount / 100;

        //FROM CARD DA PUL YETARLIMI
        if (balanceFromCard < (amount + commissionAmount))
            return new ApiResponse("Mablag' yetarli emas", false);

        balanceFromCard = balanceFromCard - amount - commissionAmount;
        balanceToCard = balanceToCard + amount;

        fromCard.setBalance(balanceFromCard);
        toCard.setBalance(balanceToCard);

        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());

        Income income = new Income(fromCard, toCard, amount, date);
        Outcome outcome = new Outcome(fromCard, toCard, amount, date, commissionAmount);

        incomeRepository.save(income);
        outcomeRepository.save(outcome);
        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        return new ApiResponse("Muvaffaqiyatli bajarildi", true);


    }




    //GET HISTORY
    public List<Object> getHistory() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        List<Income> incomeList = incomeRepository.getAllByUsername(username);
        List<Outcome> outcomeList = outcomeRepository.getAllByUsername(username);

        List<Object> resultList = new ArrayList<>(incomeList);
        resultList.addAll(outcomeList);
        return resultList;
    }
}













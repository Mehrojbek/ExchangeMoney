package uz.pdp.appexchangemoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appexchangemoney.payload.ApiResponse;
import uz.pdp.appexchangemoney.payload.TransferDto;
import uz.pdp.appexchangemoney.service.MoneyService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/money")
public class MoneyController {
    @Autowired
    MoneyService moneyService;

    @PostMapping("/transfer")
    public ResponseEntity<?> moneyTransfer(@RequestBody @Valid TransferDto transferDto){
        ApiResponse apiResponse = moneyService.moneyTransfer(transferDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(){
        List<Object> objectList = moneyService.getHistory();
        return ResponseEntity.ok(objectList);
    }

}

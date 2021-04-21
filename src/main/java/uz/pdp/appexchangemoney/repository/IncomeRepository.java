package uz.pdp.appexchangemoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appexchangemoney.entity.Card;
import uz.pdp.appexchangemoney.entity.Income;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

    @Query(value = "select * from income inc join card c on c.id=inc.from_card_id where c.username=:username", nativeQuery = true)
    List<Income> getAllByUsername(String username);
}

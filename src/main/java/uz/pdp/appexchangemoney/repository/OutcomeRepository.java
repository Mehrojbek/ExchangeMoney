package uz.pdp.appexchangemoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appexchangemoney.entity.Card;
import uz.pdp.appexchangemoney.entity.Income;
import uz.pdp.appexchangemoney.entity.Outcome;

import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {
    @Query(value = "select * from outcome out join card c on c.id=out.from_card_id where c.username=:username",nativeQuery = true)
    List<Outcome> getAllByUsername(String username);
}

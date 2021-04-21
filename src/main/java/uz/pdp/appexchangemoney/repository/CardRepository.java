package uz.pdp.appexchangemoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appexchangemoney.entity.Card;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer> {

    Optional<Card> findByNumber(Long number);
}

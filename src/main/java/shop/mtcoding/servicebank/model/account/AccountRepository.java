package shop.mtcoding.servicebank.model.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // join fetch ac.user 추가하면 같이 조회함
    @Query("select ac from Account ac join fetch ac.user where ac.number = :number")
    Optional<Account> findByNumber(@Param("number") Integer number);

    // 여기서 join fetch user를 하면 쓸데없는 조회를 하는것
    @Query("select ac from Account ac where ac.user.id = :userId")
    List<Account> findByUserId(Long userId);
}

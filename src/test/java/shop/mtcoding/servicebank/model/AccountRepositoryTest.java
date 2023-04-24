package shop.mtcoding.servicebank.model;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.servicebank.model.account.Account;
import shop.mtcoding.servicebank.model.user.User;
import shop.mtcoding.servicebank.model.user.UserRepository;

@DataJpaTest // 내부에 트랜잭션이 있음
public class AccountRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    // 레파지토리를 테스트하기위한 초기 데이터가 필요
    @BeforeEach
    public void setUp(){

        // User ssar = User.builder().


        // 트랜잭션을 걸어놓으면 영속화가 되고 나서 DB로 곧바로 들어가지 않는다.
        // id 없이 만든 영속화 객체가 id를 가지고 있을까 ? cos로 만들어서 테스트 - 전략에 의해 id 생기는듯 ?
        // Jpa.save를 하는순간 리턴받을 필요가 없다 그 객체 자체가 영속화가 된다 - PC와 동기화 -> 모든 데이터가 동일

        // Eager 전략으로 조회를 테스트할 경우 BeforeEach로 영속화 하더라도 em.clear()로 영속화 다 날리고 테스트 하면 됨
        // (account) findbyId를 100번 하면 account 조회 후 유저 조회를 100번 더 하게 된다. (join 100번) - 쓸데없는 자원낭비
        // 성능의 이점을 가지려면 쓸데 없는 조회를 줄여야 한다.

        // Lazy전략 일때 join fetch를 사용하면 한번에 조회
        // many to one일때 fetch 사용 / 한사람의 데이터를 가져올때는 Lazy로딩을 하는게 좋다.
        // 동일한 데이터인데 join fetch를 사용하면 성능에 손해
        // Jpa 특징 - PC 이용하므로 Lazy로딩으로 필요한 user 정보를 PC 에서 가져다 쓴다.

        // 1000개의 레코드가 1000개의 레코드와 관련있다면 join fetch 보다 좋은 전략이 있다.
        // distinct로 userId롤 List에 담고
        // List로 left join fetch 에 in 쿼리를 날린다.
    }

    // beforEach를 하고 나서 findbyNumber 테스트
}

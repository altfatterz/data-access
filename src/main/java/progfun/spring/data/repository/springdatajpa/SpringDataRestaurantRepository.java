package progfun.spring.data.repository.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;

public interface SpringDataRestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByWebsite(Website website);

}

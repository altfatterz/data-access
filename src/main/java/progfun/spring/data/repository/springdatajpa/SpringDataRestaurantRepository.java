package progfun.spring.data.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.repository.RestaurantRepository;

public interface SpringDataRestaurantRepository extends RestaurantRepository, Repository<Restaurant, Long> {
}

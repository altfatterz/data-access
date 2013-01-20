package progfun.spring.data.repository;

import org.springframework.data.repository.Repository;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;

import java.util.List;

public interface EnhancedRestaurantRepository extends Repository<Restaurant, Long> {

    Restaurant findOne(Long id);

    Restaurant findByWebsite(Website website);

    public List<Restaurant> findAll();

    public Restaurant save(Restaurant restaurant);
}

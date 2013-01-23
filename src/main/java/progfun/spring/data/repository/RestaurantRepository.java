package progfun.spring.data.repository;

import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;

import java.util.List;

public interface RestaurantRepository {

    Restaurant findOne(Long id);

    Restaurant findByWebsite(Website website);

    List<Restaurant> findAll();

    Restaurant save(Restaurant restaurant);

    boolean exists(Long id);

    long count();

    void delete(Long id);

    void delete(Restaurant restaurant);

    void deleteAll();
}

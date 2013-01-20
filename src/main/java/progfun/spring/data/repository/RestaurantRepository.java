package progfun.spring.data.repository;

import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;

import java.util.List;

public interface RestaurantRepository {

    Restaurant findOne(Long id);

    Restaurant findByWebsite(Website website);

    public List<Restaurant> findAll();

    public Restaurant save(Restaurant restaurant);
}

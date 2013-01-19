package progfun.spring.data.repository;

import progfun.spring.data.domain.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant findById(Long id);

    Restaurant findByName(String name);

    public List<Restaurant> findAll();

    public Restaurant save(Restaurant restaurant);
}

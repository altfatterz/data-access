package progfun.spring.data.repository;

import progfun.spring.data.domain.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaRestaurantRepository implements RestaurantRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaRestaurantRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Restaurant findById(Long id) {
        return em.find(Restaurant.class, id);
    }

    public List<Restaurant> findAll() {
        return em.createQuery("from Restaurant", Restaurant.class).getResultList();
    }

    @Override
    public Restaurant findByName(String name) {
        TypedQuery<Restaurant> query = em.createQuery("from Restaurant where lower(name) like :name", Restaurant.class);
        query.setParameter("name", name.toLowerCase());
        return query.getSingleResult();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.getId() == null) {
            LOGGER.info("insert new restaurant");
            em.persist(restaurant);
            return restaurant;
        } else {
            LOGGER.info("update existing restaurant");
            return em.merge(restaurant);
        }
    }

}

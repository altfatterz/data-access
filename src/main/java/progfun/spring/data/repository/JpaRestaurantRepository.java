package progfun.spring.data.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;

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
    public Restaurant findOne(Long id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public Restaurant findByWebsite(Website website) {
        TypedQuery<Restaurant> query = em.createQuery("from Restaurant where website like :website", Restaurant.class);
        query.setParameter("website", website);
        return query.getSingleResult();
    }

    @Override
    public List<Restaurant> findAll() {
        return em.createQuery("from Restaurant", Restaurant.class).getResultList();
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

    @Override
    public boolean exists(Long id) {
        return findOne(id) != null;
    }

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(*) from Restaurant", Long.class);
        return query.getSingleResult();
    }

    @Override
    public void delete(Long id) {
        delete(findOne(id));
    }

    @Override
    public void delete(Restaurant restaurant) {
        em.remove(restaurant);
    }

    @Override
    public void deleteAll() {
        for(Restaurant restaurant : findAll()) {
            delete(restaurant);
        }
    }

}

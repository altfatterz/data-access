package progfun.spring.data.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import progfun.spring.data.domain.Address;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app-context.xml"})
public class JpaRestaurantRepositoryTest {

    @Autowired
    private JpaRestaurantRepository repository;

    @Test
    @Transactional
    // without @Transactional fetching the reviews throws org.hibernate.LazyInitializationException
    public void testFindById() {
        Restaurant restaurant = repository.findById(102L);
        assertThat(restaurant.getName(), is(equalTo("Cafe Olivier")));
        assertThat(restaurant.getReviews().size(), is(equalTo(2)));
    }

    @Test
    public void testFindAll() {
        List<Restaurant> restaurants = repository.findAll();
        assertThat(restaurants.size(), is(equalTo(3)));
    }

    @Test
    public void testFindByName() {
        Restaurant restaurant = repository.findByName("le connaisseur");
        assertThat(restaurant.getName(), is(equalTo("Le Connaisseur")));
    }

    @Test
    @Transactional
    // without @Transactional it is not persisted
    public void testPersist() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Ledig Erf");
        restaurant.setWebsite(new Website("http://www.ledigerf.nl"));
        Address address = new Address("Tolsteegbrug", "3", "3511 ZN", "Utrecht");
        restaurant.setAddress(address);

        repository.save(restaurant);

        assertThat(restaurant.getId(), is(not(nullValue())));
    }

}

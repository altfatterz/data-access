package progfun.spring.data.repository;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import progfun.spring.data.domain.Address;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ContextConfiguration(locations = {"classpath:app-context.xml"})
public class JpaRestaurantRepositoryTest extends AbstractTest {

    @Autowired
    private JpaRestaurantRepository repository;

    @Test
    public void testFindById() {
        Restaurant restaurant = repository.findOne(102L);
        assertThat(restaurant.getName(), is(equalTo("Cafe Olivier")));
        assertThat(restaurant.getReviews().size(), is(equalTo(2)));
    }

    @Test
    public void testFindAll() {
        List<Restaurant> restaurants = repository.findAll();
        assertThat(restaurants.size(), is(equalTo(3)));
    }

    @Test
    public void testFindResturantByWebsite() {
        Restaurant restaurant = repository.findByWebsite(new Website("http://www.leconnaisseur.nl"));
        assertThat(restaurant.getName(), is(equalTo("Le Connaisseur")));
    }

    @Test
    public void testSaveRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Ledig Erf");
        restaurant.setWebsite(new Website("http://www.ledigerf.nl"));
        Address address = new Address("Tolsteegbrug", "3", "3511 ZN", "Utrecht");
        restaurant.setAddress(address);

        repository.save(restaurant);

        assertThat(restaurant.getId(), is(not(nullValue())));
    }

}

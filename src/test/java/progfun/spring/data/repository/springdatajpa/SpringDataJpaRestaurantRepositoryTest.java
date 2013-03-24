package progfun.spring.data.repository.springdatajpa;

import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import progfun.spring.data.domain.Address;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;
import progfun.spring.data.repository.AbstractTest;
import progfun.spring.data.repository.RestaurantRepository;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.Direction.*;

@ContextConfiguration(locations = {"classpath:test-context.xml"})
@ActiveProfiles(profiles = "spring-data-jpa")
public class SpringDataJpaRestaurantRepositoryTest extends AbstractTest {

    @Autowired
    private SpringDataRestaurantRepository repository;

    @Test
    public void findById() {
        Restaurant restaurant = repository.findOne(102L);
        assertThat(restaurant.getName(), is(equalTo("Cafe Olivier")));
        assertThat(restaurant.getReviews().size(), is(equalTo(2)));
    }

    @Test
    public void findAll() {
        List<Restaurant> restaurants = repository.findAll();
        assertThat(restaurants.size(), is(equalTo(3)));
    }

    @Test
    public void findResturantByWebsite() {
        Restaurant restaurant = repository.findByWebsite(new Website("http://www.leconnaisseur.nl"));
        assertThat(restaurant.getName(), is(equalTo("Le Connaisseur")));
    }

    @Test
    public void exists() {
        assertThat(repository.exists(102L), is(equalTo(true)));
        assertThat(repository.exists(202L), is(equalTo(false)));
    }

    @Test
    public void count() throws Exception {
        assertThat(repository.count(), is(equalTo(3L)));
    }

    @Test
    public void deleteById() throws Exception {
        assertThat(repository.count(), is(equalTo(3L)));
        repository.delete(102L);
        assertThat(repository.count(), is(equalTo(2L)));
    }

    @Test
    public void delete() throws Exception {
        assertThat(repository.count(), is(equalTo(3L)));
        repository.delete(repository.findOne(102L));
        assertThat(repository.count(), is(equalTo(2L)));
    }

    @Test
    public void deleteAll() throws Exception {
        assertThat(repository.count(), is(equalTo(3L)));
        repository.deleteAll();
        assertThat(repository.count(), is(equalTo(0L)));
    }

    @Test
    public void save() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Ledig Erf");
        restaurant.setWebsite(new Website("http://www.ledigerf.nl"));
        Address address = new Address("Tolsteegbrug", "3", "3511 ZN", "Utrecht");
        restaurant.setAddress(address);

        repository.save(restaurant);

        assertThat(restaurant.getId(), is(not(nullValue())));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveIncompleteData() {
        repository.save(new Restaurant());
    }

    @Test
    public void sort() {
        List<Restaurant> restaurants = repository.findAll(new Sort(ASC, "name"));
        assertThat(restaurants.get(0).getName(), is("Cafe Olivier"));
        assertThat(restaurants.get(1).getName(), is("Le Connaisseur"));
        assertThat(restaurants.get(2).getName(), is("Pothuys"));
    }

    @Test
    public void page() {
        Page<Restaurant> firstPageOfRestaurants = repository.findAll(new PageRequest(0, 2, new Sort(ASC, "name")));
        List<Restaurant> restaurants = firstPageOfRestaurants.getContent();

        assertThat(restaurants.get(0).getName(), is("Cafe Olivier"));
        assertThat(restaurants.get(1).getName(), is("Le Connaisseur"));
        assertThat(restaurants.size(), is(2));
    }

}

package progfun.spring.data.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import progfun.spring.data.domain.Address;
import progfun.spring.data.domain.Rate;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Review;
import progfun.spring.data.domain.Website;
import progfun.spring.data.repository.JpaRestaurantRepository;

import java.util.Date;
import java.util.List;

public class JpaSample {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaSample.class);

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");

        JpaRestaurantRepository jpaRestaurantRepository = ctx.getBean("jpaRestaurantRepository", JpaRestaurantRepository.class);


        Restaurant restaurant = jpaRestaurantRepository.findById(102L);
        System.out.println(restaurant.getName());


        List<Restaurant> restaurants = jpaRestaurantRepository.findAll();
        System.out.println("Number of restaurants: " + restaurants.size());

        restaurant = jpaRestaurantRepository.findByName("Le connaisseur");
        System.out.println(restaurant.getName());

        restaurant = new Restaurant();
        restaurant.setName("Ledig Erf");
        restaurant.setWebsite(new Website("http://www.ledigerf.nl"));
        restaurant.setAddress(new Address("Oudegracht", "23", "2352 DD", "Utrecht"));
        Review review = new Review();
        review.setDescription("Good food");
        review.setCreated(new Date());
        review.setRate(Rate.VERY_GOOD);
        restaurant.addReview(review);
        jpaRestaurantRepository.save(restaurant);

        System.out.println("Number of restaurants: " + jpaRestaurantRepository.findAll().size());

    }
}

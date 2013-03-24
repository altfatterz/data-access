package progfun.spring.data.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import progfun.spring.data.domain.Address;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;
import progfun.spring.data.repository.RestaurantRepository;


/**
 * Depends on a running mysql instance. See jdbc.properties file
 */
public class Main {

    private static RestaurantRepository repository;

    // using programmatic transaction management
    private static PlatformTransactionManager transactionManager;

    private static void removeRestaurant() throws Exception {

        // removing of a restaurant cascades also to addresses and reviews tables

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Restaurant restaurant = repository.findByWebsite(new Website("http://www.ledigerf.nl"));
            repository.delete(restaurant);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        transactionManager.commit(status);
    }

    private static void searchRestaurant() {

        // interacting with repository without using a transaction
        Restaurant one = repository.findOne(101L);
        System.out.println(one);
    }

    // not sure why the version field is not incremented...
    private static void optimisticLocking() throws Exception {

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Restaurant restaurant = repository.findByWebsite(new Website("http://www.ledigerf.nl"));
            restaurant.setName("Ledig Erf Updated");

            repository.save(restaurant);

        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        transactionManager.commit(status);

    }

    private static void addRestaurant() throws Exception {

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Ledig Erf");
            restaurant.setWebsite(new Website("http://www.ledigerf.nl"));
            Address address = new Address("Tolsteegbrug", "3", "3511 ZN", "Utrecht");
            restaurant.setAddress(address);

            repository.save(restaurant);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        transactionManager.commit(status);

    }

    public static void main(String[] args) throws Exception {

        System.setProperty("spring.profiles.active", "jpa");
        ApplicationContext context = new ClassPathXmlApplicationContext("mysql-context.xml");
        repository = context.getBean("jpaRestaurantRepository", RestaurantRepository.class);
        transactionManager = context.getBean("transactionManager", PlatformTransactionManager.class);

        //searchRestaurant();

        //removeRestaurant();

        //addRestaurant();

        optimisticLocking();

    }
}

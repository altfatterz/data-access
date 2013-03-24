package progfun.spring.data.repository.jpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import progfun.spring.data.domain.Restaurant;
import progfun.spring.data.domain.Website;
import progfun.spring.data.repository.RestaurantRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@ActiveProfiles(profiles = "jpa")
public class OptimisticLockingTest {

    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test(expected = HibernateOptimisticLockingFailureException.class)
    public void locking() throws Exception {

        Assert.assertNotNull(repository);
        Assert.assertNotNull(transactionManager);

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Restaurant restaurant = repository.findByWebsite(new Website("http://www.pothuys.nl"));
            restaurant.setName("The New Pothuys");

            updateRestaurantFromAnotherTransaction();

            repository.save(restaurant);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        transactionManager.commit(status);
    }

    private void updateRestaurantFromAnotherTransaction() throws Exception {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Restaurant restaurant = repository.findByWebsite(new Website("http://www.pothuys.nl"));
            restaurant.setName("The New Pothuys Updated!");

            repository.save(restaurant);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
        transactionManager.commit(status);
    }

}

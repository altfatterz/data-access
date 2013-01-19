package progfun.spring.data.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractEntity {

    private String name;

    @Column(unique = true)
    private Website website;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "restaurant_id")
    // using @JoinColumn to create a unidirectional one-to-many mapping without a join table.
    // this is an added feature in JPA 2.0
    // when one review is removed from the restaurant, the removed review is considered an orphan.
    // if orphanRemoval is set to true, the review will be deleted when the review is removed from the order.
    private List<Review> reviews;

    public Restaurant() {
        reviews = new ArrayList<Review>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Website getWebsite() {
        return website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}

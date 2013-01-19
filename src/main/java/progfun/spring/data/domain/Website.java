package progfun.spring.data.domain;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

/**
 * A value object abstraction of a website.
 */
@Embeddable
public class Website {

    private static final String WEBSITE_REGEX = "^(http(s?):\\/\\/)(www.)?(\\w|-)+(\\.(\\w|-)+)*((\\.[a-zA-Z]{2,3})|\\.(aero|coop|info|museum|name))+(\\/)?$";
    private static final Pattern PATTERN = Pattern.compile(WEBSITE_REGEX);

    @Column(name = "website")
    private String value;

    /**
     * Creates a new {@link Website} from the given string source.
     *
     * @param website must not be {@literal null} or empty.
     */
    public Website(String website) {
        Assert.isTrue(isValid(website), "Invalid website!");
        this.value = website;
    }

    protected Website() {
    }

    /**
     * Returns whether the given {@link String} is a valid {@link Website} which means you can safely instantiate the
     * class.
     *
     * @param candidate
     * @return
     */
    public static boolean isValid(String candidate) {
        return candidate == null ? false : PATTERN.matcher(candidate).matches();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return value;
    }
}

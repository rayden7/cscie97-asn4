package cscie97.asn4.ecommerce.authentication;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Abstract parent class for {@link cscie97.asn4.ecommerce.authentication.Role} and
 * {@link cscie97.asn4.ecommerce.authentication.Permission}.  Since Entitlements can be added to
 * {@link java.util.Collections}, includes methods for overriding hashCode() and equals().  Parent class of
 * {@link cscie97.asn4.ecommerce.authentication.Permission} and {@link cscie97.asn4.ecommerce.authentication.Role}.
 *
 * @author David Killeffer &lt;rayden7@gmail.com&gt;
 * @version 1.0
 * @see cscie97.asn4.ecommerce.authentication.Item
 * @see cscie97.asn4.ecommerce.authentication.Permission
 * @see cscie97.asn4.ecommerce.authentication.Role
 */
public abstract class Entitlement extends Item implements IAuthenticationVisitable {

    /**
     * Abstract class constructor.
     *
     * @param id                  the unique authentication item ID
     * @param name                the authentication item name
     * @param description         authentication item description
     */
    public Entitlement(String id, String name, String description) {
        this.setID(id);
        this.setName(name);
        this.setDescription(description);
    }

    /**
     * No-argument class constructor.
     */
    public Entitlement() { }

    /**
     * Accepts a visitor object for the purposes of building up an inventory of items in the AuthenticationService.
     *
     * @param visitor  the visiting object used to build up the inventory
     * @return  the string representation of the current object for inclusion in a printable inventory
     */
    public String acceptVisitor(IAuthenticationVisitor visitor) {
        return visitor.visitEntitlement(this);
    }

    /**
     * Since {@link cscie97.asn4.ecommerce.authentication.Entitlement} objects may be added to collections, and also
     * since the {@link cscie97.asn4.ecommerce.authentication.IAuthenticationServiceAPI} enforces that all entitlement
     * items be unique, this method provides a way to determine if another
     * {@link cscie97.asn4.ecommerce.authentication.Entitlement} item is the same as the current one based on shared
     * properties.  Uses the Apache Commons {@link org.apache.commons.lang3.builder.EqualsBuilder} to determine if
     * the two objects are indeed equal to each other.
     *
     * @param compare  the item to compare to the current object to test for equality
     * @return  true if the objects are the same, false otherwise
     * @see <a href="http://stackoverflow.com/questions/27581/overriding-equals-and-hashcode-in-java">http://stackoverflow.com/questions/27581/overriding-equals-and-hashcode-in-java</a>
     * @see <a href="http://www.java-tutorial.ch/core-java-tutorial/equalsbuilder">http://www.java-tutorial.ch/core-java-tutorial/equalsbuilder</a>
     * @see <a href="http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/builder/EqualsBuilder.html">http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/builder/EqualsBuilder.html</a>
     */
    @Override
    public boolean equals(Object compare){
        if (compare == null)
            return false;
        if (!(compare instanceof Entitlement))
            return false;
        if (compare == this)
            return true;

        Entitlement rhs = (Entitlement) compare;
        return new EqualsBuilder()
                    .append(this.getID(), rhs.getID())
                    .append(this.getName(), rhs.getName())
                    .append(this.getDescription(), rhs.getDescription())
                    .isEquals();
    }

    /**
     * Since {@link cscie97.asn4.ecommerce.authentication.Item} objects may be added to collections, and also since
     * the {@link cscie97.asn4.ecommerce.authentication.IAuthenticationServiceAPI} enforces that all authentication
     * items be unique, this method provides a way to get the unique hash code for the current item.  Uses the Apache
     * Commons {@link org.apache.commons.lang3.builder.HashCodeBuilder} to generate a unique hash code for the current
     * item based on two randomly chosen unique prime numbers and all the object properties.
     *
     * @return  a unique integer hash code for this particular object
     * @see <a href="http://stackoverflow.com/questions/27581/overriding-equals-and-hashcode-in-java">http://stackoverflow.com/questions/27581/overriding-equals-and-hashcode-in-java</a>
     * @see <a href="http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/builder/HashCodeBuilder.html">http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/builder/HashCodeBuilder.html</a>
     * @see <a href="http://primes.utm.edu/lists/small/1000.txt">http://primes.utm.edu/lists/small/1000.txt</a>
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(829, 2617)
                    .append(this.getID())
                    .append(this.getName())
                    .append(this.getDescription())
                    .toHashCode();
    }
}
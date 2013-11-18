package cscie97.asn4.ecommerce.authentication;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dkilleffer
 * Date: 11/13/13
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class User extends Item {

    private Set<Credentials> credentials = new HashSet<Credentials>();

    private Set<Entitlement> entitlements = new HashSet<Entitlement>();

    private AccessToken token;

    /**
     * Class constructor.
     *
     * @param id           the unique user ID
     * @param name         the authentication item name
     * @param description  authentication item description
     */
    public User(String id, String name, String description) {
        this.setID(id);
        this.setName(name);
        this.setDescription(description);
    }

    /**
     * Class constructor.  Since no ID or description are specified, will generate a new GUID for the ID, and
     * also use the name as part of the description.
     *
     * @param name  the user name
     */
    public User(String name) {
        this(UUID.randomUUID().toString(), name, String.format("User account for %s", name) );
    }

    /**
     * Class constructor.  Since no description is specified, will generate a default description based on the name
     *
     * @param id    the user ID
     * @param name  the user name
     */
    public User(String id, String name) {
        this(id, name, String.format("User account for %s", name) );
    }

    public Set<Credentials> getCredentials() {
        return credentials;
    }

    public void setCredentials(Set<Credentials> credentials) {
        this.credentials = credentials;
    }

    public Set<Entitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(Set<Entitlement> entitlements) {
        this.entitlements = entitlements;
    }

    public AccessToken getAccessToken() {
        return token;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.token = accessToken;
    }

    public void addCredential(Credentials credential) {
        this.credentials.add(credential);
    }

    public void addCredential(String username, String password) {
        Credentials credential = new Credentials(username, password);
        addCredential(credential);
    }

    public void addEntitlement(Entitlement entitlement) {
        this.entitlements.add(entitlement);
    }

    public boolean hasPermission(String permissionID) {
        for (Entitlement e : getEntitlements()) {
            if (e.getID().equals(permissionID)) {
                return true;
            }
            else if (e instanceof Role) {
                RoleIterator iterator = ((Role) e).getIterator();
                while (iterator.hasNext()) {
                    Entitlement e2 = iterator.next();
                    if (e2.getID().equals(permissionID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean validatePassword(String password) {
        for (Credentials c : credentials) {
            try {
                if (PasswordHash.validatePassword(password, c.getPasswordHash()) )
                    return true;
            }
            catch (NoSuchAlgorithmException nsae) { }
            catch (InvalidKeySpecException ikse) { }
        }
        return false;
    }

    /**
     * Since {@link cscie97.asn4.ecommerce.product.Content} objects may be added to collections, and also since
     * the {@link cscie97.asn4.ecommerce.product.IProductAPI} enforces that all content items be unique, this method
     * provides a way to determine if another {@link cscie97.asn4.ecommerce.product.Content} item is the same as the
     * current one.  Uses the Apache Commons {@link org.apache.commons.lang3.builder.EqualsBuilder} to determine if
     * the two objects are indeed equal to each other.
     *
     * @param compare  the {@link cscie97.asn4.ecommerce.product.Content} item to compare to the current object to test for equality
     * @return  true if the objects are the same, false otherwise
     * @see <a href="http://stackoverflow.com/questions/27581/overriding-equals-and-hashcode-in-java">http://stackoverflow.com/questions/27581/overriding-equals-and-hashcode-in-java</a>
     * @see <a href="http://www.java-tutorial.ch/core-java-tutorial/equalsbuilder">http://www.java-tutorial.ch/core-java-tutorial/equalsbuilder</a>
     * @see <a href="http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/builder/EqualsBuilder.html">http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/builder/EqualsBuilder.html</a>
     */
    @Override
    public boolean equals(Object compare){
        if (compare == null)
            return false;
        if (!(compare instanceof User))
            return false;
        if (compare == this)
            return true;

        User rhs = (User) compare;
        return new EqualsBuilder()
                    .append(this.getID(), rhs.getID())
                    .append(this.getName(), rhs.getName())
                    .append(this.getDescription(), rhs.getDescription())
                    .isEquals();
    }

    /**
     * Since {@link cscie97.asn4.ecommerce.product.Content} objects may be added to collections, and also since
     * the {@link cscie97.asn4.ecommerce.product.IProductAPI} enforces that all content items be unique, this method
     * provides a way to get the unique hash code for the current content item.  Uses the Apache Commons
     * {@link org.apache.commons.lang3.builder.HashCodeBuilder} to generate a unique hash code for the current item
     * based on two randomly chosen unique prime numbers and all the object properties.
     *
     * @return  a unique integer hash code for this particular object
     * @see <a href="http://stackoverflow.com/questions/27581/overriding-equals-and-hashcode-in-java">http://stackoverflow.com/questions/27581/overriding-equals-and-hashcode-in-java</a>
     * @see <a href="http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/builder/HashCodeBuilder.html">http://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/builder/HashCodeBuilder.html</a>
     * @see <a href="http://primes.utm.edu/lists/small/1000.txt">http://primes.utm.edu/lists/small/1000.txt</a>
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(7867, 5653)
                    .append(this.getID())
                    .append(this.getName())
                    .append(this.getDescription())
                    .toHashCode();
    }

}
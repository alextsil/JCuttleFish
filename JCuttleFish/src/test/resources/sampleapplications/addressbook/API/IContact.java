package API;

/**
 *
 * @author Ξ“Ο�Ξ·Ξ³Ο�Ο�Ξ·Ο‚
 */
public interface IContact {

    String getAddress();

    String getCity();

    String getEmail();

    String getFirstname();

    String getId();

    String getLastname();

    String getMtelephone();

    String getTelephone();

    void setAddress(String address);

    void setCity(String city);

    void setEmail(String email);

    void setFirstname(String firstname);

    void setId(String id);

    void setLastname(String lastname);

    void setMtelephone(String mtelephone);

    void setTelephone(String telephone);
    
}

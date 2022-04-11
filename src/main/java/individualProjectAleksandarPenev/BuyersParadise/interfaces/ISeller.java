package individualProjectAleksandarPenev.BuyersParadise.interfaces;

import java.util.List;

public interface ISeller {

    public String getFirstName();
    public void setFirstName(String firstName);

    public String getLastName();
    public void setLastName(String lastName);

    public String getPhoneNumber();
    public void setPhoneNumber(String phoneNumber);

    List<IOffer> getOffers();
    void setOffers(List<IOffer> offer);

    public int getId();
    public void setId(int id);

}

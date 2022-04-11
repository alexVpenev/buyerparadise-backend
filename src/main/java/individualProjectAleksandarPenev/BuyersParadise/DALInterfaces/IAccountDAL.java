package individualProjectAleksandarPenev.BuyersParadise.DALInterfaces;

import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccount;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.ISeller;

public interface IAccountDAL {

    IAccount getAccountByUsername(String username);
    int getAccountIDByUsername(String username);
    boolean addAccount(String username, String email, String Password);
    void becomeSeller(int id, String firstName, String lastName, String phone);
    ISeller getAllSellersInfo(int id);

}

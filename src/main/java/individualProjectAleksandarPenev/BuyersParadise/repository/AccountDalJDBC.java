package individualProjectAleksandarPenev.BuyersParadise.repository;

import individualProjectAleksandarPenev.BuyersParadise.DALInterfaces.IAccountDAL;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IAccount;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.ISeller;
import individualProjectAleksandarPenev.BuyersParadise.model.Account;
import individualProjectAleksandarPenev.BuyersParadise.model.Offer;
import individualProjectAleksandarPenev.BuyersParadise.model.Seller;
import lombok.Builder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDalJDBC extends JDBCRepository implements IAccountDAL {

    @Override
    public IAccount getAccountByUsername(String username) {

        String sql = "SELECT * from account WHERE username = ?" ;
        Connection connection = this.getDatabaseConnection();
        IAccount account = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);


            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                int accountId = resultSet.getInt("ID");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");


                account = new Account(accountId, username, email, password, role);


            }




        } catch (Exception e) {System.out.println(e);}
        finally {
            closePreparedConnection(statement, connection);
        }

        return account;
    }

    @Override
    public int getAccountIDByUsername(String username) {
        String sql = "SELECT ID from account WHERE username = ?" ;
        Connection connection = this.getDatabaseConnection();
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            int accountId = resultSet.getInt("ID");

            return accountId;

        } catch (SQLException throwable) {System.out.println("Can't get account by username");}
        finally {
            closePreparedConnection(statement, connection);
        }

        return 0;
    }


    @Override
    public boolean addAccount(String username, String email, String password) {
        String sql = "INSERT INTO account (`ID`, `username`, `email`, `password`, `role`) VALUES (NULL, ?, ?, ?, 'USER');";
        Connection connection = this.getDatabaseConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);



            statement.executeUpdate();
            return true;


        } catch (SQLException throwable) {}
        finally {
            closePreparedConnection(statement, connection);
        }
        return false;
    }
    @Override
    public void becomeSeller(int id, String firstName, String lastName, String phone) {

        this.addSellerInfo(id, firstName, lastName, phone);
        this.changeRole(id);

    }


    private void addSellerInfo(int id, String firstName, String lastName, String phone) {

        String sql = "INSERT INTO seller_info (`ID`, `first_name`, `last_name`, `phone`) VALUES (?, ?, ?, ?);";
        Connection connection = this.getDatabaseConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, phone);

            statement.executeUpdate();



        } catch (SQLException throwable) {}
        finally {
            closePreparedConnection(statement, connection);
        }

    }

    private void changeRole(int id) {

        String sql = "UPDATE `account` SET `role` = 'SELLER' WHERE `account`.`ID` = ?";
        Connection connection = this.getDatabaseConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException throwable) {}
        finally {
            closePreparedConnection(statement, connection);
        }

    }

    private ISeller getEmptySeller(int id) {

        ISeller seller = null;
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT * FROM account" +
                " INNER JOIN seller_info ON seller_info.ID = account.ID" +
                " WHERE seller_info.ID = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {

                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone");

                seller = new Seller(id, username, email, "", role, firstName, lastName, phoneNumber, new ArrayList<IOffer>());

            }




        } catch (SQLException throwable) {
            System.out.println(throwable);

        } finally {
            closePreparedConnection(statement, connection);
        }

        return seller;

    }

    private List<IOffer> getAllSellersOffers(int id) {
        Connection connection = this.getDatabaseConnection();
        String sql = "SELECT offer.ID, offer.seller_id, offer.name, offer.price, offer.description, account.username from offer " +
                " INNER jOIN account ON offer.seller_id = account.ID " +
                "WHERE offer.seller_id = ?";
        List<IOffer> offers = new ArrayList<IOffer>();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {

                int offerID = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                String seller_name = resultSet.getString("username");

                IOffer offer = new Offer(offerID, id, name, price,description, seller_name);

                offers.add(offer);

            }




        } catch (SQLException throwable) {
            System.out.println("getAllSellersOffers");

        } finally {
            closePreparedConnection(statement, connection);
        }

        return offers;
    }

    @Override
    public ISeller getAllSellersInfo(int id) {
        ISeller seller = getEmptySeller(id);

        if(seller != null) {
            List<IOffer> offers = getAllSellersOffers(id);

            seller.setOffers(offers);

            return seller;
        }

        return null;

    }

    private void closePreparedConnection(PreparedStatement statement, Connection connection) {
        try{
            if(statement != null) {
                statement.close();
            }
            connection.commit();
            connection.close();
        }
        catch (SQLException throwable){
            System.out.println("Can't close connection");
        }
    }

}

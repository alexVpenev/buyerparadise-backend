package individualProjectAleksandarPenev.BuyersParadise.repository;

import individualProjectAleksandarPenev.BuyersParadise.DALInterfaces.IOfferDAL;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.model.Offer;
import individualProjectAleksandarPenev.BuyersParadise.model.request.OfferCreateRequest;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OfferDalJDBC extends JDBCRepository implements IOfferDAL {

    @Override
    public List<IOffer> getAllOffers() {
        List<IOffer> offers = new ArrayList<IOffer>();
        Connection connection = this.getDatabaseConnection();

        Statement statement = null;

        String sql = "SELECT offer.ID, offer.seller_id, offer.name, offer.price, offer.description, account.username from offer" +
                " INNER JOIN account ON offer.seller_id = account.ID";

        try {

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int seller_id = resultSet.getInt("seller_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                String seller_name = resultSet.getString("username");

                IOffer offer = new Offer(id, seller_id, name,price,description, seller_name);
                offers.add(offer);
            }


        } catch (SQLException throwable) {
            System.out.println("Ne sum swyrzan");

        } finally {
            closeConnection(statement, connection);
        }

        return offers;
    }

    @Override
    public IOffer getOfferByID(int id) {

        String sql = "SELECT offer.ID, offer.seller_id, offer.name, offer.price, offer.description, account.username from offer " +
                " INNER jOIN account ON offer.seller_id = account.ID " +
                "WHERE offer.ID = ?";
        Connection connection = this.getDatabaseConnection();
        PreparedStatement statement = null;
        IOffer offer = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            int offerID = resultSet.getInt("ID");
            int seller_id = resultSet.getInt("seller_id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            String description = resultSet.getString("description");
            String seller_name = resultSet.getString("username");

            offer = new Offer(offerID, seller_id, name, price,description, seller_name);


        } catch (SQLException throwable) {
            System.out.println("Ne sum swyrzan");

        } finally {
            closePreparedConnection(statement, connection);
        }

        return offer;
    }

    @Override
    public void CreateOffer(OfferCreateRequest offer, int id)
    {
        Connection connection = this.getDatabaseConnection();

        String sql = "INSERT INTO offer (`seller_id`, `name`, `price`, `description`) VALUES (?,?,?,?)";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, offer.getName());
            statement.setDouble(3, offer.getPrice());
            statement.setString(4, offer.getDescription());

            statement.executeUpdate();

        } catch (SQLException throwable) {throwable.toString();}

        finally {
            closePreparedConnection(statement, connection);
        }
    }

    @Override
    public int getOfferIDByInfo(int seller_id, String offerName, String description) {

        String sql = "SELECT offer.ID from offer " +
                "WHERE offer.seller_id = ? AND offer.name = ? AND offer.description = ? " +
                "ORDER BY offer.ID DESC";
        Connection connection = this.getDatabaseConnection();
        PreparedStatement statement = null;
        int offerId = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, seller_id);
            statement.setString(2, offerName);
            statement.setString(3, description);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            offerId = resultSet.getInt("ID");



        } catch (SQLException throwable) {
            System.out.println("getOfferIDByInfo");

        } finally {
            closePreparedConnection(statement, connection);
        }

        return offerId;
    }









    private boolean checkIfUserOwnsOffer(int userId, int offerID) {

        boolean check = false;

        String sql = "SELECT * FROM offer" +
                " WHERE seller_id = ? AND ID = ?";


        PreparedStatement statement = null;
        Connection connection = this.getDatabaseConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, offerID);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                check = true;
            }
        } catch (SQLException throwable) {
            System.out.println("Can't check if user owns selected offer");
        } finally {
            closePreparedConnection(statement, connection);
        }

        return check;

    }

    private void deleteOffer(int offerID) {

        String sql = "DELETE FROM offer" +
                " WHERE ID = ?";

        Connection connection = this.getDatabaseConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, offerID);

            statement.executeUpdate();

        } catch (SQLException throwable) {
            System.out.println("Cant delete offer");
        } finally {
            closePreparedConnection(statement, connection);
        }

    }

    @Override
    public void deleteOfferWithCheck(int userId, int offerID) {

        if(this.checkIfUserOwnsOffer(userId, offerID)) {
            this.deleteOffer(offerID);
        }

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

    private void closeConnection(Statement statement, Connection connection) {
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

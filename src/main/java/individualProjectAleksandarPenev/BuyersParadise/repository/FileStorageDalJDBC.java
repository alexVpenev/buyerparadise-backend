package individualProjectAleksandarPenev.BuyersParadise.repository;

import individualProjectAleksandarPenev.BuyersParadise.DALInterfaces.IFileStorageDAL;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IOffer;
import individualProjectAleksandarPenev.BuyersParadise.model.Offer;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class FileStorageDalJDBC extends JDBCRepository implements IFileStorageDAL {

    @Override
    public String getPhotoByOfferId(int id) {
        String sql = "SELECT * from offer_photos WHERE offer_id = ?";
        Connection connection = this.getDatabaseConnection();
        String path = "";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                path = resultSet.getString("path");
            }



        }
        catch (SQLException throwable) {System.out.println("Can't get photo of offer");}

        finally {
            closePreparedConnection(statement, connection);
        }

        return path;
    }

    @Override
    public void createPathLinkForOffer(String path) {

        int id = this.getLatestOfferID();

        Connection connection = this.getDatabaseConnection();

        String sql = "INSERT INTO offer_photos (`ID`, `offer_id`, `path`) VALUES (NULL ,?,?)";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, path);

            statement.executeUpdate();

        } catch (SQLException throwable) {throwable.toString();}

        finally {
            closePreparedConnection(statement, connection);
        }

    }

    private int getLatestOfferID() {
        int id = 0;
        Connection connection = this.getDatabaseConnection();

        String sql = "SELECT ID FROM offer ORDER BY ID DESC";
        Statement statement = null;

        try {

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            if (resultSet.next()) {
                id = resultSet.getInt("ID");

            }


        } catch (SQLException throwable) {
            System.out.println("Ne sum swyrzan");

        } finally {
            closeConnection(statement, connection);
        }

        return id;
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

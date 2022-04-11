package individualProjectAleksandarPenev.BuyersParadise.DALInterfaces;

public interface IFileStorageDAL {

    String getPhotoByOfferId(int id);
    void createPathLinkForOffer(String path);

}

package individualProjectAleksandarPenev.BuyersParadise.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

    ResponseEntity<Resource> returnPhotoOfOfferById(int id);
    void save(MultipartFile file);
}

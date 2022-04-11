package individualProjectAleksandarPenev.BuyersParadise.service;

import individualProjectAleksandarPenev.BuyersParadise.DALInterfaces.IFileStorageDAL;
import individualProjectAleksandarPenev.BuyersParadise.interfaces.IFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService implements IFileStorageService {

    private final Path root = Paths.get("photos");

    @Autowired
    IFileStorageDAL dal;

    @Override
    public ResponseEntity<Resource> returnPhotoOfOfferById(int id)
    {
        String filename =  dal.getPhotoByOfferId(id);

        if(filename.equals("")) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource inputStream = null;
        try{
            String directory = new File("./" ).getCanonicalPath() + "/photos/" + filename;

            inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                    directory)));
        }
        catch (Exception e){}


        if(inputStream != null) {
            return ResponseEntity.ok()
                    .contentLength(inputStream.contentLength())
                    .body(inputStream);
        }else {
            return ResponseEntity.notFound().build();
        }


    }

    @Override
    public void save(MultipartFile file) {
        try {
            System.out.println(file.getOriginalFilename());
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            dal.createPathLinkForOffer(file.getOriginalFilename());
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

}

package individualProjectAleksandarPenev.BuyersParadise.controller;

import individualProjectAleksandarPenev.BuyersParadise.interfaces.IFileStorageService;
import individualProjectAleksandarPenev.BuyersParadise.model.request.CreateEntryPathRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/file")
public class FileStorageController {

    @Autowired
    IFileStorageService fileStorageService;

    @GetMapping("/offer/{id}")
    public ResponseEntity<Resource> getOfferPhotoById(@PathVariable(value = "id") int id)
    {
        return fileStorageService.returnPhotoOfOfferById(id);
    }

    @PostMapping("/upload/photo")
    public ResponseEntity UploadPhoto(@RequestParam("file") MultipartFile file)
    {
        try{
            fileStorageService.save(file);
            return ResponseEntity.ok().body("File uploaded");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }

    }

}

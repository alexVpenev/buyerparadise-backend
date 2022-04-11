package individualProjectAleksandarPenev.BuyersParadise.FileStorageTests;

import individualProjectAleksandarPenev.BuyersParadise.repository.FileStorageDalJDBC;
import individualProjectAleksandarPenev.BuyersParadise.service.FileStorageService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStorageServiceUnitTest {

    @Autowired
    FileStorageService fileStorageService;

    @MockBean
    FileStorageDalJDBC dal;



}

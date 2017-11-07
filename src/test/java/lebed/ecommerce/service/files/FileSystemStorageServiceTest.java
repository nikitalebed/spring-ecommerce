package lebed.ecommerce.service.files;

import org.junit.After;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Objects;

import static org.junit.Assert.*;

public class FileSystemStorageServiceTest {

    private static final String UPLOADS = "uploads";
    private FileSystemStorageService fileSystemStorageService = new FileSystemStorageService();

    @Test
    public void storeShouldReturnNonNullResult() {
        String result = fileSystemStorageService.store(
                new MockMultipartFile("FileName", new byte[]{1, 2, 3, 4, 5, 6, 7, 8}), UPLOADS);
        assertTrue(Objects.nonNull(result));

    }

    @After
    public void tearDown() {
        fileSystemStorageService.deleteAll();
    }


}
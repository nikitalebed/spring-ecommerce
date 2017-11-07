package lebed.ecommerce.service.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private static final String UPLOADS_PATH = "uploads";
    private static final String COULD_NOT_READ_FILE = "Could not read file: ";
    private static final String COULD_NOT_INITIALIZE_STORAGE = "Could not initialize storage";
    private static final Integer BOUND = 100000;

    private Path rootLocation;

    @Autowired
    public FileSystemStorageService() {
        this.rootLocation = Paths.get(UPLOADS_PATH);
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException(COULD_NOT_INITIALIZE_STORAGE, e);
        }
    }

    @Override
    public String store(MultipartFile file, String path) {
        if (file.isEmpty()) {
            throw new StorageException("File is empty: " + file.getOriginalFilename());
        }
        try {
            final Path location = Paths.get(rootLocation.toString() + "/" + path);
            if (!Files.isDirectory(location)) {
                Files.createDirectories(location);
            }
            String filename = file.getOriginalFilename();
            filename = new Date().getTime()
                    + "-" + (BOUND + new Random().nextInt(BOUND))
                    + "-" + filename;
            Files.copy(file.getInputStream(), location.resolve(filename));
            return filename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(path -> rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            throw new StorageException(COULD_NOT_READ_FILE + filename);

        } catch (MalformedURLException e) {
            throw new StorageException(COULD_NOT_READ_FILE + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}

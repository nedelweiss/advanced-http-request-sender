package com.github.nedelweiss.multipart;

import com.github.nedelweiss.multipart.multipart_file_types.ConstantsHolder;
import com.github.nedelweiss.multipart.multipart_file_types.CustomMultipartFile;
import com.github.nedelweiss.multipart.multipart_file_types.MultipartFileRecord;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

@Component
public class ImageRequestSender {

    private static final String TEMP_DIR_NAME = "temp_dir";

    private final Path tempDirectoryPath;
    private final HttpClient httpClient;

    public ImageRequestSender(HttpClient httpClient) throws IOException {
        this.tempDirectoryPath = Files.createDirectory(Paths.get(TEMP_DIR_NAME));
        this.httpClient = httpClient;
    }

    @PreDestroy
    public void removeTempDirectory() {
        try(Stream<Path> paths = Files.walk(Paths.get(TEMP_DIR_NAME)).sorted(Comparator.reverseOrder())) {
            paths.forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    // TODO: handle exception, add logger
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            // TODO: handle exception, add logger
        }
    }

    public void send(MultipartFile image) {
        try {
            MultipartFileRecord mpr = new MultipartFileRecord(
                ConstantsHolder.FILE_KEY,
                saveImageToDir(image),
                image.getContentType(),
                image.getOriginalFilename()
            );

            CustomMultipartFile customMpr = new Builder().addPart(mpr).build();

            byte[] bodyAsBytes = customMpr.getBytes();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", customMpr.getContentType())
                .uri(URI.create("http://localhost:5000/convert_image"))
                .POST(HttpRequest.BodyPublishers.ofByteArray(bodyAsBytes))
                .build();

            // TODO: handle response
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            // TODO: handle exception, add logger
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO: add logger
            Thread.currentThread().interrupt();
        }
    }

    private File saveImageToDir(MultipartFile image) throws IOException {
        Path root = tempDirectoryPath.toAbsolutePath().normalize();
        String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());
        File file = new File(root + "/" + "saved" + "." + extension);
        ImageIO.write(ImageIO.read(image.getInputStream()), extension, file);
        return file;
    }
}

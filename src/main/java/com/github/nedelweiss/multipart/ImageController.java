package com.github.nedelweiss.multipart;

import com.github.nedelweiss.multipart.multipart_file_types.ConstantsHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class ImageController {

    private final ImageRequestSender imageRequestSender;

    public ImageController(ImageRequestSender imageRequestSender) {
        this.imageRequestSender = imageRequestSender;
    }

    @PostMapping("/convert")
    public void convert(@RequestParam(ConstantsHolder.FILE_KEY) MultipartFile image) {
        imageRequestSender.send(image);
    }
}

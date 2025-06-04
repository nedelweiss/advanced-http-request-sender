package com.github.nedelweiss.multipart.multipart_file_types;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MultipartFileRecord extends MultipartRecord {

    public MultipartFileRecord(String fieldName, Object content, String contentType, String filename) {
        super(fieldName, content, contentType, filename);
    }

    @Override
    protected void buildUniquePart(ByteArrayOutputStream out) throws IOException {
        byte[] bytes = Files.readAllBytes(((File) content).getAbsoluteFile().toPath());

        writeToOutputStream(out, "Content-Type: application/octet-stream\r\n\r\n");
        out.write(bytes);
    }
}

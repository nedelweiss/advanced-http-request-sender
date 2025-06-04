package com.github.nedelweiss.multipart;

import com.github.nedelweiss.multipart.multipart_file_types.CustomMultipartFile;
import com.github.nedelweiss.multipart.multipart_file_types.MultipartRecord;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Builder {

    private final String boundary = new BigInteger(256, new SecureRandom()).toString();
    private final List<MultipartRecord> parts = new ArrayList<>();

    public Builder addPart(MultipartRecord multipartRecord) {
        this.parts.add(multipartRecord);
        return this;
    }

    public String getContentType() {
        return "multipart/form-data; boundary=" + this.boundary;
    }

    public CustomMultipartFile build() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            for (MultipartRecord part : parts) {
                part.buildPart(boundary, out);
            }

            out.write(("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));

            return new CustomMultipartFile(getContentType(), out.toByteArray());
        } catch (Exception e) {
            // TODO: handle exception, add logger
            throw new RuntimeException();
        }
    }
}

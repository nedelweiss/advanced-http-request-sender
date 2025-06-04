package com.github.nedelweiss.multipart.multipart_file_types;

public class CustomMultipartFile {

    private final String contentType;
    private final byte[] bytes; // body

    public CustomMultipartFile(String contentType, byte[] bytes) {
        this.contentType = contentType;
        this.bytes = bytes;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getBytes() {
        return bytes;
    }
}

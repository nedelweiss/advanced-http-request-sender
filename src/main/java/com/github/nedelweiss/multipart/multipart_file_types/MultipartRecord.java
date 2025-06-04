package com.github.nedelweiss.multipart.multipart_file_types;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class MultipartRecord {

    protected final Object content;
    protected final String fieldName;
    protected String filename = null;
    protected String contentType = null;

    public MultipartRecord(String fieldName, Object content) {
        this.fieldName = fieldName;
        this.content = content;
    }

    public MultipartRecord(String fieldName, String contentType, Object content) {
        this.fieldName = fieldName;
        this.contentType = contentType;
        this.content = content;
    }

    public MultipartRecord(String fieldName, Object content, String contentType, String filename) {
        this.fieldName = fieldName;
        this.filename = filename;
        this.contentType = contentType;
        this.content = content;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getContent() {
        return content;
    }

    public String getFilename() {
        return filename;
    }

    public void buildPart(String boundary, ByteArrayOutputStream out) throws IOException {
        writeToOutputStream(out, "--");
        writeToOutputStream(out, boundary);
        writeToOutputStream(out, "\r\n");
        writeToOutputStream(out, "Content-Disposition: form-data; name=\"");
        writeToOutputStream(out, this.getFieldName());

        if (this.getFilename() != null) {
            writeToOutputStream(out, "\"; filename=\"");
            writeToOutputStream(out, this.getFilename());
        }

        writeToOutputStream(out, "\"\r\n");
        this.buildUniquePart(out);
        writeToOutputStream(out, "\r\n");
    }

    protected void writeToOutputStream(ByteArrayOutputStream out, String part) throws IOException {
        out.write(part.getBytes(StandardCharsets.UTF_8));
    }

    protected abstract void buildUniquePart(ByteArrayOutputStream out) throws IOException;
}

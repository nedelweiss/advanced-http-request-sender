## Short notes
______________________________________________

### Multipart body
For an HTTP request that uses a multipart body, especially for file uploads, the Content-Type header is set to multipart/form-data. Additionally, each part of the multipart body (e.g., each file, each form field) needs its own headers, including a Content-Disposition header with a name parameter to identify the field.
Here's a more detailed breakdown:
1. _**Content-Type: multipart/form-data:**_
   This header indicates that the request body is structured as a multipart message.
   It's commonly used when sending file uploads along with other form data.
   The multipart/form-data content type is used for HTML forms that use the enctype="multipart/form-data" attribute.
2. **_Content-Disposition header for each part:_**
   Content-Disposition: form-data; name="field_name":This header identifies each individual part within the multipart body.
   name="field_name": This parameter specifies the name of the form field or the file field associated with the part.
   If the part is a file, you can also include a filename parameter to specify the filename: Content-Disposition: form-data; name="file_field"; filename="actual_filename".
   RFC 2183 specifies the format and usage of the Content-Disposition header.
3. **_Other relevant headers for each part:_**
   Content-Type: application/octet-stream or Content-Type: text/plain:
   This header defines the content type of each part. For binary data like files, application/octet-stream is a common default. If you know the specific file type (e.g., application/pdf, image/jpeg), you should use that.
4. **_Content-Transfer-Encoding: binary_**:
   This header is optional but often used for files to indicate that the data is binary
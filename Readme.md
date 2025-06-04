## About project

--------------------------------------------------

```java
            // not full request
            /** can't build correct request just using this way,
             *  but created multipart body manually
             */
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("http://localhost:5000/convert_image"))
//                    .header("Content-Type", "multipart/form-data")
//                    .header("Accept-Encoding", "gzip, deflate, br")
//                    .header("Content-Transfer-Encoding", "binary")
//                    .header("Content-Disposition", "form-data; name=\"image\"; filename=\"cat.png\"") // need to build it manually
//                    .header("Content-Length", "4883860") // restricted
//                    .POST(HttpRequest.BodyPublishers.ofFile(Path.of(pathname)))
//                    .build();
//            httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
```
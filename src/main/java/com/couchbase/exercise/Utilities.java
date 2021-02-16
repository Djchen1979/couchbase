package com.couchbase.exercise;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.MutationResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Utilities {
    private Cluster cluster;
    private Bucket bucket;
    private Collection collection;
    private String configFile = "config.json";

    public static void main(String... args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter file or folder name: ");
        String fileName = scanner.nextLine();

        System.out.println("Enter optional regex pattern: ");
        String pattern;
        pattern = scanner.nextLine();

        Utilities loadDocument = new Utilities();
        loadDocument.loadDocument(fileName, pattern);
    }

    public void loadDocument(String fileName, String pattern) throws IOException {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            System.out.println(fileName + " does not exist!");
            return;
        }
        if(!Files.isDirectory(filePath)) {
            loadDocument(fileName);
            return;
        }
        Files.list(filePath).parallel().forEach(path -> {
            try {
                String name = path.toFile().getAbsolutePath();
                if (pattern == null || pattern.equals("")) {
                    loadDocument(name);
                } else if (name.matches(pattern)) {
                    loadDocument(name);
                }
            } catch (IOException e) {
                // print error stracktrace to allow following document upload
                e.printStackTrace();
            }
        });
    }

    public void loadDocument(String fileName) throws IOException {
        Path filePath = Paths.get(fileName).toAbsolutePath();
        if (!Files.exists(filePath)) {
            System.out.println(fileName + " does not exist!");
            return;
        }
        String content = new String(Files.readAllBytes(filePath.toAbsolutePath()));
        String docID = "docid-" + System.currentTimeMillis();
        MutationResult result = collection.upsert(docID, content);
        System.out.println(result.toString());
        return;
    }

    public Utilities() throws IOException {
        InputStream input = getFileFromResourceAsStream(configFile);
        String configJson = convertInputStreamToString(input);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson  = gsonBuilder.create();
        Config config = gson.fromJson(configJson, Config.class);

        cluster = Cluster.connect(config.getConnection(), config.getUsername(), config.getPassword());
        bucket = cluster.bucket(config.getBucket());
        collection = bucket.defaultCollection();
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
}

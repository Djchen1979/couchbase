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

public class Utilities {
    private Cluster cluster;
    private Bucket bucket;
    private Collection collection;

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    private String configFile = "config.json";

    public static void main(String... args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter file or folder name: ");
        String fileName = scanner.nextLine();

        System.out.println("Enter optional regex pattern: ");
        String pattern;
        pattern = scanner.nextLine();

        Utilities utils = new Utilities();
        utils.loadDocument(fileName, pattern);
    }

    public void doLoad(Runnable toLoad) {
        toLoad.run();
    }

    public void loadEmployee(String fileName) {
        Path filePath = Paths.get(fileName).toAbsolutePath();
        if (!Files.exists(filePath)) {
            System.out.println(fileName + " does not exist!");
            return;
        }
        String content = null;
        try {
            content = new String(Files.readAllBytes(filePath.toAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson  = gsonBuilder.create();
        Employee employee = gson.fromJson(content, Employee.class);

        String docID = "docid-" + System.currentTimeMillis();
        MutationResult result = collection.upsert(docID, employee);
        System.out.println(result.toString());
        return;
    }

    public void loadDocument(String fileName, String pattern) throws IOException {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            System.out.println(fileName + " does not exist!");
            return;
        }
        if(!Files.isDirectory(filePath)) {
            //TODO
            doLoad(() -> loadEmployee(filePath.toFile().getAbsolutePath()));
            return;
        }
        Files.list(filePath).parallel().forEach(path -> {
            String name = path.toFile().getAbsolutePath();
            if (pattern == null || pattern.equals("") || name.matches(pattern)) {
                //TODO
                doLoad(() -> loadEmployee(name));
            }
        });
    }

    public void loadDocument(String fileName) {
        Path filePath = Paths.get(fileName).toAbsolutePath();
        if (!Files.exists(filePath)) {
            System.out.println(fileName + " does not exist!");
            return;
        }
        String content = null;
        try {
            content = new String(Files.readAllBytes(filePath.toAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

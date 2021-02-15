package com.couchbase.exercise;

import com.google.gson.annotations.Expose;

/**
 *   "connection" : "127.0.0.1",
 *   "username" : "Administrator",
 *   "password" : "passw0rd",
 *   "bucket" : "default"
 */
public class Config {
    public String getConnection() {
        return connection;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    @Expose
    private String connection;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String bucket;
}

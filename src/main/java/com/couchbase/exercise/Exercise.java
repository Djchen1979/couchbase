package com.couchbase.exercise;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.manager.bucket.BucketManager;
import com.couchbase.client.java.query.QueryResult;

public class Exercise {
    public static void main(String... args) {
        Cluster cluster = Cluster.connect("127.0.0.1", "Administrator", "passw0rd");
        Bucket bucket = cluster.bucket("default");
        Collection collection = bucket.defaultCollection();
        String content = "{" +
                            "\"firstname\":" + "\"DJ\"" +
                            "\"lastname\":" + "\"Chen\"" +
                         "}";

        MutationResult upsertResult = collection.upsert(
                "djchen-document",
                content
                //JsonObject.create().put("name", "djchen")
        );

        GetResult getResult = collection.get("dj-document");
        String name = getResult.contentAsObject().getString("name");
        System.out.println(name);
        QueryResult result = cluster.query("select \"Hello World\" as greeting");
        System.out.println(result.rowsAsObject());

        /*
        MutationResult insertResult = collection.insert(
                "test-document",
                JsonObject.create().put("type", "testing")
        );

        getResult = collection.get("test-document");
        name = getResult.contentAsObject().getString("type");
        System.out.println(name);
        */
        System.out.println();
    }
}

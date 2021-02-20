package com.couchbase.exercise;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *     {
 *       "#docs": 3,
 *       "$schema": "http://json-schema.org/draft-06/schema",
 *       "Flavor": "`state` = \"CA\"",
 *       "properties": {
 *         "city": {
 *           "#docs": 3,
 *           "%docs": 100,
 *           "samples": [
 *             "Los Angeles",
 *             "San Francisco",
 *             "San Jose"
 *           ],
 *           "type": "string"
 *         },
 *         "firstName": {
 *           "#docs": 3,
 *           "%docs": 100,
 *           "samples": [
 *             "John",
 *             "Johnny",
 *             "Robert"
 *           ],
 *           "type": "string"
 *         },
 *         "id": {
 *           "#docs": 3,
 *           "%docs": 100,
 *           "samples": [
 *             "1",
 *             "2",
 *             "3"
 *           ],
 *           "type": "string"
 *         },
 *         "lastName": {
 *           "#docs": 3,
 *           "%docs": 100,
 *           "samples": [
 *             "John",
 *             "Smith",
 *             "Steveson"
 *           ],
 *           "type": "string"
 *         },
 *         "state": {
 *           "#docs": 3,
 *           "%docs": 100,
 *           "samples": [
 *             "CA"
 *           ],
 *           "type": "string"
 *         }
 *       },
 *       "type": "object"
 *     }
 */
public class Employee {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}

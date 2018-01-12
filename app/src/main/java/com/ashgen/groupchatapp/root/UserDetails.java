package com.ashgen.groupchatapp.root;

/**
 * Created by malavan on 13/01/18.
 */

public class UserDetails {

    private static UserDetails   userDetails;
    private String name;
    private String uniqueID;
    private String color;

    public UserDetails(String name, String uniqueID, String color) {
        this.name = name;
        this.uniqueID = uniqueID;
        this.color = color;
    }

    public static UserDetails getUserDetails() {
        return userDetails;
    }

    public static void setUserDetails(UserDetails userDetails) {
        UserDetails.userDetails = userDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

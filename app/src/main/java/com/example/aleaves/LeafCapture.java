package com.example.aleaves;

import android.location.Location;
import android.media.Image;

import java.sql.Timestamp;

public class LeafCapture {//Stand-in for leaf database? Time to research mobile databases.

    private User contributingUser;
    private Image leafImage;
    private Timestamp timestamp;
    private Location location;

    public User getContributingUser() {
        return contributingUser;
    }

    public void setContributingUser(User contributingUser) {
        this.contributingUser = contributingUser;
    }

    public Image getLeafImage() {
        return leafImage;
    }

    public void setLeafImage(Image leafImage) {
        this.leafImage = leafImage;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}

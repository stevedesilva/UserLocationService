package com.silvade.geolocation.location.model;

public class Location {

    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Location from(User user) {
        return new Location(user.getLatitude(), user.getLongitude());
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }

    public String toString() {
        return latitude + "::" + longitude;
    }

}

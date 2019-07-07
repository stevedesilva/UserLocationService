package com.silvade.geolocation.location.model;

public class DistanceCalculator {

    static final double EARTH_RADIUS_IN_MILES = 3958.8d;

    private final Location from;
    private final Location to;

    public DistanceCalculator(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    public double computeInMiles() {
        double fromLatitude = Math.toRadians(from.latitude());
        double fromLongitude = Math.toRadians(from.longitude());
        double toLatitude = Math.toRadians(to.latitude());
        double toLongitude = Math.toRadians(to.longitude());

        double dlatitude = toLatitude - fromLatitude;
        double dlongitude = toLongitude - fromLongitude;

        double a = Math.pow(Math.sin(dlatitude / 2), 2)
                + Math.cos(fromLatitude) * Math.cos(toLatitude)
                * Math.pow(Math.sin(dlongitude / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return c * EARTH_RADIUS_IN_MILES;
    }

}

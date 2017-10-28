package com.example.hashhacks;

/**
 * Created by Achintya on 10/27/2017.
 */

public class Coordinate {
    public Double lat;
    public Double lan;

    Coordinate(){
        lat=lan=0.0;
    }

    Coordinate(double lat, double lan){
        this.lan=lan;
        this.lat=lat;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLan() {
        return lan;
    }

    public void setLan(Double lan) {
        this.lan = lan;
    }
}

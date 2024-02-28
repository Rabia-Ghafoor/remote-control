package edu.iu.habahram.remotecontroller.model;

public class Stereo {

    String location = "";

    public Stereo(String location) {
        this.location = location;
    }

    public String on() {
        return location + " stereo is on";
    }

    public String off() {
        return location + " stereo is off";
    }
}
package com.example.asteroidproject;

import org.json.JSONArray;

public class Asteroide {

    int id, user_id;
    String neo_reference_id, name, nasa_jpl_url;
    Double absolute_magnitude, estimated_diameter_min, estimated_diameter_max, miss_distance, velocity;
    boolean hazardous;

    public Asteroide() {
    }

    public Asteroide(int id, int user_id, String neo_reference_id, String name, String nasa_jpl_url, Double absolute_magnitude, Double estimated_diameter_min, Double estimated_diameter_max, Double miss_distance, Double velocity, boolean hazardous) {
        this.id = id;
        this.user_id = user_id;
        this.neo_reference_id = neo_reference_id;
        this.name = name;
        this.nasa_jpl_url = nasa_jpl_url;
        this.absolute_magnitude = absolute_magnitude;
        this.estimated_diameter_min = estimated_diameter_min;
        this.estimated_diameter_max = estimated_diameter_max;
        this.miss_distance = miss_distance;
        this.velocity = velocity;
        this.hazardous = hazardous;
    }

    @Override
    public String toString() {
        return "Asteroide{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", neo_reference_id='" + neo_reference_id + '\'' +
                ", name='" + name + '\'' +
                ", nasa_jpl_url='" + nasa_jpl_url + '\'' +
                ", absolute_magnitude=" + absolute_magnitude +
                ", estimated_diameter_min=" + estimated_diameter_min +
                ", estimated_diameter_max=" + estimated_diameter_max +
                ", miss_distance=" + miss_distance +
                ", velocity=" + velocity +
                ", hazardous=" + hazardous +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNeo_reference_id() {
        return neo_reference_id;
    }

    public void setNeo_reference_id(String neo_reference_id) {
        this.neo_reference_id = neo_reference_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNasa_jpl_url() {
        return nasa_jpl_url;
    }

    public void setNasa_jpl_url(String nasa_jpl_url) {
        this.nasa_jpl_url = nasa_jpl_url;
    }

    public Double getAbsolute_magnitude() {
        return absolute_magnitude;
    }

    public void setAbsolute_magnitude(Double absolute_magnitude) {
        this.absolute_magnitude = absolute_magnitude;
    }

    public Double getEstimated_diameter_min() {
        return estimated_diameter_min;
    }

    public void setEstimated_diameter_min(Double estimated_diameter_min) {
        this.estimated_diameter_min = estimated_diameter_min;
    }

    public Double getEstimated_diameter_max() {
        return estimated_diameter_max;
    }

    public void setEstimated_diameter_max(Double estimated_diameter_max) {
        this.estimated_diameter_max = estimated_diameter_max;
    }

    public Double getMiss_distance() {
        return miss_distance;
    }

    public void setMiss_distance(Double miss_distance) {
        this.miss_distance = miss_distance;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public boolean isHazardous() {
        return hazardous;
    }

    public void setHazardous(boolean hazardous) {
        this.hazardous = hazardous;
    }
}
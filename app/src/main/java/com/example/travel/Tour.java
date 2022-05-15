package com.example.travel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Tour implements Serializable {
    private String tourId;
    private String namePlace;
    private String Descriptive;
    private String Locate;
    private int price;
    private String imageURL;


    public Tour() {
    }

    public Tour(String namePlace, String descriptive, String locate, int price, String imageURL) {
        this.namePlace = namePlace;
        this.Descriptive = descriptive;
        this.Locate = locate;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getDescriptive() {
        return Descriptive;
    }

    public void setDescriptive(String descriptive) {
        Descriptive = descriptive;
    }

    public String getLocate() {
        return Locate;
    }

    public void setLocate(String locate) {
        Locate = locate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("namePlace",namePlace);
        result.put("Descriptive",Descriptive);
        result.put("Locate",Locate);
        result.put("price",price);
        return result;
    }
}

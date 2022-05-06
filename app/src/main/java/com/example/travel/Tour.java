package com.example.travel;

public class Tour {
    public String namePlace;
    public String Descriptive;
    public String Locate;
    public int price;
    private int imageURL;

    public Tour() {
    }

    public Tour(String namePlace, String descriptive, String locate, int price, int imageURL) {
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

    public int getImageURL() {
        return imageURL;
    }

    public void setImageURL(int imageURL) {
        this.imageURL = imageURL;
    }
}

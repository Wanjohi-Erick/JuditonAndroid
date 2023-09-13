package com.rickieyInnovates.juditon.models.animals;

public class Category {
    int id, farm;

    String category;

    public Category(int id, int farm, String category) {
        this.id = id;
        this.farm = farm;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

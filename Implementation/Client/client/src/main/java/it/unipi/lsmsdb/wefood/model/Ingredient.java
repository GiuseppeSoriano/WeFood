package it.unipi.lsmsdb.wefood.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {

    private String name;
    private Double calories;

    public Ingredient(@JsonProperty("name") String name,
                      @JsonProperty("calories") Double calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

}

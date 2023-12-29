package it.unipi.lsmsdb.wefood.model;

import java.util.List;
import java.util.Map;

public class Recipe {
    private String name;
    private String image;
    private List<String> steps;
    private Map<Ingredient, Double> ingredients;

    public Recipe(String name, String image, List<String> steps, Map<Ingredient, Double> ingredients) {
        this.name = name;
        this.image = image;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public Map<Ingredient, Double> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, Double> ingredients) {
        this.ingredients = ingredients;
    }
}

package it.unipi.lsmsdb.wefood.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Recipe {
    
    private String name;
    private String image;
    private List<String> steps;
    private Map<String, Double> ingredients;

    private Double totalCalories;

    public Recipe(@JsonProperty("name") String name,
                  @JsonProperty("image") String image,
                  @JsonProperty("steps") List<String> steps,
                  @JsonProperty("ingredients") Map<String, Double> ingredients,
                  @JsonProperty("totalCalories") Double totalCalories) {
        this.name = name;
        this.image = image;
        this.steps = steps;
        this.ingredients = ingredients;
        this.totalCalories = totalCalories;
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

    public String getStepsString() {
        String str = "[";
        for(int i=0; i<steps.size(); i++){
            str += "'" + steps.get(i) + "'";
            if(i != steps.size()-1){
                str += ", ";
            }
        }
        str += "]";
        return str;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public Map<String, Double> getIngredients() {
        return ingredients;
    }

    public String getIngredientsString() {
        String str = "[";
        int i = 0;
        for (Map.Entry<String, Double> entry : ingredients.entrySet()) {
            String ingredient = entry.getKey();
            Double quantity = entry.getValue();
            str += "{name: '" + ingredient + "', quantity: " + quantity + "}";
            if(i != ingredients.size()-1){
                str += ", ";
            }
            i++;
        }
        str += "]";
        return str;
    }

    public void setIngredients(Map<String, Double> ingredients) {
        this.ingredients = ingredients;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }

}

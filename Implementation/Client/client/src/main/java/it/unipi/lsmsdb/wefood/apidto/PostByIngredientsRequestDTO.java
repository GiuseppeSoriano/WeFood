package it.unipi.lsmsdb.wefood.apidto;

import java.util.List;

public class PostByIngredientsRequestDTO {
    List<String> ingredientNames;
    Long hours;
    Integer limit;

    public PostByIngredientsRequestDTO(List<String> ingredientNames, Long hours, Integer limit) {
        this.ingredientNames = ingredientNames;
        this.hours = hours;
        this.limit = limit;
    }

    public List<String> getIngredientNames() {
        return ingredientNames;
    }

    public void setIngredientNames(List<String> ingredientNames) {
        this.ingredientNames = ingredientNames;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}

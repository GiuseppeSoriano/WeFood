package it.unipi.lsmsdb.wefood.apidto;

public class IngredientAndLimitRequestDTO {
    private String ingredientName;
    private int limit;


    public IngredientAndLimitRequestDTO(String ingredientName, int limit) {
        this.ingredientName = ingredientName;
        this.limit = limit;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

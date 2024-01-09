package it.unipi.lsmsdb.wefood.apidto;

public class PostByCaloriesRequestDTO {
    Double minCalories;
    Double maxCalories;
    Long hours;
    Integer limit;

    public PostByCaloriesRequestDTO(Double minCalories, Double maxCalories, Long hours, Integer limit) {
        this.minCalories = minCalories;
        this.maxCalories = maxCalories;
        this.hours = hours;
        this.limit = limit;
    }

    public Double getMinCalories() {
        return minCalories;
    }

    public void setMinCalories(Double minCalories) {
        this.minCalories = minCalories;
    }

    public Double getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(Double maxCalories) {
        this.maxCalories = maxCalories;
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

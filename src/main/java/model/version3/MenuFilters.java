package model.version3;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class MenuFilters {

    @Schema (description = "the type of cuisine", example ="french" , required=true)
    private  String cuisineType ;

    @Schema (description = "the presence or absence of alcohol in the drink" , example="true")
    private String alcohol ;

    @Schema(description = "the list of health labels" , example = "[\"pork-free\",\"vegetarian\"]")
    private List<String> healthLabels ;

    @Schema(description = " an ingredient should be present in the drink",example = "lemon")
    private String ingredient ;

    @Schema(description = "maximum time in minutes to prepare the menu excluding the drink",example = "60")
    private int totalTime;

    public MenuFilters() {
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public List<String> getHealthLabels() {
        return healthLabels;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}

package model.version2;


import model.version2.DetailedIngredient;

import java.util.List;

public class DrinkSuccess {
    private final boolean success = true;
    private String name;
    private String type;
    private boolean alcoholic;
    private String imageURL ;
    private List<String> ingredients;
    private List<DetailedIngredient> detailedIngredients;
    private String instructions;

    public DrinkSuccess() {
    }

    public boolean isSuccess() {
        return success;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        this.alcoholic = alcoholic;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<DetailedIngredient> getDetailedIngredients() {
        return detailedIngredients;
    }

    public void setDetailedIngredients(List<DetailedIngredient> detailedIngredients) {
        this.detailedIngredients = detailedIngredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "DrinkSuccess{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", alcoholic=" + alcoholic +
                ", imageURL='" + imageURL + '\'' +
                ", ingredients=" + ingredients +
                ", detailedIngredients=" + detailedIngredients +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}

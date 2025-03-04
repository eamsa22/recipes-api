package model.version2;


import java.util.List;

public class MealSuccess extends Meal {

    /**
     * the fields representing a meal recipe
     */
    //private final boolean success = true;
    private String name;
    private String type;
    private String country;
    private String preparationTime;
    private String imageURL;
    private String source;
    private String ingredients;
    private List<DetailedIngredient> detailedIngredients;
    private List<String> instructions;

    public MealSuccess() {
        super(true);
    }

    /**
     * getter and setters over the fields of a meal recipe
     */


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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public List<DetailedIngredient> getDetailedIngredients() {
        return detailedIngredients;
    }

    public void setDetailedIngredients(List<DetailedIngredient> detailedIngredients) {
        this.detailedIngredients = detailedIngredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "MealSuccess{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", preparationTime='" + preparationTime + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", source='" + source + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", detailedIngredients=" + detailedIngredients +
                ", instructions=" + instructions +
                '}';
    }

}

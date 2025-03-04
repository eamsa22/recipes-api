package model.version1.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


@XmlRootElement(name = "Recipe")
@XmlType(propOrder = {
        "recipeName",
        "recipeDescription",
        "image",
        "recipeSource",
        "calories",
        "totalTime",
        "ingredientLines",
        "ingredientsDetails",
        "healthLabels"
})
public class Meal {
    @Schema(description = "Name of the recipe")
    private String recipeName;

    @Schema(description = "Recipe description")
    private RecipeDescription recipeDescription;

    @Schema(description = "Image URL of the recipe")
    private String image;

    @Schema(description = "Recipe source")
    private RecipeSource recipeSource;

    @Schema(description = "Calories of the recipe")
    private String calories;

    @Schema(description = "Total time to prepare the recipe")
    private String totalTime;

    @Schema(description = "List of ingredient lines")
    private IngredientLines ingredientLines;

    @Schema(description = "Details of ingredients")
    private IngredientsDetails ingredientsDetails;

    @Schema(description = "Health labels")
    private HealthLabels healthLabels;

    @XmlElement(name = "RecipeName")
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    @XmlElement(name = "RecipeDescription")
    public RecipeDescription getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(RecipeDescription recipeDescription) {
        this.recipeDescription = recipeDescription;
    }
    @XmlElement(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @XmlElement(name = "RecipeSource")
    public RecipeSource getRecipeSource() {
        return recipeSource;
    }

    public void setRecipeSource(RecipeSource recipeSource) {
        this.recipeSource = recipeSource;
    }
    @XmlElement(name = "calories")
    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
    @XmlElement(name = "totalTime")
    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    @XmlElement(name = "ingredientLines")
    public IngredientLines getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(IngredientLines ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    @XmlElement(name = "ingredientsDetails")
    public IngredientsDetails getIngredientsDetails() {
        return ingredientsDetails;
    }

    public void setIngredientsDetails(IngredientsDetails ingredientsDetails) {
        this.ingredientsDetails = ingredientsDetails;
    }
    @XmlElement(name = "healthLabels")
    public HealthLabels getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(HealthLabels healthLabels) {
        this.healthLabels = healthLabels;
    }


}

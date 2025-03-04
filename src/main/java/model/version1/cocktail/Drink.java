package model.version1.cocktail;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


@XmlRootElement(name = "Cocktail")
@XmlType(propOrder = {
        "cocktailName",
        "recipeType",
        "image",
        "ingredientsList",
        "ingredientsDetails",
        "instructions"
})
public class Drink {
    @Schema(description = "Name of the cocktail")
    private String cocktailName;

    @Schema(description = "Recipe details")
    private RecipeType recipeType;

    @Schema(description = "Image URL of the cocktail")
    private String image;

    @Schema(description = "List of ingredients")
    private IngredientsList ingredientsList;

    @Schema(description = "Details of ingredients")
    private CocktailIngredientsDetails ingredientsDetails;

    @Schema(description = "Instructions to prepare the cocktail")
    private String instructions;


    @XmlElement(name = "CocktailName")
    public String getCocktailName() {
        return cocktailName;
    }

    public void setCocktailName(String cocktailName) {
        this.cocktailName = cocktailName;
    }

    @XmlElement(name = "RecipeType")
    public RecipeType getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(RecipeType recipeType) {
        this.recipeType = recipeType;
    }

    @XmlElement(name = "Image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @XmlElement(name = "IngredientsList")
    public IngredientsList getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(IngredientsList ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @XmlElement(name = "IngredientsDetails")
    public CocktailIngredientsDetails getIngredientsDetails() {
        return ingredientsDetails;
    }

    public void setIngredientsDetails(CocktailIngredientsDetails ingredientsDetails) {
        this.ingredientsDetails = ingredientsDetails;
    }
    @XmlElement(name = "Instructions")
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}

package model.version1.cocktail;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;


public class CocktailIngredientsDetails {
    @Schema(description = "Details of ingredients")
    private List<CocktailIngredientDetails> ingredientDetails;

    @XmlElement(name = "IngredientDetails")
    public List<CocktailIngredientDetails> getIngredientDetails() {
        return ingredientDetails;
    }

    public void setIngredientDetails(List<CocktailIngredientDetails> ingredientDetails) {
        this.ingredientDetails = ingredientDetails;
    }
}

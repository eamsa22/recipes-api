package model.version1.meal;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import model.version1.meal.IngredientDetails;

import java.util.List;

@XmlRootElement(name = "ingredientsDetails")
public class IngredientsDetails {
    private List<IngredientDetails> ingredients ;


    @XmlElement(name = "ingredientDetails")
    public List<IngredientDetails> getIngredientS() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDetails > ingredient) {
        this.ingredients = ingredient;
    }
}
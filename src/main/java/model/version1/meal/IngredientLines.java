package model.version1.meal;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "IngredientLines")
public class IngredientLines {
    private List<String> ingredients ;


    @XmlElement(name = "ingredient")
    public List<String> getIngredientS() {
        return ingredients;
    }

    public void setIngredients(List<String > ingredient) {
        this.ingredients = ingredient;
    }
}
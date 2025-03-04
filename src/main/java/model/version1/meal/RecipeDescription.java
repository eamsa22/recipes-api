package model.version1.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {
        "cuisineType",
        "mealType",
        "dishType"
})
public class RecipeDescription {
    @Schema(description = "Cuisine type")
    private String cuisineType;

    @Schema(description = "Meal type")
    private String mealType;

    @Schema(description = "Dish type")
    private String dishType;

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }


    @XmlElement(name = "cuisineType")
    public String getCuisineType() {
        return cuisineType;
    }

    @XmlElement(name = "mealType")
    public String getMealType() {
        return mealType;
    }
    @XmlElement(name = "dishType")
    public String getDishType() {
        return dishType;
    }
}
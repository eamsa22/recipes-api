package model.version1.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {
        "text" ,
        "quantity",
        "measure",
        "food",
        "weight",
        "foodCategory",
        "foodId",
        "image"
})
public class IngredientDetails {
    @Schema(description = "Text of the ingredient")
    private String text;

    @Schema(description = "Quantity of the ingredient")
    private String quantity;

    @Schema(description = "Measurement of the ingredient")
    private String measure;

    @Schema(description = "Food name")
    private String food;

    @Schema(description = "Weight of the ingredient")
    private String weight;

    @Schema(description = "Food category")
    private String foodCategory;

    @Schema(description = "Food ID")
    private String foodId;

    @Schema(description = "Image URL of the ingredient")
    private String image;

    public void setText(String text) {
        this.text = text;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @XmlElement(name = "text")
    public String getText() {
        return text;
    }

    @XmlElement(name = "quantity")
    public String getQuantity() {
        return quantity;
    }


    @XmlElement(name = "measure")
    public String getMeasure() {
        return measure;
    }

    @XmlElement(name = "food")
    public String getFood() {
        return food;
    }

    @XmlElement(name = "weight")
    public String getWeight() {
        return weight;
    }

    @XmlElement(name = "foodCategory")
    public String getFoodCategory() {
        return foodCategory;
    }

    @XmlElement(name = "foodId")
    public String getFoodId() {
        return foodId;
    }

    @XmlElement(name = "image")
    public String getImage() {
        return image;
    }
}


package model.version2;

public class DetailedIngredient {

    /**
     * the fields representing the details of an ingredient
     */
    private String name;
    private String quantity;
    private String image;

    public String getName() {
        return name;
    }

    /**
     * getters and setters over the fields of an ingredient
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


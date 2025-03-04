package model.version1.cocktail;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {
        "alcoholic",
        "category",
        "glassType"
})
public class RecipeType {
    @Schema(description = "Alcoholic type of the cocktail")
    private String alcoholic;

    @Schema(description = "Category of the cocktail")
    private String category;

    @Schema(description = "Type of glass for the cocktail")
    private String glassType;

    @XmlElement(name = "Alcoholic")
    public String getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(String alcoholic) {
        this.alcoholic = alcoholic;
    }


    @XmlElement(name = "Category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    @XmlElement(name = "GlassType")
    public String getGlassType() {
        return glassType;
    }

    public void setGlassType(String glassType) {
        this.glassType = glassType;
    }
}

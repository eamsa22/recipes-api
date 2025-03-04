package model.version1.cocktail;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {
        "text",
        "name",
        "measure"
})
public class CocktailIngredientDetails {
    @Schema(description = "Text of the ingredient")
    private String text;

    @Schema(description = "Name of the ingredient")
    private String name;

    @Schema(description = "Measurement of the ingredient")
    private String measure;

    @XmlElement(name = "Text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Measure")
    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}

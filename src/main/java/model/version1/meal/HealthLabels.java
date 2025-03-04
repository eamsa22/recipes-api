package model.version1.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class HealthLabels {
    @Schema(description = "Health label name")
    private List<String> healthLabelsList;

    @XmlElement(name = "healthLabel")
    public List<String> getHealthLabelsList() {
        return healthLabelsList;
    }

    public void setHealthLabelsList(List<String> healthLabelsList) {
        this.healthLabelsList = healthLabelsList;
    }
}
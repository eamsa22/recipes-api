package model.version1.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Recipe")
public class Error {
    @Schema(description = "Error message")
    private String error;
    @Schema(description = "API failed name")
    private String apiFailed;

    public void setError(String error) {
        this.error = error;
    }

    public void setApiFailed(String apiFailed) {
        this.apiFailed = apiFailed;
    }


    @XmlElement(name="error")
    public String getError() {
        return error;
    }


    @XmlElement(name="api_failed")
    public String getApiFailed() {
        return apiFailed;
    }
}

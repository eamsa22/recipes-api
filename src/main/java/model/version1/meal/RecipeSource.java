package model.version1.meal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RecipeSource {
    @Schema(description = "Source name")
    private String source;

    @Schema(description = "URL of the source")
    private String url;
    @XmlElement(name = "source")
    public String getSource() {
        return source;
    }

    @XmlElement(name = "url")
    public String getUrl() {
        return url;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package model.version2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

public class MealDrinkError {

    private final boolean success = false;
    private String api_failed;

    private String api_status;
    @JsonIgnore
    private String error;

    public MealDrinkError() {
    }

    public boolean isSuccess() {
        return success;
    }

    public String getApi_failed() {
        return api_failed;
    }

    public void setApi_failed(String api_failed) {
        this.api_failed = api_failed;
    }

    public String getApi_status() {
        return api_status;
    }


    public void setApi_status(String api_status) {
        this.api_status = api_status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    @Override
    public String toString() {
        return "MealDrinkError{" +
                "success=" + success +
                ", api_failed='" + api_failed + '\'' +
                ", api_status='" + api_status + '\'' +
                '}';
    }
}

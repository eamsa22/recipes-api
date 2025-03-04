package model.version3;

public class ErrorMenu  {
    private String error;

    private final boolean success = false;
    private String api_failed;

    private String api_status;



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

package model.version2;

public abstract class Meal {

    private boolean success;

    public Meal() {
    }

    public Meal(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

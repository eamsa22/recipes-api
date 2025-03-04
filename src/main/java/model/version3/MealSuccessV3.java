package model.version3;


import model.version2.MealSuccess;

public class MealSuccessV3{
    private String calories;
    private MealSuccess meal;
    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public MealSuccess getMeal() {
        return meal;
    }

    public void setMeal(MealSuccess meal) {
        this.meal = meal;
    }
}

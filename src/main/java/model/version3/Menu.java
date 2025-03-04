package model.version3;


import model.version2.DrinkSuccess;
import model.version2.MealSuccess;

public class Menu {
    private MealSuccess starter;
    private MealSuccess mainCourse;
    private MealSuccess dessert;
    private DrinkSuccess drink;

    private float menuPreparationTime;
    private float menuTotalCalories;


    public Menu() {
    }

    public MealSuccess getStarter() {
        return starter;
    }

    public MealSuccess getMainCourse() {
        return mainCourse;
    }

    public MealSuccess getDessert() {
        return dessert;
    }

    public DrinkSuccess getDrink() {
        return drink;
    }
    public float getMenuPreparationTime() {
        return menuPreparationTime;
    }

    public float getMenuTotalCalories() {
        return menuTotalCalories;
    }
    public void setStarter(MealSuccess starter) {
        this.starter = starter;
    }

    public void setMainCourse(MealSuccess mainCourse) {
        this.mainCourse = mainCourse;
    }

    public void setDessert(MealSuccess dessert) {
        this.dessert = dessert;
    }

    public void setDrink(DrinkSuccess drink) {
        this.drink = drink;
    }



    public void setMenuPreparationTime(float menuPreparationTime) {
        this.menuPreparationTime = menuPreparationTime;
    }

    public void setMenuTotalCalories(float menuTotalCalories) {
        this.menuTotalCalories = menuTotalCalories;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "starter=" + starter +
                ", mainCourse=" + mainCourse +
                ", dessert=" + dessert +
                ", drink=" + drink+
                ", totalTime=" + menuPreparationTime +
                ", calories=" + menuTotalCalories +
                +
                '}';
    }
}

package sg.edu.np.mad.mad24p03team2.SingletonClasses;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.MealClass;


//Declaration of customed Datatype - Enumeration
enum MealNames {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    OTHER("Other");

    private String name;

    MealNames(String name) {
        this.name = name;
    }

    public String getString() {
        return name;
    }
}

public class SingletonTodayMeal {
    MealClass[] mealClassArray;

    private static volatile SingletonTodayMeal INSTANCE = null;

    // private constructor to prevent instantiation of the class
    private SingletonTodayMeal() {
        mealClassArray = new MealClass[4];
    }

    // public static method to retrieve the singleton instance
    public static SingletonTodayMeal getInstance() {
        // Check if the instance is already created
        if (INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (SingletonTodayMeal.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new SingletonTodayMeal();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }

    public void AddMeal(MealClass meal) {
        for (MealNames n : MealNames.values()) {
            if (meal.getMealName().compareTo(n.getString()) == 0)
                mealClassArray[n.ordinal()] = meal;
        }
    }

    public MealClass GetMeal(String mealName) {
        for (MealNames n : MealNames.values()) {
            if (mealName.compareTo(n.getString()) == 0)
                return mealClassArray[n.ordinal()];
        }

        //if not found, return new obj class
        return new MealClass(mealName);
    }
}

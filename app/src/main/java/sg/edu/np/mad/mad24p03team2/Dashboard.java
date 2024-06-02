package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Set;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.DietPlanClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.FoodItemClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetDietPlanOption;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetMeal;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.MealClass;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;


public class Dashboard extends Fragment implements IDBProcessListener {
    GetMeal getMeal = null;
    GetDietPlanOption getDietPlanOption = null;
    TextView proteinBox;
    TextView carbsBox;
    TextView fatsBox;
    TextView calBox;

    TextView bproteinBox;
    TextView bcarbsBox;
    TextView bfatsBox;
    TextView bcalBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ProgressBar progressBar;
        getMeal = new GetMeal(requireActivity().getApplicationContext(), this);
        getDietPlanOption = new GetDietPlanOption(requireActivity().getApplicationContext(), this);

        // HONG RONG TODO: Get Card UI Elements
        //getDietPlanOption.execute(SingletonSession.getInstance().GetAccount().getGender());
        // GET ALL MEAL DATA
        // HONG RONG TODO: Use the below code to get the details of each meal
        // getMeal.execute( "Breakfast", SingletonSession.getInstance().GetAccount().getId());
        // getMeal.execute( "Lunch", SingletonSession.getInstance().GetAccount().getId());
        // getMeal.execute( "Dinner", SingletonSession.getInstance().GetAccount().getId());
        //getMeal.execute( "Dinner", SingletonSession.getInstance().GetAccount().getId());
        getDietPlanOption.execute("Diabetic Friendly", SingletonSession.getInstance().GetAccount().getGender());


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        proteinBox = view.findViewById(R.id.tvp1);
        carbsBox = view.findViewById(R.id.tvc1);
        fatsBox = view.findViewById(R.id.tvf1);
        calBox = view.findViewById(R.id.kcalnum);

        bproteinBox = view.findViewById(R.id.tvp1_b);
        bcarbsBox = view.findViewById(R.id.tvc1_b);
        bfatsBox = view.findViewById(R.id.tvf1_b);
        bcalBox = view.findViewById(R.id.kcalnum_b);

        TextView progress = view.findViewById(R.id.tvProgress);
        ProgressBar carbBar = view.findViewById(R.id.progressBarcarbs);
        ProgressBar fatBar = view.findViewById(R.id.progressBarfats);
        ProgressBar proteinBar = view.findViewById(R.id.progressBarprotein);
        ProgressBar cbar = view.findViewById(R.id.Cbar);

        carbBar.setMax(200);
        fatBar.setMax(200);
        proteinBar.setMax(200);
        /**progress.setText(String.format("%.0f",BMR));
         cbar.setMax((int)BMR);**/

        Log.d("DASHBOARD", "ACCOUNT ID =  " + SingletonSession.getInstance().GetAccount().getId());
        getMeal.execute("Lunch", Integer.toString(SingletonSession.getInstance().GetAccount().getId()));
        getMeal.execute( "Breakfast",Integer.toString(SingletonSession.getInstance().GetAccount().getId()));
        //getMeal.execute( "Dinner",Integer.toString(SingletonSession.getInstance().GetAccount().getId()));
        //getMeal.execute( "Others",Integer.toString(SingletonSession.getInstance().GetAccount().getId()));

        return view;

    }

    @Override
    public void afterProcess(Boolean executeStatus) {

        //HONG RONG
        //TODO: After database fetch all the meals from the execution statement above,
        //TODO: all meals are stored in SingletonTodayMeal (global class)
        //TODO: To get each meal details to update the UI, write the below code
        // -->> MealClass breakfastMeal = SingletonTodayMeal.getInstance().getMeal("Breakfast");
        if (executeStatus) {
            MealClass lunch = SingletonTodayMeal.getInstance().GetMeal("Lunch");

            Set<FoodItemClass> foodList = lunch.getSelectedFoodList().keySet();
            for (FoodItemClass foodItem : foodList) {
                //update UI
                int qty = lunch.getSelectedFoodList().get(foodItem);
                proteinBox.setText(Double.toString(foodItem.getProtein_g()*qty));
                carbsBox.setText(Double.toString(foodItem.getCarbohydrates_total_g()*qty));
                fatsBox.setText(Double.toString(foodItem.getFat_total_g()*qty));
                calBox.setText(Double.toString(foodItem.getCalories()*qty));

            }

            MealClass bFast = SingletonTodayMeal.getInstance().GetMeal("Breakfast");
            foodList = bFast.getSelectedFoodList().keySet();
            for (FoodItemClass foodItem : foodList) {
                //update UI
                int qty = bFast.getSelectedFoodList().get(foodItem);
                bproteinBox.setText(Double.toString(foodItem.getProtein_g()*qty));
                bcarbsBox.setText(Double.toString(foodItem.getCarbohydrates_total_g()*qty));
                bfatsBox.setText(Double.toString(foodItem.getFat_total_g()*qty));
                bcalBox.setText(Double.toString(foodItem.getCalories()*qty));

            }

        } else {
            // Handle failure
            Toast.makeText(getContext(), "Failed to fetch meal data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }
}
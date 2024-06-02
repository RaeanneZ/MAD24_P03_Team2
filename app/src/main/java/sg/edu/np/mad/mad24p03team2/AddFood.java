package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.MealClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.UpdateMeal;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonTodayMeal;


public class AddFood extends Fragment implements IDBProcessListener {

    UpdateMeal updateMeal = null;// Initialize UpdateMeal object
private int ns=1;// Initialize the counter for the number of plates
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      // Initialize UpdateMeal object with application context and current fragment
        updateMeal = new UpdateMeal(requireContext().getApplicationContext(), this);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        // Find views by their IDs
        Button button = view.findViewById(R.id.button2);
        TextView FoodName=view.findViewById(R.id.textView23);
        TextView N=view.findViewById(R.id.textView16);
        TextView nofplate = view.findViewById(R.id.textView26);
        TextView addn= view.findViewById(R.id.tvadd);
        TextView Mn= view.findViewById(R.id.textView2);
        TextView back = view.findViewById(R.id.tvbcak);
         // Set click listener for the back button to switch fragments
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment();
            }
        });
        // Set click listener for the add button to increment the plate count
         addn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ns++;  // Increment the counter
                 // Update the TextView with the new value
                 nofplate.setText(ns+" Plate");
                 N.setText(String.valueOf(ns));
             }
         });
       // Set click listener for the minus button to decrement the plate count
        Mn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ns--;  // Increment the counter
                // Update the TextView with the new value
                nofplate.setText(ns+" Plate");
                N.setText(String.valueOf(ns));
            }
        });
       // Retrieve text from FoodName and N TextViews
        String mealName  = FoodName.getText().toString();
        String quantity =N.getText().toString();
        // Create a new MealClass object with the meal name
        MealClass mealClass= new MealClass(mealName);
  // Set click listener for the button to execute update and switch fragments
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Execute the updateMeal task with the account ID, meal name, and quantity
                updateMeal.execute(Integer.toString(SingletonSession.getInstance().GetAccount().getId()), mealName, quantity);
                // Add the meal to SingletonTodayMeal
                SingletonTodayMeal.getInstance().AddMeal(mealClass);
                // Switch to the SearchForFood fragment
                switchFragment();
            }
        });
        return view;// Return the inflated view
    }
    // Method to switch the current fragment to SearchForFood
    private void switchFragment() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity2) {
            ((MainActivity2) activity).replaceFragment(new SearchForFood());
        }
    }
    @Override
    public void afterProcess(Boolean executeStatus) {

    }

    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {

    }


}
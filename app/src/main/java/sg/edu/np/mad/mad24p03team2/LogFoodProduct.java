package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

public class LogFoodProduct extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_food_product, container, false);
        // Find the button by its ID in the inflated view
        Button button  =view.findViewById(R.id.button2);
        // Set a click listener on the button to switch fragments when clicked
        button.setOnClickListener(v -> switchFragment());
        return view;// Return the inflated view
    }
    // Method to switch the current fragment to SearchForFood
    private void switchFragment() {
        FragmentActivity activity = getActivity();// Get the current activity
        if (activity instanceof MainActivity2) {
            // Replace the current fragment with the SearchForFood fragment
            ((MainActivity2) activity).replaceFragment(new SearchForFood());
        }
    }
}
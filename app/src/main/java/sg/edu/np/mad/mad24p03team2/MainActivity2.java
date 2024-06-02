package sg.edu.np.mad.mad24p03team2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Set the content view to the specified layout
        setContentView(R.layout.activity_main2);

        // Set default fragment to Dashboard on activity launch
        replaceFragment(new Dashboard());
        // Get reference to the BottomNavigationView from the layout
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        // Set listener for bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            // Get the ID of the selected menu item
            int itemId = menuItem.getItemId();
            // Replace the fragment based on the selected menu item
            if (itemId == R.id.dashboard) {
                replaceFragment(new Dashboard());
                return true;// Indicate that the event was handled
            }
            if (itemId == R.id.logfood) {
                replaceFragment(new LogFoodProduct());
                return true;// Indicate that the event was handled
            }
            if (itemId == R.id.account) {
                replaceFragment(new accountpage());
                return true;// Indicate that the event was handled
            }
            return false; // Indicate that the event was not handled
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    // Method to replace the current fragment with a new one
    public void replaceFragment(Fragment fragment){
        // Get the fragment manager to handle fragment transactions
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Begin a new fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the current fragment in the container with the new fragment
        fragmentTransaction.replace(R.id.container2, fragment);
        // Commit the transaction to make the change
        fragmentTransaction.commit();
    }

}
package sg.edu.np.mad.mad24p03team2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.GetCurrentUserProfile;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.LoginUser;
import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;

public class LoginActivity extends AppCompatActivity implements IDBProcessListener {
    EditText emailComponent, passwordComponent;
    String email, password;
    Button loginBtn;

    LoginUser loginUser = null;
    GetCurrentUserProfile getCurrentUserProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loading), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailComponent = (EditText) findViewById(R.id.email);
        passwordComponent = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginUser = new LoginUser(getApplicationContext(),this);
        getCurrentUserProfile = new GetCurrentUserProfile(getApplicationContext(),this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailComponent.getText().toString();
                password = passwordComponent.getText().toString();
                loginUser.execute(email, password);
            }
        });
    }

    //Methods from IDBProcessListener
    @Override
    public void afterProcess(Boolean isValidUser, Boolean isValidPwd) {
        // User Login process will return 2 boolean flag to indicate whether its wrong username or
        // wrong password that caused LOGIN UNSUCCESSFUL
        // Please update your UI here

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(LoginActivity.this, "Please fill in the fields", Toast.LENGTH_SHORT).show();
        } else if (isValidPwd == false || isValidUser == false){
            Toast.makeText(LoginActivity.this, "User not found. Try again", Toast.LENGTH_SHORT).show();
        } else{
            Intent login = new Intent(LoginActivity.this, MainActivity2.class);
            startActivity(login);
            Log.d("afterProcess", "Execution status: " + (isValidPwd && isValidUser));

            // Grab current user profile and store into SingletonSession
            getCurrentUserProfile.execute(email);
        }
    }

    @Override
    public void afterProcess(Boolean executeStatus) {
        // For 1 return value (e.g. whether registration is successful)
    }
}
package com.rickieyInnovates.juditon;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEdit, passwordEdit;
        usernameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        Button login = findViewById(R.id.loginBtn);

        login.setOnClickListener(view -> {
            String username, password;
            username = usernameEdit.getText().toString();
            password = passwordEdit.getText().toString();

            if (TextUtils.isEmpty(username)) {
                usernameEdit.setError("Username cannot be empty");
                usernameEdit.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEdit.setError("Password cannot be empty");
                passwordEdit.requestFocus();
                return;
            }

            goToLogin(username, password);
        });

    }

    private void goToLogin(String username, String password) {
        ApiRequests apiRequest = new ApiRequests(this);

        apiRequest.login(username, password, Endpoints.loginApi, new ApiRequests.LoginCallback() {
            @Override
            public void onLoginSuccess(String authToken) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }

            @Override
            public void onLoginError(String errorMessage) {
                Log.e(TAG, errorMessage);
                // Handle login error, e.g., display an error message to the user
            }
        });
    }
}
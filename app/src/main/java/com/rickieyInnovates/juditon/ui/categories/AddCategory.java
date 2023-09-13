package com.rickieyInnovates.juditon.ui.categories;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.VolleyError;
import com.rickieyInnovates.juditon.ApiRequests;
import com.rickieyInnovates.juditon.Endpoints;
import com.rickieyInnovates.juditon.MainActivity;
import com.rickieyInnovates.juditon.R;
import org.json.JSONException;
import org.json.JSONObject;

public class AddCategory extends AppCompatActivity {
    private static final String TAG = "AddCategory";
    AlertDialog.Builder dialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Button addBtn = findViewById(R.id.addCategoryBtn);
        EditText categoryEdit = findViewById(R.id.categoryEdit);
        dialogBuilder = new AlertDialog.Builder(this);

        addBtn.setOnClickListener(view -> {
            String category = categoryEdit.getText().toString();
            if (TextUtils.isEmpty(category)) {
                categoryEdit.setError("Please add a category");
                categoryEdit.requestFocus();
                return;
            }

            addCategory(category);
        });
    }

    private void addCategory(String category) {
        ApiRequests apiRequests = new ApiRequests(this);
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("category", category);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        apiRequests.makeAuthenticatedPostRequest(requestBody, Endpoints.addAnimalCategory, new ApiRequests.Callback() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "onSuccess: " + response);
                dialogBuilder.setTitle("Server Response");
                dialogBuilder.setMessage(response);
                dialogBuilder.setPositiveButton("Ok", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                });
                dialogBuilder.create();
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, "onError: ", error.fillInStackTrace());
            }
        });
    }
}
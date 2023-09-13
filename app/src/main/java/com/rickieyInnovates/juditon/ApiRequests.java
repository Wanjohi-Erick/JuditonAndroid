package com.rickieyInnovates.juditon;

import android.content.Context;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiRequests {
    private static final String TAG = "MyApiRequest";

    private final RequestQueue requestQueue;
    private final TokenManager tokenManager;

    public ApiRequests(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        tokenManager = TokenManager.getInstance();
    }

    public void login(final String username, final String password, final String url, final LoginCallback callback) {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", username);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    try {
                        String authToken = response.getString("token");

                        tokenManager.setAuthToken(authToken);

                        callback.onLoginSuccess(authToken);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Notify the callback of an error
                        callback.onLoginError("Invalid response from the server");
                    }
                },
                error -> {
                    Log.e(TAG, error.getMessage());
                    // Notify the callback of an error
                    callback.onLoginError("Login failed. Check your credentials and try again.");
                });

        // Add the request to the queue
        requestQueue.add(jsonRequest);
    }

    public void makeAuthenticatedGetRequest(final Context context, final String url, final Callback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                callback::onSuccess,
                callback::onError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + tokenManager.getAuthToken());
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void makeAuthenticatedPostRequest(final JSONObject requestBody, final String url, final Callback callback) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> {
                    Log.i(TAG, "makeAuthenticatedPostRequest: " + response);
                    callback.onSuccess(String.valueOf(response));
                },
                error -> {
                    Log.e(TAG, error.getMessage());
                    callback.onError(error);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + tokenManager.getAuthToken());
                return headers;
            }
        };

        requestQueue.add(jsonRequest);
    }

    public interface Callback {
        void onSuccess(String response);
        void onError(VolleyError error);
    }

    public interface LoginCallback {
        void onLoginSuccess(String authToken);
        void onLoginError(String errorMessage);
    }
}


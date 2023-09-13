package com.rickieyInnovates.juditon.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rickieyInnovates.juditon.ApiRequests;
import com.rickieyInnovates.juditon.Endpoints;
import com.rickieyInnovates.juditon.R;
import com.rickieyInnovates.juditon.adapters.AnimalCategoriesAdapter;
import com.rickieyInnovates.juditon.models.animals.Category;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnimalCategories extends Fragment {
    private static final String TAG = "AnimalCategories";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_animal_categories, container, false);

        List<Category> categoryList = new ArrayList<>();
        RecyclerView categoriesRecycler = root.findViewById(R.id.animal_category_recycler);
        FloatingActionButton fab = root.findViewById(R.id.addCategory);
        fab.setOnClickListener(view -> startActivity(new Intent(getContext(), AddCategory.class)));
        categoriesRecycler.setHasFixedSize(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        ApiRequests apiRequests = new ApiRequests(getContext());
        apiRequests.makeAuthenticatedGetRequest(getContext(), Endpoints.animalCategories, new ApiRequests.Callback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray responseArray = new JSONArray(response);
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject responseObject = responseArray.getJSONObject(i);
                        Category category = new Category(responseObject.getInt("id"), responseObject.getJSONObject("farm").getInt("id"), responseObject.getString("category"));
                        categoryList.add(category);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AnimalCategoriesAdapter categoriesAdapter = new AnimalCategoriesAdapter(categoryList);
                categoriesRecycler.setAdapter(categoriesAdapter);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, "onError: ", error.fillInStackTrace());
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
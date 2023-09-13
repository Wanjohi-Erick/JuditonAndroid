package com.rickieyInnovates.juditon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rickieyInnovates.juditon.R;
import com.rickieyInnovates.juditon.models.animals.Category;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AnimalCategoriesAdapter extends RecyclerView.Adapter<AnimalCategoriesAdapter.MyViewHolder> {
    List<Category> categoryList;
    public AnimalCategoriesAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_categories_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.categoryView.setText(categoryList.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryView;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            categoryView = itemView.findViewById(R.id.category);
        }
    }
}

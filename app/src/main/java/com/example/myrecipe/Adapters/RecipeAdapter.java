package com.example.myrecipe.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.Listeners.RecipeClickListener;
import com.example.myrecipe.Models.Recipe;
import com.example.myrecipe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    Context context;
    List<Recipe> list;
    RecipeClickListener listener;


    public RecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.card_recipe, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.tv_title.setText(list.get(position).title);
        holder.tv_title.setSelected(true);
        holder.tv_likes.setText(list.get(position).aggregateLikes + " Likes");
        holder.tv_servings.setText((list.get(position).servings + " Portions"));
        holder.tv_time.setText(list.get(position).readyInMinutes + " Minutes");
        Picasso.get().load(list.get(position).image).into(holder.im_food);
        holder.card_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class RecipeViewHolder extends RecyclerView.ViewHolder {
    CardView card_container;
    TextView tv_title, tv_servings, tv_likes, tv_time;
    ImageView im_food;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        card_container = itemView.findViewById(R.id.card_container);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_servings = itemView.findViewById(R.id.tv_servings);
        tv_likes = itemView.findViewById(R.id.tv_likes);
        tv_time = itemView.findViewById(R.id.tv_time);
        im_food = itemView.findViewById(R.id.im_food);


    }
}

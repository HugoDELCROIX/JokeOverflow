package com.example.jokeoverflow.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.R;

import java.util.ArrayList;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder>{

    private ArrayList<Joke> jokes;

    public JokeAdapter(ArrayList<Joke> jokes){
        this.jokes = jokes;
    }

    public void setJokes(ArrayList<Joke> jokes){
        this.jokes = jokes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cards_home, parent, false);
        return new ViewHolder(view, new ClickListener() {
            @Override
            public void onRateUp(int p) {
                Toast.makeText(view.getContext(), "UP RATE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRateDown(int p) {
                Toast.makeText(view.getContext(), "DOWN RATE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Joke currentJoke = jokes.get(position);



        holder.date.setText(currentJoke.getDate());
        holder.title.setText(currentJoke.getTitle());
        holder.content.setText(currentJoke.getContent());

        if (currentJoke.getRating() > 5){
            holder.rating.setTextColor(ContextCompat.getColor(holder.rating.getContext(), R.color.orange));
        } else if (currentJoke.getRating() < 5){
            holder.rating.setTextColor(ContextCompat.getColor(holder.rating.getContext(), R.color.blue));
        }

        holder.rating.setText(Float.toString(currentJoke.getRating()));

    }

    @Override
    public int getItemCount() {
        return this.jokes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView date;
        private final TextView title;
        private final TextView content;
        private final ImageButton userPicture;
        private final TextView rating;

        private final ImageButton upRateBtn;
        private final ImageButton downRateBtn;


        ClickListener clickListener;


        public ViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);


            this.date = itemView.findViewById(R.id.jokeDate);
            this.title = itemView.findViewById(R.id.jokeTitle);
            this.content = itemView.findViewById(R.id.jokeContent);
            this.userPicture = itemView.findViewById(R.id.userPicture);
            this.rating = itemView.findViewById(R.id.jokeRating);

            this.clickListener = listener;

            this.upRateBtn = itemView.findViewById(R.id.upRateBtn);
            this.downRateBtn = itemView.findViewById(R.id.downRateBtn);

            upRateBtn.setOnClickListener(this);
            downRateBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.upRateBtn:
                    this.clickListener.onRateUp(this.getLayoutPosition());
                    break;
                case R.id.downRateBtn:
                    this.clickListener.onRateDown(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface ClickListener {
        void onRateUp(int p);
        void onRateDown(int p);
    }
}

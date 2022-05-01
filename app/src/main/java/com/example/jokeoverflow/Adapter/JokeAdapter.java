package com.example.jokeoverflow.Adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.R;
import com.example.jokeoverflow.Repository.JokeRepository;
import com.example.jokeoverflow.Repository.UserRepository;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder>{

    private ArrayList<Joke> jokes;

    private JokeRepository jokeRepository;
    private UserRepository userRepository;


    public JokeAdapter(ArrayList<Joke> jokes){
        this.jokes = jokes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        jokeRepository = JokeRepository.getInstance();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cards_home, parent, false);

        return new ViewHolder(view, new ClickListener() {
            final AppCompatActivity activity = (AppCompatActivity) view.getContext();

            final NavHostFragment navHostFragment  = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment);
            final NavController navController = navHostFragment.getNavController();

            @Override
            public void onPictureClick(int p) {
                Joke joke = jokes.get(p);

                Bundle bundle = new Bundle();
                bundle.putString("userId", joke.getUserId());

                navController.navigate(R.id.userProfileFragment, bundle);
            }

            @Override
            public void onRateUp(int p) {
                Joke joke = jokes.get(p);

                joke.setRating((double) Math.round((joke.getRating() + 0.3) * 100) / 100);
                Joke newJoke = jokeRepository.rate(joke);
                jokes.set(p, newJoke);
                notifyItemChanged(p);
            }

            @Override
            public void onRateDown(int p) {
                Joke joke = jokes.get(p);

                joke.setRating((double) Math.round((joke.getRating() - 0.3) * 100) / 100);
                Joke newJoke = jokeRepository.rate(joke);
                jokes.set(p, newJoke);
                notifyItemChanged(p);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        userRepository = UserRepository.getInstance();

        Joke currentJoke = jokes.get(position);

        holder.date.setText(currentJoke.getDate());
        holder.title.setText(currentJoke.getTitle());
        holder.content.setText(currentJoke.getContent());

        userRepository.getUserProfilePicture(currentJoke.getUserId()).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.userPicture);
            }
        });


        if (currentJoke.getRating() > 5){
            holder.rating.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.orange));
        } else if (currentJoke.getRating() < 5){
            holder.rating.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));
        }

        holder.rating.setText(Double.toString(currentJoke.getRating()));

    }

    @Override
    public int getItemCount() {
        return this.jokes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView date;
        private final TextView title;
        private final TextView content;
        private final ImageView userPicture;
        private final TextView rating;

        private final ImageView upRateBtn;
        private final ImageView downRateBtn;


        ClickListener clickListener;


        public ViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);


            this.date = itemView.findViewById(R.id.jokeDate);
            this.title = itemView.findViewById(R.id.jokeTitle);
            this.content = itemView.findViewById(R.id.jokeContent);
            this.userPicture = itemView.findViewById(R.id.userPicture);
            this.rating = itemView.findViewById(R.id.jokeRating);

            this.upRateBtn = itemView.findViewById(R.id.upRateBtn);
            this.downRateBtn = itemView.findViewById(R.id.downRateBtn);

            this.clickListener = listener;

            userPicture.setOnClickListener(this);
            upRateBtn.setOnClickListener(this);
            downRateBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(view.getId() == R.id.upRateBtn){
                this.clickListener.onRateUp(this.getLayoutPosition());
            } else if (view.getId() == R.id.downRateBtn) {
                this.clickListener.onRateDown(this.getLayoutPosition());
            } else if (view.getId() == R.id.userPicture){
                this.clickListener.onPictureClick(this.getLayoutPosition());
            }
        }
    }

    public interface ClickListener {
        void onPictureClick(int p);
        void onRateUp(int p);
        void onRateDown(int p);
    }
}

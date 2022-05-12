package com.example.jokeoverflow.Adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class JokeAdapter extends FirebaseRecyclerAdapter<Joke, JokeAdapter.ViewHolder>{

    private JokeRepository jokeRepository;
    private UserRepository userRepository;
    private ArrayList<Joke> jokes;


    public JokeAdapter(Query query, FirebaseRecyclerOptions options){
        super(options);
        jokes = new ArrayList<Joke>();
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
            public void onPictureClick(Joke joke) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", joke.getUserId());

                navController.navigate(R.id.userProfileFragment, bundle);
            }

            @Override
            public void onRateUp(Joke joke, int position) {
                joke.upRate();
                jokeRepository.rate(joke);
                notifyItemChanged(position);
            }

            @Override
            public void onRateDown(Joke joke, int position) {
                joke.downRate();
                jokeRepository.rate(joke);
                notifyItemChanged(position);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Joke model) {
        userRepository = UserRepository.getInstance();

        try{
            if(jokes.get(position) == null){
                jokes.add(model);
            } else {
                jokes.set(position, model);
            }
        } catch (IndexOutOfBoundsException e){
            jokes.add(model);
        }

        holder.date.setText(model.getFormattedDate());
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());


        userRepository.getUserProfilePicture(model.getUserId()).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.userPicture);
            }
        });


        if (model.getFormattedRating() > 5.0){
            holder.rating.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.orange));
        } else if (model.getFormattedRating() < 5.0){
            holder.rating.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.blue));
        } else {
            holder.rating.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        }


        holder.rating.setText(Double.toString(model.getFormattedRating()));

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            int position = this.getLayoutPosition();

            if(view.getId() == R.id.upRateBtn){
                this.clickListener.onRateUp(jokes.get(position), position);
            } else if (view.getId() == R.id.downRateBtn) {
                this.clickListener.onRateDown(jokes.get(position), position);
            } else if (view.getId() == R.id.userPicture){
                this.clickListener.onPictureClick(jokes.get(position));
            }
        }
    }

    public interface ClickListener {
        void onPictureClick(Joke joke);
        void onRateUp(Joke joke, int p);
        void onRateDown(Joke joke, int p);
    }

}

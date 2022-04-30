package com.example.jokeoverflow.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.R;
import com.example.jokeoverflow.ViewModel.JokesViewModel;
import com.example.jokeoverflow.ViewModel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder>{

    private ArrayList<Joke> jokes;
    private UserViewModel userViewModel;


    public JokeAdapter(ArrayList<Joke> jokes){
        this.jokes = jokes;
    }

    public void setJokes(ArrayList<Joke> jokes){
        this.jokes = jokes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JokesViewModel jokesViewModel;
        jokesViewModel = new ViewModelProvider((ViewModelStoreOwner) parent.getContext()).get(JokesViewModel.class);
        jokesViewModel.init();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cards_home, parent, false);

        return new ViewHolder(view, new ClickListener() {
            @Override
            public void onPictureClick(int p) {
                // When the picture is clicked
            }

            @Override
            public void onRateUp(int p) {
                Joke joke = jokes.get(p);

                joke.setRating((double) Math.round((joke.getRating() + 0.3) * 100) / 100);
                Joke newJoke = jokesViewModel.rate(joke);
                jokes.set(p, newJoke);
                notifyItemChanged(p);
            }

            @Override
            public void onRateDown(int p) {
                Joke joke = jokes.get(p);

                joke.setRating((double) Math.round((joke.getRating() - 0.3) * 100) / 100);
                Joke newJoke = jokesViewModel.rate(joke);
                jokes.set(p, newJoke);
                notifyItemChanged(p);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Joke currentJoke = jokes.get(position);
        userViewModel = new ViewModelProvider((ViewModelStoreOwner) holder.itemView.getContext()).get(UserViewModel.class);
        userViewModel.init();

        holder.date.setText(currentJoke.getDate());
        holder.title.setText(currentJoke.getTitle());
        holder.content.setText(currentJoke.getContent());

        userViewModel.getUserProfilePicture(currentJoke.getUserId()).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.userPicture);
            }
        });


        if (currentJoke.getRating() > 5){
            holder.rating.setTextColor(ContextCompat.getColor(holder.rating.getContext(), R.color.orange));
        } else if (currentJoke.getRating() < 5){
            holder.rating.setTextColor(ContextCompat.getColor(holder.rating.getContext(), R.color.blue));
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

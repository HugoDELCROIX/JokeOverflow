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
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.R;
import com.example.jokeoverflow.Repository.JokeRepository;
import com.example.jokeoverflow.Repository.UserRepository;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class UserAdapter extends FirebaseRecyclerAdapter<User, UserAdapter.ViewHolder> {

    UserRepository userRepository;
    JokeRepository jokeRepository;

    public UserAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull User model) {
        userRepository = UserRepository.getInstance();
        jokeRepository = JokeRepository.getInstance();

        String userKey = this.getRef(position).getKey();

        holder.ladderUsername.setText(model.getUsername());
        holder.ladderIndex.setText(Integer.toString(position+1));
        holder.ladderScore.setText(Integer.toString(model.formattedScore()));

        userRepository.getUserProfilePicture(userKey).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.ladderProfilePicture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Glide.with(holder.itemView.getContext()).load(R.drawable.resource_default).into(holder.ladderProfilePicture);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cards_user, parent, false);

        return new ViewHolder(view, new ClickListener() {
            final AppCompatActivity activity = (AppCompatActivity) view.getContext();

            final NavHostFragment navHostFragment  = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.fragment);
            final NavController navController = navHostFragment.getNavController();

            @Override
            public void onItemClick(String userKey) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", userKey);

                navController.navigate(R.id.userProfileFragment, bundle);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final CardView cardView;

        private final TextView ladderIndex;
        private final TextView ladderUsername;
        private final TextView ladderScore;
        private final ImageView ladderProfilePicture;

        private ClickListener clickListener;

        public ViewHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.user_card);

            ladderIndex = itemView.findViewById(R.id.ladderIndex);
            ladderUsername = itemView.findViewById(R.id.ladderUsername);
            ladderScore = itemView.findViewById(R.id.ladderScore);
            ladderProfilePicture = itemView.findViewById(R.id.ladderProfilePicture);

            this.clickListener = clickListener;

            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            String userKey = getRef(this.getLayoutPosition()).getKey();
            this.clickListener.onItemClick(userKey);
        }
    }

    public interface ClickListener {
        void onItemClick(String userKey);
    }
}

package com.example.matchnheal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MyScores extends AppCompatActivity {
    private FirebaseFirestore db;
    private RecyclerView score_list;
    private FirebaseAuth mauth;
    private String mlia;
    private FirestoreRecyclerAdapter adapter;
    private Button bck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scores);
        score_list = findViewById(R.id.score_list);
        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        mlia = mauth.getCurrentUser().getEmail();
        bck = findViewById(R.id.bckk);

        bck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MyScores.this, Anasayfa.class);
                startActivity(intent);
            }
        });

        Query query = db.collection("Scores").whereEqualTo("usermail",mlia);
        FirestoreRecyclerOptions<ProductModel> options = new FirestoreRecyclerOptions.Builder<ProductModel>().setQuery(query, ProductModel.class).build();

        adapter = new FirestoreRecyclerAdapter<ProductModel, ProductViewHolder>(options) {
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);

                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull ProductModel model) {
                holder.list_user.setText("Score: ");
                holder.list_score.setText(model.getScore());

            }
        };

        score_list.setHasFixedSize(true);
        score_list.setLayoutManager(new LinearLayoutManager(this));
        score_list.setAdapter(adapter);


    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView list_user;
        private TextView list_score;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            list_user = itemView.findViewById(R.id.user_name);
            list_score = itemView.findViewById(R.id.scr);


        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
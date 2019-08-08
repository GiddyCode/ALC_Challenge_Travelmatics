package com.example.alc_challenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewImageActivity extends AppCompatActivity {

    private TextView imageLocation;
    private TextView Image_Cost;

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar progressBar;

    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);


//        imageLocation = findViewById(R.id.Image_Location);
//        Image_Cost = findViewById(R.id.Image_Cost);
//
//        String cost = getIntent().getExtras().getString("cost");
//        Image_Cost.setText(cost);
//
//        String Location = getIntent().getExtras().getString("location");
//        imageLocation.setText(Location);



        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar=findViewById(R.id.progress_circular);

        mUploads=new ArrayList<>();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
               {
                   Upload upload=postSnapshot.getValue(Upload.class);
                   mUploads.add(upload);
               }
               mAdapter=new ImageAdapter(ViewImageActivity.this, mUploads);
               mRecyclerView.setAdapter(mAdapter);
               progressBar.setVisibility(View.INVISIBLE);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewImageActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}

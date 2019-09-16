package com.example.doit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements RecAdapter.RecListener{

    RecyclerView recyclerView;
    static ArrayList<GeneralRecyclerList> toDos = new ArrayList<>();
    static RecAdapter recAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        // set first list item
        toDos.add(new GeneralRecyclerList("add new to do list","this is where some other info will be", "Do it","Dont wait"));
        toDos.add(new GeneralRecyclerList("first item","the notes",new Date().toString()));

        initRecycler();
    }

    public void initRecycler() {
        // link Adapter to list
        recAdapter = new RecAdapter(toDos,this);

        // Set up Recycler manager to link to adapter
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recAdapter);
    }

    @Override
    public void onRecClick(int position) {
        Log.i("Passing Position","" + position);
        Intent intent = new Intent(this,ListActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}

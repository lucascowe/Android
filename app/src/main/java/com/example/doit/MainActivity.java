package com.example.doit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements RecAdapter.RecListener {

    RecyclerView recyclerView;
    static ArrayList<GeneralRecyclerList> toDos = new ArrayList<>();
    static RecAdapter recAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        initRecycler();
        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.newList:
                nameList(0);
//                onRecClick(0);
                return true;
            case R.id.rename:
                return true;
            case R.id.delete:
                return true;
            default:
                Log.e("Menu","Menu case not valid");
                return false;
        }
    }

    private void nameList(int position) {
        String str = "";
        final EditText nameEditText = new EditText(this);
        final int positionPasser= position;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        nameEditText.setLayoutParams(lp);

        if (position > 0) {
            nameEditText.setText(str);
        }
        new AlertDialog.Builder(this)
                .setTitle("List Name")
                .setMessage("Enter new list name")
                .setView(nameEditText)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!nameEditText.getText().equals("")) {
                            if (positionPasser > 0) {
                                toDos.get(positionPasser).setData1(nameEditText.getText().toString());
                            } else {
                                GeneralRecyclerList newList = new GeneralRecyclerList(
                                        nameEditText.getText().toString(),
                                        "","",new Date().toString());
                                toDos.add(newList);
                                Log.i("list", "loading list in position " + (toDos.size() - 1));
                                loadList(toDos.size() - 1);
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing
                    }
                })
                .show();
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
        if (position > 0) {
            loadList(position);
        } else {
            nameList(position);
        }
    }

    public void loadList(int position) {
        Log.i("Passing Position","" + position);
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("com.example.doit", Context.MODE_PRIVATE);
        toDos.clear();
        ArrayList<String> headers = new ArrayList<>();
        ArrayList<String> comments = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        try {
            headers = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences
                    .getString("headings", ObjectSerializer.serialize( new ArrayList<String>())));
            comments = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences
                    .getString("comments", ObjectSerializer.serialize(new ArrayList<String>())));
            dates = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences
                    .getString("dateCreated", ObjectSerializer.serialize(new ArrayList<String>())));
            if (headers != null && comments != null && dates != null) {
                if (headers.size() == comments.size() && comments.size() == dates.size()) {
                    for (int i = 0; i < headers.size(); i++) {
                        toDos.add(new GeneralRecyclerList(headers.get(i),comments.get(i),"",dates.get(i)));
                    }
                }
            }
            if (toDos.size() == 0) {
                toDos.add(new GeneralRecyclerList("add new to do list"));
            }
            recAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.doit", Context.MODE_PRIVATE);
        ArrayList<String> headers = new ArrayList<>();
        ArrayList<String> comments = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        for (int i = 0; i < toDos.size(); i++) {
            headers.add(toDos.get(i).getData1());
            comments.add(toDos.get(i).getData2());
            dates.add(toDos.get(i).getData4());
        }
        try {
            sharedPreferences.edit().putString("headings", ObjectSerializer.serialize(headers)).apply();
            sharedPreferences.edit().putString("comments", ObjectSerializer.serialize(comments)).apply();
            sharedPreferences.edit().putString("dates",ObjectSerializer.serialize(comments)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }
}

package com.example.doit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.widget.EditText;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    EditText editText;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        editText = findViewById(R.id.editText);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        editText.setText(MainActivity.toDos.get(position).getData2());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.toDos.get(position).setData2(editText.getText().toString());
        MainActivity.recAdapter.notifyDataSetChanged();
        Log.d("info","Saving on destroy");
    }
}

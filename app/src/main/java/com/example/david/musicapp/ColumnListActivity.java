package com.example.david.musicapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class ColumnListActivity extends AppCompatActivity {
    private List<Color> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.column_list_recycler_view);
                /*
        recyclerView.setAdapter(new ColumnElement(elements, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast toast = Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT);
                int color = android.graphics.Color.parseColor(colors.get(position).getHex());
                toast.getView().setBackgroundColor(color);
                toast.show();
            }
        }));
                */

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
            //recyclerView.addItemDecoration(new DividerItemDecoration(this));
        }
    }

package com.monaara.sahan.fancytodolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monaara.sahan.fancytodolist.adapter.ToDoAdapter;
import com.monaara.sahan.fancytodolist.helper.ToDoDbHelper;

public class ViewActivity extends AppCompatActivity implements TimeDialog.TimeDialogListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ToDoDbHelper dbHelper;
    private ToDoAdapter adapter;
    LinearLayout errorView;
    FloatingActionButton addButton;
    TextView noTodoList;
    ImageView filter;
    public  String f,u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        //initialize the variables
        noTodoList = findViewById(R.id.noListAvailable);
        errorView = findViewById(R.id.errorView);
        addButton = findViewById(R.id.addButton);
        filter = findViewById(R.id.filter);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerTodoList);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //adding sub items to the task
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewActivity.this,CreateActivity.class);
                startActivity(intent);

            }
        });
        //populate recycler
        populateRecyclerView(null);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBox();
            }
        });
    }
    //opening filter dialogbox
    private void openDialogBox(){
        TimeDialog timeDialog =  new TimeDialog();
        timeDialog.show(getSupportFragmentManager(),"Time Dialog");
    }
    //populate the recycler view
    private void populateRecyclerView(String selectedDate) {

        dbHelper = new ToDoDbHelper(this);
            errorView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            adapter = new ToDoAdapter(dbHelper.toDoItemList(selectedDate,null), this, mRecyclerView);
            mRecyclerView.setAdapter(adapter);

    }

    //passing filter variables
    @Override
    public void passDates(String start, String end , String finished ,String unfinished) {
        f= finished;
        u = unfinished;
        dbHelper = new ToDoDbHelper(this);
        errorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        dbHelper.toDoItemList(null,null).clear();

        adapter = new ToDoAdapter(dbHelper.toDoItemList(start, end), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        if (f.equals("finished") && u.equals("not")){
            adapter = new ToDoAdapter(dbHelper.viewByStatus("finished","not"), this, mRecyclerView);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
        if (u.equals("unfinished")&& f.equals("not")){
            adapter = new ToDoAdapter(dbHelper.viewByStatus("unfinished","not"), this, mRecyclerView);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        if (f.equals("finished") && u.equals("unfinished") ){
            adapter = new ToDoAdapter(dbHelper.viewByStatus("finished","unfinished"), this, mRecyclerView);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

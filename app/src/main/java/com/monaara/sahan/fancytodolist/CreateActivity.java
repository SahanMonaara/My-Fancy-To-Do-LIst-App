package com.monaara.sahan.fancytodolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.monaara.sahan.fancytodolist.helper.ToDoDbHelper;
import com.monaara.sahan.fancytodolist.model.ToDoItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateActivity extends AppCompatActivity {
    private static final String TAG = "CreateAvtivity";
    EditText titleValue,dateValue;
    Button saveBtn,addItemBtn;
     ToDoDbHelper toDoDbHelper;
    ListView listView;
    private ArrayList<String> list;
    ArrayAdapter <String> arrayAdapter;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        titleValue = findViewById(R.id.titleValue);
        dateValue = findViewById(R.id.dateValue);
        saveBtn = findViewById(R.id.saveBtn);
        addItemBtn = findViewById(R.id.addItemBtn);
        listView = findViewById(R.id.listOfItems);

        list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = titleValue.getText().toString();
                list.add(item);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        });


        dateValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    new DatePickerDialog(CreateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDoItem(dateValue.getText().toString(),list);
            }
        });
    }


     DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dateValue.setText(sdf.format(myCalendar.getTime()));
    }

    private void saveToDoItem(String mdate, ArrayList list) {
        String title = titleValue.getText().toString().trim();
        String date = dateValue.getText().toString().trim();
        ArrayList items = list;
        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                ToDoItem toDoItem = new ToDoItem(items.get(i).toString(), date, "not completed", "not finished");
                ToDoDbHelper listToAdd = new ToDoDbHelper(this);
                listToAdd.saveItem(toDoItem);
            }
        }
        else {
            ToDoItem toDoItem = new ToDoItem(title, date,"not completed","not finished");
            ToDoDbHelper toDoDbHelper = new ToDoDbHelper(this);
            toDoDbHelper.saveItem(toDoItem);
        }

        if (title.isEmpty()) {
            //error title is empty
            titleValue.setError("Enter a task");
        }
        if (date.isEmpty()) {
            //error time is empty
            dateValue.setError("Pick a date");
        }


        //create new list
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success")
                .setContentText("Event Successfully \n Added!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }).show();

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ViewActivity.class));
        finish();
    }
}

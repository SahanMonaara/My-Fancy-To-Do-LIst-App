package com.monaara.sahan.fancytodolist;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeDialog extends AppCompatDialogFragment {
    private EditText startdate,endDate;
    private CheckBox finishedFilter,unfinishedFilter;
    private TimeDialogListener listener;
    final Calendar myCalendar = Calendar.getInstance();
    public String sDate,eDate,finished=null,unfinished=null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_box,null);
        builder.setView(view)
                .setTitle("Filter")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String start = sDate;
                        String end = eDate;
                        String isFinished = finished;
                        String isUnfinished = unfinished;
                        listener.passDates(start,end,isFinished,isUnfinished);
                    }
                });

        startdate = view.findViewById(R.id.startDate);
        endDate= view.findViewById(R.id.endDate);
        finishedFilter = view.findViewById(R.id.finishedFilter);
        unfinishedFilter = view.findViewById(R.id.unfinishedFilter);

        if (unfinishedFilter.isChecked()&& finishedFilter.isChecked()){
            finished= "finished";
            unfinished = "unfinished";
        }
        finishedFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (finishedFilter.isChecked() && !unfinishedFilter.isChecked()){
                   finished = "finished";
                   unfinished= "not";
               }


            }
        });

        unfinishedFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unfinishedFilter.isChecked() && !finishedFilter.isChecked()){
                    unfinished = "unfinished";
                    finished="not";
                }

            }
        });


        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStartingDate();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEndDate();
            }
        });

        return builder.create();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TimeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must Implement TimeDailogListener ");
        }

    }

    public void getStartingDate() {
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStart();
        }

    };

    private void updateLabelStart() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        sDate =(sdf.format(myCalendar.getTime()));
        startdate.setText(sDate);
    }

    private void getEndDate() {
        new DatePickerDialog(getContext(), dateEnd, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelEnd();
        }

    };

    private void updateLabelEnd() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        eDate =(sdf.format(myCalendar.getTime()));
        endDate.setText(eDate);
    }


    public interface TimeDialogListener{
        void passDates(String start,String end, String finishedChecked ,String unfinishedChecked);
    }
}

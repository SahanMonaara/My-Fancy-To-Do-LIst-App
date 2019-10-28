package com.monaara.sahan.fancytodolist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.monaara.sahan.fancytodolist.R;
import com.monaara.sahan.fancytodolist.ViewActivity;
import com.monaara.sahan.fancytodolist.helper.ToDoDbHelper;
import com.monaara.sahan.fancytodolist.model.ToDoItem;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> implements View.OnClickListener {
    private List<ToDoItem> mToDoItemList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView titleTxtV;
        public TextView timeTxtV;
        public Button deleteBtn;
        public View layout;
        private ToDoDbHelper toDoDbHelper;
        private CheckBox completed,finished;
        private AdapterView.OnItemClickListener mItemClickListener;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            titleTxtV = (TextView) v.findViewById(R.id.titleItem);
            timeTxtV = (TextView) v.findViewById(R.id.timeItem);
            deleteBtn = (Button) v.findViewById(R.id.delete);
            completed = (CheckBox) v.findViewById(R.id.completed);
            finished = (CheckBox) v.findViewById(R.id.finished);
            toDoDbHelper = new ToDoDbHelper(mContext);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDoDbHelper.deleteItem(mToDoItemList.get(getAdapterPosition()).getId());
                    remove(getPosition());

                    }
            });

            completed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (completed.isChecked()) {
                            toDoDbHelper.updateCompletedOnly(mToDoItemList.get(getAdapterPosition()).getId(),"completed");
                        }
                        else {
                            toDoDbHelper.updateCompletedOnly(mToDoItemList.get(getAdapterPosition()).getId(),"incomplete");
                        }
                }
            });
            finished.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (finished.isChecked()) {
                            toDoDbHelper.updateFinishedOnly(mToDoItemList.get(getAdapterPosition()).getId(),"finished");
                        }
                        else{
                            toDoDbHelper.updateFinishedOnly(mToDoItemList.get(getAdapterPosition()).getId(),"unfinished");
                        }
                }
            });
        }
    }

    public void remove(int position) {
        mToDoItemList.remove(position);
        notifyItemRemoved(position);
    }

    public ToDoAdapter(List<ToDoItem> myDataset, Context context, RecyclerView recyclerView) {
        mToDoItemList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_to_do, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ToDoAdapter.ViewHolder holder, final int position) {
        //adding data to holder
        final ToDoItem toDoItem = mToDoItemList.get(position);

        String isCompleted = toDoItem.getCompleteStatus();
        String isFinished = toDoItem.getFinishStatus();
        holder.titleTxtV.setText(toDoItem.getTitle());
        holder.timeTxtV.setText(toDoItem.getDate());
        if (isCompleted.equals("completed")){
            holder.completed.setChecked(true);
        }
        if (isFinished.equals("finished")){
            holder.finished.setChecked(true);
        }



    }

    @Override
    public int getItemCount() {
        return mToDoItemList.size();
    }
}

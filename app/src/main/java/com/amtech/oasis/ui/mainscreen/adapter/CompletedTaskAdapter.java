package com.amtech.oasis.ui.mainscreen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amtech.oasis.R;
import com.amtech.oasis.model.AssignStorArr;
import com.amtech.oasis.model.AssignStore;
import com.amtech.oasis.model.StoreDataObj;
import com.amtech.oasis.model.Stores;
import com.amtech.oasis.model.Tasks;

import java.util.ArrayList;
import java.util.List;

public class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder>{

    private Context context;
    private ClickListener clickListener;
    private String taskType;
    private List<Stores> arrayList;
    private List<AssignStorArr>pendingTasksArrayList;

    public CompletedTaskAdapter(Context context, String taskType, List<Stores> arrayList,List<AssignStorArr> pendingTasksArrayList, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        this.taskType = taskType;
        this.arrayList = arrayList;
        this.pendingTasksArrayList = pendingTasksArrayList;
    }

    @NonNull
    @Override
    public CompletedTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_completed_task, parent, false);

        CompletedTaskAdapter.ViewHolder holder = new CompletedTaskAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTaskAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(taskType.equals("completed"))
        {
            holder.tableCompleted.setVisibility(View.VISIBLE);
        }

        if(taskType.equals("pending"))
        {
            holder.tablePending.setVisibility(View.VISIBLE);
            holder.tvPendingTaskName.setText(this.pendingTasksArrayList.get(position).getTaskName());
            holder.tvPendingTaskDate.setText(this.pendingTasksArrayList.get(position).getTaskDate());

            for(int j =0;j<this.pendingTasksArrayList.get(position).getCheckLists().size();j++)
            {
                AppCompatTextView txtName = new AppCompatTextView(context);
//                txtName.setId("28");
                txtName.setEllipsize(TextUtils.TruncateAt.END);
                txtName.setMaxLines(2);
                txtName.setTextSize(12);
                txtName.setText(this.pendingTasksArrayList.get(position).getCheckLists().get(j).getCheckListName());
                holder.layoutCheckList.addView(txtName);
            }
        }

        if(taskType.equals("assigned"))
        {
            holder.tableAllAssigned.setVisibility(View.VISIBLE);
            for(int i =0;i<arrayList.size();i++)
            {
                holder.tvAssignStoreName.setText(arrayList.get(i).getStoreName());
                holder.tvAssignedCountry.setText(arrayList.get(i).getCountry());
                holder.tvAssignedRegion.setText(arrayList.get(i).getRegion());
            }
        }

        holder.imvCompletedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClickCompleted(position);
            }
        });

        holder.imvPendingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClickPending(position);
            }
        });

        holder.imvAssignedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClickAllAssign(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(taskType.equals("assigned"))
        {
            return arrayList.size();
        }
        else if(taskType.equals("pending"))
        {
            return pendingTasksArrayList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imvCompletedView,imvPendingView,imvAssignedView;
        AppCompatTextView tvAssignStoreName,tvPendingTaskName,tvPendingTaskDate,tvAssignedCountry,tvAssignedRegion;
        TableLayout tableCompleted,tablePending,tableAllAssigned;
        LinearLayoutCompat layoutCheckList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imvCompletedView = itemView.findViewById(R.id.imvViewCompletedTask);
            imvPendingView = itemView.findViewById(R.id.imvPendingView);
            imvAssignedView = itemView.findViewById(R.id.imvViewAllAssigned);

            tvAssignStoreName = itemView.findViewById(R.id.tvAssignStoreName);

            tableCompleted = itemView.findViewById(R.id.tableCompleted);
            tablePending = itemView.findViewById(R.id.tablePending);
            tableAllAssigned = itemView.findViewById(R.id.tableAllAssigned);
            tvAssignedCountry = itemView.findViewById(R.id.tvAssignedStoreCountry);
            tvAssignedRegion = itemView.findViewById(R.id.tvAssignedStoreRegion);

            tvPendingTaskName = itemView.findViewById(R.id.tvPendingTaksName);
            tvPendingTaskDate = itemView.findViewById(R.id.tvPendingTaskDate);

            layoutCheckList = itemView.findViewById(R.id.layoutPendingChecklist);
        }
    }

    public interface ClickListener
    {
        void itemClickCompleted(int position);
        void itemClickPending(int position);
        void itemClickAllAssign(int position);
    }
}

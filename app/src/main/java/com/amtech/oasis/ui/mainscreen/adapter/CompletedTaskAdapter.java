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
            if(this.pendingTasksArrayList.get(position).getTaskStatus().equals("Completed"))
            {
                holder.tableCompleted.setVisibility(View.VISIBLE);

                holder.tvCompletedTaskNam.setText(this.pendingTasksArrayList.get(position).getTaskName());
                holder.tvCompletedStartDate.setText(this.pendingTasksArrayList.get(position).getTaskDate());
                holder.tvCompletedEndDate.setText(this.pendingTasksArrayList.get(position).getTaskEndDate());

                for(int j =0;j<this.pendingTasksArrayList.get(position).getCheckLists().size();j++)
                {
                    AppCompatTextView txtName = new AppCompatTextView(context);
//                txtName.setId("28");
                    txtName.setEllipsize(TextUtils.TruncateAt.END);
                    txtName.setMaxLines(2);
                    txtName.setTextSize(12);
                    txtName.setText(this.pendingTasksArrayList.get(position).getCheckLists().get(j).getCheckListName());
                    holder.layoutCompletedCheckList.addView(txtName);
                }
            }
        }

        if(taskType.equals("pending"))
        {
            if(this.pendingTasksArrayList.get(position).getTaskStatus().equals("Pending"))
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
        }

        if(taskType.equals("assigned"))
        {
            holder.tableAllAssigned.setVisibility(View.VISIBLE);
            holder.tvAssignStoreName.setText(arrayList.get(position).getStoreName());
            holder.tvAssignedCountry.setText(arrayList.get(position).getCountry());
            holder.tvAssignedRegion.setText(arrayList.get(position).getRegion());
            holder.tvAssignLat.setText(arrayList.get(position).getStoreLat());
            holder.tvAssignLong.setText(arrayList.get(position).getStoreLong());
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

        else if(taskType.equals("completed"))
        {
            return pendingTasksArrayList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imvCompletedView,imvPendingView,imvAssignedView;
        AppCompatTextView tvAssignStoreName,tvPendingTaskName,tvPendingTaskDate,tvAssignedCountry,tvAssignedRegion
                ,tvAssignLat,tvAssignLong,tvCompletedTaskNam,tvCompletedStartDate,tvCompletedEndDate;
        TableLayout tableCompleted,tablePending,tableAllAssigned;
        LinearLayoutCompat layoutCheckList,layoutCompletedCheckList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imvCompletedView = itemView.findViewById(R.id.imvViewCompletedTask);
            imvPendingView = itemView.findViewById(R.id.imvPendingView);
            imvAssignedView = itemView.findViewById(R.id.imvViewAllAssigned);

            tvAssignStoreName = itemView.findViewById(R.id.tvAssignStoreName);

            tableCompleted = itemView.findViewById(R.id.tableCompleted);
            tvCompletedTaskNam = itemView.findViewById(R.id.tvCompletedTaskName);
            tvCompletedStartDate = itemView.findViewById(R.id.tvCompletedStartDate);
            tvCompletedEndDate = itemView.findViewById(R.id.tvCompletedEndDate);
            layoutCompletedCheckList = itemView.findViewById(R.id.tvCompletedCheckLayout);

            tablePending = itemView.findViewById(R.id.tablePending);
            tableAllAssigned = itemView.findViewById(R.id.tableAllAssigned);
            tvAssignedCountry = itemView.findViewById(R.id.tvAssignedStoreCountry);
            tvAssignedRegion = itemView.findViewById(R.id.tvAssignedStoreRegion);
            tvAssignLat = itemView.findViewById(R.id.tvAllAssignedLat);
            tvAssignLong = itemView.findViewById(R.id.tvAllAssignedLong);

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

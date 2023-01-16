package com.amtech.oasis.ui.mainscreen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amtech.oasis.R;
import com.amtech.oasis.model.AssignStorArr;
import com.amtech.oasis.model.AssignStore;
import com.amtech.oasis.model.StoreDataObj;
import com.amtech.oasis.model.Stores;
import com.amtech.oasis.model.Tasks;
import com.amtech.oasis.ui.detailscreen.dialog.CompletedDialog;
import com.squareup.picasso.Picasso;

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

                /*for(int j =0;j<this.pendingTasksArrayList.get(position).getCheckLists().size();j++)
                {
                    AppCompatTextView txtName = new AppCompatTextView(context);
//                txtName.setId("28");
                    txtName.setEllipsize(TextUtils.TruncateAt.END);
                    txtName.setMaxLines(2);
                    txtName.setTextSize(12);
                    txtName.setText(this.pendingTasksArrayList.get(position).getCheckLists().get(j).getCheckListName());
                    holder.layoutCompletedCheckList.addView(txtName);
                }*/

                for(int j =0;j<this.pendingTasksArrayList.get(position).getCheckLists().size();j++)
                {
                    LinearLayout parent = new LinearLayout(context);
                    parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
                    parent.setOrientation(LinearLayout.HORIZONTAL);

                    AppCompatTextView txtName = new AppCompatTextView(context);
                    txtName.setEllipsize(TextUtils.TruncateAt.END);
                    txtName.setMaxLines(2);
                    txtName.setTextSize(10);
                    txtName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    txtName.setText(this.pendingTasksArrayList.get(position).getCheckLists().get(j).getCheckListName());

                    holder.layoutCompletedCheckList.addView(txtName);
                    holder.layoutCompletedCheckList.addView(parent);

                    if(this.pendingTasksArrayList.get(position).getCheckLists().get(j).getImageCheck().equals("0"))
                    {
                        AppCompatTextView txtYes = new AppCompatTextView(context);
                        txtYes.setTextSize(8);
                        txtYes.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        AppCompatTextView txtNo = new AppCompatTextView(context);
                        txtNo.setTextSize(8);
                        txtNo.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        AppCompatImageView imvYes = new AppCompatImageView(context);
                        AppCompatImageView imvNo = new AppCompatImageView(context);

                        txtYes.setText("Yes");
                        txtNo.setText("No");
                        imvYes.setImageResource(R.drawable.icon_yes);
                        imvNo.setImageResource(R.drawable.icon_no);

                        if(this.pendingTasksArrayList.get(position).getCheckLists().get(j).getImage().equals("yes"))
                        {
                            parent.addView(imvYes);
                            parent.addView(txtYes);
                            parent.addView(imvNo);
                            parent.addView(txtNo);
                        }
                        else
                        {
                            parent.addView(imvNo);
                            parent.addView(txtYes);
                            parent.addView(imvYes);
                            parent.addView(txtNo);
                        }
                    }
                    else
                    {
                        AppCompatTextView txtImage = new AppCompatTextView(context);
                        txtImage.setEllipsize(TextUtils.TruncateAt.END);
                        txtImage.setMaxLines(1);
                        txtImage.setTextSize(8);
                        txtImage.setTextColor(ContextCompat.getColor(context, R.color.oceanBlue));
                        txtImage.setText(this.pendingTasksArrayList.get(position).getCheckLists().get(j).getImage());
                        txtImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clickListener.showDialog(txtImage.getText().toString());
                            }
                        });
                        holder.layoutCompletedCheckList.addView(txtImage);
                    }
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
            holder.tvAssignedId.setText(arrayList.get(position).getStoreId());
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
                clickListener.itemClickPending(position,"view");
            }
        });

        holder.imvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClickPending(position,"update");
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

        ImageView imvCompletedView,imvPendingView,imvAssignedView,imvUpdate;
        AppCompatTextView tvAssignStoreName,tvPendingTaskName,tvPendingTaskDate,tvAssignedCountry,tvAssignedRegion
                ,tvAssignLat,tvAssignLong,tvAssignedId,tvCompletedTaskNam,tvCompletedStartDate,tvCompletedEndDate;
        TableLayout tableCompleted,tablePending,tableAllAssigned;
        LinearLayoutCompat layoutCheckList,layoutCompletedCheckList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imvCompletedView = itemView.findViewById(R.id.imvViewCompletedTask);
            imvPendingView = itemView.findViewById(R.id.imvPendingView);
            imvAssignedView = itemView.findViewById(R.id.imvViewAllAssigned);
            imvUpdate = itemView.findViewById(R.id.imvUpdate);

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
            tvAssignedId = itemView.findViewById(R.id.tvAssignedStoreId);

            tvPendingTaskName = itemView.findViewById(R.id.tvPendingTaksName);
            tvPendingTaskDate = itemView.findViewById(R.id.tvPendingTaskDate);

            layoutCheckList = itemView.findViewById(R.id.layoutPendingChecklist);
        }
    }

    public interface ClickListener
    {
        void itemClickCompleted(int position);
        void itemClickPending(int position,String update);
        void itemClickAllAssign(int position);
        void showDialog(String imageUrl);
    }
}

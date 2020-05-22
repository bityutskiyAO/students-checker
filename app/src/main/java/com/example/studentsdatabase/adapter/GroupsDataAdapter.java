package com.example.studentsdatabase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsdatabase.R;
import com.example.studentsdatabase.entity.Group;

import java.util.List;

public class GroupsDataAdapter extends RecyclerView.Adapter<GroupsDataAdapter.GroupsViewHolder> {

    public List<Group> groups;

    public GroupsDataAdapter(List<Group> groups) {
        this.groups = groups;
    }

    @NonNull
    @Override
    public GroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_group, parent, false);
        return new GroupsDataAdapter.GroupsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupsViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.faculty.setText(group.faculty);
        holder.groupNumber.setText(group.groupNumber);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    class GroupsViewHolder extends RecyclerView.ViewHolder {
        private TextView faculty, groupNumber;

        public GroupsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.faculty = itemView.findViewById(R.id.textFaculty);
            this.groupNumber = itemView.findViewById(R.id.textGroupNumber);
        }
    }
}

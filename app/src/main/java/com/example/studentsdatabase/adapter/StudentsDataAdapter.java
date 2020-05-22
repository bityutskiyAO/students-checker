package com.example.studentsdatabase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentsdatabase.R;
import com.example.studentsdatabase.entity.Student;

import java.util.List;

public class StudentsDataAdapter extends RecyclerView.Adapter<StudentsDataAdapter.StudentsViewHolder> {
    public List<Student> students;

    public StudentsDataAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student, parent, false);
        return new StudentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder holder, int position) {
        Student student = students.get(position);
        holder.name.setText(student.name);
        holder.surname.setText(student.surname);
        holder.middleName.setText(student.middleName);
        holder.group.setText(student.group);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentsViewHolder extends RecyclerView.ViewHolder {
        private TextView name, surname, middleName, group;

        public StudentsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.studentName);
            this.surname = itemView.findViewById(R.id.studentSurname);
            this.middleName = itemView.findViewById(R.id.studentMiddleName);
            this.group = itemView.findViewById(R.id.groupTitle);
        }
    }
}

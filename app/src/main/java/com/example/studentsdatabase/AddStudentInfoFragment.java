package com.example.studentsdatabase;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsdatabase.database.DataBase;
import com.example.studentsdatabase.entity.Student;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddStudentInfoFragment extends Fragment {

    final DataBase dataBase = new DataBase();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_student_info_fragment, container, false);
        view.setVisibility(View.GONE);

        Button addButton = view.findViewById(R.id.addStudentBtn);
        final EditText name = view.findViewById(R.id.addName);
        final EditText surname = view.findViewById(R.id.addSurname);
        final EditText middleName = view.findViewById(R.id.addMiddleName);
        final EditText group = view.findViewById(R.id.addGroup);
        final TextView studentKey = view.findViewById(R.id.hideStudentKey);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentKey.getText().toString().equals("")) {
                    if (!name.getText().toString().equals("") && !surname.getText().toString().equals("") && !middleName.getText().toString().equals("") && !group.getText().toString().equals("")) {
                        Student newStudent = new Student(name.getText().toString(), surname.getText().toString(), middleName.getText().toString(), group.getText().toString());
                        DatabaseReference reference = dataBase.createRef("students");
                        reference.push().setValue(newStudent);
                        name.setText("");
                        surname.setText("");
                        group.setText("");
                        middleName.setText("");
                    } else {
                        Toast toast = Toast.makeText(view.getContext(), "Введите все данные для добавления студента в базу данных.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 180);
                        toast.show();
                    }
                } else {
                    DatabaseReference reference = dataBase.createRef("students").child(studentKey.getText().toString());
                    Student newStudent = new Student(name.getText().toString(), surname.getText().toString(), middleName.getText().toString(), group.getText().toString());
                    reference.setValue(newStudent);
                    name.setText("");
                    surname.setText("");
                    group.setText("");
                    middleName.setText("");
                }
            }
        });

        return view;
    }


}

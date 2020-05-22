package com.example.studentsdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.studentsdatabase.adapter.StudentsDataAdapter;
import com.example.studentsdatabase.database.DataBase;
import com.example.studentsdatabase.entity.Student;
import com.example.studentsdatabase.swipeConroller.SwipeController;
import com.example.studentsdatabase.swipeConroller.SwipeControllerActions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentsActivity extends AppCompatActivity {
    final private DataBase dataBase = new DataBase();
    private StudentsDataAdapter adapter;
    private Button addButton;
    private Button deleteButton;
    private ImageButton sortButton;
    private ArrayList<String> studentsKeysList = new ArrayList<>();
    private List<Student> studentsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        //create database ref
        final DatabaseReference dbReference = dataBase.createRef("students");

        // Нижняя навигация
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setSelectedItemId(R.id.navStudents);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navStudents: {
                        return true;
                    }
                    case R.id.navGroup: {
                        startActivity(new Intent(getApplicationContext(), GroupsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
                return false;
            }
        });


        Query query = dbReference;
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Student student = dataSnapshot.getValue(Student.class);
                studentsKeysList.add(dataSnapshot.getKey());
                studentsList.add(student);
                adapter = new StudentsDataAdapter(studentsList);
                setupRecyclerView(dbReference);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Student student = dataSnapshot.getValue(Student.class);
                studentsKeysList.add(dataSnapshot.getKey());
                studentsList.remove(studentsKeysList.indexOf(dataSnapshot.getKey()));
                studentsList.add(studentsKeysList.indexOf(dataSnapshot.getKey()), student);
                adapter = new StudentsDataAdapter(studentsList);
                setupRecyclerView(dbReference);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Отображение/удаление окна с добавлением студента
        addButton = findViewById(R.id.studentAddBtn);
        deleteButton = findViewById(R.id.studentCloseBtn);
        sortButton = findViewById(R.id.studentSortBtn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
                bottomNavigationView.setVisibility(View.GONE);

                ImageView imageView = findViewById(R.id.grayBackground);
                imageView.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.GONE);
                sortButton.setVisibility(View.GONE);

                AddStudentInfoFragment addFragment = (AddStudentInfoFragment) getSupportFragmentManager().findFragmentById(R.id.addStudentFragment);
                addFragment.getView().setVisibility(View.VISIBLE);

                deleteButton.setVisibility(View.VISIBLE);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
                bottomNavigationView.setVisibility(View.VISIBLE);

                ImageView imageView = findViewById(R.id.grayBackground);
                imageView.setVisibility(View.GONE);
                addButton.setVisibility(View.VISIBLE);
                sortButton.setVisibility(View.VISIBLE);

                AddStudentInfoFragment addFragment = (AddStudentInfoFragment) getSupportFragmentManager().findFragmentById(R.id.addStudentFragment);
                addFragment.getView().setVisibility(View.GONE);

                deleteButton.setVisibility(View.GONE);
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference dbReference = dataBase.createRef("students");
                Query query = dbReference.orderByChild("surname");
                studentsList.clear();
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Student student = dataSnapshot.getValue(Student.class);
                        studentsList.add(student);
                        adapter = new StudentsDataAdapter(studentsList);
                        setupRecyclerView(dbReference);

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void setupRecyclerView(final DatabaseReference dbReference) {
        RecyclerView recyclerView = findViewById(R.id.studentsListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
                bottomNavigationView.setVisibility(View.GONE);

                ImageView imageView = findViewById(R.id.grayBackground);
                imageView.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.GONE);
                sortButton.setVisibility(View.GONE);

                AddStudentInfoFragment addFragment = (AddStudentInfoFragment) getSupportFragmentManager().findFragmentById(R.id.addStudentFragment);
                View view = addFragment.getView();
                view.setVisibility(View.VISIBLE);

                EditText name = view.findViewById(R.id.addName);
                EditText surname = view.findViewById(R.id.addSurname);
                EditText middleName = view.findViewById(R.id.addMiddleName);
                EditText group = view.findViewById(R.id.addGroup);
                TextView studentKey = view.findViewById(R.id.hideStudentKey);

                Student editStudent = studentsList.get(position);

                studentKey.setText(studentsKeysList.get(position));
                name.setText(editStudent.name);
                surname.setText(editStudent.surname);
                middleName.setText(editStudent.middleName);
                group.setText(editStudent.group);

                deleteButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRightClicked(int position) {
                dbReference.child(studentsKeysList.get(position)).removeValue();
                adapter.students.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}

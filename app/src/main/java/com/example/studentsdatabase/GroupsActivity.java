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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studentsdatabase.adapter.GroupsDataAdapter;
import com.example.studentsdatabase.adapter.StudentsDataAdapter;
import com.example.studentsdatabase.database.DataBase;
import com.example.studentsdatabase.entity.Group;
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

public class GroupsActivity extends AppCompatActivity {

    private DataBase dataBase = new DataBase();
    private Button addButton;
    private Button deleteButton;
    private GroupsDataAdapter adapter;
    private List<Group> groupList = new ArrayList<>();
    private List<String> groupsKeysList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        final DatabaseReference dbReference = dataBase.createRef("groups");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.navGroup);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navStudents: {
                        startActivity(new Intent(getApplicationContext(), StudentsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    case R.id.navGroup: {
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
                Group group = dataSnapshot.getValue(Group.class);
                groupsKeysList.add(dataSnapshot.getKey());
                groupList.add(group);
                adapter = new GroupsDataAdapter(groupList);
                setupRecyclerView(dbReference);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Group group = dataSnapshot.getValue(Group.class);
                groupsKeysList.add(dataSnapshot.getKey());
                groupList.remove(groupsKeysList.indexOf(dataSnapshot.getKey()));
                groupList.add(groupsKeysList.indexOf(dataSnapshot.getKey()), group);
                adapter = new GroupsDataAdapter(groupList);
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

        addButton = findViewById(R.id.groupsAddButton);
        deleteButton = findViewById(R.id.groupCloseButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
                bottomNavigationView.setVisibility(View.GONE);

                ImageView imageView = findViewById(R.id.grayBackground);
                imageView.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.GONE);

                AddGroupInfoFragment addFragment = (AddGroupInfoFragment) getSupportFragmentManager().findFragmentById(R.id.addGroupFragment);
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

                AddGroupInfoFragment addFragment = (AddGroupInfoFragment) getSupportFragmentManager().findFragmentById(R.id.addGroupFragment);
                addFragment.getView().setVisibility(View.GONE);

                deleteButton.setVisibility(View.GONE);
            }
        });


    }
    private void setupRecyclerView(final DatabaseReference dbReference) {
        RecyclerView recyclerView = findViewById(R.id.groupsListView);
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

                AddGroupInfoFragment addFragment = (AddGroupInfoFragment) getSupportFragmentManager().findFragmentById(R.id.addGroupFragment);
                View view = addFragment.getView();
                view.setVisibility(View.VISIBLE);

                EditText faculty = view.findViewById(R.id.addFaculty);
                EditText groupNumber = view.findViewById(R.id.addGroup);
                TextView groupKey = view.findViewById(R.id.hideGroupKey);

                Group editGroup = groupList.get(position);

                groupKey.setText(groupsKeysList.get(position));
                faculty.setText(editGroup.faculty);
                groupNumber.setText(editGroup.groupNumber);

                deleteButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRightClicked(int position) {
                dbReference.child(groupsKeysList.get(position)).removeValue();
                adapter.groups.remove(position);
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

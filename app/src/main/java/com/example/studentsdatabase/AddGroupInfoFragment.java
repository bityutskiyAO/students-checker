package com.example.studentsdatabase;

import android.os.Bundle;

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
import com.example.studentsdatabase.entity.Group;
import com.example.studentsdatabase.entity.Student;
import com.google.firebase.database.DatabaseReference;


public class AddGroupInfoFragment extends Fragment {

    final DataBase dataBase = new DataBase();
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddGroupInfoFragment() {
        // Required empty public constructor
    }

    public static AddGroupInfoFragment newInstance() {
        AddGroupInfoFragment fragment = new AddGroupInfoFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_group_info, container, false);
        view.setVisibility(View.GONE);

        Button addButton = view.findViewById(R.id.addGroupBtn);
        final EditText faculty = view.findViewById(R.id.addFaculty);
        final EditText groupNumber = view.findViewById(R.id.addGroup);

        final TextView groupKey = view.findViewById(R.id.hideGroupKey);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupKey.getText().toString().equals("")) {
                    if (!faculty.getText().toString().equals("") && !groupNumber.getText().toString().equals("")){
                        Group newGroup = new Group(faculty.getText().toString(), groupNumber.getText().toString());
                        DatabaseReference reference = dataBase.createRef("groups");
                        reference.push().setValue(newGroup);
                        faculty.setText("");
                        groupNumber.setText("");
                    } else {
                        Toast toast = Toast.makeText(view.getContext(), "Введите все данные для добавления группы в базу данных.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 180);
                        toast.show();
                    }
                } else {
                    DatabaseReference reference = dataBase.createRef("groups").child(groupKey.getText().toString());
                    Group editGroup = new Group(faculty.getText().toString(), groupNumber.getText().toString());
                    reference.setValue(editGroup);
                    faculty.setText("");
                    groupNumber.setText("");
                }
            }
        });

        return view;
    }
}

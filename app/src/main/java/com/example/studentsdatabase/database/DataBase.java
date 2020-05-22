package com.example.studentsdatabase.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBase {
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public DataBase() {
        this.database = FirebaseDatabase.getInstance();
    }

    public DataBase(DatabaseReference reference) {
        this.database = FirebaseDatabase.getInstance();
        this.reference = reference;
    }

    public DatabaseReference createRef(String refName) {
        return database.getReference(refName);
    }
}

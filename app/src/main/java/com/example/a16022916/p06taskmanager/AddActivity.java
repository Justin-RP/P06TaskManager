package com.example.a16022916.p06taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    public DatabaseHelper db = new DatabaseHelper(this);

    EditText etName, etDesc, etTime;
    Button btnAddTask, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent i = getIntent();

        etName = findViewById(R.id.addName);
        etDesc = findViewById(R.id.addDesc);
        btnAddTask = findViewById(R.id.addAddTask);
        btnCancel = findViewById(R.id.addCancel);
        etTime = findViewById(R.id.addTime);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                String strName = etName.getText().toString();
                String strDesc = etDesc.getText().toString();
                int intTime = Integer.valueOf(etTime.getText().toString());
                ArrayList<Item> al = db.getAllItems();
                int intId = al.size() + 1;
                db.addData(new Item(intId,strName,strDesc,intTime));
                setResult(RESULT_OK,i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

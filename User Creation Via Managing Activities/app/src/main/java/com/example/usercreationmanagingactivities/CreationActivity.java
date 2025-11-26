package com.example.usercreationmanagingactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
/*
Assignment 3
Ayden Hocking
CreationActivity.java
 */
public class CreationActivity extends AppCompatActivity {
    EditText editTextName, editTextEmailAddress;
    RadioGroup radioGroupRole;
    String roleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creation);
        editTextName = findViewById(R.id.editTextName);
        editTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        radioGroupRole = findViewById(R.id.radioGroupRole);
        editTextName.setText("");
        editTextEmailAddress.setText("");
        radioGroupRole.clearCheck();

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = editTextName.getText().toString();
                String emailText = editTextEmailAddress.getText().toString();
                int checkedDept = radioGroupRole.getCheckedRadioButtonId();

                if(nameText.isEmpty()){
                    Toast.makeText(CreationActivity.this, "Invalid Name Setup", Toast.LENGTH_SHORT).show();
                }else if (emailText.isEmpty()) {
                    Toast.makeText(CreationActivity.this, "Invalid Email Setup", Toast.LENGTH_SHORT).show();
                }else if(checkedDept == -1){
                    Toast.makeText(CreationActivity.this, "Select a Role", Toast.LENGTH_SHORT).show();
                }else{
                    RadioButton selectedDept = findViewById(checkedDept);
                    roleText = selectedDept.getText().toString();
                    Intent intent = new Intent(CreationActivity.this, ProfileActivity.class);
                    User user = new User(nameText,emailText,roleText);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
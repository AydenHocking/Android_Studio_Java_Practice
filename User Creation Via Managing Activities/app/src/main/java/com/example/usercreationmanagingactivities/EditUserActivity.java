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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
/*
Assignment 3
Ayden Hocking
EditUserActivity.java
 */
public class EditUserActivity extends AppCompatActivity {
    EditText editTextName, editTextEmailAddress;
    RadioGroup radioGroupRole;
    String roleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        editTextName = findViewById(R.id.editTextName);
        editTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        radioGroupRole = findViewById(R.id.radioGroupRole);
        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            editTextName.setText(user.getName());
            editTextEmailAddress.setText(user.getEmail());
            String radioButtonRoleText = user.getRole().toString();
            if (radioButtonRoleText.equals( "Student")){
                radioGroupRole.check(R.id.radioButtonStudent);
            }else if(radioButtonRoleText.equals("Employee")){
                radioGroupRole.check(R.id.radioButtonEmployee);
            }else{
                radioGroupRole.check(R.id.radioButtonOther);
            }
        }
        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = editTextName.getText().toString();
                String emailText = editTextEmailAddress.getText().toString();
                int checkedDept = radioGroupRole.getCheckedRadioButtonId();

                if(nameText.isEmpty()){
                    Toast.makeText(EditUserActivity.this, "Invalid Name Setup", Toast.LENGTH_SHORT).show();
                }else if (emailText.isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "Invalid Email Setup", Toast.LENGTH_SHORT).show();
                }else if(checkedDept == -1){
                    Toast.makeText(EditUserActivity.this, "Select a Role", Toast.LENGTH_SHORT).show();
                }else {
                    RadioButton selectedDept = findViewById(checkedDept);
                    roleText = selectedDept.getText().toString();
                    User user = new User(nameText,emailText,roleText);
                    Intent intent = new Intent();
                    intent.putExtra("user",user);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }
}
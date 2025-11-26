package com.example.usercreationmanagingactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
/*
Assignment 3
Ayden Hocking
ProfileActivity.java
 */
public class ProfileActivity extends AppCompatActivity {
    TextView textViewProfileName, textViewProfileEmail, textViewProfileRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        textViewProfileRole = findViewById(R.id.textViewRole);
        textViewProfileEmail = findViewById(R.id.textViewEmail);
        textViewProfileName = findViewById(R.id.textViewName);
        if(getIntent() != null && getIntent().getExtras() != null){

            User user = (User) getIntent().getSerializableExtra("user");
            textViewProfileName.setText(user.name);
            textViewProfileEmail.setText(user.email);
            textViewProfileRole.setText(user.role);
        }
        ActivityResultLauncher<Intent> startDepartmentActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    User user = (User) result.getData().getSerializableExtra("user");
                    if (user != null) {
                        textViewProfileRole.setText(user.getRole());
                        textViewProfileName.setText(user.getName());
                        textViewProfileEmail.setText(user.getEmail());
                    }
                }
            }
        });

        findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditUserActivity.class);
                User user = new User(textViewProfileName.getText().toString(), textViewProfileEmail.getText().toString(), textViewProfileRole.getText().toString());
                intent.putExtra("user", user);
                startDepartmentActivityForResult.launch(intent);
            }
        });

    }

}
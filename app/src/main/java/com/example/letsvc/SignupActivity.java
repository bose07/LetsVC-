package com.example.letsvc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText emailBox, passwordBox , nameBox ;
    Button loginBtn, signupBtn;

    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        emailBox =    findViewById(R.id.emailBox);
        nameBox = findViewById(R.id.nameBox);
        passwordBox = findViewById(R.id.passwordBox);
        loginBtn =    findViewById(R.id.loginBtn);
        signupBtn =   findViewById(R.id.createBtn);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass, name;
                name = nameBox.getText().toString();
                email = emailBox.getText().toString();
                pass =  passwordBox.getText().toString();


                final User user = new User();
                user.getName(name);
                user.setEmail(email);
                user.setPassword(pass);




                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                              if(task.isSuccessful()){
                                  database.collection("User").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {
                                                 startActivities(new Intent[]{new Intent(SignupActivity.this, LoginActivity.class)});
                                      }
                                  }) ;
                                  Toast.makeText(SignupActivity.this, "Account is create", Toast.LENGTH_SHORT).show();

                              } else{

                                  Toast.makeText( SignupActivity.this,  task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                              }
                    }
                });


            }
        });




         }
}
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity

{
    EditText mFullName,mEmail,mPassword,mPhone;
            Button mRegisterbtn;
            TextView mLoginBtn;
            String st;
            FirebaseAuth fAuth;


    public EditText getmPhone() {
        return mPhone;
    }

    public void setmPhone(EditText mPhone) {
        this.mPhone = mPhone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterbtn = findViewById(R.id.registerbtn);
        mLoginBtn = findViewById(R.id.createtext);



        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
              finish();
        }

        mRegisterbtn.setOnClickListener(new View.OnClickListener()
    {
            @Override
            public void onClick(View v)
            {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String name = mFullName.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }

                 if(TextUtils.isEmpty(password)) {
                     mPassword.setError("Password is required");
                     return;
                 }

                if (password.length()<8)
                {
                    mPassword.setError("Password needs to be >= 8");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            FirebaseDatabase.getInstance().getReference().child(name).child("PhoneNumber").setValue(phone);
                            FirebaseDatabase.getInstance().getReference().child(name).child("Email").setValue(email);
                            FirebaseDatabase.getInstance().getReference().child(name).child("Email").setValue(name);

                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Register.this, "Error Occured"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }


        });
                       mLoginBtn.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               startActivity(new Intent(getApplicationContext(),Login.class));


                           }
                       });

    }
}
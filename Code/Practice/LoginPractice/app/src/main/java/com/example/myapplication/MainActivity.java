package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    String st,eNAME;
    FirebaseDatabase fAuth1;
    FirebaseAuth FA;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fAuth1 = FirebaseDatabase.getInstance();
        FA = FirebaseAuth.getInstance();
        tv =findViewById(R.id.textV);

       st= FA.getCurrentUser().getEmail();
       eNAME = st.replaceAll("@gmail.com","");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference(eNAME);
        Query checkUser = database.orderByChild("PhoneNumber");
        tv.setText("123456789");
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phone = snapshot.child("PhoneNumber").getValue(String.class);
                tv.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void LogOut(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}
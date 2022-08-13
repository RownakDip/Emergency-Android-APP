package com.example.lifesaverric;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Call extends android.app.Activity {
    private Button button,btn;
    FirebaseAuth fAuth;
    String PHONE,st,eNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        fAuth = FirebaseAuth.getInstance();
      TextView tv =findViewById(R.id.textV);
         String s = fAuth.getCurrentUser().getEmail().toString();
         tv.setText("Logged in as: "+s);

       // fAuth = FirebaseAuth.getInstance();
        //tv =findViewById(R.id.textV);

        st= fAuth.getCurrentUser().getEmail();
        eNAME = st.replaceAll("@gmail.com","");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference(eNAME);
        Query checkUser = database.orderByChild("PhoneNumber");

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String phone = snapshot.child("PhoneNumber").getValue(String.class);
                PHONE=phone;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel: "+PHONE));
                if (ActivityCompat.checkSelfPermission(Call.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
               // tv.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


                button = findViewById(R.id.logout);
                btn= findViewById(R.id.btn);
//fAuth.getCurrentUser().getPhoneNumber();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                startActivity(new Intent(Call.this,HomePage.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                fAuth.signOut();
                startActivity(new Intent(Call.this,Login.class));
            }
        });

            }


    }

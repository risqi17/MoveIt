package com.moveit.moveit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth firebaseAuth;
    TextView textViewUserEmail;
    Button btLogout, btMove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), RealLogin.class));
        }

        textViewUserEmail = (TextView)findViewById(R.id.textUserEmail);
        btLogout = (Button)findViewById(R.id.btLogout);
        btLogout.setOnClickListener(this);

        btMove = (Button)findViewById(R.id.btMove);
        btMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LetsMove.class));
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();
            textViewUserEmail.setText("Welcome "+email);

        }
    }

    private void userLogout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), RealLogin.class));
    }

    @Override
    public void onClick(View v) {
        if (v == btLogout){
            userLogout();
        }
    }
}

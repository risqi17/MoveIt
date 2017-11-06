package com.moveit.moveit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity implements View.OnClickListener {
    EditText editEmail;
    EditText editPass;
    Button btSignUp;
    TextView toLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    String em;
    String nama;
    String password;

    private DatabaseReference databaseReference;
    EditText editNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager. LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");

        progressDialog = new ProgressDialog(this);

        editEmail = (EditText)findViewById(R.id.editEmail);
        editPass = (EditText)findViewById(R.id.editPass);
        editNama = (EditText)findViewById(R.id.editName);
        toLogin = (TextView)findViewById(R.id.toLogin);

        btSignUp = (Button)findViewById(R.id.btSignUp);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, RealLogin.class);
                startActivity(i);

            }
        });

        btSignUp.setOnClickListener(this);


    }

    private void registerUser(){
        String email = editEmail.getText().toString().trim();
        password = editPass.getText().toString().trim();
        nama = editNama.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nama)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

       // progressDialog.setMessage("Signing up...");
        // progressDialog.show();

        int etEmail = email.length();
        em = email.substring(0, etEmail-4);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Map<String, Object> map = new HashMap<String, Object>();

                        databaseReference.updateChildren(map);

                        DatabaseReference message_root = databaseReference.child(em);
                        Map<String, Object> map2 = new HashMap<String, Object>();
                        map2.put("nama", nama);
                        map2.put("status", "0");
                        map2.put("jam", "");
                        map2.put("lokasi", "");
                        map2.put("olahraga", "");
                        map2.put("tgl", "");
                        map2.put("lat", "");
                        map2.put("long", "");


                        message_root.updateChildren(map2);
                        Toast.makeText(Login.this, "Register successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), RealLogin.class));
                    } else {
                        Toast.makeText(Login.this, "Register failure", Toast.LENGTH_SHORT).show();
                    }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if (v == btSignUp){
            registerUser();
        }
    }
}

package com.moveit.moveit;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

public class RealLogin extends Activity implements View.OnClickListener {
    TextView toSignUp;
    EditText editEmail, editPass;
    Button btLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager. LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_real_login);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ListTemanAktif.class));
        }
        toSignUp = (TextView)findViewById(R.id.toSignup);
        toSignUp.setOnClickListener(this);

        editEmail = (EditText)findViewById(R.id.editEmail);
        editPass = (EditText)findViewById(R.id.editPass);
        btLogin = (Button)findViewById(R.id.btSignIn);
        btLogin.setOnClickListener(this);

    }

    private void userLogin(){
        String email = editEmail.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(RealLogin.this, "Sign in failed",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            finish();
                            startActivity(new Intent(getApplicationContext(), ListTemanAktif.class));
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {
     if (v == btLogin){
         userLogin();
     }
     if (v == toSignUp){
         Intent intent = new Intent(RealLogin.this, Login.class);
         startActivity(intent);
     }
    }
}

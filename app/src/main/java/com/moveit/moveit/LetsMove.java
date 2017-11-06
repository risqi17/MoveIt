package com.moveit.moveit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LetsMove extends AppCompatActivity implements View.OnClickListener {

    EditText editOr, editJam, editMenit;
    TextView txtLocation;
    Button btSimpan;
    String email, alamat, lat, longi, temp_keyChat;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, dbChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_move);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Lets Move");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        dbChat = FirebaseDatabase.getInstance().getReference().child("chat");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        alamat = getIntent().getStringExtra("alamat");
        lat = getIntent().getStringExtra("lat");
        longi = getIntent().getStringExtra("longi");

        editOr = (EditText)findViewById(R.id.editOr);
        editJam = (EditText)findViewById(R.id.editJam);
        editMenit = (EditText)findViewById(R.id.editMenit);
        btSimpan = (Button)findViewById(R.id.btSimpan);
        btSimpan.setOnClickListener(this);

        txtLocation = (TextView) findViewById(R.id.txtLocation);

        txtLocation.setText(alamat);
    }

    private void moveSave(){
        String olahraga = editOr.getText().toString().trim();
        String jam = editJam.getText().toString().trim();
        String menit = editMenit.getText().toString().trim();

        int etEmail = email.length();
        String em = email.substring(0, etEmail-4);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String tgl = dateFormat.format(date);

        Map<String, Object> map = new HashMap<String, Object>();
        databaseReference.updateChildren(map);

        DatabaseReference message_root = databaseReference.child(em);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("status", "0");
        map2.put("jam", jam+"."+menit);
        map2.put("lokasi", alamat);
        map2.put("tgl", tgl);
        map2.put("lat", lat);
        map2.put("long", longi);
        map2.put("olahraga", olahraga);

        message_root.updateChildren(map2);

        //FIREBASE TO CHAT
        DatabaseReference message_rootChat1 = dbChat.child(alamat);
        Map<String, Object> mapChat1 = new HashMap<String, Object>();
        //mapChat1.put("email", em);
        message_rootChat1.updateChildren(mapChat1);

        Map<String, Object> mapChat = new HashMap<String, Object>();
        temp_keyChat = dbChat.child(alamat).push().getKey();
        dbChat.updateChildren(mapChat);

        DatabaseReference message_rootChat = dbChat.child(alamat).child(temp_keyChat);
        Map<String, Object> mapChat2 = new HashMap<String, Object>();
        mapChat2.put("email", em);
        mapChat2.put("jam", jam+"."+menit);
        mapChat2.put("pesan", "Telah masuk obrolan");
        mapChat2.put("tgl", tgl);

        message_rootChat.updateChildren(mapChat2);

        dbChat.child(em).removeValue();

        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == btSimpan){
            moveSave();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

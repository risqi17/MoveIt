package com.moveit.moveit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moveit.moveit.adapters.ChatAdapter;
import com.moveit.moveit.settergetters.ChatSetGet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chat extends AppCompatActivity {

    private String room_name, temp_key, jam, tgl;
    private EditText edtPesan;
    private ImageView imgKirim;
    private ArrayList listItem = new ArrayList<>();
    private ListView lvPesan;
    private DatabaseReference dbRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getStringExtra("lokasi"));

        lvPesan = (ListView) findViewById(R.id.lvPesan);
        listItem = new ArrayList<HashMap<String, String>>();
        edtPesan = (EditText) findViewById(R.id.edtPesan);
        imgKirim = (ImageView) findViewById(R.id.imgKirim);
        room_name = getIntent().getStringExtra("lokasi");

        //get Time
        Calendar c = Calendar.getInstance();
        final SimpleDateFormat dfjam = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dftgl = new SimpleDateFormat("dd-MM-yyyy");
        jam = dfjam.format(c.getTime());
        tgl = dftgl.format(c.getTime());


        dbRoot = FirebaseDatabase.getInstance().getReference().child("chat").child(room_name);
        //queryFirebase = dbRoot.orderByChild("status").equalTo("2");

        imgKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp_key = "";
                if (TextUtils.isEmpty(edtPesan.getText())){
                    edtPesan.setError("Pesan harus diisi");
                    edtPesan.requestFocus();
                }else {
                    kirimPesan();
                }
            }
        });

        dbRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getPesan(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getPesan(DataSnapshot dataSnapshot){
        Iterator iterator = dataSnapshot.getChildren().iterator();
        ChatSetGet varGetSet = null;

        while(iterator.hasNext()){
            varGetSet = new ChatSetGet();

            varGetSet.setEmail(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setJam(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setPesan(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setTgl(((DataSnapshot)iterator.next()).getValue().toString());

            listItem.add(varGetSet);
        }

        ListAdapter adapter = new ChatAdapter(Chat.this, listItem);
        lvPesan.setAdapter(adapter);
        lvPesan.setSelection(listItem.size() - 1);
        lvPesan.smoothScrollToPosition(listItem.size() - 1);
    }

    private void kirimPesan(){
        //FIREBASE
        Map<String, Object> map = new HashMap<String, Object>();
        temp_key = dbRoot.push().getKey();
        dbRoot.updateChildren(map);

        DatabaseReference message_root = dbRoot.child(temp_key);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("email", "user@user");
        map2.put("jam", jam);
        map2.put("pesan", edtPesan.getText().toString());
        map2.put("tgl", tgl);

        message_root.updateChildren(map2);

        edtPesan.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public class ListenChat extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            //FIREBASE
            /*Map<String, Object> map = new HashMap<String, Object>();
            temp_key = dbRoot.push().getKey();
            dbRoot.updateChildren(map);

            DatabaseReference message_root = dbRoot.child(temp_key);
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("email", "user@user");
            map2.put("jam", jam);
            map2.put("pesan", Chat.edtPesan.getText().toString());
            map2.put("tgl", tgl);

            message_root.updateChildren(map2);*/
            return null;
        }
    }
}

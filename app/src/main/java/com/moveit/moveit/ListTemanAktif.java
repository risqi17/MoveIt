package com.moveit.moveit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.moveit.moveit.adapters.ListTemanAdapter;
import com.moveit.moveit.settergetters.ListTemanSetGet;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;

public class ListTemanAktif extends AppCompatActivity {

    private LinearLayout containerLv;
    private ArrayList listItem;
    private JSONArray data;
    private RecyclerView rvTeman;
    private ListTemanAdapter recyclerAdapter;
    private DatabaseReference dbRoot, chatRoot;
    private Query query;
    private int REQUEST_INVITE = 100;
    FirebaseAuth firebaseAuth;
    private FloatingActionButton fab;
    private String alamat, emailUser, shortEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_teman_aktif);

        setTitle("Teman Aktif");
        firebaseAuth = FirebaseAuth.getInstance();

        dbRoot = FirebaseDatabase.getInstance().getReference().child("user");
        chatRoot = FirebaseDatabase.getInstance().getReference().child("chat_list");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        emailUser = user.getEmail();
        int etEmail = emailUser.length();
        shortEmail = emailUser.substring(0, etEmail-4);


        containerLv = (LinearLayout) findViewById(R.id.container);
        listItem = new ArrayList<>();

        rvTeman = (RecyclerView) findViewById(R.id.rvTeman);
        recyclerAdapter = new ListTemanAdapter(this, listItem);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvTeman.setLayoutManager(layoutManager);
        rvTeman.setItemAnimator(new DefaultItemAnimator());
        rvTeman.setAdapter(recyclerAdapter);

        dbRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getTeman(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getTeman(dataSnapshot);

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

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListPlaces.class));
            }
        });

    }

    private void getTeman(DataSnapshot dataSnapshot){
        Iterator iterator = dataSnapshot.getChildren().iterator();
        ListTemanSetGet varGetSet = null;

        while(iterator.hasNext()){
            varGetSet = new ListTemanSetGet();

            varGetSet.setJam(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setLat(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setLokasi(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setLongi(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setNama(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setOlahraga(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setStatus(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setTgl(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setEmail(dataSnapshot.getKey());

            if (!dataSnapshot.getKey().equals(shortEmail) && !varGetSet.getLokasi().equals("")) {
                listItem.add(varGetSet);
            }
        }

        recyclerAdapter.notifyDataSetChanged();

        //listPesan.getLayoutManager().scrollToPosition(0);
    }

    private void cobaLagi(){
        containerLv.setVisibility(View.INVISIBLE);
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder("Move It Invitation")
                .setMessage("Ayo olahraga bersama, cari partner tebaikmu di Move It")
                .setDeepLink(Uri.parse("https://www.google.co.id"))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.keluar) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), RealLogin.class));
        } else if (id == R.id.about) {

        } else if (id == R.id.add) {
            startActivity(new Intent(getApplicationContext(), ListPlaces.class));
        } else if (id == R.id.invite) {
            onInviteClicked();
        }

        return super.onOptionsItemSelected(item);
    }
}

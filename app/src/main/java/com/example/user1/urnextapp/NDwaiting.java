package com.example.user1.urnextapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NDwaiting extends Fragment {
    private TextView numberOfPatient;
    private Button logout;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    DatabaseReference external = database.getReference("ExternalDB");
    DatabaseReference waiting = database.getReference("waiting time and queue number");
    String id=" ";
    //Constructor default
    public NDwaiting(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View p = inflater.inflate(R.layout.fragment_ndwaiting, container, false);

        numberOfPatient= (TextView) p.findViewById(R.id.numberOfPatient);
        logout = (Button) p.findViewById(R.id.logout2);

        if (user != null)
        {
            id=user.getUid();
        }
        if (user.getEmail().contains("hde")) {
            external.child("Doctors").child(id).child("Name").addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String dname = dataSnapshot.getValue(String.class);
                    waiting.child(dname).addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final Long count = dataSnapshot.getChildrenCount();
                            numberOfPatient.setText(count.toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        else if (user.getEmail().contains("hne")) {
            external.child("Nurse").child(id).child("Doctor ID").addValueEventListener(new ValueEventListener() {
               public void onDataChange(DataSnapshot dataSnapshot) {
                    String id2=dataSnapshot.getValue().toString();
                    external.child("Doctors").child(id2).child("Name").addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String dname = dataSnapshot.getValue(String.class);
                            waiting.child(dname).addValueEventListener(new ValueEventListener() {
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    final Long count = dataSnapshot.getChildrenCount();
                                    numberOfPatient.setText(count.toString());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Log out")
                        .setMessage("Are you sure you want to logging out?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(firebaseAuth != null)
                                {
                                    firebaseAuth.signOut();
                                    Toast.makeText(getContext(),"You successfully logged out!", Toast.LENGTH_SHORT).show();
                                    Intent h= new Intent(getContext(), WelcomePage.class);
                                    startActivity(h); }
                            }

                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }

        });
        return p;
    }
}

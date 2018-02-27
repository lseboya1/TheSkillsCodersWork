package za.co.lutendomlab.loginfirebase.for_all_users;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import za.co.lutendomlab.loginfirebase.adapter_and_object.User;

public class FirebaseHelper {

    DatabaseReference db;
//    Boolean saved;
    ArrayList<User> users = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db){

        this.db = db;
    }
//
//    public boolean save(User user){
//        if(user == null){
//            saved = false;
//        }else {
//            try {
//                db.child("user").push().setValue(user);
//                saved = true;
//            }catch (DatabaseException e){
//                e.printStackTrace();
//                saved = false;
//            }
//        }
//        return saved;
//    }

    private void fetchData(DataSnapshot dataSnapshot){

        users.clear();

        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            User user = ds.getValue(User.class);
            users.add(user);
        }
    }

    public ArrayList<User> retrive(){

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                fetchData(dataSnapshot);
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
        return users;
    }
}

package za.co.lutendomlab.loginfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by codetribe on 8/23/2017.
 */

public class UpdateProfileActivity extends AppCompatActivity {


    private EditText name;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText email;
    private TextView facility;
    private TextView role;


    private String nameinput;
    private String lastNameinput;
    private String phoneNumberinput;
    private String emailinput;
    private String facilityiput;
    private String roleinput;
    FirebaseUser firebaseUser;


    String keyUser;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    User user;

    private ImageView profile_Pic;
    int PICK_IMAGE_REQUEST = 111;
    Bitmap bitmap ;
    Uri filePath ;
    ProgressDialog pd;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://the-skills-coders-work.appspot.com/");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        keyUser = firebaseAuth.getCurrentUser().getUid();

        firebaseUser = firebaseAuth.getCurrentUser();

        profile_Pic=(ImageView)findViewById(R.id.profile_picture);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(keyUser);

        name = (EditText)findViewById(R.id.name);
        lastName = (EditText)findViewById(R.id.lastName);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        email = (EditText)findViewById(R.id.email);
        facility = (TextView)findViewById(R.id.facility);
        role = (TextView)findViewById(R.id.role);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);

                name.setText(user.getName());
                lastName.setText(user.getLastName());
                email.setText(user.getEmail());
                phoneNumber.setText(user.getPhoneNumber());
                facility.setText(user.getFacility());
                role.setText(user.getRole());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(firebaseUser.getPhotoUrl() != null){
            String url = firebaseUser.getPhotoUrl().toString();
            Glide.with(getApplicationContext()).load(url).into(profile_Pic);
        }

//        Intent intent = getIntent();
//        keyUser =  intent.getStringExtra("User_KEY");
//
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void updateUser(){

        nameinput = name.getText().toString().trim();
        lastNameinput = lastName.getText().toString().trim();
        phoneNumberinput = phoneNumber.getText().toString().trim();
        emailinput = email.getText().toString().trim();
        facilityiput = facility.getText().toString().trim();
        roleinput = role.getText().toString().trim();

<<<<<<< HEAD
        User user = new User(keyUser, nameinput , lastNameinput,emailinput,roleinput,facilityiput,phoneNumberinput );
=======
        User user = new User(keyUser, nameinput , lastNameinput,emailinput,phoneNumberinput,profile_Pic);
>>>>>>> 35c23a1892ece4c374dca290bfb52987a132ef9c

        databaseReference.child("name").setValue(user.getName());
        databaseReference.child("lastName").setValue(user.getLastName());
        databaseReference.child("email").setValue(user.getEmail());
        databaseReference.child("phoneNumber").setValue(user.getPhoneNumber());
<<<<<<< HEAD
        databaseReference.child("facility").setValue(user.getFacility());
        databaseReference.child("role").setValue(user.getRole());
=======
        databaseReference.child("").setValue(user.getProfile());
>>>>>>> 35c23a1892ece4c374dca290bfb52987a132ef9c

        databaseReference.setValue(user);
    }

    public void Save(View view){
        updateUser();
        startActivity(new Intent(UpdateProfileActivity.this, ViewProfileStudent.class));
    }

    public void ProfilePictureSelect(View view){

        pd = new ProgressDialog(UpdateProfileActivity.this);
        pd.setMessage("Uploading....");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting image to ImageView
                profile_Pic.setImageBitmap(bitmap);

                // uploading
                if(filePath != null) {
                    pd.show();

                    StorageReference childRef = storageRef.child(firebaseUser.getUid()+".jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot task) {
                            Uri downloadUrl = task.getDownloadUrl();
                            assert downloadUrl != null;
                            String profile_url = downloadUrl.toString();


                            pd.dismiss();
                            Toast.makeText(UpdateProfileActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(profile_url))
                                    .build();

                            firebaseUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UpdateProfileActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(UpdateProfileActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

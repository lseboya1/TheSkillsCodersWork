package za.co.lutendomlab.loginfirebase.adapter_and_object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import za.co.lutendomlab.loginfirebase.R;


public class UserAdapter extends ArrayAdapter<User> {

    Context context;
    private List<User> users;
    private int resource;
    View  viewList;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
//    FirebaseStorage storage = FirebaseStorage.getInstance();
//    StorageReference storageRef = storage.getReferenceFromUrl("gs://the-skills-coders-work.appspot.com/");

    public UserAdapter(Context context,int resource ,List<User> users ) {
        super(context, resource, users);

        this.context = context;
        this.users = users;
        this.resource = resource;
    }

    @Override
    public User getItem(int position) {
        if (this.users != null) {
            return this.users.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        if (this.users != null) {
            return this.users.size();
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;

        if(listViewItem == null) {

            listViewItem = LayoutInflater.from(context).inflate(R.layout.model,parent,false);
        }

        User currentUser = getItem(position);

        TextView name = (TextView)listViewItem.findViewById(R.id.name);
        TextView email = (TextView)listViewItem.findViewById(R.id.email);
        ImageView profile = (ImageView)listViewItem.findViewById(R.id.profile);
        TextView phoneNumber = (TextView)listViewItem.findViewById(R.id.phoneNumber);

//        name.setText(String.format("Name: %s", currentUser.getName()));
//        email.setText(String.format("Email: %s", currentUser.getEmail()));

            name.setText("" + currentUser.getName() + " " + currentUser.getLastName());
            email.setText("" + currentUser.getEmail());
            phoneNumber.setText(String.format("%s", currentUser.getPhoneNumber()));


//        profile.setImageResource(currentUser.getProfile());
//        profile.setImageResource(currentUser.getProfile());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser.getPhotoUrl() != null){
        String url = firebaseUser.getPhotoUrl().toString();
        Glide.with(getContext()).load(url).into(profile);
    }else {
            profile.setImageResource(R.drawable.profile_pic);
        }

        return listViewItem;
    }
}

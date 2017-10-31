package za.co.lutendomlab.loginfirebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;


public class UserAdapter extends ArrayAdapter<User> {

    Context context;
    private List<User> users;
    private int resource;
    View  viewList;

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
        TextView lastName = (TextView)listViewItem.findViewById(R.id.lastName);
        TextView email = (TextView)listViewItem.findViewById(R.id.email);
        TextView location = (TextView)listViewItem.findViewById(R.id.location);
        ImageView profile = (ImageView)listViewItem.findViewById(R.id.profile);



        name.setText(String.format("Name: %s", currentUser.getName()));
        email.setText(String.format("Email: %s", currentUser.getEmail()));
//        location.setText(String.format("Facility: %s", currentUser.getFacility()));
//        TextView lastName = (TextView)listViewItem.findViewById(R.id.lastName);
//        TextView location = (TextView)listViewItem.findViewById(R.id.location);
//        ImageView profile = (ImageView)listViewItem.findViewById(R.id.profile);



        name.setText("Name: " + currentUser.getName() + " , " + currentUser.getLastName());
//        lastName.setText("Last name: " + currentUser.getLastName());
        email.setText("Email: " + currentUser.getEmail());
//        location.setText("Facility: " + currentUser.getFacility());


//        profile.setImageResource(currentUser.getProfile());

        return listViewItem;
    }
}

package za.co.lutendomlab.loginfirebase.adapter_and_object;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import za.co.lutendomlab.loginfirebase.R;

public class LeaveAdapter extends ArrayAdapter<Leave>{

    Context context;
    private List<Leave> leaves;
    private int resource;

    public LeaveAdapter(@NonNull Context context, int resource, @NonNull List<Leave> leaves) {
        super(context, resource, leaves);

        this.context = context;
        this.leaves = leaves;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listViewItem = convertView;

        if(listViewItem == null){
            listViewItem = LayoutInflater.from(context).inflate(R.layout.activity_leave_lis,parent,false);
        }

        Leave currentLeaveForm = getItem(position);

        TextView name = (TextView)listViewItem.findViewById(R.id.name);
        TextView leave_type = (TextView)listViewItem.findViewById(R.id.leave_type);

        name.setText("Name: " + currentLeaveForm.getName() + " " + currentLeaveForm.getLastName());
        leave_type.setText("Email: " + currentLeaveForm.getLeaveType());

        return listViewItem;
    }
}

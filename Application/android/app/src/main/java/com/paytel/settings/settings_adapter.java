package com.paytel.settings;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paytel.R;
import com.paytel.authenticatoractivity;
import com.paytel.home;
import com.paytel.sign_up.authentication_signup_bankinfo;
import com.paytel.settings.settings_identity;

public class settings_adapter extends RecyclerView.Adapter<settings_adapter.ViewHolder> {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.settings_option);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }
        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            final Intent intent;
            switch (position){
                case 0:
                    intent =  new Intent(context, settings_identity.class);
                    break;
                case 1:
                    intent =  new Intent(context, settings_bankinfo.class);
                    break;
                case 2:
                    intent =  new Intent(context, settings_address.class);
                    break;
                case 3:
                    intent = new Intent(context, authenticatoractivity.class);
                    break;
                default:
                    intent =  new Intent(context, home.class);
                    break;
            }
            context.startActivity(intent);
        }
    }

    private String[] mDataset;
    // Provide a suitable constructor (depends on the kind of dataset)
    public settings_adapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public settings_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settings, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameTextView.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
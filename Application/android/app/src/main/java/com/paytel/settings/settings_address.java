package com.paytel.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;
import com.santalu.widget.MaskEditText;


public class settings_address extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    userDataObject current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_address);

        current_user = ((global_objects) getApplication()).getCurrent_user();
        display_userinfo();

        Button btn_SAVE_userinfo = findViewById(R.id.btn_save_userinfo);
        btn_SAVE_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                boolean next = edit_userinfo();
                if (next == true) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ((global_objects) getApplication()).getDynamoDBMapper().save(current_user);
                            // Item updated
                        }
                    }).start();
                    try {
                        Intent k = new Intent(settings_address.this, settings_main.class);
                        startActivity(k);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //state drop down stuff:
        Spinner spinner = (Spinner) findViewById(R.id.states_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        spinner.setOnItemSelectedListener(this);
        //set default value of State drop-down menu
        display_state(spinner, adapter);

        //toolbar stuff
        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Billing Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),settings_main.class));
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    boolean edit_userinfo(){

        Context context = getApplicationContext();
        int dShort = Toast.LENGTH_SHORT;
        int dLong = Toast.LENGTH_LONG;

        TextInputLayout street = findViewById(R.id.txt_street);
        TextInputLayout zip = findViewById(R.id.txt_zipcode);
        TextInputLayout city = findViewById(R.id.txt_city);
        Spinner spinner = findViewById(R.id.states_spinner);

        if(street.getEditText().getText().toString().length() == 0 || zip.getEditText().getText().toString().length() == 0 ||
                city.getEditText().getText().toString().length() == 0){
            CharSequence fail = "No field can be left blank";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        if(street.getEditText().getText().toString().length() >=50){
            CharSequence fail = "Street address must be under 50 characters";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(zip.getEditText().getText().toString().length() != 5){
            CharSequence fail = "Zip code must be 5 digits";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(city.getEditText().getText().toString().length() >=50){
            CharSequence fail = "City must be under 50 characters";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else{
            if(street != null)current_user.setStreet(street.getEditText().getText().toString().trim());
            if(city != null)current_user.setCity(city.getEditText().getText().toString().trim());
            if(zip != null)current_user.setZipCode(zip.getEditText().getText().toString().trim());
            if(spinner != null)current_user.setState(String.valueOf(spinner.getSelectedItem()));
            return true;
        }
    }

    void display_userinfo(){
        TextInputLayout street = findViewById(R.id.txt_street);
        TextInputLayout zip = findViewById(R.id.txt_zipcode);
        TextInputLayout city = findViewById(R.id.txt_city);

        street.getEditText().setText(current_user.getStreet());
        zip.getEditText().setText(current_user.getZipCode());
        city.getEditText().setText(current_user.getCity());
    }

    void display_state(Spinner spinner, ArrayAdapter adapter){
        String myString = current_user.getState(); //the value you want the position for
        try {
            int spinnerPosition = adapter.getPosition(myString);
            //set the default according to value
            spinner.setSelection(spinnerPosition);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
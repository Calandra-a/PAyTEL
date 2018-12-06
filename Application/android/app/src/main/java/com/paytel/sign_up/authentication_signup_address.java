package com.paytel.sign_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;

//Alex Dapoz
//I created this screen to make the signup process cleaner
//TODO: logic


public class authentication_signup_address extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    userDataObject new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_address);

        new_user = ((global_objects) getApplication()).getNew_user();

        //user data
        Button btn_NEXT_userdata = findViewById(R.id.btn_next_address);

        btn_NEXT_userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                boolean next = add_address();
                if (next == true) {
                    try {
                        Intent k = new Intent(authentication_signup_address.this, authentication_signup_facial.class);
                        startActivity(k);
                    } catch (Exception e) {
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
        spinner.setSelection(21);//default to Michigan
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    boolean add_address(){

        Context context = getApplicationContext();
        int dShort = Toast.LENGTH_SHORT;
        int dLong = Toast.LENGTH_LONG;

        TextInputLayout street = findViewById(R.id.txt_street);
        TextInputLayout zip = findViewById(R.id.txt_zipcode);
        TextInputLayout city = findViewById(R.id.txt_city);
        Spinner spinner = findViewById(R.id.states_spinner);
        boolean fail = false;

        if(street.getEditText().getText().toString().length() == 0){
            street.setError("No field can be left blank");
            fail = true;
        }
        else if(street.getEditText().getText().toString().length() >=50){
            street.setError("Street must be under 50 characters");
            fail = true;
        }
        else{
            street.setErrorEnabled(false);

        }
        if( zip.getEditText().getText().toString().length() ==0){
            zip.setError("No field can be left blank");
            fail = true;
        }
        else if(zip.getEditText().getText().toString().length() != 5){
            zip.setError("ZIP must be 5 digits");
            fail = true;
        }
        else{
            zip.setErrorEnabled(false);

        }
         if(city.getEditText().getText().toString().length() == 0){
             city.setError("No field can be left blank");
             fail = true;
         }
         else if(city.getEditText().getText().toString().length() >=50){
             city.setError("City must be under 50 characters");
             fail = true;
         }
         else{
             city.setErrorEnabled(false);

         }


        if(fail == false) {
            new_user.setStreet(street.getEditText().getText().toString());
            new_user.setCity(city.getEditText().getText().toString());
            new_user.setZipCode(zip.getEditText().getText().toString());
            new_user.setState(String.valueOf(spinner.getSelectedItem()));
            return true;
        }
        else{
            return false;
        }
    }
}

package com.example.taller3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring buttons
    Button btnRegister, btnExit, btnCarsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adjusting buttons
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnCarsList = (Button)findViewById(R.id.btnCarsList);
        btnCarsList.setOnClickListener(this);
        btnExit = (Button)findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
    }


    @Override
    //OnclickListener method
    public void onClick(View v) {

        //OnClickListener Conditional
        switch (v.getId()){

            //If it is Register Button then open new Register Activity
            case R.id.btnRegister:
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                break;

            //If it is Cars List button then open new CarsLists Activity
            case R.id.btnCarsList:
                Intent i2 = new Intent(getApplicationContext(), CarsListActivity.class);
                CarsListActivity.plateFilter = "Placa";
                CarsListActivity.brandFilter = "Marca";
                startActivity(i2);
                break;

            //If it is Exit Button then close app
            case R.id.btnExit:
                finishAffinity();
                break;
        }
    }
}
package com.example.taller3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.regex.*;

import android.widget.Toast;

import com.example.taller3.MODEL.DataBaseHelper;
import com.example.taller3.MODEL.InfractionModel;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditTexts
    EditText edtPlate, edtBrand, edtModel, edtDescription, edtAddress;

    //Declaring button
    Button btnRegister, btnBack;

    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//      Adjusting EditTexts
        edtPlate = findViewById(R.id.edtPlate);
        edtBrand = findViewById(R.id.edtBrand);
        edtModel = findViewById(R.id.edtModel);
        edtDescription = findViewById(R.id.edtDescription);
        edtAddress = findViewById(R.id.edtAddress);

        //Adjusting Buttons
        btnRegister = findViewById(R.id.btnRegister2);
        btnRegister.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack2);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

//        OnClickListener conditional
        switch (v.getId()){
            case R.id.btnRegister2:
                register();
                break;

            case R.id.btnBack2:
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                break;
            //
        }
    }

//    Register a new Infraction
    public void register(){

//          Validating if fields are completed
        if(!TextUtils.isEmpty(edtPlate.getText()) && !TextUtils.isEmpty(edtBrand.getText()) && !TextUtils.isEmpty(edtModel.getText()) && !TextUtils.isEmpty(edtDescription.getText()) && !TextUtils.isEmpty(edtAddress.getText())){

//              Saving values
            String plate = edtPlate.getText().toString();
            String brandAux = edtBrand.getText().toString(); String brand = brandAux.toUpperCase();
            String modelAux = edtModel.getText().toString(); String model = modelAux.toUpperCase();
            String description = edtDescription.getText().toString();
            String address = edtAddress.getText().toString();

//              REGEX for validating plate
            Pattern pat = Pattern.compile("^[A-Z]{3}[0-9]{3}");
            Matcher mat = pat.matcher(plate);
//            If it is a valid license plate
            if(mat.matches()){

//                Creating a new Infraction Model for add to dataBase
                InfractionModel im = new InfractionModel();
                im.setCarLicensePLate(plate);
                im.setCarBrand(brand);
                im.setCarModel(model);
                im.setInfractionDescription(description);
                im.setInfractionAddress(address);

//                Instance a DataBaseHelper for commands
                DataBaseHelper db = new DataBaseHelper(RegisterActivity.this);

//                if it was a successfully insertion
                if(db.addInfraction(im)){
                    flag = 1;
                    createAlert("Infracción agregada");
                }
                else{
                    createAlert("Error al agregar infracción");
                }

            }
            else{
                createAlert("Ingrese una placa válida");
            }
        }
        else{
            createAlert("Rellene todos los campos");
        }
    }

//      Alerts for messaging
    private void createAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("");
        alert.setMessage(message);
        if(flag == 0){
            alert.setPositiveButton("OK",null);
        }
        else{
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    flag = 0;
                    startActivity(i);
                }
            });
        }
        alert.show();
    }

    @Override
    public void onBackPressed(){}
}
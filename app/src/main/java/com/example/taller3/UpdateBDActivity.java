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

import com.example.taller3.MODEL.DataBaseHelper;
import com.example.taller3.MODEL.InfractionModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateBDActivity extends AppCompatActivity implements View.OnClickListener {
    //Declaring EditTexts
    EditText edtPlate1, edtBrand1, edtModel1, edtDescription1, edtAddress1;

    //Declaring buttonS
    Button btnBack, btnUpdate, btnDelete;

    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_b_d);

        //      Adjusting EditTexts
        edtPlate1 = findViewById(R.id.edtPlate1);
        edtBrand1 = findViewById(R.id.edtBrand1);
        edtModel1 = findViewById(R.id.edtModel1);
        edtDescription1 = findViewById(R.id.edtDescription1);
        edtAddress1 = findViewById(R.id.edtAddress1);

        //Adjusting Buttons
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        edtPlate1.setText(CarsListActivity.imSelected.getCarLicensePLate());
        edtBrand1.setText(CarsListActivity.imSelected.getCarBrand());
        edtModel1.setText(CarsListActivity.imSelected.getCarModel());
        edtDescription1.setText(CarsListActivity.imSelected.getInfractionDescription());
        edtAddress1.setText(CarsListActivity.imSelected.getInfractionAddress());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent i = new Intent(getApplicationContext(),CarsListActivity.class);
                startActivity(i);
                break;

            case R.id.btnUpdate:
                updateInfraction();
                break;

            case R.id.btnDelete:
                flag = 2;
                createAlert("¿Seguro que desesa eliminar infracción'");
                break;
        }
    }

    private void updateInfraction() {
        if(!TextUtils.isEmpty(edtPlate1.getText()) && !TextUtils.isEmpty(edtBrand1.getText()) && !TextUtils.isEmpty(edtModel1.getText()) && !TextUtils.isEmpty(edtDescription1.getText()) && !TextUtils.isEmpty(edtAddress1.getText())){
            InfractionModel im = new InfractionModel();
            //              Saving values
            String plate = edtPlate1.getText().toString();
            String brandAux = edtBrand1.getText().toString(); String brand = brandAux.toUpperCase();
            String modelAux = edtModel1.getText().toString(); String model = modelAux.toUpperCase();
            String description = edtDescription1.getText().toString();
            String address = edtAddress1.getText().toString();

            //              REGEX for validating plate
            Pattern pat = Pattern.compile("^[A-Z]{3}[0-9]{3}");
            Matcher mat = pat.matcher(plate);
//            If it is a valid license plate
            if(mat.matches()) {
                im.setId(CarsListActivity.imSelected.getId());
                im.setCarLicensePLate(plate);
                im.setCarBrand(brand);
                im.setCarModel(model);
                im.setInfractionDescription(description);
                im.setInfractionAddress(address);

                DataBaseHelper db = new DataBaseHelper(UpdateBDActivity.this);

                if(db.updateInfraction(im)){
                    flag = 1;
                    createAlert("Infracción actualizada");
                }
                else{
                    createAlert("Error en actualización");
                }
            }
            else{
                createAlert("Ingrese una placa válidad");
            }
        }
        else{
            createAlert("Campos vacíos");
        }
    }

    private void createAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("");
        alert.setMessage(message);
        if(flag == 0){
            alert.setPositiveButton("OK",null);
        }
        else if(flag == 1){
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(getApplicationContext(),CarsListActivity.class);
                    flag = 0;
                    startActivity(i);
                }
            });
        }
        else if(flag == 2){
            alert.setNegativeButton("CANCELAR", null);
            alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteInfraction();
                }
            });

        }
        alert.show();
    }

    private void deleteInfraction() {
        DataBaseHelper db = new DataBaseHelper(UpdateBDActivity.this);

        if(db.deleteInfraction(CarsListActivity.imSelected.getId())){
            flag = 1;
            createAlert("Infracción eliminada");
        }
        else {
            createAlert("Error al eliminar infracción");
        }
    }

    @Override
    public void onBackPressed(){}
}
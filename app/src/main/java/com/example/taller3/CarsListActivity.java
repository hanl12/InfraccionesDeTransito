package com.example.taller3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taller3.MODEL.DataBaseHelper;
import com.example.taller3.MODEL.InfractionModel;

import java.util.ArrayList;

public class CarsListActivity extends AppCompatActivity implements View.OnClickListener {

//    Declaring
    ListView lvList;
    TextView lvId, lvLicensePlate, lvBrand, lvModel, lvDescription, lvAddress, tvFind;
    Spinner spPlateFilter, spBrandFilter;
    Button btnBack;

    public static String plateFilter = "Placa";
    public static String brandFilter = "Marca";
    public DataBaseHelper db;


    public static InfractionModel imSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_list);

//        Adjusting ListView
        lvList = findViewById(R.id.lvList);
        lvId = findViewById(R.id.lvId);
        lvLicensePlate = findViewById(R.id.lvLicensePLate);
        lvBrand = findViewById(R.id.lvBrand);
        lvModel = findViewById(R.id.lvModel);
        lvDescription = findViewById(R.id.lvDescription);
        lvAddress = findViewById(R.id.lvAddress);
        tvFind = findViewById(R.id.tvFind);
        spPlateFilter = findViewById(R.id.spnPlateFilter);
        spBrandFilter = findViewById(R.id.spBrandFilter);
        btnBack = findViewById(R.id.btnBack3);
        btnBack.setOnClickListener(this);

        //        Cursor adapter
        db = new DataBaseHelper(CarsListActivity.this);
        SimpleCursorAdapter spa = db.populateLisView();
        lvList.setAdapter(spa);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) spa.getItem(position);
                imSelected = new InfractionModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                Intent i = new Intent(getApplicationContext(), UpdateBDActivity.class);
                startActivity(i);
            }
        });

//        Listener for chose option to filter
        spBrandFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandFilter = spBrandFilter.getSelectedItem().toString();
                SimpleCursorAdapter spa = db.populateLisView();
                lvList.setAdapter(spa);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spPlateFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                Listener for searching
                if(spPlateFilter.getSelectedItem().toString().equals("Buscar")){
                    tvFind.setVisibility(View.VISIBLE);
                    tvFind.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            plateFilter = tvFind.getText().toString();
                            SimpleCursorAdapter spa = db.populateLisView();
                            lvList.setAdapter(spa);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
                }else{
                    tvFind.setVisibility(View.INVISIBLE);
                    plateFilter = spPlateFilter.getSelectedItem().toString();
                    SimpleCursorAdapter spa = db.populateLisView();
                    lvList.setAdapter(spa);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

//        Filters
        ArrayList<String> plates = db.getData(db.CAR_LICENSE_PLATE_COL);
        plates.add(0, "Placa");
        plates.add(1, "Buscar");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, plates);
        spPlateFilter.setAdapter(adapter);

        ArrayList<String> brand = db.getData(db.CAR_BRAND_COL);
        brand.add(0, "Marca");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, brand);
        spBrandFilter.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack3:
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                break;
        }
    }
    @Override
    public void onBackPressed(){}
}
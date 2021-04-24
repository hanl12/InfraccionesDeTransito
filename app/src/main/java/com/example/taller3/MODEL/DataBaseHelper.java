package com.example.taller3.MODEL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;

import com.example.taller3.CarsListActivity;
import com.example.taller3.R;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String dbName = "traffic.db";

    public static final String INFRACTION_TABLE = "INFRACTION";
    public static final String ID_COL = "ID";
    public static final String CAR_LICENSE_PLATE_COL = "CAR_LICENSE_PLATE";
    public static final String CAR_BRAND_COL = "CAR_BRAND";
    public static final String CAR_MODEL_COL = "CAR_MODEL";
    public static final String INFRACTION_DESCRIPTION_COL = "INFRACTION_DESCRIPTION";
    public static final String INFRACTION_ADDRESS_COL = "INFRACTION_ADDRESS";
    public static final String cols[] = {CAR_LICENSE_PLATE_COL, CAR_BRAND_COL, CAR_MODEL_COL, INFRACTION_DESCRIPTION_COL, INFRACTION_ADDRESS_COL};

    public static Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, dbName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + INFRACTION_TABLE +
                " ( " + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAR_LICENSE_PLATE_COL +
                " TEXT, " + CAR_BRAND_COL + " TEXT, " + CAR_MODEL_COL + " TEXT, " +
                INFRACTION_DESCRIPTION_COL + " TEXT, " + INFRACTION_ADDRESS_COL + ")";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

//    Add a new infraction to database
    public boolean addInfraction(InfractionModel im){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CAR_LICENSE_PLATE_COL, im.getCarLicensePLate());
        cv.put(CAR_BRAND_COL, im.getCarBrand());
        cv.put(CAR_MODEL_COL, im.getCarModel());
        cv.put(INFRACTION_DESCRIPTION_COL, im.getInfractionDescription());
        cv.put(INFRACTION_ADDRESS_COL, im.getInfractionAddress());

        long insert = db.insert(INFRACTION_TABLE, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<String> getData(String column){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT DISTINCT " + column + " FROM " + INFRACTION_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String> data = new ArrayList<>();

        while(cursor.moveToNext()){
            data.add(cursor.getString(0));
        }

        return data;
    }

    public SimpleCursorAdapter populateLisView(){
        SQLiteDatabase db = this.getReadableDatabase();

        String plate = CarsListActivity.plateFilter;
        String brand = CarsListActivity.brandFilter;
        String query = "";

        if(plate.equals("Placa") && brand.equals("Marca")){
            query = "SELECT ID AS _id,  "+ CAR_LICENSE_PLATE_COL + ", " + CAR_BRAND_COL +
                    ", " + CAR_MODEL_COL + ", " + INFRACTION_DESCRIPTION_COL + ", " +
                     INFRACTION_ADDRESS_COL + " FROM " + INFRACTION_TABLE;
        }
        else if (!plate.equals("Placa") && brand.equals("Marca")){
            query = "SELECT ID AS _id,  "+ CAR_LICENSE_PLATE_COL + ", " + CAR_BRAND_COL +
                    ", " + CAR_MODEL_COL + ", " + INFRACTION_DESCRIPTION_COL + ", " +
                    INFRACTION_ADDRESS_COL + " FROM " + INFRACTION_TABLE + " WHERE " +
                    CAR_LICENSE_PLATE_COL + " LIKE '" + plate + "'";
        }
        else if(plate.equals("Placa") && !brand.equals("Marca")){
            query = "SELECT ID AS _id,  "+ CAR_LICENSE_PLATE_COL + ", " + CAR_BRAND_COL +
                    ", " + CAR_MODEL_COL + ", " + INFRACTION_DESCRIPTION_COL + ", " +
                    INFRACTION_ADDRESS_COL + " FROM " + INFRACTION_TABLE + " WHERE " +
                    CAR_BRAND_COL + " LIKE '" + brand + "'";
        }else{
            query = "SELECT ID AS _id,  "+ CAR_LICENSE_PLATE_COL + ", " + CAR_BRAND_COL +
                    ", " + CAR_MODEL_COL + ", " + INFRACTION_DESCRIPTION_COL + ", " +
                    INFRACTION_ADDRESS_COL + " FROM " + INFRACTION_TABLE + " WHERE " +
                    CAR_LICENSE_PLATE_COL + " LIKE '" + plate + "' AND " + CAR_BRAND_COL + " LIKE '" + brand + "'";
        }

        Cursor cursor = db.rawQuery(query,null);
        String[] fields = new String[]{
                "_id", CAR_LICENSE_PLATE_COL, CAR_BRAND_COL, CAR_MODEL_COL, INFRACTION_DESCRIPTION_COL, INFRACTION_ADDRESS_COL
        };

        int[] ids = new int[]{
                R.id.lvId, R.id.lvLicensePLate, R.id.lvBrand, R.id.lvModel, R.id.lvDescription, R.id.lvAddress
        };

        SimpleCursorAdapter sCursor = new SimpleCursorAdapter(
                context,
                R.layout.single_item,
                cursor,
                fields,
                ids,
                0
        );

        return sCursor;
    }

    public boolean updateInfraction(InfractionModel im){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CAR_LICENSE_PLATE_COL, im.getCarLicensePLate());
        cv.put(CAR_BRAND_COL, im.getCarBrand());
        cv.put(CAR_MODEL_COL, im.getCarModel());
        cv.put(INFRACTION_DESCRIPTION_COL, im.getInfractionDescription());
        cv.put(INFRACTION_ADDRESS_COL, im.getInfractionAddress());

        int rows = db.update(INFRACTION_TABLE, cv, ID_COL + "=" + im.getId(), null);

        if(rows <= 0){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteInfraction(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(INFRACTION_TABLE, ID_COL + "=" + id, null);
        if(rows <= 0){
            return false;
        }
        else{
            return true;
        }
    }

//    public Cursor getRegister(int id){
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(INFRACTION_TABLE, cols, ID_COL + "=?",);
//    }
}

package com.game.samplecollection;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String name, byte[] image, String city, String state, String monsoon, String sampleType, String latLon, String ph, String ec,
                           String tds, String arsenic, String nitrate, String fluoride, String sulphate, String chloride, String hardness, String alkalinity){//, String price,7
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO FOOD VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";//, ?,7

        SQLiteStatement statement = database.compileStatement(sql);//
        statement.clearBindings();

        statement.bindString(1, name);
//        statement.bindString(2, price);
        statement.bindBlob(2, image);
        statement.bindString(3, city);
        statement.bindString(4, state);
        statement.bindString(5, monsoon);
        statement.bindString(6, sampleType);
        statement.bindString(7, latLon);
        statement.bindString(8, ph);
        statement.bindString(9, ec);
        statement.bindString(10, tds);
        statement.bindString(11, arsenic);
        statement.bindString(12, nitrate);
        statement.bindString(13, fluoride);
        statement.bindString(14, sulphate);
        statement.bindString(15, chloride);
        statement.bindString(16, hardness);
        statement.bindString(17, alkalinity);

        statement.executeInsert();
    }

    public void updateData(int id, String name, byte[] image, String city, String state, String monsoon, String sampleType, String latLon, String ph, String ec,
                           String tds, String arsenic, String nitrate, String fluoride, String sulphate, String chloride, String hardness, String alkalinity) {// String price,
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE FOOD SET name = ?, image = ?, city=?, state=?, monsoon=?, sampleType=?, latLon=?, ph=?, ec=?, tds=?, arsenic=?, nitrate=?, fluoride=?, sulphate=?, chloride=?, hardness=?, alkalinity=? WHERE id = ?";// price = ?,
        SQLiteStatement statement = database.compileStatement(sql);

//        statement.bindLong(0,id);
//        statement.bindDouble(1, (double)id);//
        statement.bindString(1, name);
        statement.bindBlob(2, image);
        statement.bindString(3, city);
        statement.bindString(4, state);
        statement.bindString(5, monsoon);
        statement.bindString(6, sampleType);
        statement.bindString(7, latLon);
        statement.bindString(8, ph);
        statement.bindString(9, ec);
        statement.bindString(10, tds);
        statement.bindString(11, arsenic);
        statement.bindString(12, nitrate);
        statement.bindString(13, fluoride);
        statement.bindString(14, sulphate);
        statement.bindString(15, chloride);
        statement.bindString(16, hardness);
        statement.bindString(17, alkalinity);
//        statement.bindString(8, price);

        statement.execute();
        database.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM FOOD WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

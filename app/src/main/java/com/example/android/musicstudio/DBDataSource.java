package com.example.android.musicstudio;

/**
 * Created by ASUS on 19/06/2016.
 */
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBDataSource {

    private SQLiteDatabase database;

    private DBHelper dbHelper;

    private String[] allColumns = { DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME,
            DBHelper.COLUMN_ALAMAT, DBHelper.COLUMN_HARGA, DBHelper.COLUMN_GAMBAR,
            DBHelper.COLUMN_JAMLITE,
            DBHelper.COLUMN_CALLLITE,
            DBHelper.COLUMN_ALATMUSIK,
            DBHelper.COLUMN_LASTUPDATE,
            DBHelper.COLUMN_RATINGALAT,
            DBHelper.COLUMN_RATINGRECORDING,
            DBHelper.COLUMN_RATINGTEMPAT,
            DBHelper.COLUMN_LATITUDE,
            DBHelper.COLUMN_LONGITUDE
    };

    public DBDataSource(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Insert data
    public boolean insertStudioMusik(String id, String nama, String alamat, String harga, String gambar,
                                     String jam,
                                     String call,
                                     String alatmusik,
                                     String lastupdate,
                                     String ratingalat,
                                     String ratingrecording,
                                     String ratingtempat,
                                     Double latitude,
                                     Double longitude) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ID, id);
        values.put(DBHelper.COLUMN_NAME, nama);
        values.put(DBHelper.COLUMN_ALAMAT, alamat);
        values.put(DBHelper.COLUMN_HARGA, harga);
        values.put(DBHelper.COLUMN_GAMBAR, gambar);
        values.put(DBHelper.COLUMN_JAMLITE, jam);
        values.put(DBHelper.COLUMN_CALLLITE, call);
        values.put(DBHelper.COLUMN_ALATMUSIK, alatmusik);
        values.put(DBHelper.COLUMN_LASTUPDATE, lastupdate);
        values.put(DBHelper.COLUMN_RATINGALAT, ratingalat);
        values.put(DBHelper.COLUMN_RATINGRECORDING, ratingrecording);
        values.put(DBHelper.COLUMN_RATINGTEMPAT, ratingtempat);
        values.put(DBHelper.COLUMN_LATITUDE, latitude);
        values.put(DBHelper.COLUMN_LONGITUDE, longitude);

        long insertId = database.insert(DBHelper.TABLE_NAME,null,
                values);

        if (insertId == -1) return false;
        else return true;
    }

    //set data
    private StudioMusik cursorToStudioMusik(Cursor cursor)
    {
        StudioMusik studiomusik = new StudioMusik();
        // debug LOGCAT
        studiomusik.setId(cursor.getInt(0));
        studiomusik.setNama(cursor.getString(1));
        studiomusik.setAlamat(cursor.getString(2));
        studiomusik.setHarga(cursor.getString(3));
        studiomusik.setGambar(cursor.getString(4));
        studiomusik.setJam(cursor.getString(5));
        studiomusik.setCall(cursor.getString(6));
        studiomusik.setAlatmusik(cursor.getString(7));
        studiomusik.setLastupdate(cursor.getString(8));
        studiomusik.setRatingalat(cursor.getString(9));
        studiomusik.setRatingrecording(cursor.getString(10));
        studiomusik.setRatingtempat(cursor.getString(11));
        studiomusik.setLatitude(cursor.getString(12));
        studiomusik.setLongitude(cursor.getString(13));

        return studiomusik;
    }

    //mengambil semua data StudioMusik
    public ArrayList<StudioMusik> getStudioMusik() {
        ArrayList<StudioMusik> daftarStudioMusik = new ArrayList<StudioMusik>();

        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns, null, null, null, null, null, null);

        // pindah ke data paling pertama
        cursor.moveToFirst();
        // jika masih ada data, masukkan data barang ke
        // daftar barang
        while (!cursor.isAfterLast()) {
            StudioMusik studioMusik = cursorToStudioMusik(cursor);
            daftarStudioMusik.add(studioMusik);
            cursor.moveToNext();
        }
        cursor.close();
        return daftarStudioMusik;
    }


    //ambil satu barang sesuai id
    public boolean isFavorite(Integer id)
    {
        StudioMusik studiomusik = new StudioMusik();
        //select query
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, "_id ="+id,null, null, null, null, null);
        if (cursor.getCount()>0) return true;
        else return false;
    }

    //update studio yang diedit
    public void updateStudioMusik(StudioMusik b)
    {
        //ambil id barang
        String strFilter = "_id=" + b.getId();
        //memasukkan ke content values
        ContentValues args = new ContentValues();
        //masukkan data sesuai dengan kolom pada database
        args.put(DBHelper.COLUMN_NAME, b.getNama());
        args.put(DBHelper.COLUMN_ALAMAT, b.getAlamat());
        args.put(DBHelper.COLUMN_HARGA, b.getHarga());
        args.put(DBHelper.COLUMN_GAMBAR, b.getGambar());
        //
        args.put(DBHelper.COLUMN_JAMLITE, b.getJam());
        args.put(DBHelper.COLUMN_CALLLITE, b.getCall());
        args.put(DBHelper.COLUMN_ALATMUSIK, b.getAlatmusik());
        args.put(DBHelper.COLUMN_LASTUPDATE, b.getLastupdate());
        args.put(DBHelper.COLUMN_RATINGALAT, b.getRatingalat());
        args.put(DBHelper.COLUMN_RATINGRECORDING, b.getRatingrecording());
        args.put(DBHelper.COLUMN_RATINGTEMPAT, b.getRatingtempat());
        args.put(DBHelper.COLUMN_LATITUDE, b.getLatitude());
        args.put(DBHelper.COLUMN_LONGITUDE, b.getLongitude());

        //update query
        database.update(DBHelper.TABLE_NAME, args, strFilter, null);
    }

    // delete studio sesuai ID
    public void deleteStudioMusik(Integer id)
    {
        String strFilter = "_id=" + id;
        database.delete(DBHelper.TABLE_NAME, strFilter, null);
    }

}
package com.example.android.musicstudio;

/**
 * Created by ASUS on 19/06/2016.
 */
//deklarasi import package
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "StudioMusik";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "nama";
    public static final String COLUMN_ALAMAT = "alamat";
    public static final String COLUMN_HARGA = "harga";
    public static final String COLUMN_GAMBAR = "gambar";
    public static final String COLUMN_JAMLITE = "jamlite";
    public static final String COLUMN_CALLLITE = "call";
    public static final String COLUMN_ALATMUSIK = "alatmusik";
    public static final String COLUMN_LASTUPDATE = "lastupdate";
    public static final String COLUMN_RATINGALAT = "ratingalat";
    public static final String COLUMN_RATINGRECORDING = "ratingrecording";
    public static final String COLUMN_RATINGTEMPAT = "ratingtempat";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

//    public static final Double COLUMN_LATITUDE = Double.parseDouble("latitude");
//    public static final Double COLUMN_LONGITUDE = Double.parseDouble("longitude");

    private static final String db_name ="studioivan.db";
    private static final int db_version=3;
    private static final String db_create = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID +" integer primary key autoincrement, "
            + COLUMN_NAME+ " varchar(50) not null, "
            + COLUMN_ALAMAT+ " varchar(50) not null, "
            + COLUMN_HARGA + " varchar(50) not null,"
            + COLUMN_GAMBAR + " varchar(50) not null,"
            + COLUMN_JAMLITE + " varchar(50) not null,"
            + COLUMN_CALLLITE + " varchar(50) not null,"
            + COLUMN_ALATMUSIK + " varchar(50) not null,"
            + COLUMN_LASTUPDATE + " varchar(50) not null,"
            + COLUMN_RATINGALAT + " varchar(50) not null,"
            + COLUMN_RATINGRECORDING + " varchar(50) not null,"
            + COLUMN_RATINGTEMPAT + " varchar(50) not null,"
            + COLUMN_LATITUDE + " varchar (50) not null,"
            + COLUMN_LONGITUDE + " varchar (50) not null);";

    public DBHelper(Context context) {
        super(context, db_name, null, db_version);
        // Auto generated
    }

    //mengeksekusi perintah SQL di atas untuk membuat tabel database baru
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_create);
    }

    // dijalankan apabila ingin mengupgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
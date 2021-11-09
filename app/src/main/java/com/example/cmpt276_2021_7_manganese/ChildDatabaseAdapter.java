package com.example.cmpt276_2021_7_manganese;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChildDatabaseAdapter {
    private static final String TAG = "ChildDBAdapter";

    public static final String KEY_ROWID = "_id";

    public static final String KEY_NAME = "name";

    public static final int COL_NAME = 1;

    public static final String[] ALL_KEYS = new String[] {KEY_NAME};

    public static final String DATABASE_TABLE = "ChildTable";

    public static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_NAME + " text not null, "
                    + ");";

    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;


    public ChildDatabaseAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public ChildDatabaseAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

.
    public void close() {
        myDBHelper.close();
    }


    public long insertRow(String name) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public boolean updateRow(long rowId, String name) {
        String where = KEY_ROWID + "=" + rowId;
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);


        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }


}

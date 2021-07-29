package sg.edu.rp.c346.id20019018.p10ndpsongscustomlv;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ndpSongs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONGS = "songs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableSQL ="CREATE TABLE " + TABLE_SONGS +"("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_SINGERS + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_STARS + " FLOAT)";
        db.execSQL(createTableSQL);
        Log.i("info","created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        // Create table(s) again
        onCreate(db);

    }

    public long insertSong(String title, String singers, int year, float stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGERS,singers);
        values.put(COLUMN_YEAR,year);
        values.put(COLUMN_STARS,stars);
        long result = db.insert(TABLE_SONGS, null, values);
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE +  "," + COLUMN_SINGERS + "," + COLUMN_YEAR + "," + COLUMN_STARS + " FROM " + TABLE_SONGS
                + " ORDER BY " + COLUMN_STARS + " DESC ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songTitle = cursor.getString(1);
                String songSinger = cursor.getString(2);
                int songYear = cursor.getInt(3);
                float songStars = cursor.getFloat(4);
                Song song = new Song(id,songTitle,songSinger,songYear,songStars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int updateSong(Song data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGERS,data.getSingers());
        values.put(COLUMN_YEAR,data.getYear());
        values.put(COLUMN_STARS,data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONGS, values, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Update failed");
        }
        db.close();
        return result;
    }

    public int deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONGS, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Song> getAllSongs(String keyword) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID,COLUMN_TITLE,COLUMN_SINGERS,COLUMN_YEAR, COLUMN_STARS};
        String condition = COLUMN_STARS + " = 5";
        Cursor cursor = db.query(TABLE_SONGS, columns, condition,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                float stars = cursor.getFloat(4);

                Song note = new Song( title, singers,  year, stars);
                songs.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

}

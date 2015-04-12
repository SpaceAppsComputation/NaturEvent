package com.example.asabater.pruebasmaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alberto on 12/04/2015.
 */
public class PointsDbAdapter {

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESC = "description";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LON = "longitude";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_DISLIKES = "dislikes";
    public static final String KEY_CAT = "category";

    private static final String TAG = "PointsDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_CREATE = "create table points (_id integer primary key autoincrement, " +
            "title text not null, description text, latitude float not null, longitude float not null, likes integer " +
            "not null, dislikes integer not null, category text not null)";

    private static final String DATABASE_NAME = "NaturEvent";
    private static final String DATABASE_TABLE = "Points";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public PointsDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public PointsDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     *
     * @param title the title of the note
     * @param description the body of the note
     * @param category the category id related to the note
     * @return rowId or -1 if failed
     */
    public long createPoint(String title, String description, String category, double latitude, double longitude) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_TITLE, title);
            initialValues.put(KEY_DESC, description);
            initialValues.put(KEY_LAT, latitude);
            initialValues.put(KEY_LON, longitude);
            initialValues.put(KEY_CAT, category);
            initialValues.put(KEY_LIKES, 0);
            initialValues.put(KEY_DISLIKES, 0);

            return mDb.insert(DATABASE_TABLE, null, initialValues);

    }

    /**
     * Delete the note with the given rowId
     *
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteNote(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all notes in the database
     *
     * @return Cursor over all notes
     */
    public Cursor fetchAllPoints() {

        Cursor mCursor = mDb.query(DATABASE_TABLE, new String[] {KEY_ID,
                KEY_TITLE, KEY_DESC,KEY_LAT,KEY_LON,KEY_LIKES,KEY_DISLIKES,KEY_CAT}, null, null, null, null, KEY_TITLE);

        if (mCursor != null) {
            //mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     *
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchPoint(double latitude, double longitude) throws SQLException {

        Cursor mCursor =

                mDb.query(DATABASE_TABLE, new String[] {KEY_ID,
                                KEY_TITLE, KEY_DESC,KEY_LAT,KEY_LON,KEY_LIKES,KEY_DISLIKES,KEY_CAT}
                        , KEY_LAT + "=" + latitude + " AND " + KEY_LON + "=" + longitude , null,null,
                        null, KEY_TITLE);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }


    public Cursor fetchPointByCat(String cat) throws SQLException {

        Cursor mCursor =

                mDb.query(DATABASE_TABLE, new String[] {KEY_ID,
                                KEY_TITLE, KEY_DESC,KEY_LAT,KEY_LON,KEY_LIKES,KEY_DISLIKES,KEY_CAT}
                        , KEY_CAT + "='" + cat +"'" , null,null,
                        null, KEY_TITLE);

        if (mCursor != null) {
            //mCursor.moveToFirst();
        }
        return mCursor;

    }



    public void addRating(long rowId, int tipoVoto){
        if(tipoVoto == 1){
            mDb.execSQL("update " + DATABASE_TABLE + " SET likes=likes + 1 WHERE _id=" + rowId);
        }
        if(tipoVoto == -1){
            mDb.execSQL("update " + DATABASE_TABLE + " SET dislikes=dislikes + 1 WHERE _id=" + rowId);
        }
    }

    public void deleteAll(){
        mDb.delete(DATABASE_TABLE,null,null);
    }


    /**
     * Return a Cursor over the list of the notes following the criteria given by the user
     *
     * @param category the category filter
     *
     * @param orden the orden filter
     *
     * @return Cursor over the notes filtered
     */
    //public Cursor fetchNotesFiltered(int category, String orden){
    //    if(category==0 && orden==null){

    		/* All kind of notes */
        //    return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
        //            KEY_BODY, KEY_CAT}, null, null, null, null, KEY_TITLE);
        //}
        //else if(category == 0 && orden != null){

    		/* Notes with an order filter */
        //    return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
       //             KEY_BODY, KEY_CAT}, null, null, null, null, orden);
     //   }
     //   else if(category != 0 && orden == null){

    		/* Notes with a category filter */
            //return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
          //          KEY_BODY, KEY_CAT}, KEY_CAT + "=" + category, null, null, null, KEY_TITLE);
        //}
      //  else {

    		/* Notes with a category filter and an order */
    //        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
  //                  KEY_BODY, KEY_CAT}, KEY_CAT + "=" + category, null, null, null, orden);
//        }

//    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     *
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @param category value to set note category to
     * @return true if the note was successfully updated, false otherwise
     */
   // public boolean updateNote(long rowId, String title, String body, int category) {

    //    if(title!=null && (title.equals("") || category < 1)){
    //        return false;
    //    }
    //    else{
    //        ContentValues args = new ContentValues();
    //        args.put(KEY_TITLE, title);
    //        args.put(KEY_BODY, body);
    //        args.put(KEY_CAT, category);

     //       return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
     //   }
    //}



}
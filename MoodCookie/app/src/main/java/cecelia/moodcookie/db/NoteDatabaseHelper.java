package cecelia.moodcookie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import cecelia.moodcookie.types.Mood;
import cecelia.moodcookie.types.Note;


public class NoteDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notes.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteContract.NoteEntry.TABLE_NAME + " (" +
                    NoteContract.NoteEntry._ID + " INTEGER PRIMARY KEY," +
                    NoteContract.NoteEntry.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    NoteContract.NoteEntry.COLUMN_NAME_MOOD + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE_NAME;

    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addToDatabase(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_NAME_TEXT, note.getText());
        values.put(NoteContract.NoteEntry.COLUMN_NAME_MOOD, note.getMood().toString());
        db.insert(NoteContract.NoteEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Note> getAllNotes() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_NAME_TEXT,
                NoteContract.NoteEntry.COLUMN_NAME_MOOD
        };

        String sortOrder = NoteContract.NoteEntry.COLUMN_NAME_TEXT + " DESC";

        Cursor cursor = db.query(
                NoteContract.NoteEntry.TABLE_NAME,   // The table to query
                projection,                          // The columns to return
                null,                                // The columns for the WHERE clause
                null,                                // The values for the WHERE clause
                null,                                // don't group the rows
                null,                                // don't filter by row groups
                sortOrder                            // The sort order
        );

        final ArrayList<Note> allNotes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(NoteContract.NoteEntry._ID));
                String text = cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_TEXT));
                String mood = cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_MOOD));

                allNotes.add(new Note(id, text, mood));
                cursor.moveToNext();
            } while (!cursor.isLast());
        }

        cursor.close();
        db.close();

        return allNotes;
    }

    public ArrayList<Note> getNotesByMood(Mood mood) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_NAME_TEXT,
                NoteContract.NoteEntry.COLUMN_NAME_MOOD
        };

        String selection = NoteContract.NoteEntry.COLUMN_NAME_MOOD + " = ?";
        String sortOrder = NoteContract.NoteEntry.COLUMN_NAME_TEXT + " DESC";
        String[] selectionArgs = {mood.toString()};

        Cursor cursor = db.query(
                NoteContract.NoteEntry.TABLE_NAME,   // The table to query
                projection,                          // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                                // The values for the WHERE clause
                null,                                // don't group the rows
                null,                                // don't filter by row groups
                sortOrder                            // The sort order
        );

        final ArrayList<Note> allNotes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(NoteContract.NoteEntry._ID));
                String text = cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_NAME_TEXT));

                allNotes.add(new Note(id, text, mood.toString()));
                cursor.moveToNext();
            } while (!cursor.isLast());
        }

        cursor.close();
        db.close();

        return allNotes;
    }

}

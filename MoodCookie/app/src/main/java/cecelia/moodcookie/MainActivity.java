package cecelia.moodcookie;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    NoteDatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.databaseHelper = new NoteDatabaseHelper(this);

        // read sad notes from the database
        Cursor c = getReadCursorFromDatabase(NoteContract.NoteEntry.COLUMN_NAME_MOOD, Moods.SAD.toString());

        //log the sad notes
        boolean atLast = false;
        c.moveToFirst();
        while (!atLast) {
            Log.d("MainActivity", c.getString(c.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_TEXT)));
            atLast = c.isLast();
            c.moveToNext();
        }

    }

    public void addToDatabase(String text, String mood) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_NAME_TEXT, text);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_MOOD, mood);
        long newRowId = db.insert(NoteContract.NoteEntry.TABLE_NAME, null, values);
    }

    public Cursor getReadCursorFromDatabase(String column, String arg) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_NAME_TEXT,
                NoteContract.NoteEntry.COLUMN_NAME_MOOD
        };
        String selection = column + " = ?";
        String[] selectionArgs = {arg};
        String sortOrder =
                NoteContract.NoteEntry.COLUMN_NAME_TEXT + " DESC";

        Cursor c = db.query(
                NoteContract.NoteEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return c;
    }

}

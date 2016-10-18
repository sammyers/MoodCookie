package cecelia.moodcookie.db;

import android.provider.BaseColumns;

/**
 * Created by Cecelia on 10/17/16.
 */

public final class NoteContract {

    public NoteContract() {}

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "note";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_MOOD = "mood";
    }
}

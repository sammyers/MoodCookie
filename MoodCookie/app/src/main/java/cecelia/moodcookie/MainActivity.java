package cecelia.moodcookie;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cecelia.moodcookie.db.NoteDatabaseHelper;
import cecelia.moodcookie.types.Mood;
import cecelia.moodcookie.types.Note;

public class MainActivity extends AppCompatActivity {

    NoteDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dbHelper = new NoteDatabaseHelper(this);

        // read notes from the database
        ArrayList<Note> notes = dbHelper.getAllNotes();

        for (Note note : notes) {
            Log.d("Main Activity", note.getText());
        }

        addMainPageFragment();

    }

    public void addMainPageFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, new HomepageFragment(), "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
    }
}

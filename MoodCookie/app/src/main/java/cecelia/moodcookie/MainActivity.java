package cecelia.moodcookie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cecelia.moodcookie.Indico.IndicoExperimenting;
import cecelia.moodcookie.db.NoteDatabaseHelper;
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
        changeFragment(new IndicoExperimenting());
    }

    //switches fragments, new fragment is input
    public void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}

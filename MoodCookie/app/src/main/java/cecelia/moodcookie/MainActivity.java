package cecelia.moodcookie;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cecelia.moodcookie.db.NoteDatabaseHelper;
import cecelia.moodcookie.types.Mood;
import cecelia.moodcookie.types.Note;

public class MainActivity extends AppCompatActivity {

    NoteDatabaseHelper dbHelper;

    private Bitmap mImageBitmap;

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

        startHomepageFragment();

    }

    public void startHomepageFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, new HomepageFragment(), "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
    }

    public void startConfirmationPageFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, new ConfirmationPageFragment(), "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            handleCameraPhoto(data);
        }
    }

    private void handleCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        this.mImageBitmap = (Bitmap) extras.get("data");
        startConfirmationPageFragment();
    }
}

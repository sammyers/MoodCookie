package cecelia.moodcookie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cecelia.moodcookie.camera.CameraInterface;
import cecelia.moodcookie.camera.PhotoHandler;
import cecelia.moodcookie.db.NoteDatabaseHelper;
import cecelia.moodcookie.types.Note;

public class MainActivity extends AppCompatActivity implements CameraInterface {

    private NoteDatabaseHelper dbHelper;
    private PhotoHandler photoHandler;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new NoteDatabaseHelper(this);
        photoHandler = new PhotoHandler(this);
        fragmentManager = getFragmentManager();

        // read notes from the database
        ArrayList<Note> notes = dbHelper.getAllNotes();

        for (Note note : notes) {
            Log.d("Main Activity", note.getText());
        }

        startHomepageFragment();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            handleCameraPhoto();
        }
    }

    private void handleCameraPhoto() {
        startConfirmationPageFragment();
    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
        fragmentTransaction.replace(R.id.fragment_holder, fragment, "CURRENT_FRAGMENT");
        fragmentTransaction.commit();
    }

    public void startDisplayPageFragment() {
        startFragment(new DisplayPageFragment());
    }

    public void startHomepageFragment() {
        startFragment(new HomepageFragment());
    }

    public void startConfirmationPageFragment() {
        startFragment(new ConfirmationPageFragment());
    }

    public PhotoHandler getPhotoHandler() {
        return photoHandler;
    }
}

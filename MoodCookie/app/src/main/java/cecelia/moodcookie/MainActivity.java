package cecelia.moodcookie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cecelia.moodcookie.camera.CameraInterface;
import cecelia.moodcookie.camera.PhotoHandler;
import cecelia.moodcookie.db.NoteDatabaseHelper;
import cecelia.moodcookie.types.Mood;
import cecelia.moodcookie.types.Note;
public class MainActivity extends AppCompatActivity implements CameraInterface {

    static final String TAG = "MainActivity";

    NoteDatabaseHelper dbHelper;
    private PhotoHandler photoHandler;
    private Bitmap mImageBitmap;
    static final int SELECT_GALLERY_IMAGE = 1;
    IndicoHandler indicoHandler;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new NoteDatabaseHelper(this);
        photoHandler = new PhotoHandler(this);
        fragmentManager = getFragmentManager();

        startHomepageFragment();

    }

    private void handleCameraPhoto() {
        startConfirmationPageFragment();
    }

    private void startFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, fragment, tag);
        fragmentTransaction.commit();
    }

    public void startDisplayPageFragment(String filePath) {
        startFragment(new DisplayPageFragment(), filePath);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_GALLERY_IMAGE) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                handleCameraPhoto();
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            Log.d(TAG, "The chosen image was invalid.");
            return null;
        }
        // this is our fallback here
        return uri.getPath();
    }

    public void startHomepageFragment() {
        startFragment(new HomepageFragment(), "");
    }

    public void startConfirmationPageFragment() {
        startFragment(new ConfirmationPageFragment(), "");
    }

    public PhotoHandler getPhotoHandler() {
        return photoHandler;
    }
}

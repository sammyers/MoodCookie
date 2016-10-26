package cecelia.moodcookie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import cecelia.moodcookie.camera.CameraInterface;
import cecelia.moodcookie.camera.PhotoHandler;
import cecelia.moodcookie.db.NoteDatabaseHelper;
import cecelia.moodcookie.types.Note;
public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    NoteDatabaseHelper dbHelper;
    private PhotoHandler photoHandler;
    IndicoHandler indicoHandler;
    static final int SELECT_GALLERY_IMAGE = 2;
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

    private void handleGalleryPhoto(Intent data) {
        Uri selectedImageUri = data.getData();
        photoHandler.setPhotoUri(selectedImageUri);
        startConfirmationPageFragment();
    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);
        fragmentTransaction.replace(R.id.fragment_holder, fragment, "");
        fragmentTransaction.commit();
    }

    public void startDisplayPageFragment() {
        startFragment(new DisplayPageFragment());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == photoHandler.SELECT_GALLERY_IMAGE) {
                handleGalleryPhoto(data);
            } else if (requestCode == photoHandler.REQUEST_IMAGE_CAPTURE) {
                handleCameraPhoto();
            }
        }
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

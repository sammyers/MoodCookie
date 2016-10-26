package cecelia.moodcookie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cecelia.moodcookie.camera.PhotoHandler;
import cecelia.moodcookie.db.NoteDatabaseHelper;
public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3;

    NoteDatabaseHelper dbHelper;
    private PhotoHandler photoHandler;
    IndicoHandler indicoHandler;
    static final int SELECT_GALLERY_IMAGE = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private FragmentManager fragmentManager;

    private String document_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new NoteDatabaseHelper(this);
        photoHandler = new PhotoHandler(this);
        fragmentManager = getFragmentManager();
        indicoHandler = new IndicoHandler(this);

        startHomepageFragment();
    }

    private void handleCameraPhoto() {
        startConfirmationPageFragment();
    }

    private void handleGalleryPhoto(Intent data) {
        Uri selectedImageUri = data.getData();

        //head before merge with sam
//        photoHandler.setPhotoUri(selectedImageUri);
//        photoHandler.setPhotoPath(getPath(selectedImageUri));
//        startConfirmationPageFragment();

        Cursor cursor = getContentResolver().query(selectedImageUri, null, null, null, null);
        cursor.moveToFirst();
        document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    prepareConfirmationPage();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
            } else {
                prepareConfirmationPage();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prepareConfirmationPage();
                }
                return;
            }
        }
    }

    private void prepareConfirmationPage() {
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null
        );
        cursor.moveToFirst();

        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        photoHandler.setPhotoPath(path);
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

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            Log.d(TAG, "The chosen image was invalid.");
            return null;
        }
        // this is our fallback here
        return uri.getPath();
    }

    public PhotoHandler getPhotoHandler() {
        return photoHandler;
    }

    public IndicoHandler getIndicoHandler() {
        return indicoHandler;
    }

    public void prepareDisplayPage() {
        Bitmap bm = this.getPhotoHandler().getBitmap();
        this.indicoHandler.setBitmap(bm);
        this.indicoHandler.analyzePicture();
    }
}

package cecelia.moodcookie.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import cecelia.moodcookie.MainActivity;
import cecelia.moodcookie.R;

/**
 * Created by smyers on 10/20/2016.
 */

public class PhotoHandler {

    private Context context;
    public int REQUEST_IMAGE_CAPTURE = 1;
    public int SELECT_GALLERY_IMAGE = 2;
    private String mCurrentPhotoPath;
    private Uri mCurrentPhotoUri;

    public PhotoHandler(Context context) {
        this.context = context;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) { }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        context,
                        "com.example.android.fileprovider",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                ((Activity) context).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void dispatchGalleryIntent() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) context).startActivityForResult(Intent.createChooser(galleryIntent,
                "Select Picture"), SELECT_GALLERY_IMAGE);
    }

    public void setPhoto(ImageView imageView) {
        if (mCurrentPhotoPath != null) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            int width = imageBitmap.getWidth();
            int height = imageBitmap.getHeight();

            Matrix matrix = new Matrix();
            matrix.postRotate(90);

            Bitmap rotatedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, width, height, matrix, true);
            imageView.setImageBitmap(rotatedBitmap);
        }
    }

    public String getPhotoPath() {
        return mCurrentPhotoPath;
    }

    public void setPhotoPath(String path) {
        mCurrentPhotoPath = path;
    }

    public void setPhotoUri(Uri uri) {
        mCurrentPhotoUri = uri;
    }
}

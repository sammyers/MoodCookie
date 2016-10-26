package cecelia.moodcookie.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by smyers on 10/20/2016.
 */

public class PhotoHandler {

    private Context context;
    private int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    public PhotoHandler(Context context) {
        this.context = context;
    }

    public File createImageFile() throws IOException {
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
            } catch (IOException ex) {
            }

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

    public void setPhoto(ImageView imageView) { //sets passed-in image view to current photo
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

}
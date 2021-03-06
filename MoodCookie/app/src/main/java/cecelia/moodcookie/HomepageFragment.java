package cecelia.moodcookie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import cecelia.moodcookie.camera.CameraInterface;
import cecelia.moodcookie.camera.PhotoHandler;

public class HomepageFragment extends Fragment {

    private MainActivity mainActivity;

    private static final String TAG = "HomepageFragment";

    private ImageButton cameraButton;
    private ImageButton libraryButton;

    public HomepageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mainActivity = getMainActivity();

        View view = inflater.inflate(R.layout.homepage_fragment, container, false);

        cameraButton = (ImageButton) view.findViewById(R.id.pic_with_camera);
        libraryButton = (ImageButton) view.findViewById(R.id.choose_from_library);

        setOnClickListeners();

        return view;
    }

    private void setOnClickListeners() {
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Camera button clicked.");
                mainActivity.getPhotoHandler().dispatchTakePictureIntent();
            }
        });

        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Library button clicked.");
                mainActivity.getPhotoHandler().dispatchGalleryIntent();
            }
        });
    }



    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}

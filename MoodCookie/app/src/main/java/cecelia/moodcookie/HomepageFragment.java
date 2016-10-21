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

public class HomepageFragment extends Fragment {

    private static final String TAG = "HomepageFragment";

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageButton cameraButton;
    private ImageButton libraryButton;

    public HomepageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
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
                dispatchTakePictureIntent();
            }
        });

        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Library button clicked.");
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}

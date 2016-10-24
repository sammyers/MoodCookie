package cecelia.moodcookie;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class HomepageFragment extends Fragment {

    private static final String TAG = "HomepageFragment";

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
            }
        });

        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Library button clicked.");
                dispatchhGalleryIntent();
            }
        });
    }

    private void dispatchhGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        MainActivity activity = getMainActivity();
        activity.startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), activity.SELECT_GALLERY_IMAGE);
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}

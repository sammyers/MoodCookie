package cecelia.moodcookie;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import cecelia.moodcookie.camera.CameraInterface;


public class ConfirmationPageFragment extends Fragment {

    private CameraInterface mInterface;

    public static final String TAG = "ConfirmationFragment";

    private ImageView mImageView;
    private ImageButton yesButton;
    private ImageButton noButton;

    public ConfirmationPageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mInterface = (CameraInterface) getActivity();

        View view = inflater.inflate(R.layout.confirmation_fragment, container, false);

        mImageView = (ImageView) view.findViewById(R.id.pic_view);
        mInterface.getPhotoHandler().setPhoto(mImageView);

        yesButton = (ImageButton) view.findViewById(R.id.yes_pic);
        noButton = (ImageButton) view.findViewById(R.id.no_pic);

        setOnClickListeners();

        return view;
    }

    private void setOnClickListeners() {
        this.yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "yes clicked");
            }
        });

        this.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "no clicked");
            }
        });
    }
}

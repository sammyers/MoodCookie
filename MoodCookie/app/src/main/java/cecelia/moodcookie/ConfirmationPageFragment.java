package cecelia.moodcookie;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import cecelia.moodcookie.camera.CameraInterface;


public class ConfirmationPageFragment extends Fragment {

    /*
     Instead of directly calling methods on the mainActivity and keeping reference to it, I'd recommend
     defining an interface and using the MainActivity as an implementation of this interface instead
      */
    private MainActivity mainActivity;

    public static final String TAG = "ConfirmationFragment";

    private ImageView mImageView;
    private ImageButton yesButton;
    private ImageButton noButton;

    public ConfirmationPageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();

        View view = inflater.inflate(R.layout.confirmation_fragment, container, false);

        setUpViews(view);

        setOnClickListeners();

        return view;
    }

    public void setUpViews(View view) {
        // Butterknife would be good here - not super necessary to encapsulate this, but good thought
        yesButton = (ImageButton) view.findViewById(R.id.yes_pic);
        noButton = (ImageButton) view.findViewById(R.id.no_pic);
        mImageView = (ImageView) view.findViewById(R.id.pic_view);
        mainActivity.getPhotoHandler().setPhoto(mImageView);
    }


    private void setOnClickListeners() {
        this.yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.prepareDisplayPage();
            }
        });

        this.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startHomepageFragment();
            }
        });
    }

}

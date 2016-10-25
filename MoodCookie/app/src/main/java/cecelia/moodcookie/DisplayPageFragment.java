package cecelia.moodcookie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Cecelia on 10/24/16.
 */

public class DisplayPageFragment extends Fragment {

    private ImageButton backButton;
    private MainActivity mainActivity;
    private ImageView imageView;

    public DisplayPageFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_fragment, container, false);

        backButton = (ImageButton) view.findViewById(R.id.back_to_homepage);
        mainActivity = (MainActivity) getActivity();
        imageView = (ImageView) view.findViewById(R.id.display_image_view);

        mainActivity.getPhotoHandler().setPhoto(imageView);

        setOnClickListeners();

        return view;
    }

    public void setOnClickListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startHomepageFragment();
            }
        });
    }
}

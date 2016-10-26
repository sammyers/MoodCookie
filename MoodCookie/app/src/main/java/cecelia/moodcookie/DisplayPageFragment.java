package cecelia.moodcookie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import cecelia.moodcookie.camera.PhotoHandler;

/**
 * Created by Cecelia on 10/24/16.
 */

public class DisplayPageFragment extends Fragment {

    private ImageButton backButton;
    private ImageButton saveButton;

    public DisplayPageFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_fragment, container, false);

        this.backButton = (ImageButton) view.findViewById(R.id.back_to_homepage);
        this.saveButton = (ImageButton) view.findViewById(R.id.save_mookie);

        setOnClickListenerBack();
        setOnClickListenerSave();

        return view;
    }

    public void setOnClickListenerBack() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.startHomepageFragment();
            }
        });
    }

    public void setOnClickListenerSave() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                PhotoHandler photoHandler = new PhotoHandler(activity);
                //take screenshot of layout
                //save to external storage using photoHandler
                //maybe change button to check when done

            }
        });
    }

}

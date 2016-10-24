package cecelia.moodcookie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Cecelia on 10/24/16.
 */

public class DisplayPageFragment extends Fragment {

    private ImageButton backButton;

    public DisplayPageFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_fragment, container, false);

        this.backButton = (ImageButton) view.findViewById(R.id.back_to_homepage);

        setOnClickListeners();

        return view;
    }

    public void setOnClickListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.startHomepageFragment();
            }
        });
    }
}

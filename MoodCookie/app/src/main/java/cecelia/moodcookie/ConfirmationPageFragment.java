package cecelia.moodcookie;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class ConfirmationPageFragment extends Fragment {

    public static final String TAG = "ConfirmationFragment";

    ImageButton yesButton;
    ImageButton noButton;

    public ConfirmationPageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirmation_fragment, container, false);

        this.yesButton = (ImageButton) view.findViewById(R.id.yes_pic);
        this.noButton = (ImageButton) view.findViewById(R.id.no_pic);

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

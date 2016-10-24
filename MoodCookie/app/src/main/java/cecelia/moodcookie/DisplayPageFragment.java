package cecelia.moodcookie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Random;

import cecelia.moodcookie.types.Mood;
import cecelia.moodcookie.types.Note;

/**
 * Created by Cecelia on 10/24/16.
 */

public class DisplayPageFragment extends Fragment {

    private ImageButton backButton;

    public DisplayPageFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_fragment, container, false);

        this.backButton = (ImageButton) view.findViewById(R.id.back_to_homepage);

        String filepath = getTag();
        String mood = getMainActivity().indicoHandler.analyzePicture(filepath);
        Note note = pickNote(mood);

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

    private Note pickNote(String moodString) {
        Mood mood = Mood.getMood(moodString);
        ArrayList<Note> notes = getMainActivity().dbHelper.getNotesByMood(mood);
        Random random = new Random();
        int position = random.nextInt(notes.size());
        Note note = notes.get(position);
        return note;
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}

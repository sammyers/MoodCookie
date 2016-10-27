package cecelia.moodcookie;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import cecelia.moodcookie.types.Mood;
import cecelia.moodcookie.types.Note;


public class DisplayPageFragment extends Fragment {

    private ImageButton backButton;
    private ImageButton saveButton;
    private MainActivity mainActivity;
    private ImageView imageView;
    private TextView noteView;

    public DisplayPageFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_fragment, container, false);
        getViews(view);
        View relativeLayout = view.findViewById(R.id.outerLayout); //only want innerLayout, but it doesn't work correctly rn
        mainActivity.getPhotoHandler().setPhoto(imageView);
        setNote();
        setOnClickListenerBack();
        setOnClickListenerSave(relativeLayout);
        return view;
    }

    private void getViews(View view) {
        backButton = (ImageButton) view.findViewById(R.id.back_to_homepage);
        saveButton = (ImageButton) view.findViewById(R.id.save_mookie);
        mainActivity = (MainActivity) getActivity();
        imageView = (ImageView) view.findViewById(R.id.display_image_view);
        noteView = (TextView) view.findViewById(R.id.note);
    }

    private void setOnClickListenerBack() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startHomepageFragment();
            }
        });
    }

    public void setOnClickListenerSave(final View relativeLayout) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View layout) {
                //take screenshot of view in bitmap form
                relativeLayout.setDrawingCacheEnabled(true);
                Bitmap viewBitmap = Bitmap.createBitmap(relativeLayout.getWidth(),relativeLayout.getHeight(),Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(viewBitmap);
                relativeLayout.draw(canvas);
                //save to external storage
                try {
                    saveToGallery(viewBitmap);
                    saveButton.setImageResource(R.drawable.check); //change download button to check if successful :)
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void saveToGallery(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes); //format as jpg -- compress to bytes object?
        String imageFileName = "Mookie_from_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap,imageFileName, "Brought to you by MoodCookie");
    }

    public void setNote() {
        String mood = getMainActivity().getIndicoHandler().getMaxEmotion();
        Log.d("MOOD***********", mood);
        Note note = pickNote(mood);
        noteView.setText(note.getText());
    }

    private Note pickNote(String moodString) {
        Mood mood = Mood.getMood(moodString);
        mainActivity.dbHelper.addToDatabase(new Note("holla", Mood.HAPPY));
        mainActivity.dbHelper.addToDatabase(new Note("bob", Mood.HAPPY));
        ArrayList<Note> notes = getMainActivity().dbHelper.getNotesByMood(mood);
        Random random = new Random();
        Log.d("NOTE SIZE **********", String.valueOf(notes.size()));
        int position = random.nextInt(notes.size());
        Note note = notes.get(position);
        return note;
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}

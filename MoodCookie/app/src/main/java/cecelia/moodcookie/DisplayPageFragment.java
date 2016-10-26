package cecelia.moodcookie;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cecelia.moodcookie.camera.PhotoHandler;

/**
 * Created by Cecelia on 10/24/16.
 */

public class DisplayPageFragment extends Fragment {

    private ImageButton backButton;
    private ImageButton saveButton;
    private MainActivity mainActivity;
    private ImageView imageView;
    private TextView note;

    public DisplayPageFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_fragment, container, false);
        Context context = getActivity().getBaseContext();
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relLayout);
        View screenView = relativeLayout;

        this.backButton = (ImageButton) view.findViewById(R.id.back_to_homepage);
        this.saveButton = (ImageButton) view.findViewById(R.id.save_mookie);
        this.note = (TextView) view.findViewById(R.id.note);

        //set to photo they just took (or imported)
//        imageView = (ImageView) view.findViewById(R.id.display_image_view);
//        mainActivity.getPhotoHandler().setPhoto(imageView);

        //call Indico's API and get text for mood cookie
//        processImage(context);

        //set listeners on back and save buttons
        setOnClickListenerBack();
        setOnClickListenerSave(screenView);

        return view;
    }

    public void setOnClickListenerBack() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startHomepageFragment();
            }
        });
    }

    public void setOnClickListenerSave(View view) {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View layout) {
//                MainActivity activity = (MainActivity) getActivity();
                //take screenshot of view in bitmap form
                Log.d("CREATED BITMAP","----------------------------------------------------");
                layout.setDrawingCacheEnabled(true);
//                Bitmap viewBitmap = Bitmap.createBitmap(layout.getDrawingCache());
                Bitmap viewBitmap = Bitmap.createBitmap(layout.getWidth(),layout.getHeight(),Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(viewBitmap);
                layout.draw(canvas);
                //save to external storage
                try {
                    Log.d("TRYING","----------------------------------------------------");
                    saveToGallery(viewBitmap);
                    saveButton.setImageResource(R.drawable.check); //change download button to check if successful :)
                } catch (IOException e) {
                    Log.d("ERROR","----------------------------------------------------");
                    e.printStackTrace();
                }
            }
        });
    }

    public void saveToGallery(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes); //format as jpg -- compress to bytes object?
        File imageFile = new File(Environment.getExternalStorageDirectory() + File.separator + //give unique name so it won't overwrite files
                "Mookie_from_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
        Log.d("CREATED IMAGE**********",imageFile.toString());
        imageFile.createNewFile(); //make an empty image file
        Log.d("CREATED IMAGE2*******", imageFile.toString());
        FileOutputStream fileOutput = new FileOutputStream(imageFile);
        fileOutput.write(bytes.toByteArray()); //write bytes retrieved from bitmap above (?) to file
        fileOutput.close();
    }

//    public void processImage(Context context){
//        IndicoHandler indico = new IndicoHandler(context);
//        String mood = indico.analyzePicture(mainActivity.getPhotoHandler().getPhotoPath());
//        //Todo: Cecelia's doing this
//        String cookieText = "Hello, Cecelia";
//        note.setText(cookieText);
//    }

}

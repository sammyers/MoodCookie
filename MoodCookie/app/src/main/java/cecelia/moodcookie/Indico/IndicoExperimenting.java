package cecelia.moodcookie.Indico;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.Map;

import cecelia.moodcookie.R;
import io.indico.Indico;
//import io.indico.api.image.FacialEmotion;
import io.indico.enums.FacialEmotion;
import io.indico.results.IndicoResult;
import io.indico.utils.IndicoException;
import io.indico.network.IndicoCallback;


public class IndicoExperimenting extends Fragment {

    public IndicoExperimenting() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_indico_experimenting, container, false);

//        ImageView sampleIV = (ImageView) view.findViewById(R.id.samplePic);
        String APIKey = "8f9117c50bd2909f3c13fb5ab04ea528";
        final String filePath = "/drawable/happy_lady.jpg";
        final TextView output = (TextView) view.findViewById(R.id.sampleOutput);

        Indico.init(getContext(),"8f9117c50bd2909f3c13fb5ab04ea528", null); //nothing happened here

        Log.d("RUH ROH1", "**************************");
        try {
            Indico.sentiment.predict("indico is so easy to use!", new IndicoCallback<IndicoResult>() {
                @Override public void handle(IndicoResult result) throws IndicoException {
                    Log.d("RUH ROH2", "**************************");
                    Log.i("Indico Sentiment", "sentiment of: " + result.getSentiment());
                    output.setText(result.getSentiment().toString());
                }
            });
        } catch (IOException | IndicoException e) {
            Log.d("RUH ROH4", "**************************");
            e.printStackTrace();
        }
        Log.d("RUH ROH5", "**************************");


//        try {
//            Log.d("ABOUT TO TRY",APIKey);
//            Indico.fer.predict(filePath, new IndicoCallback<IndicoResult>() { //pass in picture and get output
//                @Override public void handle(IndicoResult result) throws IndicoException {
//                    //create map of facial emotions to doubles
//                    Map<FacialEmotion, Double> resultMap = result.getFer();
//                    String sampleText = resultMap.get("Happy").toString();
//                    //set output to text view
//                    Log.d("ABOUT TO SET TEXT",sampleText);
//                    output.setText("BOB");
//                }
//            });
//        } catch (IOException | IndicoException e) {
//            Log.d("ABOUT TO PRINT ERROR","You done fucked up");
//            e.printStackTrace();
//        }


        return view;
    }
}
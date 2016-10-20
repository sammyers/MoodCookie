package cecelia.moodcookie.Indico;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.Map;

import io.indico.Indico;
import io.indico.api.image.FacialEmotion;

import cecelia.moodcookie.R;
import io.indico.api.results.IndicoResult;
import io.indico.api.utils.IndicoException;


public class IndicoExperimenting extends Fragment {

    public IndicoExperimenting() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indico_experimenting, container, false);

//        ImageView sampleIV = (ImageView) view.findViewById(R.id.samplePic);
        String APIKey = getContext().getResources().getString(R.string.indico_key);
        Indico indico = new Indico(APIKey);
        String filePath = "/drawable/happy_kid.jpg";
        IndicoResult single = null;
        try {
            single = indico.fer.predict(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndicoException e) {
            e.printStackTrace();
        }
        try {
            Map<FacialEmotion, Double> result = single.getFer();
        } catch (IndicoException e) {
            e.printStackTrace();
        }

        return view;
    }

}
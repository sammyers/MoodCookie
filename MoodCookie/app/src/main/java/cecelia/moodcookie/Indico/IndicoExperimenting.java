package cecelia.moodcookie.Indico;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.indico.Indico;
import io.indico.api.ImageApi;

import cecelia.moodcookie.R;


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



        return view;
    }

}
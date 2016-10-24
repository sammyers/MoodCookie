package cecelia.moodcookie;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import cecelia.moodcookie.types.MySingleton;


public class IndicoCrap extends Fragment {

    public IndicoCrap() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_indico_experimenting, container, false);

        final String APIKey = BuildConfig.API_KEY;
        final TextView output = (TextView) view.findViewById(R.id.sampleOutput);
        String url = "https://apiv2.indico.io/fer"; //url for Indico's facial emotion recognition

        StringRequest postRequest=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HashMap<String, Float> emotionDict = new HashMap(); //dictionary we store emotions and their values in
                Float max_val = Float.parseFloat("0");
                String max_emotion = "";
                String shortenedResponse = response.substring(13, response.length()-2).replaceAll("\"",""); //strip "{"results": {", "}", and " from original response
                String[] splitResponse = shortenedResponse.split(", "); //split emotions into a list of emotions + their corresponding values
                for (int i = 0; i < splitResponse.length; i++){ //loops through emotions
                    String[] separated = splitResponse[i].split(": "); //split emotion and corresponding value, creates list of length 2 (key, value)
                    String key = separated[0];
                    Float val = Float.parseFloat(separated[1]); //convert from string into float
                    if (val > max_val){ //this conditional will tell us what emotion is most prevalent in the image
                        max_val = val;
                        max_emotion = key;
                    }
                    emotionDict.put(key,val); //put key/value pairs in map
                }

                output.setText("You are " + max_emotion.toLowerCase()); //set emotion to text
                Log.d("EDITED*****:",shortenedResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("error:",volleyError.toString());

            }
        }){@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params= new HashMap<>(); //only params are data (picture in base64)
            Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.happy_lady); //decode picture
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //compress data stream
            byte[] byteArray = baos.toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT); //create string via Base64
            params.put("data",encodedImage); //put data into params list
            return params;
        }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers= new HashMap<>();
                headers.put("X-APIKey", APIKey); //Indico requires this as of August 1st
                return headers;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(postRequest);

        return view;
    }
}
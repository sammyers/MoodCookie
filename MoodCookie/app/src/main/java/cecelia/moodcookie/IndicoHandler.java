package cecelia.moodcookie;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import cecelia.moodcookie.types.MySingleton;


public class IndicoHandler {
    private Context context;
    private final String APIKey = BuildConfig.API_KEY;
    private String url; //url for Indico's facial emotion recognition
    private String maxEmotion;
    private Bitmap faceImageBitmap;

    public IndicoHandler(Context context) {
        url = context.getString(cecelia.moodcookie.R.string.ind_url);
        this.context = context;
        maxEmotion = "";
        faceImageBitmap = null;
    }

    public void analyzePicture() {
        StringRequest postRequest=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HashMap<String, Float> emotionDict = new HashMap(); //dictionary we store emotions and their values in, want a fresh one each time
                Float max_val = Float.parseFloat("0");
                String shortenedResponse = response.substring(13, response.length()-2).replaceAll("\"",""); //strip "{"results": {", "}", and " from original response
                String[] splitResponse = shortenedResponse.split(", "); //split emotions into a list of emotions + their corresponding values
                for (int i = 0; i < splitResponse.length; i++){ //loops through emotions
                    String[] separated = splitResponse[i].split(": "); //split emotion and corresponding value, creates list of length 2 (key, value)
                    String key = separated[0];
                    Float val = Float.parseFloat(separated[1]); //convert from string into float
                    if (val > max_val){ //this conditional will tell us what emotion is most prevalent in the image
                        max_val = val;
                        maxEmotion = key;
                    }
                    emotionDict.put(key,val); //put key/value pairs in map
                    ((MainActivity)context).startDisplayPageFragment();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("error:",volleyError.toString());
            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>(); //only params are data (picture in base64)
                if (faceImageBitmap != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    faceImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //compress data stream
                    byte[] byteArray = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT); //create string via Base64
                    params.put("data",encodedImage); //put data into params list
                    return params;
                } else {
                    throw new NullPointerException("there is not bitmap in the indico api handler");
                }
        }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers= new HashMap<>();
                headers.put("X-APIKey", APIKey); //Indico requires this as of August 1st
                return headers;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public void setBitmap(Bitmap bitmap) {
        faceImageBitmap = bitmap;
    }

    public void setBitmapFromPath(String path) {
        faceImageBitmap = BitmapFactory.decodeFile(path);
    }

    public String getMaxEmotion() {
        return maxEmotion;
    }
}
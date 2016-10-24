package cecelia.moodcookie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cecelia.moodcookie.db.NoteDatabaseHelper;
import cecelia.moodcookie.types.Mood;
import cecelia.moodcookie.types.Note;

public class MainActivity extends AppCompatActivity {

    NoteDatabaseHelper dbHelper;
    FragmentManager fragmentManager;
    IndicoHandler indicoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dbHelper = new NoteDatabaseHelper(this);
        this.fragmentManager = getFragmentManager();

        startHomepageFragment();

    }

    private void startFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, fragment, tag);
        fragmentTransaction.commit();
    }

    public void startDisplayPageFragment(String filePath) {
        startFragment(new DisplayPageFragment(), filePath);
    }

    public void startHomepageFragment() {
        startFragment(new HomepageFragment(), "");
    }

    public void startConfirmationPageFragment() {
        startFragment(new ConfirmationPageFragment(), "");
    }

}

package mcg.android.Droid500px;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PopularActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the 500px Popular Photos tab");
        setContentView(textview);
    }

}

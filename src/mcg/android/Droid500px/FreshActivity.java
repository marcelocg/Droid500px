package mcg.android.Droid500px;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FreshActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the 500px Fresh Photos tab");
        setContentView(textview);
    }

}

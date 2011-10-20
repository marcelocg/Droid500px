package mcg.android.Droid500px;

import mcg.android.Droid500px.helpers.RequestManager;
import android.os.Bundle;

public class UpcomingActivity extends GridViewActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, RequestManager.UPCOMING);
    }

}

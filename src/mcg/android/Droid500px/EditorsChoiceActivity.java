package mcg.android.Droid500px;

import mcg.android.Droid500px.helpers.RequestManager;
import android.os.Bundle;

public class EditorsChoiceActivity extends GridViewActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, RequestManager.EDITORS_CHOICE);
    }

}

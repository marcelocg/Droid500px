package mcg.android.Droid500px;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainTabsActivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_tab_host);

//	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, FreshActivity.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("fresh").setIndicator("Fresh")
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, PopularActivity.class);
	    spec = tabHost.newTabSpec("popular").setIndicator("Popular")
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, EditorsChoiceActivity.class);
	    spec = tabHost.newTabSpec("editorsChoice").setIndicator("Editor's Choice")
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, UpcomingActivity.class);
	    spec = tabHost.newTabSpec("upcoming").setIndicator("Upcoming")
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
}

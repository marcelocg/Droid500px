package mcg.android.Droid500px;

import mcg.android.Droid500px.helpers.Settings;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class Droid500pxActivity extends Activity {
	
	OAuthConsumer consumer;
	OAuthProvider provider;

	private static SharedPreferences settings;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);

        initPreferences();
        
        consumer = new CommonsHttpOAuthConsumer(
				getResources().getString(R.string.consumer_key), 
				getResources().getString(R.string.consumer_secret));
        
        provider = new CommonsHttpOAuthProvider(
				getResources().getString(R.string.request_token_url),
				getResources().getString(R.string.access_token_url),
				getResources().getString(R.string.authorize_url));
        
        provider.setOAuth10a(true);
		Toast.makeText(this, "Trying to get authenticated...", Toast.LENGTH_LONG).show();

        authenticate();
        
    }

    private void authenticate() {
		// We look for saved user keys
		String token = Settings.getAccessToken();
		String secret = Settings.getAccessSecret();
		
		if(!(token == null || secret == null)) {
			consumer.setTokenWithSecret(token, secret);
			Toast.makeText(this, "User already authenticated. Token = " + token + ". Secret = " + secret, Toast.LENGTH_LONG).show();
		} else {
			String uri;
			try {
				uri = getAuthURL();
				startActivity(new Intent("android.intent.action.VIEW", Uri.parse(uri)));
			} catch (OAuthMessageSignerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthNotAuthorizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initPreferences() {
    	settings = getSharedPreferences("d5px", Context.MODE_PRIVATE);
        Settings.initSettings(settings);
        Settings.CALLBACK_URL = getResources().getString(R.string.callback_url);
	}

	@Override
	public void onResume() {
		super.onResume();

		Uri uri = this.getIntent().getData();

		if (uri != null) {
			String token = uri.getQueryParameter("oauth_token");
			String secret = uri.getQueryParameter("oauth_verifier");
			Settings.saveAccessCredentials(token, secret);
			Toast.makeText(this, "Authenticated with token = " + token + " and secret = " + secret, Toast.LENGTH_LONG).show();
		}
	}    
    
 

	private String getAuthURL()	throws 	OAuthMessageSignerException, OAuthNotAuthorizedException,
										OAuthExpectationFailedException, OAuthCommunicationException {
		String authUrl = provider.retrieveRequestToken(consumer, Settings.CALLBACK_URL );
		return authUrl;
	}

}
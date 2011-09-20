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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class AuthenticateActivity extends Activity {
	
	OAuthConsumer consumer;
	OAuthProvider provider;

	private String token;
	private String secret;
	
	static final int DIALOG_CHOOSE_AUTHENTICATE_OR_GUEST = 0;
	
	private static SharedPreferences settings;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        setContentView(R.layout.main);

        initPreferences();
        
        consumer = new CommonsHttpOAuthConsumer(
				getResources().getString(R.string.consumer_key), 
				getResources().getString(R.string.consumer_secret));
        
        provider = new CommonsHttpOAuthProvider(
				getResources().getString(R.string.request_token_url),
				getResources().getString(R.string.access_token_url),
				getResources().getString(R.string.authorize_url));
        
        provider.setOAuth10a(true);
//		Toast.makeText(this, "Trying to get authenticated...", Toast.LENGTH_LONG).show();

        checkAuthenticatedUser();
        
    }

    private void checkAuthenticatedUser() {
		// We look for saved user keys
		token = Settings.getAccessToken();
		secret = Settings.getAccessSecret();
		
		if(!(token == null || secret == null)) {
			consumer.setTokenWithSecret(token, secret);
			Toast.makeText(this, "User already authenticated. Token = " + token + ". Secret = " + secret, Toast.LENGTH_LONG).show();
		} else {
			
			showDialog(DIALOG_CHOOSE_AUTHENTICATE_OR_GUEST);
			
		}
	}

	private void authenticate() {
		String uri;
		try {
			uri = getAuthURL();
			startActivity(new Intent("android.intent.action.VIEW", Uri.parse(uri)));
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}
		
	}
    
	private void initPreferences() {
    	settings = getSharedPreferences("d5px", Context.MODE_PRIVATE);
        Settings.initSettings(settings);
        Settings.CALLBACK_URL = getResources().getString(R.string.callback_url);
	}

	private void runAsGuest() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Ok, proceeding as guest...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainTabsActivity.class);
        startActivity(intent);        
	}

	@Override
	public void onResume() {
		super.onResume();

		Uri uri = this.getIntent().getData();

		if (uri != null) {
			token = uri.getQueryParameter("oauth_token");
			secret = uri.getQueryParameter("oauth_verifier");
			Settings.saveAccessCredentials(token, secret);
			runAsAuthenticatedUser();
		}
	}    
    
	private void runAsAuthenticatedUser() {
		Toast.makeText(this, "Authenticated with token = " + token + " and secret = " + secret, Toast.LENGTH_LONG).show();
        //TODO Implement the flow to authenticated user
		
	}

	protected Dialog onCreateDialog(int id) {
	    AlertDialog alert;
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    
	    switch(id) {
	    case DIALOG_CHOOSE_AUTHENTICATE_OR_GUEST:
			
			builder.setTitle("There's no user account connected!")
				   .setMessage("Would you like to authenticate and bind a 500px account to this app?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                //TODO Implement the flow to authenticated user
			        	   authenticate();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   dialog.cancel();
			        	   runAsGuest();
			        	   //TODO Implement the flow to guest user
			           }

			       });
			alert = builder.create();		
	        break;
	    default:
	        alert = null;
	    }
	    return alert;
	}
	
	private String getAuthURL()	throws 	OAuthMessageSignerException, OAuthNotAuthorizedException,
										OAuthExpectationFailedException, OAuthCommunicationException {
		String authUrl = provider.retrieveRequestToken(consumer, Settings.CALLBACK_URL );
		return authUrl;
	}

}
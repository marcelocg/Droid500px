package mcg.android.Droid500px.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class RequestManager {

	private static String BASE_URL              = "https://api.500px.com/v1/photos?consumer_key=" + Settings.CONSUMER_KEY;
	
	private static String EDITORS_CHOICE_PARAM  = "&feature=editors";
	private static String FRESH_TODAY_PARAM     = "&feature=fresh_today";
	private static String FRESH_YESTERDAY_PARAM = "&feature=fresh_yesterday";
	private static String FRESH_WEEK_PARAM      = "&feature=fresh_week";
	private static String UPCOMING_PARAM        = "&feature=upcoming";
	private static String POPULAR_PARAM         = "&feature=popular";
	
	public static String EDITORS_CHOICE        = BASE_URL + EDITORS_CHOICE_PARAM;
	public static String FRESH_TODAY           = BASE_URL + FRESH_TODAY_PARAM;
	public static String FRESH_YESTERDAY       = BASE_URL + FRESH_YESTERDAY_PARAM;
	public static String FRESH_WEEK            = BASE_URL + FRESH_WEEK_PARAM;
	public static String UPCOMING              = BASE_URL + UPCOMING_PARAM;
	public static String POPULAR               = BASE_URL + POPULAR_PARAM;
	public static String MY_PHOTOS             = "";

	public static String[] readPhotoStream(String stream){
		return readPhotoStream(stream, 20, 1);
	}
	
	public static String[] readPhotoStream(String stream, int photosPerPage){
		return readPhotoStream(stream, photosPerPage, 1);
	}

	public static String[] readPhotoStreamPage(String stream, int page){
		return readPhotoStream(stream, 20, page);
	}
	
	public static String[] readPhotoStream(String stream, int photosPerPage, int page){
		
		String resultStream = request(stream, rectifyPhotosPerPage(photosPerPage), rectifyPageNumber(page));
		String[] urls = new String[photosPerPage];
		
		
		try {
			JSONObject feature = new JSONObject(resultStream);
			JSONArray photos = feature.getJSONArray("photos");
			
			for (int i = 0; i < photos.length(); i++) {
				String photoURL = photos.getJSONObject(i).getString("image_url");
				urls[i] = photoURL.replace("/2.jpg", "/1.jpg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return urls;
	}
	
	private static String request(String url, int photosPerPage, int page){
		
		url += "&rpp=" + photosPerPage + "&page=" + page;
		
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
		
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(RequestManager.class.toString(), "Failed to acesss photo stream at address: \n " + url);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	

	private static int rectifyPhotosPerPage(int photosPerPage){
		return photosPerPage > 100 ? 100 : photosPerPage;
	}

	private static int rectifyPageNumber(int page){
		return page < 1 ? 1 : page;
	}

}

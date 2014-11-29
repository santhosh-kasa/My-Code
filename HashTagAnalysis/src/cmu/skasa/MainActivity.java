package cmu.skasa;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;
/**
 * This is the main activity which is launched on app home screen
 * @author santhosh
 *
 */
public class MainActivity extends Activity {
	public  static final String SER_KEY = "test"; 
	public static String search="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
/**
 * This is call back listener on submit button
 * @param view
 */
	public void searchKeyword(View view) {
		// Kabloey
		String result="";
		// make a call and extract
		EditText text = (EditText)findViewById(R.id.keyword);
		String kw  = text.getText().toString();
		
		HashTag ht = new HashTag();
		ht.search(kw, this);
		search = kw;
		
		
		System.out.println(kw);
	}

	/**
	 * This method is used to process Json frmo the GAE
	 * @param result - input string
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public void processJSON(String result) throws IOException{

		final Context context = this;
		JSONObject jobj=null ;
		JSONArray jarr =null;
		ArrayList<HashTag> arrlist = new ArrayList<HashTag>();
		try {
			jobj = new JSONObject(result);
			jarr= jobj.getJSONArray("data");
			HashTag actualTag = extractData(jobj.getJSONObject("queryHashtag"));
//
			for(int i =0;i<jarr.length();i++){
				arrlist.add(extractData(jarr.getJSONObject(i)));
			}

//			HashTagData htd = new HashTagData(arrlist, actualTag);
			MainActivity ip = this;
			// write to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			for (HashTag element : arrlist) {
			    out.writeUTF(element.toString());
			}
			byte[] bytes = baos.toByteArray();
			
			
			Intent intent = new Intent(context, views.class);
			
			Bundle mBundle = new Bundle(); 
			mBundle.putSerializable(SER_KEY,actualTag); 
			intent.putExtras(mBundle);
			startActivity(intent);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * This method is used to extract data from JSON into hashtag class format	
 * @param obj - json object
 * @return- hashtag
 */
	public static HashTag extractData(JSONObject obj){
		HashTag ht = new HashTag();
		try {
			ht.tag = obj.getString("tag");
//			ht.frequency = obj.getString("frequency");
			ht.retweets  = obj.getString("retweets");
			ht.links = obj.getString("links");
			ht.mentions = obj.getString("mentions");
			ht.density = obj.getString("density");
			ht.potential_views = obj.getString("potential_views");
			ht.color = obj.getString("color");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ht;
	} 
}

package cmu.skasa;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
/**
 * This class is a second view to display results.
 * @author santhosh
 *
 */
public class views extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.views);
		
		try{
			
//			HashTag ht = ((HashTagData)this.getApplicationContext()).getHashTag();
			
			// add the image
			 final views ip = this;
			 String searchword = MainActivity.search;
			search(searchword, ip);
			
			Intent i = getIntent();
			

			HashTag ht1 = (HashTag)i.getSerializableExtra(MainActivity.SER_KEY);  
//			HashTagData htd = new HashTagData();
			String element = "";
		
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,ht1.toArray());
			

			ListView listView = (ListView) findViewById(R.id.listView1);
			listView.setAdapter(adapter);
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
	views ip1=null;
	Context context1;
	
// THE BELOW CODE PERFORMS SEARCH OF THE BACKGROUND IMAGE FROM FLICKR
	
	public void search(String searchTerm, views ip) {
		this.ip1 = ip;
		new AsyncFlickrSearch().execute(searchTerm);
	}
	
	public void setBackground(Bitmap Picture){
		@SuppressWarnings("deprecation")
		BitmapDrawable ob = new BitmapDrawable(Picture);
		ob.setAlpha(50);
		  ListView root=(ListView)findViewById(R.id.listView1);
		  root.setBackground(ob);
		
	}
	
	
    private class AsyncFlickrSearch extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return search(urls[0]);
        }

        protected void onPostExecute(Bitmap picture) {
        	
        	ip1.setBackground(picture);
        	
        }

        /* 
         * Search Flickr.com for the searchTerm argument, and return a Bitmap that can be put in an ImageView
         */
        private Bitmap search(String searchTerm) {        
    	      String pictureURL = null;
    	      Document doc = getRemoteXML("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=178c92d16570cac67bd2bb5f0e36ca63&is_getty=true&tags="+searchTerm);
    	      NodeList nl = doc.getElementsByTagName("photo"); 
    	      if (nl.getLength() == 0) {
//    	        	return null; // no pictures found
    	        	doc = getRemoteXML("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=178c92d16570cac67bd2bb5f0e36ca63&is_getty=true&tags="+"twitter");
    	    	    nl = doc.getElementsByTagName("photo");
    	        	
    	       }
    	        	int pic = new Random().nextInt(nl.getLength()); //choose a random picture
    	        	Element e = (Element) nl.item(pic);
    	        	String farm = e.getAttribute("farm");
    	        	String server = e.getAttribute("server");
    	        	String id = e.getAttribute("id");
    	        	String secret = e.getAttribute("secret");
    	        	pictureURL = "http://farm"+farm+".static.flickr.com/"+server+"/"+id+"_"+secret+"_z.jpg";
    	         
    	      // At this point, we have the URL of the picture that resulted from the search.  Now load the image itself.
    	        try {
    	            	URL u = new URL(pictureURL);            	            	
    	            	return getRemoteImage(u);
    	            } catch (Exception e1) {
    	                e1.printStackTrace();
    	                return null; // so compiler does not complain
    	          }

        }
        
        /* 
         * Given a url that will request XML, return a Document with that XML, else null
         */
        private Document getRemoteXML(String url) {
        	 try {
	                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	                DocumentBuilder db = dbf.newDocumentBuilder();
	                InputSource is = new InputSource(url);
	                return db.parse(is);
	        } catch (Exception e) {
	        	System.out.print("Yikes, hit the error: "+e);
	        	return null;
	        }
        }
        
        /*
         * Given a URL referring to an image, return a bitmap of that image 
         */
        private Bitmap getRemoteImage(final URL url) {
            try {
                final URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                Bitmap bm = BitmapFactory.decodeStream(bis);
                bis.close();
                return bm;
            } catch (IOException e) {
                e.printStackTrace();
                return null;   
            }
        }
    }
	
	



}

package cmu.skasa;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
/**
 * This is a class which represents a HashTag object
 * @author santhosh
 *
 */
public class HashTag implements Serializable{

	public String tag;
	public String frequency;
	public String retweets;
	public String links;
	public String mentions;
	public String potential_views;
	public String density;
	public String color;

/**
 * This method is default constructor.
 * @param line - String input
 */
	HashTag(String line){

		StringTokenizer tokenizer = new StringTokenizer(line);

		this.tag = tokenizer.nextToken();
		this.frequency = tokenizer.nextToken();
		this.retweets = tokenizer.nextToken();
		this.links = tokenizer.nextToken();
		this.mentions = tokenizer.nextToken();
		this.potential_views = tokenizer.nextToken();
		this.density = tokenizer.nextToken();
		this.color = tokenizer.nextToken();	
	}
	public HashTag() {

	}
/**
 * This method convert HashTag to String array for list view display
 * @return String array
 */
	public  String[] toArray(){
		String[] ret = new String[9];
		
		ret[0]= "tag:" + "  " +this.tag;
		ret[1] = "\n";
		if(this.retweets==null)
			this.retweets=" ";
		ret[2]= "number of retweets per hour:" + "  " +this.retweets;
		if (this.links==null)
			this.links= " ";
		ret[3]= "percentage of tweets with links:" + "  " +this.links;
		if (this.mentions.equals("0"))
			this.mentions = "-";
		ret[4]= "percentage of tweets with mentions:" + "  " +this.mentions;
		if(this.potential_views==null)
			this.potential_views = " ";
		ret[5]= "times hashtag is appearing in home feed:" + "  " +this.potential_views;
		if (this.density==null)
			this.density= " ";
		ret[6]= "number of unique tweets with this hashtag per hour:" + "  " +this.density;
//		ret[7] = "color" + "  " + this.color;
		if (this.color.equals("0"))
			ret[7]= "triteness level:" + "  " +"Unused";
		else if  (this.color.equals("1")) 
			ret[7]= "triteness level:" + "  " +"Over Used";
		else if  (this.color.equals("2"))
			ret[7]= "triteness level:" + "  " +"Good to use";	
		else if  (this.color.equals("3"))
			ret[7]= "triteness level:" + "  " +"Great to use";
		ret[8] = "\n" + "\n" + "\n" + "\n" + "\n" ;
		return ret;
	}

/// The BELOW CODE is for Searching a HashTag statistics
	MainActivity ip = null;
	Context context;

	public void search(String searchTerm, MainActivity ip) {
		this.ip = ip;
		this.context = this.ip.getApplicationContext();
		new AsyncArtistSearch().execute(searchTerm);
	}

	private class AsyncArtistSearch extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... urls) {
			String searchResult = "";
			try {
				searchResult = search(urls[0]);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return searchResult;
		}

		//Set the progressbar
		protected void onPreExecute() {
			super.onPreExecute(); 
		}

		//Get the result and dismiss the progressbar
		protected void onPostExecute(String result) {
			try {
				ip.processJSON(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@SuppressWarnings("finally")
		private String search(String searchTerm) throws UnsupportedEncodingException {

			String result = "";

			try {
				String url = "http://hashtagkasa.appspot.com/HashTag?String=" + searchTerm;
				int readCount = 0;
				byte[] buff = new byte[1024];

				HttpClient client = new DefaultHttpClient();
				HttpGet request = new HttpGet(url);

				HttpResponse response = client.execute(request);

				// process the content. 
				HttpEntity entity = response.getEntity();
				InputStream ist = entity.getContent();
				ByteArrayOutputStream content = new ByteArrayOutputStream();

				while ((readCount = ist.read(buff)) != -1) {
					content.write(buff, 0, readCount);
				}
				result = new String (content.toByteArray());
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally{
				return result;
			}
		}
	}



}

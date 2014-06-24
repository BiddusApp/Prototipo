package com.example.prototipobiddus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.R.string;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Entity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private TextView lblResultado;
	private ListView lstClientes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lblResultado = (TextView)findViewById(R.id.lblResultado);
        lstClientes = (ListView)findViewById(R.id.lstClientes);
		
		Button obtener =(Button)(findViewById(R.id.obtener));
		
		obtener.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				TareaWSObtener tarea = new TareaWSObtener();
				tarea.execute("1");
			}
		});
		
		
	}

	private class TareaWSObtener extends AsyncTask<String,Integer,Boolean> {

		private String brand;

		protected Boolean doInBackground(String... params) {
	
			boolean resul = true;

			HttpClient httpClient = new DefaultHttpClient();

			String id = params[0];

			HttpGet del =
					new HttpGet("http://occulus-stg.herokuapp.com/campaigns/" + id);

			del.setHeader("content-type", "application/json");

			try
			{	
				HttpResponse resp = httpClient.execute(del);
				String respStr = EntityUtils.toString(resp.getEntity());

				JSONObject respJSON = new JSONObject(respStr);

				brand = respJSON.getString("description");
			}
			catch(Exception ex)
			{
				Log.e("ServicioRest","Error!", ex);
				resul = false;	
			}

			return resul;
		}

		protected void onPostExecute(Boolean result) {

			if (result)
			{
				lblResultado.setText(brand);
			}
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}

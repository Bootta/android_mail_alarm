package com.example.mailtest;

import java.io.IOException;
import java.util.*;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import com.sun.mail.pop3.POP3Store;

import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private int countMail = 0;

	public int getCountMail() {
		return countMail;
	}

	public void setCountMail(int countMail) {
		this.countMail = countMail;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Toast.makeText(getApplicationContext(), "Hi",
				// Toast.LENGTH_LONG).show();

				// new SendMail().execute();
				// new PullTasksThread().start();
				new PullTasksThread(MainActivity.this).start();

				// Toast.makeText(getApplicationContext(), "Hi",
				// Toast.LENGTH_SHORT).show();

			}
		});
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

	private class SendMail extends AsyncTask<String, Integer, Void> {

		protected void onProgressUpdate() {
			// called when the background task makes any progress
		}

		protected void onPreExecute() {
			// called before doInBackground() is started
		}

		protected void onPostExecute() {
			// called after doInBackground() has finished
		}

		@Override
		protected Void doInBackground(String... params) {
			Properties props = new Properties();
			String txt = "";
			// EditText etxtCount=(EditText)findViewById(R.id.editText1);
			// etxtCount.setEnabled(false);
			// props.setProperty("mail.store.protocol", "imaps"); //imap gmail
			props.setProperty("mail.store.protocol", "pop3");
			try {
				Session session = Session.getInstance(props, null);
				Store store = session.getStore();
				// store.connect("imap.googlemail.com",
				// "nebojsa.ackrila@gmail.com", "password"); //imap gmail
				store.connect("pop3.teol.net", "nebojsa.ristic@financ.ba",
						"hermes11");
				Folder inbox = store.getFolder("INBOX");
				// Folder inbox = store.getDefaultFolder();

				inbox.open(Folder.READ_ONLY);
				//
				txt = inbox.getMessageCount() + "";
				// setCountMail(inbox.getMessageCount());
				Message msg = inbox.getMessage(inbox.getMessageCount());
				Address[] in = msg.getFrom();
				Log.d("INfo", "Count: " + inbox.getMessageCount());
				for (Address address : in) {
					// System.out.println("FROM:" + address.toString());
				}
				Multipart mp = (Multipart) msg.getContent();
				BodyPart bp = mp.getBodyPart(0);

				// Log.d("INfo", "Content: "+bp.getContent());
				// runOnUiThread(new Runnable() {
				// public void run() {
				//
				// //etxtCount.setText("Count: "+txt);
				// Toast.makeText(getApplicationContext(), "Hello ",
				// Toast.LENGTH_SHORT).show();
				// }
				// });

				// Toast.makeText(getApplicationContext(), "CONTENT: " +
				// bp.getContent(), Toast.LENGTH_LONG).show();
			} catch (Exception mex) {
				mex.printStackTrace();
			}
			return null;
		}
	}

	public class PullTasksThread extends Thread {
		MainActivity ma = null;

		public PullTasksThread(MainActivity main) {
			ma = main;

			

			
		}

		public void run() {
			Properties props = new Properties();
			String txt = "";

			// etxtCount.setEnabled(false);
			// props.setProperty("mail.store.protocol", "imaps"); //imap gmail
			props.setProperty("mail.store.protocol", "pop3");
			try {
				Session session = Session.getInstance(props, null);
				Store store = session.getStore();
				// store.connect("imap.googlemail.com",
				// "nebojsa.ackrila@gmail.com", "password"); //imap gmail
				store.connect("pop3.teol.net", "nebojsa.ristic@financ.ba",
						"hermes11");
				
				Folder inbox = store.getFolder("INBOX");
				// Folder inbox = store.getDefaultFolder();

				inbox.open(Folder.READ_ONLY);
				//
				txt = inbox.getMessageCount() + "";
				Message msg = inbox.getMessage(inbox.getMessageCount());
				Address[] in = msg.getFrom();
				ma.setCountMail(inbox.getMessageCount());
				Log.d("INfo", "Count: " + inbox.getMessageCount());
				for (Address address : in) {
					// System.out.println("FROM:" + address.toString());
				}
				Log.d("Message: ", msg.getContent()+" \n"+msg.getDescription()+" \nDate: "+msg.getSentDate());
				//Multipart mp = (Multipart) msg.getContent();
				//BodyPart bp = mp.getBodyPart(0);

				// Log.d("INfo", "Content: "+bp.getContent());
				// runOnUiThread(new Runnable() {
				// public void run() {
				//
				// //etxtCount.setText("Count: "+txt);
				// Toast.makeText(getApplicationContext(), "Hello ",
				// Toast.LENGTH_SHORT).show();
				// }
				// });

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// This code will always run on the UI thread, therefore
						// is safe to modify UI elements.
						// myTextBox.setText("my text");
						EditText etxtCount = (EditText) findViewById(R.id.editText1);
						etxtCount.setText(ma.getCountMail() + "");
					}
				});

				inbox.close(false);
				store.close();

				// Toast.makeText(getApplicationContext(), "CONTENT: " +
				// bp.getContent(), Toast.LENGTH_LONG).show();
			} catch (Exception mex) {
				mex.printStackTrace();
			}
		}
	}

}

package edu.grinnell.appdev.grinnelldirectory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.grinnell.appdev.grinnelldirectory.dummy.Profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class RequestTask extends AsyncTask<String, Void, ArrayList<Profile>>{

	String responseString;
	ArrayList<Profile> profileList;
	String currentUri;
	
    protected ArrayList<Profile> doInBackground(String... uri) {
    	profileList = new ArrayList<Profile>();
    	currentUri = uri[0];
    	
    	iterativelyScrapePages();
    	return profileList;
    }
    
    private void iterativelyScrapePages(){
    	//make the request
    		//if that reutrns 0, then parseResponse
    			//if that returns not "", go again
    	do{
    		makeRequest();
    	} while(parseResponse());
    }
    
    private int makeRequest(String... uri){
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        try {
            response = httpclient.execute(new HttpGet(currentUri));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
                return 0;
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
       return -1;
    }
    
    private boolean parseResponse(){
    	StringTokenizer strTok = new StringTokenizer(responseString, "\n");
    	String curTok, firstName, lastName, username;
    	String picurl = "";
    	boolean anotherPage = false;
    	
    	for(int i=0; i<87; i++) strTok.nextToken();
    	if (strTok.nextToken().contains("<strong>no")){
    		//no entries found
    		return false;
    	}
    	else
    	{
	    	
	    	for(int i=0; i<9; i++) strTok.nextToken();
	    	curTok = strTok.nextToken();
	    	
	    	if (curTok.contains("Next Page")){
	    		anotherPage = true;
	    		currentUri = "https://itwebapps.grinnell.edu" + curTok.substring(53, curTok.length()-38);
	    		for(int i=0; i<22; i++) strTok.nextToken();
	    		curTok = strTok.nextToken();
	    	}
	    	else
	    	{
	    		anotherPage = false;
	    		for(int i=0; i<20; i++) strTok.nextToken();
	    		curTok = strTok.nextToken();
	    	}
	    	
	    	do{
	    		//if(curTok.contains("image1")){
	    		//	picurl = curTok.substring(curTok.length()-131, curTok.length()-72);
	    		//}
	    		//else picurl = " ";
		    	curTok = strTok.nextToken();
		    	String fullName = curTok.substring(curTok.substring(40).indexOf('>')+41, curTok.substring(40).indexOf('<')+40);
		    	firstName = fullName.substring(0, fullName.indexOf(','));
		    	lastName = fullName.substring(fullName.indexOf(',')+2);
		    	strTok.nextToken();
		    	strTok.nextToken();
		    	curTok = strTok.nextToken();
		    	username = curTok.substring(53, curTok.indexOf('@'));
		    	
		    	for(int i=0; i<5; i++) strTok.nextToken();
		    	curTok = strTok.nextToken();
		    	if (curTok.equals("<tr>")){
		    		//senator
		    		for(int i=0; i<13; i++) strTok.nextToken();
		        	curTok = strTok.nextToken();
		    	}
		    	profileList.add(new Profile(firstName, lastName, username));
		    	
	    	} while (curTok.contains("&nbsp"));
	    	
	    	return anotherPage;
	    	
	    	/*
	    	if(anotherPage){
	    		for(int i=0; i<6; i++) strTok.nextToken();
	        	curTok = strTok.nextToken();
	        	
	        	String beginningOfURL = curTok.substring(66);
	        	return "https://itwebapps.grinnell.edu" + beginningOfURL.substring(0, beginningOfURL.indexOf('"'));
	        	
	    	}
	    	*/
    	}
		
    }
    
}
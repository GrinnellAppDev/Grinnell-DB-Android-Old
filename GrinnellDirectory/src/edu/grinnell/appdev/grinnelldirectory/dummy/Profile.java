package edu.grinnell.appdev.grinnelldirectory.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

public class Profile {

    public static List<Profile> ITEMS = new ArrayList<Profile>();
    public static Map<String, Profile> ITEM_MAP = new HashMap<String, Profile>();
	
	public String firstname;
	public String lastname;
	public String username;
	public String dept;
	public String phone;
	public String address;
	public String sgapos;
	public String picurl;
	public Bitmap pic;
	
	public Profile(String f, String l, String u){
		firstname = f;
		lastname = l;
		username = u;
	}
	
	public static void setList(ArrayList<Profile> profileList){
		ITEMS.clear();
		ITEM_MAP.clear();
		for(Profile i: profileList){
			ITEMS.add(i);
			ITEM_MAP.put(i.username, i);
		}
	}
	
	public static void resetList(){
		ITEMS.clear();
		ITEM_MAP.clear();		
	}
	
	public static void addToList(ArrayList<Profile> profileList){
		for(Profile i: profileList){
			ITEMS.add(i);
			ITEM_MAP.put(i.username, i);
		}
	}
	
}

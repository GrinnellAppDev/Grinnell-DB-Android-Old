package edu.grinnell.appdev.grinnelldirectory.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.graphics.Bitmap;
import android.util.Log;

public class Profile {

    public static List<Profile> ITEMS = new ArrayList<Profile>();
    public static Map<String, Profile> ITEM_MAP = new HashMap<String, Profile>();
	

	public String picurl, firstName, lastName, username, dept, phonenum, campusaddress, boxno, stufacstatus, sgapos;
	public Bitmap pic;

	
	@SuppressWarnings("unchecked")
	public Profile(String picurl, String firstName, String lastName,
			String username, String dept, String phonenum,
			String campusaddress, String boxno, String stufacstatus,
			String sgapos) {
		this.picurl = picurl;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.dept = dept;
		this.phonenum = phonenum;
		this.campusaddress = campusaddress;
		this.boxno = boxno;
		this.stufacstatus = stufacstatus;
		this.sgapos = sgapos;
		this.pic = null;
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
	
	public void downloadImage(){
		try {
			this.pic = (Bitmap) new ImgDownload().execute(picurl).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return picurl + "\n" + firstName
				+ "\n" + lastName + "\n" + username
				+ "\n" + dept + "\n" + phonenum
				+ "\n" + campusaddress + "\n" + boxno
				+ "\n" + stufacstatus + "\n" + sgapos;
	}
	
}

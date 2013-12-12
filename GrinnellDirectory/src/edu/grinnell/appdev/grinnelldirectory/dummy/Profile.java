/**************************************
 * Profile
 * Profile object, which stores the information from a single DB entry.
 * This class also stores all downloaded data staticly.
 * 	This is totes unprofessional. Don't do as I do.
 * ************************************/

package edu.grinnell.appdev.grinnelldirectory.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

public class Profile {

//ITEMS stores the downloaded data. ITEM_MAP is a hash function for storing the data.
    public static List<Profile> ITEMS = new ArrayList<Profile>();
    public static Map<String, Profile> ITEM_MAP = new HashMap<String, Profile>();
	

	public String picurl;
	public String firstName = "";
	public String lastName = "";
	public String username = "";
	public String dept = "";
	public String phonenum = "";
	public String campusaddress = "";
	public String boxno = "";
	public String stufacstatus = "";
	public String sgapos = "";
	

	//Constructor
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
	}
	
	//Clear the downloaded data, and saves all information in profileList
	public static void setList(ArrayList<Profile> profileList){
		ITEMS.clear();
		ITEM_MAP.clear();
		for(Profile i: profileList){
			ITEMS.add(i);
			ITEM_MAP.put(i.username, i);
		}
	}
	
	//clears the downloaded data
	public static void resetList(){
		ITEMS.clear();
		ITEM_MAP.clear();		
	}
	
	//Adds the Profiles in profileList to the downloaded data
	public static void addToList(ArrayList<Profile> profileList){
		for(Profile i: profileList){
			ITEMS.add(i);
			ITEM_MAP.put(i.username, i);
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

/////////////////////////////////////////////////////////////////////////
//////// Author: KRISHNA NAGARAJA ///////////////////////////////////////
/////// Backend of the project   ////////////////////////////////////////
//////  A brief description of this class    ////////////////////////////
//// This class firstly stores all the data provided by the API and stores it systematically //
//// In various tables protected with constraints. JDBC is used here to communicate with an embedded //
//// Derby database.                                                                                 //
//// Secondly, This provides functions for creating proper folder hierarchy and provides functions //
//// To download all files belongng to all courses in the LMS  //
//// Thirdly, it serves as an Api for the GUI part by giving functions which sort and provide a //
//// list of assignments, forum posts etc. Also it stores whether the The assignment is done, or seen etc //
//// For the GUI to notify to the user /////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
package Backend;
import api.API;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Database.Database;
public class Backend {
	public static API api1=new API();
	public static API api2=new API();
	public static API api=new API();
	static Database d=new Database();
	static boolean user_logged_in=false;
	// public static void main(String args[]) throws SQLException{
		
	// 	table_setup();
	// 	///////////////// here login of gui will come /////////////////////
	// 	try {
	// 		user_logged_in=login("imt201Xxxx","5uPEr-5eREt-pa55wO$D");
 //                        System.out.println("Logged in");
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// 	///////////////////////////////////////////////////////////////////
	// 	if(user_logged_in){
	// 		Runnable r = new Runnable() {
	// 	         public void run() {
	// 	        	 while(true){
 //                                        System.out.println("DB - Update");
	// 	        		 update();	 
	// 	        	 }
		        	 
	// 	         }
	// 	     };
	// 	     new Thread(r).start();

			
	// 	}
	// 	else{
	// 		System.out.println("not logged in");
	// 	}
	// }
	//It creates the directory at the given path if it does not exist already
	static void create_directory(String path){
		File theDir = new File(path);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
		        theDir.mkdir();    
		}	
	}
	//It creates a folder hierarchy with each course being assigned a folder, and inside each course,
	//It creates the necessary folders so that files can be downloaded later
   static void create_hierarchy() {
	   System.out.println("Creating hierarchy");
	   try{
	String common="Lms_files/";
	create_directory("Lms_files");   
	List<String []> a = api.hiddenCourses();
	for(int i=0;i<a.size();i++){
		String c_name=a.get(i)[0];
		System.out.println(c_name);
		List<String []> folders=api.getFolders(a.get(i)[1]);
		c_name.replace(' ', '_');
		String t_path=common+c_name;
		create_directory(t_path);
		for(int j=0;j<folders.size();j++)
		{
			String f=t_path+"/"+folders.get(j)[0];	
			create_directory(f);
		}
	}
	   }
	   catch(Exception e){
		   System.out.println(e.getMessage());
	   }
   }
   //It adds a user into the database
   //User name, id and password to be given
	static void addUser(String name,String id,String password){
		String []val={id,name,"false",password,"0"};
		String []types={"string","string","bool","string","int"};
		try {
			d.insert("users",val,types,0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//logs out the user and makes the necessary changes in the database
	public static void logout(){
	if(user_logged_in){
		api.logout();
		user_logged_in=false;
		String changes[]={"currentuser=false"};
		try {
			d.update("users", changes, "userid='"+getCurrentUser()+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}}
	//Returns true if the user is already registered in this client
	public static boolean user_exists(String uid){
		return d.number_of_results("users","userid='"+uid+"'")>0;
	}
	//This method is called when a user logs in using this client for the first time
	//It downloads his files, assignments, forum posts etc
	//Multithreading is used here so that downloading and filling up the database tables occur simultaneously.
	public static void setup(String uid,String pass) {
		try{
		addUser(api.getName(),uid,pass);
		String[] ch={"currentuser=true"};
		d.update("users",ch,"userid='"+uid+"'");
		populate_courses();
		try {
				create_hierarchy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		Runnable r = new Runnable() {
	         public void run() {
	        	 int check=0;
	 			while(true){
	 				try{
	 					fill_tables();
	 					check=1;
	 				}
	 				catch(Exception e){
	 					
	 				}
	 				if(check==1){
	 					break;
	 				}
	 			}
	             
	         }
	     };
	     Runnable r1 = new Runnable() {
	         public void run() {
	        	 int check=0;
	        	 while(true)
	        	 {
		 			try {
		 				download_coursefiles();
		 				check=1;
		 			} catch (Exception e) {
		 				e.printStackTrace();
		 			}
	        	 if(check==1){
	        		 break;
	        	 }
	         }}
	     };
	     Thread t1=new Thread(r1,"download");
	     Thread t2=new Thread(r,"fill");
	     t1.start();
	     t2.start();
	     t1.join();
	     t2.join();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	//A function which will log in the user
	public static boolean login(String name,String password) {
		try{
		if(api.login(name, password))
		{
			api1.login(name, password);
			api2.login(name, password);

			System.out.println("logged in");
			if(d.number_of_results("users","userid='"+name+"'")>0){
				String[] ch={"currentuser=true"};
				d.update("users",ch,"userid='"+name+"'");
				
			}
			else{
				addUser(api.getName(),name,password);
				String[] ch={"currentuser=true"};
				d.update("users",ch,"userid='"+name+"'");
				populate_courses();
				try {
	 				create_hierarchy();
	 			} catch (Exception e) {
	 				e.printStackTrace();
	 			}
				Runnable r = new Runnable() {
			         public void run() {
			        	 int check=0;
			 			while(true){
			 				try{
			 					fill_tables();
			 					check=1;
			 				}
			 				catch(Exception e){
			 					
			 				}
			 				if(check==1){
			 					break;
			 				}
			 			}
			             
			         }
			     };
			     Runnable r1 = new Runnable() {
			         public void run() {
			        	 int check=0;
			        	 while(true)
			        	 {
				 			try {
				 				download_coursefiles();
				 				check=1;
				 			} catch (Exception e) {
				 				e.printStackTrace();
				 			}
			        	 if(check==1){
			        		 break;
			        	 }
			         }}
			     };
			     Thread t1=new Thread(r1,"download");
			     Thread t2=new Thread(r,"fill");
			     t1.start();
			     t2.start();
			     t1.join();
			     t2.join();
			}
		return true;
		}
		else{
			return false;
		}}
		catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}
	//Sets up the required tables by executing relevant create statements
	public static void table_setup(){
		ArrayList<String> l=new ArrayList<String>();
		ArrayList<String> un=new ArrayList<String>();
		try {
			l=d.show_tables();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//check if tables already exist
		if((un=getunavailabletables(l)).size()>0){
			for(int i=0;i<un.size();i++){
				if(un.get(i).equalsIgnoreCase("users")){
					String[][] input={{"userid","varchar(20)","primary key"," not null"},{"Name","varchar(70)","not null"},{"CurrentUser","boolean","not null"},{"password","varchar(90)","not null"},{"passkey","int"}};
					try {
						d.create_table(un.get(i),input);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else if(un.get(i).equalsIgnoreCase("courses")){
					String[][] input={{"courseid","varchar(20)","primary key"," not null"},{"Name","varchar(250)","not null"},{"userid","varchar(20)","not null"," references users(userid)"}};
					try {
						d.create_table(un.get(i),input);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				else if(un.get(i).equalsIgnoreCase("assignments")){
					String[][] input={{"assignmentid","varchar(20)","primary key"," not null"},{"courseid","varchar(20)"," references courses(courseid)"," not null"},{"Name","varchar(150)","not null"},{"userid","varchar(20)","not null","references users(userid)"},
							{"dueby","timestamp"},{"done","boolean"},{"seen","boolean"},{"submissionstatus","varchar(100)"},{"gradingstatus","varchar(100)"},{"timeremaining","varchar(50)"},{"description","long varchar"},{"files","varchar(100)"}};
					try {
						d.create_table(un.get(i),input);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else if(un.get(i).equalsIgnoreCase("forums")){
					String[][] input={{"postid","varchar(20)"," not null","primary key"},{"courseid","varchar(20)"," references courses(courseid)"," not null"},{"Name","varchar(250)","not null"},{"time","timestamp"},{"userid","varchar(20)","not null"," references users(userid)"},
							{"type","varchar(100)"},{"files","varchar(100)","not null"},{"seen","boolean"},{"content","long varchar"}};
					try {
						d.create_table(un.get(i),input);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else{}
			}
		}
		else{}
			
		
	}
	//Populating all tables with values downloaded from LMS
	public static void fill_tables(){
		System.out.println("filling tables");
		populate_assignments();
		populate_forums();		
		
	}
	// Checks if any new change has occurred in any part and updates the database
	public static void update(){
	System.out.println("UPDATING...............");
	populate_courses();
	populate_assignments();
	update_forum();
	}
//Sort and give functions
	
//gives sorted forum posts of the given course (latest first)
//course id to be given 
// returns a list of hashmap, which implies, each element in the list corresponds to one forum post
// The keys in the dictionary/hashmap are "postid", "courseid","Name","time","userid","type","files","content"
//Name gives the title of the post, time gives time at which it was posted, type gives general/news etc
// content gives the actual text in the post (which you might have to word wrap
// files gives the path to the folder where the attached files will be downloaded
public static List<HashMap<String,String>> sortedforumposts(String cid) {
	try{
	List<HashMap<String,String>> a;
	String filters[]={"userid='"+getCurrentUser()+"'","courseid='"+cid+"'"};
	a=d.query("forums",filters,"time","desc");
	return a;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
}
//The same function overloaded, which gives all the forum posts of all courses
public static List<HashMap<String,String>> sortedforumposts(){
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid="+getCurrentUser()};
		a=d.query("forums",filters,"time","desc");
		return a;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
}
//Gives only the forum posts which are not seen by the user yet of all courses
public static List<HashMap<String,String>> unseensortedforumposts() {
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid='"+getCurrentUser()+"'","seen=false"};
		a=d.query("forums",filters,"time","desc");
		return a;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
}
//Gives only the forum posts which are not seen by the user yet of given course
//course id to be given
public static List<HashMap<String,String>> unseensortedforumposts(String id) {
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid='"+getCurrentUser()+"'","courseid='"+id+"'","seen=false"};
		a=d.query("forums",filters,"time","desc");
		return a;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
}
// Gives a list of hashmaps where each hashmap corresponds to a course
// The keys of hashmap will be "courseid","name","userid"
public static List<HashMap<String,String>> getcourses() {
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid='"+getCurrentUser()+"'"};
		a=d.query("courses",filters);
		return a;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
}
//gives not done assignments of all courses
// This also gives list of hashmaps
// each element in the list corresponds to one assignment
// The keys of hashmap are, 
//"assignmentid","courseid","Name","userid","dueby","done","submissionstatus","gradingstatus","timeremaining","description","files"
//Name is title of the assignment, due by gives the timestamp of whwn the assignment is due
//done is a string which is either true or false, here in this function only not done assignments are fetched
//description is again the stuff like instruction they put in an assignment
public static List<HashMap<String,String>> todosortedassignments() {
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid="+getCurrentUser(),"done=true"};
		a=d.query("assignments",filters,"dueby","asc");
		return a;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
}
//Gives the unseen assignments sorted by due date of all courses
public static List<HashMap<String,String>> unseensortedassignments() {
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid='"+getCurrentUser()+"'","seen=false"};
		a=d.query("assignments",filters,"dueby","desc");
		return a;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
}
//Gives the unseen assignments sorted by due date of given course
//course id to be given
public static List<HashMap<String,String>> unseensortedassignments(String id) {
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid='"+getCurrentUser()+"'","courseid='"+id+"'","seen=false"};
		a=d.query("assignments",filters,"dueby","desc");
		return a;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
}
public static boolean usercredentials(String[] arr){
	try{
		if(d.number_of_results("users")>0){
			List<HashMap<String,String>> a;
			a=d.query("users");
			arr[0]=a.get(0).get("USERID");
			arr[1]=a.get(0).get("PASSWORD");
			return true;
		}
		else{
			return false;
		}
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return false;
	}
	
}
public static String getcoursebyid(String id){
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid='"+getCurrentUser()+"'","courseid='"+id+"'"};
		a=d.query("courses",filters);
		return a.get(0).get("NAME");
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
	
}
//gives all assignments , done and not done of all courses
public static List<HashMap<String,String>> sortedassignments() {
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid='"+getCurrentUser()+"'"};
		a=d.query("assignments",filters,"dueby","asc");
		return a;
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
}
//gives all assignments done and not done of the given course
public static List<HashMap<String,String>> sortedassignments(String cid) {
	try{
		List<HashMap<String,String>> a;
		String filters[]={"userid='"+getCurrentUser()+"'","courseid='"+cid+"'"};
		a=d.query("assignments",filters,"dueby","asc");
		return a;
		
	}
	catch(Exception e){
		System.out.println(e.getMessage());
		return null;
	}
	
}
//////////////////////////////////////////////////////
//Download functions
//Downloads all the course related files posted on LMS
public static void download_coursefiles() {
	try{
		System.out.println("DOWNLOADING..................");
		List<String []> courses = null;
		try {
		courses = api2.hiddenCourses();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (String [] course : courses){
			List<String []> folders=api2.getFolders(course[1]);
			for(String [] folder : folders){
				API.Parent a = api2.getFilesInFolder(folder[1]);
				recurse_create(a, "Lms_files/"+(course[0]+"/"+folder[0]));
			}
			List<String []> r=api2.getResources(course[1]);
			
			for(String[] st : r){
				File f=new File("Lms_files/"+course[0]);
				api2.downloadFileID(st[1],f.getAbsolutePath()+"/"+st[0]);
			}
		}

	}
	catch(Exception e){
		System.out.println(e.getMessage());
	}
			
}
//It will create files and folders recursively starting from path fp
static void recurse_create(API.Parent a, String fp){
	for(API.Child c : a.content){
		File test = new File(fp+"/"+c.name);
		if (!test.exists()) 
		api2.downloadURL(c.content.get(0),fp+"/"+c.name);
	}
	for(API.Parent p :a.children){
		create_directory(fp+"/"+p.name);
		recurse_create(p, fp+"/"+p.name);
	}
}
/////////////////////////////////////////////////////////////////
//removes the assignment from the new assignments list,as soon as he sees it and hence stops alerting the user about it
//takes id of assignment as an argument
public static void assignment_seen(String id) {
	try{
		String changes[]={"seen=true"};
		d.update("assignments", changes, "assignmentid='"+id+"'");
	}
	catch(Exception e){
		System.out.println(e.getMessage());
	}
	
}
//Marks the assignment as done
//takes id of assignment as an argument
public static void assignment_done(String aid) {
	try{
		String changes[]={"done=true"};
		d.update("assignments", changes, "assignmentid='"+aid+"'");	
	}
	
	catch(Exception e){
		System.out.println(e.getMessage());

	}
}
// Marks the post as seen by the user
// Takes post id as argument
public static void post_seen(String id) {
	try{
		String changes[]={"seen=true"};
		d.update("forums", changes, "forumid='"+id+"'");
	}
	catch(Exception e){
		System.out.println(e.getMessage());
	}
	
}
//Returns the id of the current user
// Ex output: imt2015512
public static String getCurrentUser(){
	String uid = null;
	String[] filters={"currentuser=true"};
	try {
		uid=d.query("users",filters).get(0).get("USERID");
	} catch (SQLException e1) {
		e1.printStackTrace();
	}
	return uid;
}
//PRIVATE FUNCTIONS
//It takes list of table names as rgument and returns the tbles that are missing in it
// There are four tables to be present in the database,
// Forums, Assignments, Users, Courses
	private static ArrayList<String> getunavailabletables(ArrayList<String> l){
		ArrayList<String> un=new ArrayList<String>();
		String[] required_tables={"users","courses","assignments","forums"};
		for(int i=0;i<required_tables.length;i++){
			int c=0;
			for(int j=0;j<l.size();j++){
				if(required_tables[i].equalsIgnoreCase(l.get(j))){
					c++;
					break;
				}
			}
			if(c==0){
				un.add(required_tables[i]);
			}
		}
		return un;
	}
	//Populates the table assignments by using the getAssignments method of the api
	private static void populate_assignments(){
		System.out.println("in assignment:");
		ArrayList<HashMap<String,String>> courses=null;
		try {
			courses=d.query("courses");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String uid=getCurrentUser();
		for(int i=0;i<courses.size();i++){
			String c_id=courses.get(i).get("COURSEID");
			List<String []> a=null;
			try {
				a = api1.getAssignments(c_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for(int j=0;j<a.size();j++){
				if(d.number_of_results("assignments","assignmentid='"+a.get(j)[1]+"'"+" and courseid='"+courses.get(i).get("COURSEID")+"'"+" and userid='"+uid+"'")>0){
					
				}
				else{
					System.out.println(a.get(j)[0]);
					List<List<String>> details=null;
					String time="";String sstatus="";String gstatus="";String tr="";String files="";
					String description="";
					String[] left=new String[6];
					String[] right=new String[6];
					try {
						System.out.println(c_id);
						details=api1.getAssignmentDetails(a.get(j)[1]);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					for(String r: details.get(0)){
						description += (r+"\n");
					}
					HashMap<String,String> h=new HashMap<String,String>();
					for(int r=0;r<details.get(1).size();r++){
						h.put(details.get(1).get(r).toLowerCase(),details.get(2).get(r));
					}
					if(h.containsKey("submission status")){
						sstatus=h.get("submission status");
					}
					if(sstatus.length()>100){
						sstatus="not submitted";
					}
					if(h.containsKey("grading status")){
						gstatus=h.get("grading status");
					}
					if(gstatus.length()>100){
						gstatus="not graded";
					}
					time=h.get("due date");
					//System.out.println(time);
					if(h.containsKey("time remaining")){
						tr=h.get("time remaining");
					}
					if(tr.length()>50){
						tr="forever";
					}
					String file="Lms_files/"+courses.get(i).get("NAME")+"/"+"Assignment/";
					try {
						System.out.println(time);
						time=giveDate(time);
						
					} catch (Exception e1) {
						time="";
						System.out.println("entered");
					}
					System.out.println("time= "+time+"t");
					if(time.length()>21){
						time="2025-12-31 23:59:59";
					}
					API.Parent p=null;
					try {

						 p=api1.getSubmittedFiles(a.get(j)[1]);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//download submitted files
					String path="Lms_files/"+courses.get(i).get("NAME")+"/Assignments";
					create_directory(path);
					path+="/";
					path+=a.get(j)[0];
					create_directory(path);
					create_directory(path+"/"+"submitted");
					recurse_create(p,path+"/"+"submitted");
					API.Parent pa=null;
					try {
						 pa=api1.getAssignmentAttachments(a.get(j)[1]);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//download attachments
					create_directory(path+"/"+"attachments");
					recurse_create(pa,path+"/"+"attachments");
					String name=a.get(j)[0];
					if(name.length()>150){
						name=name.substring(0, 150);
					}
					String values[]={a.get(j)[1],c_id,name,uid,time,"false","false",sstatus,gstatus,tr,description,file};
					try {
						d.insert("assignments",values);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}}		
	}
	//It efficiently checks for new forum posts and updates them in the database
	private static void update_forum(){
		ArrayList<HashMap<String,String>> courses=null;
		try {
			courses=d.query("courses");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String uid=getCurrentUser();
		for(int i=0;i<courses.size();i++){
				String c_id=courses.get(i).get("COURSEID");
				List<String []> a=null;
				try {
					a = api1.getForums(c_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(int j=0;j<a.size();j++){
							String forum_id=a.get(j)[1];
							String type=a.get(j)[0];
							List<String []> posts=null;
							try {
								posts =api.getForumPosts(forum_id);
							} catch (Exception e) {
								e.printStackTrace();
							}
							for(int k=0;k<posts.size();k++){
				
									String p_id=posts.get(k)[1];
									String p_time=posts.get(k)[4];
									boolean brk=false;
									
										p_time=giveDate(p_time);
									
									String p_name=posts.get(k)[0];
									List<List<String []>> details=null;
									try {
										details=api.getForumPost(p_id);
									} catch (Exception e) {
										e.printStackTrace();
									}
									String content="";
									int p=1;
									for(List<String[]> m:details){
											for(String [] c : m){
												for(String x: c){
													content+=x;
													content+="\n";
													//System.out.println(x);	
												}
												content+="\n";
												//System.out.println("*****************************************");
											}
											if(d.number_of_results("forums","postid='"+p_id+"_"+Integer.toString(p)+"'"+" and courseid='"+courses.get(i).get("COURSEID")+"'"+" and userid='"+uid+"'")>0){
												System.out.println(p_id+"_"+Integer.toString(p));
												System.out.println(p_name);
												System.out.println("-------------------------------------");
												p++;
												brk=true;
												break;
											}
											else{
												if(p_name.length()>250){
													p_name=p_name.substring(0,250);
												}
												if(p_time.length()>21){
													p_time="2025-12-31 23:59:59";
												}
												if(type.length()>100){
													type=type.substring(0, 100);
												}
												String values[]={p_id+"_"+Integer.toString(p),courses.get(i).get("COURSEID"),p_name,p_time,uid,type,"","false",content};
												try {
													d.insert("forums",values);
													System.out.println(p_name);
												} catch (SQLException e) {
													e.printStackTrace();
												}
											}
											p++;
											}
									if(brk){
										System.out.println("breaking from course="+courses.get(i).get("NAME")+"forum= "+a.get(j)[0]);
										break;
									}
				
			}
			}
			}
	
	
	}
	//Populates the FORUMS table
	//Goes through each forum of each assignment and stores the information in the database
	private static void populate_forums(){
		System.out.println("in forum:");
		ArrayList<HashMap<String,String>> courses=null;
		try {
			courses=d.query("courses");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String uid=getCurrentUser();
		for(int i=0;i<courses.size();i++){
			String c_id=courses.get(i).get("COURSEID");
			List<String []> a=null;
			try {
				a = api1.getForums(c_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for(int j=0;j<a.size();j++){
			String forum_id=a.get(j)[1];
			String type=a.get(j)[0];
			List<String []> posts=null;
			try {
				posts =api1.getForumPosts(forum_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for(int k=0;k<posts.size();k++){
				String p_id=posts.get(k)[1];
				String p_time=posts.get(k)[4];
				p_time=giveDate(p_time);
				String p_name=posts.get(k)[0];
				List<List<String []>> details=null;
				try {
					details=api1.getForumPost(p_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String content="";
				int p=1;
				for(List<String[]> m:details){
					for(String [] c : m){
						for(String x: c){
							content+=x;
							content+="\n";
						}
						content+="\n";
					}
					if(d.number_of_results("forums","postid='"+p_id+"_"+Integer.toString(p)+"'"+" and courseid='"+courses.get(i).get("COURSEID")+"'"+" and userid='"+uid+"'")>0){
						System.out.println(p_id+"_"+Integer.toString(p));
						System.out.println(p_name);
						System.out.println("-------------------------------------");
					}
					else{
						if(p_name.length()>250){
							p_name=p_name.substring(0,250);
						}
						if(p_time.length()>21){
							p_time="2025-12-31 23:59:59";
						}
						if(type.length()>100){
							type=type.substring(0, 100);
						}
						String values[]={p_id+"_"+Integer.toString(p),courses.get(i).get("COURSEID"),p_name,p_time,uid,type,"","false",content};
						try {
							d.insert("forums",values);
							System.out.println(p_name);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					p++;
				}
				
				
			}
			}
			}
	
	}
	// Populates the courses table with the courses loaded from the LMS
	private static void populate_courses(){
		List<String []> a = null;
		try {
		a = api1.hiddenCourses();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String uid=getCurrentUser();
		String[] col={"name","courseid","userid"};
		for(String[] s: a){
			if(d.number_of_results("courses","courseid='"+s[1]+"'"+" and userid='"+uid+"'")>0){
			System.out.println(s[0]);	
			}
			else{
				String values[]={s[1],s[0],uid};
				try {
					d.insert("courses",values);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			d.insert("courses", a,col,"'"+uid+"'" );
		} catch (SQLException e) {
		}
		
	}
//A function which takes the date like Wednesday, 2016 november 12 11:55 AM or Wednesday, 2016 Nov 12 11:55 AM into,
// 1016-11-12 11:55:00
private static String giveDate(String d) {
	if(d==null){
		return "2050-12-31 11:11:11";
	}
	String[] array=d.split(",");
	for( String x: array){
		x=x.trim();
	}	
	String date=array[1];
	date=date.trim();
	String da[]=date.split(" ");
	String month=da[1];
	if(month.substring(0,3).equalsIgnoreCase("jan")){
		month="01";
	}
	else if(month.substring(0,3).equalsIgnoreCase("feb")){
		month="02";
	}
	else if(month.substring(0,3).equalsIgnoreCase("mar")){
		month="03";
	}
	else if(month.substring(0,3).equalsIgnoreCase("apr")){
		month="04";
	}
	else if(month.substring(0,3).equalsIgnoreCase("may")){
		month="05";
	}
	else if(month.substring(0,3).equalsIgnoreCase("jun")){
		month="06";
	}
	else if(month.substring(0,3).equalsIgnoreCase("jul")){
		month="07";
	}
	else if(month.substring(0,3).equalsIgnoreCase("aug")){
		month="08";
	}
	else if(month.substring(0,3).equalsIgnoreCase("sep")){
		month="09";
	}
	else if(month.substring(0,3).equalsIgnoreCase("oct")){
		month="10";
	}
	else if(month.substring(0,3).equalsIgnoreCase("nov")){
		month="11";
	}
	else if(month.substring(0,3).equalsIgnoreCase("dec")){
		month="12";
	}
	date=da[2]+"-"+month+"-"+da[0];
	try{
	SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
	Date dates = parseFormat.parse(array[2]);
	String t=displayFormat.format(dates);
	date+=(" "+t);
	return date;}
	catch(Exception e){
		System.out.println(e.getMessage());
		return "2050-12-31 11:11:11";
	}
	
}
}
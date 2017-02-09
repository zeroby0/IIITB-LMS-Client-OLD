/////////////////////////////////////////////////////////////
// VINAY CHANDRASEKHAR K - IMT2015523
// JAVA Project - LMS Client
// LMS API - Web scraper
/////////////////////////////////////////////////////////////

package api;

import com.jaunt.*;
import com.jaunt.util.*;
import com.jaunt.component.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class API {
	private String LMSUrl = "https://lms.iiitb.ac.in/moodle/login/index.php";
	private String LMSHome = "https://lms.iiitb.ac.in/moodle/my/";
	private String LMSHiddenHome = "https://lms.iiitb.ac.in/moodle/my/index.php?mynumber=-2";
	private String LMSLogOut = "https://lms.iiitb.ac.in/moodle/login/logout.php";
	private String LMSCourse = "https://lms.iiitb.ac.in/moodle/course/view.php?id=";
	private String LMSResource = "https://lms.iiitb.ac.in/moodle/mod/resource/view.php?id=";
	private String LMSFile = "https://lms.iiitb.ac.in/moodle/mod/resource/view.php?id=";
	private String LMSFolder = "https://lms.iiitb.ac.in/moodle/mod/folder/view.php?id=";
	private String LMSForum = "https://lms.iiitb.ac.in/moodle/mod/forum/view.php?id=";
	private String LMSForumPost = "https://lms.iiitb.ac.in/moodle/mod/forum/discuss.php?d=";
	private String LMSAssignment = "https://lms.iiitb.ac.in/moodle/mod/assign/view.php?id=";
	private String userName = "";
	private String password = "";
	private boolean validAccount = false;
	UserAgent userAgent;
	
	public API(){
		userAgent = new UserAgent();
		userAgent.settings.checkSSLCerts = false;		
		HandlerForBinary handlerForBinary = new HandlerForBinary();
		userAgent.setHandler("application/msword", handlerForBinary);
                userAgent.setHandler("application/x-forcedownload", handlerForBinary);
                userAgent.setHandler("application/pdf", handlerForBinary);
                userAgent.setHandler("text/plain", handlerForBinary);
                userAgent.setHandler("", handlerForBinary);
	}
	
	// Login
	public boolean login(String uName, String pass){
		try{
			userName = uName;
			password = pass;
			userAgent.visit(LMSUrl);
			userAgent.doc.apply(uName, pass);
			userAgent.doc.submit("Log in");
			if(userAgent.getLocation().equals(LMSHome)){
				validAccount = true;
				return true;
			}else{
				validAccount = false;
				return false;
			}
		}catch(JauntException e){
			validAccount = false;
			return false;
		}
	}
	
	// See if internet is available
	public boolean internetAvailable(){
		try{
			userAgent.visit("https://google.com");
			return true;
		}catch(JauntException e){
			return false;
		}
	}
	
	// See if LMS is available
	public boolean lmsAvailable(){
		try{
			userAgent.visit(LMSUrl);
			return true;
		}catch(JauntException e){
			return false;
		}
	}
	
	// Get visible course list
	// returns [["Course title 1", "Course id1"], ["Course title 2", "Course id2"]]
	public List<String []> mainCourses() throws Exception{
		try{
			check();
			userAgent.visit(LMSHome);
			List<String []> listOfCourses = new ArrayList<String []> ();
			Elements courses = userAgent.doc.findEach("<div class=\"course_title\">");
			for(Element course : courses){
				listOfCourses.add(new String[] {course.findFirst("<a>").innerHTML(), course.findAttributeValues("<a href>").get(0).split("=")[1]});
			}  
			return listOfCourses;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Get hidden + visible course list
	// returns [["Course title 1", "Course id1"], ["Course title 2", "Course id2"]]
	public List<String []> hiddenCourses() throws Exception{
		try{
			check();
			userAgent.visit(LMSHiddenHome);
			List<String []> listOfCourses = new ArrayList<String []> ();
			Elements courses = userAgent.doc.findEach("<div class=\"course_title\">");
			for(Element course : courses){
				listOfCourses.add(new String[] {course.findFirst("<a>").getText(), course.findAttributeValues("<a href>").get(0).split("=")[1]});
			}  
			return listOfCourses;
		}catch(Exception e){
			throw e;
		}
	}
	
	// gets Name of the user logged in
	public String getName() throws Exception{
		try{
			check();
			userAgent.visit(LMSHome);
			Element details = userAgent.doc.findFirst("<div class=\"logininfo\">");
			return details.findFirst("<a>").innerHTML();
		}catch(Exception e){
			throw e;
		}
	}
	
	// Logout
	public void logout(){
		try{
			userAgent.visit(LMSLogOut);
			userAgent.doc.submit("Continue");
		}catch(JauntException e){}
	}
		
	// Get all forums in a given course
	// must give course id as argument
	// returns [["Forum title 1", "Forum id1"], ["Forum title 2", "Forum id2"]]
	public List<String []> getForums(String id) throws Exception{
		try{
			check();
			userAgent.visit(LMSCourse + id);
			List<String []> listOfForums = new ArrayList<String []> ();
			Elements forums = userAgent.doc.findEach("<li class=\"activity forum modtype_forum \"");
			for(Element forum : forums){
				listOfForums.add(new String[] {forum.findFirst("<span>").getText(), forum.findAttributeValues("<a href>").get(0).split("=")[1]});
			}  
			return listOfForums;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Get all folders in a given course
	// must give course id as argument
	// returns [["Folder title 1", "Folder id1"], ["Folder title 2", "Folder id2"]]
	public List<String []> getFolders(String id) throws Exception{
		try{
			check();
			userAgent.visit(LMSCourse + id);
			List<String []> listOfForums = new ArrayList<String []> ();
			Elements folders = userAgent.doc.findEach("<li class=\"activity folder modtype_folder \"");
			for(Element folder : folders){
				listOfForums.add(new String[] {folder.findFirst("<span>").getText(), folder.findAttributeValues("<a href>").get(0).split("=")[1]});
			}
			return listOfForums;
		}catch(Exception e){
			throw e;
		}
	}

	// Get all assignments in a given course
	// must give course id as argument
	// returns [["assignments title 1", "assignments id1"], ["assignments title 2", "assignments id2"]]
	public List<String []> getAssignments(String id) throws Exception{
		try{
			check();
			userAgent.visit(LMSCourse + id);
			List<String []> listOfAssignments = new ArrayList<String []> ();
			Elements assignments = userAgent.doc.findEach("<li class=\"activity assign modtype_assign \"");
			for(Element assignment : assignments){
				listOfAssignments.add(new String[] {assignment.findFirst("<span>").getText(), assignment.findAttributeValues("<a href>").get(0).split("=")[1]});
			}
			return listOfAssignments;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Get all resources in a given course
	// must give course id as argument
	// returns [["resources title 1", "resources id1", "resources URL", "resources type"]]
	public List<String []> getResources(String id) throws Exception{
		try{
			check();
			userAgent.visit(LMSCourse + id);
			List<String []> listOfResources = new ArrayList<String []> ();
			Elements resources = userAgent.doc.findEach("<li class=\"activity resource modtype_resource \"");
			for(Element resource : resources){
				String resourceType = resource.findAttributeValues("<img src>").get(0).split("%")[1].substring(2);
				resourceType = resourceType.substring(0, resourceType.length()-3);
				String resourceID = resource.findAttributeValues("<a href>").get(0).split("=")[1];
				String resourceURL = LMSResource + resourceID;
				listOfResources.add(new String[] {resource.findFirst("<span>").getText(), resourceID, resourceURL, resourceType});
			}
			return listOfResources;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Downloads a file into the given location
	// url is the url of source file, to is the location of the download file. the file name included
	// to = "/user/meow/topsecret.pdf
	public boolean downloadURL(String url, String to) {
		try{
			check();
			File file = new File(to);
			userAgent.download(url, file);
			return true;
		}catch(Exception e){
			//System.out.println(e);
			return false;
		}
	}
	
	// Downloads a file id into the given location
	//id of file, to is the location of the download file. the file name included
	// to = "/user/meow/topsecret.pdf
	public boolean downloadFileID(String id, String to) {
		try{
			check();
			File file = new File(to);
			userAgent.download(LMSFile + id, file);
			userAgent.open(file);
			String url = userAgent.doc.findAttributeValues("<a href>").get(0);
			userAgent.download(url, file);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public Parent getFilesInFolder(String id) throws Exception{
		try{
			check();
			userAgent.visit(LMSFolder + id);
			return findFiles(userAgent.doc.findFirst("<div id=\"folder_tree0\""));
		}catch(Exception e){
			throw e;
		}
	}
	
	// Gets all posts in a forum
	// id of forum to be given
	// returns [["post title", "id", "started by", "last post by", "last post time"]]
	public List<String []> getForumPosts(String id) throws Exception{
		try{
			check();
			List<String []> listOfPosts = new ArrayList<String []> ();
			int found = 1;
			int page = 0;
			while(found > 0){
				userAgent.visit(LMSForum + id + "&page=" + page++);
				Elements posts = userAgent.doc.findEach("<tr");
				for(int i = 1; i<posts.size(); i++){
					Element post = posts.getElement(i);
					listOfPosts.add(new String[] {post.findFirst("<a").getText(), post.findAttributeValues("<a href>").get(0).split("=")[1], post.getFirst("<td class=\"author\">").getFirst("<a").getText(), post.getFirst("<td class=\"lastpost\">").getEach("<a").getElement(0).getText(), post.getFirst("<td class=\"lastpost\">").getEach("<a").getElement(1).getText()});
				}
				found = posts.size() - 1;
			}
			return listOfPosts;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Gets a forum post
	// id of post to be given
	// returns [[["subject", "author", "time"], ["post line 1", "post line 2"], ["attachments"]]]
	public List<List<String []>> getForumPost(String id) throws Exception{
		try{
			check();
			List<List<String []>> listOfPosts = new ArrayList<List<String []>> ();
			userAgent.visit(LMSForumPost + id);
			Elements posts = userAgent.doc.findEach("<div class=\"forumpost clearfix\">");
			Element mainPost = userAgent.doc.findFirst("<div class=\"forumpost clearfix firstpost starter\">");
			
			List<String []> res = new ArrayList<String []> ();
			String time = mainPost.findFirst("<div class=\"author\"").getText();
			Elements content = mainPost.findFirst("<div class=\"posting fullpost\">").getEach("<p");
			String[] lines =  new String[content.size()];
			int i = 0;
			for(Element line: content){
				lines[i++] = line.getText();
			}
			Elements attchments = userAgent.doc.findEach("<div class=\"attachments\">");
			String[] attachments =  new String[attchments.size()];
			i = 0;
			for(Element attch: attchments){
				attachments[i++] = attch.findAttributeValues("<a href>").get(0);
			}
			res.add(new String[] {mainPost.findFirst("<div class=\"subject\"").getText(),mainPost.findFirst("<div class=\"author\"").getFirst("<a").getText(), time.substring(6, time.length())});
			res.add(lines);
			res.add(attachments);
			listOfPosts.add(res);
			
			for(Element post : posts){
				res = new ArrayList<String []> ();
				time = post.findFirst("<div class=\"author\"").getText();
				content = post.findFirst("<div class=\"posting fullpost\">").getEach("<p");
				lines =  new String[content.size()];
				i = 0;
				for(Element line: content){
					lines[i++] = line.getText();
				}
				attchments = userAgent.doc.findEach("<div class=\"attachments\">");
				attachments =  new String[attchments.size()];
				i = 0;
				for(Element attch: attchments){
					attachments[i++] = attch.findAttributeValues("<a href>").get(0);
				}
				res.add(new String[] {post.findFirst("<div class=\"subject\"").getText(),post.findFirst("<div class=\"author\"").getFirst("<a").getText(), time.substring(6, time.length())});
				res.add(lines);
				res.add(attachments);
				listOfPosts.add(res);
			}
			return listOfPosts;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Get assignment details
	// id must be given
	// returns [["description lines"], ["table left"], ["table right"]]
	public List<List<String>> getAssignmentDetails(String id) throws Exception{
		try{
			check();
			List<List<String>> listOfDetails = new ArrayList<List<String>> ();
			userAgent.visit(LMSAssignment + id);
			
			List<String> description = new ArrayList<String> ();
			Elements content = userAgent.doc.findFirst("<div id=\"intro\">").getFirst("<div").getEach("<p");
			Elements content2 = userAgent.doc.findFirst("<div id=\"intro\">").getFirst("<div").findEach("<span");
			for(Element line: content){
				System.out.println(line.getText());
				description.add(line.getText());
			}
			
			for(Element line: content2){
				System.out.println(line.getText());
				description.add(line.getText());
			}
			
			List<String> tableLeft = new ArrayList<String> ();
			List<String> tableRight = new ArrayList<String> ();
			Elements tables = userAgent.doc.findEvery("<table class=\"generaltable\">");
			for(Element table : tables){
				Elements rows = table.findEvery("<tr");
				for(Element row : rows){
					String r1 = row.findEvery("<td").getElement(0).getText();
					if(!(r1.equals("Submission comments"))){
						tableLeft.add(r1);
						tableRight.add(row.findEvery("<td").getElement(1).getText());
					}
				}
			}
			
			listOfDetails.add(description);
			listOfDetails.add(tableLeft);
			listOfDetails.add(tableRight);
			return listOfDetails;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Gets submissions to the assignments
	// id must be given
	public Parent getSubmittedFiles(String id) throws Exception{
		try{
			check();
			userAgent.visit(LMSAssignment + id);
			Parent parent = new Parent();
			if(userAgent.doc.findFirst("<table class=\"generaltable\">").findEvery("<ul").size() > 0){
				Elements files = userAgent.doc.findFirst("<table class=\"generaltable\">").findFirst("<ul").findEvery("<a");
				for(Element file : files){
					Child f = new Child();
					f.name = file.getText();
					f.content.add(file.getAt("href"));
					parent.content.add(f);
				}
			}
			return parent;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Gets attachments to the assignments
	// id must be given
	public Parent getAssignmentAttachments(String id) throws Exception{
		try{
			check();
			userAgent.visit(LMSAssignment + id);
			Parent parent = new Parent();
			if(userAgent.doc.findFirst("<div id=\"intro\" class=\"box generalbox boxaligncenter\">").getEach("<div").size() > 1){
				Elements files = userAgent.doc.findFirst("<div id=\"intro\" class=\"box generalbox boxaligncenter\">").getEach("<div").getElement(1).findEvery("<a");
				for(Element file : files){
					Child f = new Child();
					f.name = file.getText();
					f.content.add(file.getAt("href"));
					parent.content.add(f);
				}
			}
			return parent;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Uploads a submission to assignment
	// must give assignment id and filepath
	public boolean uploadAssignment(String id, String file){
		try{
			check();
			userAgent.settings.autoRedirect = false;
			userAgent.visit(LMSAssignment + id + "&action=editsubmission");
			String rememberForm = userAgent.doc.findFirst("<form").outerHTML();
			String nextURL = userAgent.doc.findFirst("<noscript").getFirst("<div").findAttributeValues("<object data>").get(0).replaceAll("&amp;", "&");
			userAgent.visit(nextURL);
			nextURL = userAgent.doc.findFirst("<div class=\"filemanager-toolbar\">").findAttributeValues("<a href>").get(0).replaceAll("&amp;", "&");
			userAgent.visit(nextURL);
			nextURL = userAgent.doc.findFirst("<ul").getEach("<li").getElement(1).findAttributeValues("<a href>").get(0).replaceAll("&amp;", "&");
			userAgent.visit(nextURL);
			nextURL = userAgent.doc.findAttributeValues("<form action").get(0).replaceAll("&amp;", "&");
			userAgent.doc.findFirst("<form ").setAttribute("action", nextURL);
			userAgent.doc.apply(new File(file));
			userAgent.doc.submit();
			nextURL = userAgent.doc.findFirst("<div class=\"continuebutton\"").findAttributeValues("<a href>").get(0).replaceAll("&amp;", "&");
			userAgent.visit(nextURL);
			userAgent.openContent("<html><body>"+rememberForm+"</body></html>");
			userAgent.doc.submit();
			userAgent.settings.autoRedirect = true;
			return true;
		}catch(Exception e){
			userAgent.settings.autoRedirect = true;
			return false;
		}
	}
	
	
	/* PRIVATE FUNCTIONS */
	
	// Recursive folder traversal
	private Parent findFiles(Element root) throws Exception{
		try{
			Parent parent = new Parent();
			parent.name = root.findFirst("<span class=\"fp-filename\"").getText();
			Elements children = root.getFirst("<ul").getEach("<li");
			for(Element child : children){
				if(child.getEach("<ul").size() > 0){
					parent.children.add(findFiles(child));
				}else if(child.findAttributeValues("<a href>").size() > 0){
					Child file = new Child();
					file.content.add(child.findAttributeValues("<a href>").get(0));
					file.name = child.findFirst("<span class=\"fp-filename\"").getText();
					parent.content.add(file);
					//System.out.println("F " + file.name);
				}
			}
			return parent;
		}catch(Exception e){
			throw e;
		}
	}
	
	// Checks login if not logged in raises exception
	private void check() throws Exception{
		if(!validAccount){
			throw new Exception("NOT_LOGGED_IN");
		}else{
                    /* Remove this later!!!! */
			//timeoutCheck();
		}
	}
	
	// ReLogs in if timeout has occurred
	private void timeoutCheck(){
		if(!validAccount) return;
		try{
			userAgent.visit(LMSHome);
			if(userAgent.getLocation().equals(LMSHome)){
				return;
			}else{
				login(userName, password);
				userAgent.visit(LMSHome);
				if(userAgent.getLocation().equals(LMSHome)){
					return;
				}else{
					validAccount = false;
				}
			}
		}catch(JauntException e){
                    System.out.println(e);
                }
	}
	
	/* public classes */
	
	// data structure to show parent child relationship between different objects
	public class Parent{
		public String name;
		public List<Parent> children;
		public List<Child> content;
		
		public Parent(){
			children = new ArrayList<Parent>();
			content = new ArrayList<Child>();
		}
	}
	
	public class Child{
		public String name;
		public List<String> content;
		
		public Child(){
			content = new ArrayList<String>();
		}
	}
	
}
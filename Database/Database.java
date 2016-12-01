//// This class basically serves as an API to interact with the Derby database
package Database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Properties;
import java.util.HashMap;
import java.util.List;

//import org.apache.derby.iapi.sql.ResultSet;

public class Database {
	//
	Connection connection;
	public final String Driver="org.apache.derby.jdbc.EmbeddedDriver";
	public final String JDBC_url="jdbc:derby:lms;create=true";
    
    public Database(){
    	try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	try {
			connection = DriverManager.getConnection(JDBC_url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	    }
    //Creates a table in the database with the given table name, and the set of columns with their types (and constraints if any)
    //Eg: Input can be, 
    //String[][] col={{"Name","varchar(20)"},{"age","varchar(3)"}};
	//d.create_table("first", col); (here d is a database object
    // With this input this function creates a table named first, with two columns Name, and age
	public void create_table(String table_name,String[][] columns) throws SQLException{
		//int n1=columns[0].length;
		int n2=columns.length;
		String statement="create table "+table_name+"(";
		for(int i=0;i<n2;i++){
			for(int j=0;j<columns[i].length;j++){
				statement+=(columns[i][j]+" ");
			}
			if(i!= n2-1){
				statement+=",";
			}
		}
		statement+=")";
		System.out.println(statement);
		connection.createStatement().execute(statement);

		
	}
	//Add columns to the table
	//Takes table name and an array of array of strings as paramenter
	// The columns to be added will be represented in the array of array of strings like this:
	// [[column1 name,column1 type],[column2 name,column2 type]......]
	public void addcolumns(String table_name,String[][] columns) throws SQLException{
		String statement="alter table "+table_name+" add ";
		for(int i=0;i<columns.length;i++){
			for(int j=0;j<columns[i].length;j++){
				statement+=(columns[i][j]+" ");
			}
			if (i!=columns.length-1){
				statement+= ", ";
			}
		}
		connection.createStatement().execute(statement);
		
	}
	//Returns the number of results by querying the table of given tablename
	// and satisfying the given condition
	public int number_of_results(String table_name,String condition) {
		String statement="select * from "+table_name+" where "+condition;
		Statement s = null;
		try {
			s = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = s.executeQuery(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int c=0;
		try {
			while(rs.next())
			{
				c++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	//Overloaded to provide the same functionality as above but without any condition
	public int number_of_results(String table_name) {
		String statement="select * from "+table_name;
		Statement s = null;
		try {
			s = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = s.executeQuery(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int c=0;
		try {
			while(rs.next())
			{
				c++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	//Used to update the table
	// It takes table name, An array which contains the changes to be made, and condition as argument
	public void update(String table_name, String[] changes,String condition) throws SQLException{
		String statement="update "+table_name+" set ";
		for(int i=0;i<changes.length;i++){
			statement+= changes[i];
			if(i!=changes.length-1){
				statement+=", ";
			}
		}
		statement+=" where ";
		statement+=condition;
		connection.createStatement().execute(statement);
	}
	//Several versions of the insert function which will provide various ways to insert data into the database
	public void insert(String table_name,String[] values,String[] types,int overload) throws SQLException{
		String statement="insert into "+table_name+" values(";
		
		for(int i=0;i<values.length;i++){
			if(types[i].equalsIgnoreCase("string")){
				statement+="'";
				statement+=values[i];
				statement+="'";
			}
			else{
				statement+=values[i];
			}
			if(i!=values.length-1){
				statement+=",";
			}
		}
		statement+=")";
		connection.createStatement().execute(statement);
	}
	public void insert(String table_name,String[] values) throws SQLException{
		//String statement="insert into "+table_name+" values(";
		String statement="insert into "+table_name+" values(";
		
		for(int i=0;i<values.length;i++){
			statement+="'";
			if(values[i]==null){
				values[i]="";
			}
			statement+=(values[i].replace("'","''"));
			statement+="'";
			if(i!=values.length-1){
				statement+=",";
			}
		}
		statement+=")";
		connection.createStatement().execute(statement);
	}
	//It inserts a new row into the table specified
	// Example:  if String val1[]={"Krishna","19"};
	// insert("t1",val1); would cause the row with name as Krishna and age as 19 to be added into the table t1
	public void insert(String table_name,String[] values,String[] columns,String d){
		//String statement="insert into "+table_name+" values(";
		String statement="insert into "+table_name+" ( ";
		for(int i=0;i<columns.length;i++){
			statement+=columns[i];
			if(i!=columns.length-1)
			{
				statement+=" , ";
			}
			else
			{
				statement+=") values(";
			}
		}
		for(int i=0;i<values.length;i++){
			statement+="'";
			statement+=values[i];
			statement+="'";
			if(i!=values.length-1){
				statement+=",";
			}
		}
		if(values.length<columns.length){
			statement+=(","+d);
		}
		statement+=")";
		try {
			connection.createStatement().execute(statement);
		} catch (SQLException e) {
		}
	}
	//An overloaded function to insert multiple rows at once
	//Example: String val1[][]={{"Aravind","32"},{"Vinay","47"}};
	// insert("t1",val1); would insert two rows into the table t1 with names and ages as specified in the inner arrays.
	public void insert(String table_name,List<String[]> values,String[] columns,String d) throws SQLException{
		for(int i=0;i<values.size();i++){
			insert(table_name,values.get(i),columns,d);
		}
	}
	
	public ArrayList<HashMap<String,String>> query(String table_name,String [] filters) throws SQLException{
		String statement="Select * from ";
		statement+= table_name;
		for(int i=0;i<filters.length;i++){
			if(i==0){
				statement+=" where ";
			}
			statement+=filters[i];
			if(i!=filters.length-1){
				statement+=" and ";
			}
		}
		
		ArrayList<HashMap<String,String>> Result=new ArrayList<HashMap<String,String>>();
		ArrayList<String> col=new ArrayList<String>();
		Statement s= connection.createStatement();
		ResultSet rs=s.executeQuery(statement);
		ResultSetMetaData rm=rs.getMetaData();
		int column_count=rm.getColumnCount();
		for(int i=1;i<=column_count;i++){
			col.add(rm.getColumnName(i));
			System.out.format("%20s", rm.getColumnName(i));
		}
		System.out.println();
		while(rs.next()){
			HashMap<String,String> R= new HashMap<String,String>();
			for(int i=1;i<=column_count;i++){
				R.put(col.get(i-1), rs.getString(i));
				System.out.format("%20s", rs.getString(i));
			}
			System.out.println();
			Result.add(R);
		}
		return Result;
	}
	//It querys the given table name with the filters given in a string array and displays it in the order specified on the given column
	public ArrayList<HashMap<String,String>> query(String table_name,String [] filters,String column,String order) throws SQLException{
		String statement="Select * from ";
		statement+= table_name;
		for(int i=0;i<filters.length;i++){
			if(i==0){
				statement+=" where ";
			}
			statement+=filters[i];
			if(i!=filters.length-1){
				statement+=" and ";
			}
		}
		statement+=" ";
		statement+=(" order by "+column+" "+order);
		ArrayList<HashMap<String,String>> Result=new ArrayList<HashMap<String,String>>();
		ArrayList<String> col=new ArrayList<String>();
		Statement s= connection.createStatement();
		ResultSet rs=s.executeQuery(statement);
		ResultSetMetaData rm=rs.getMetaData();
		int column_count=rm.getColumnCount();
		for(int i=1;i<=column_count;i++){
			col.add(rm.getColumnName(i));
			System.out.format("%20s", rm.getColumnName(i));
		}
		System.out.println();
		while(rs.next()){
			HashMap<String,String> R= new HashMap<String,String>();
			for(int i=1;i<=column_count;i++){
				R.put(col.get(i-1), rs.getString(i));
				System.out.format("%20s", rs.getString(i));
			}
			System.out.println();
			Result.add(R);
		}
		return Result;
	}
	public ArrayList<HashMap<String,String>> query(String table_name) throws SQLException{
		String statement="Select * from ";
		//String statement="Select postid,name from ";
		statement+= table_name;
		Statement s= connection.createStatement();
		ResultSet rs=s.executeQuery(statement);
		ResultSetMetaData rm=rs.getMetaData();
		int column_count=rm.getColumnCount();
		ArrayList<HashMap<String,String>> Result=new ArrayList<HashMap<String,String>>();
		ArrayList<String> col=new ArrayList<String>();
		for(int i=1;i<=column_count;i++){
			col.add(rm.getColumnName(i));
			System.out.format("%20s", rm.getColumnName(i));
		}
		System.out.println();
		while(rs.next()){
			HashMap<String,String> R= new HashMap<String,String>();
			for(int i=1;i<=column_count;i++){
				System.out.format("%20s", rs.getString(i));
				R.put(col.get(i-1), rs.getString(i));
			}
			Result.add(R);
			System.out.println();
		}
		return Result;
	}
	public void execute(String query) throws SQLException{
		connection.createStatement().execute(query);
	}
	public ArrayList<String> show_tables() throws SQLException{
		DatabaseMetaData md = connection.getMetaData();
		//ResultSet rs = md.getTables(null, null, "%", null);
		String[] types = {"TABLE"};
        ResultSet rs = md.getTables(null, null, "%", types);
		ArrayList<String> tables=new ArrayList<String>();
        while (rs.next()) {
        	tables.add(rs.getString("TABLE_NAME"));
            System.out.println(rs.getString("TABLE_NAME"));
        }
        return tables;
	}
}
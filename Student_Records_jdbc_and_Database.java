	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.Reader;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.Scanner;

	public class Student_Records_jdbc_and_Database {

		 static Connection con =null; // static because don't want to create object
		 static Scanner sc = new Scanner (System.in);
		 static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		public static void main(String[] args) {//main method
			
			try {
				//jdbc database connection
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","System","180799");
			
			boolean enter = true;
			System.out.println("Enter Number which Operation you Want to Perform : \n1: Create Table \n2: insert Data By Registration Number In Table \n3: update Data By Registration Number From Table \n4: fetch All Data From Table \n"
					+ "5: fetch Data By Registration Number From Table \n6: delete Data By Registration Number from Table \n7: delete All students record from Table ( Once TRuncated Can't be rollback Record's) \n8: Delete table Structure ( Once Dropped Can't be rollback Table please think twice before performing ) ");
			int choice =sc.nextInt();
			
		switch(choice) {
			
		case 1: 
			{
				createTable();
			}
			break;
		case 2: 
		   {
			insertDataInTable();
		   }
		   break;
		case 3: 
		   {
			updateDataFromTable();
		   }
		   break;
		case 4: 
		   {
			fetchAllDataFromTable();
		   }
		  break;
		case 5: 
		  {
		  	fetchDataByRegNumberFromTable();
		  }
		 break;
		case 6: 
		  {
			  deleteDataByRegNumberfromTable();
		  }
		  break;
		case 7: 
		{
			deleteAllDatafromTable();
		}
		  break;
		case 8: 
		{
			deleteTableStructure();
		}
		  break;
		 
		default:
			{
				System.out.println("Wrong Input Entered... please try again ");
			}
				
	 }
			
		}//try end
			catch (Exception e) {
				e.printStackTrace();
			}
	}// main method closed

		
		
		


		// create methods for different operations
		
	    public static void createTable()  {   // create Table
			
	   		try {
				
				String q="create table Student_Applications_Records(Registration_id number(5),Student_name varchar2(30) not null,University_Name varchar2(25)not null,College_Name varchar2(25)not null,course varchar2(10), Department_Name varchar2(25),Phone_Number number(10)not null,Email_Address VARCHAR2(40) not null)";
				
				Statement smt = con.createStatement();
				boolean ans = smt.execute(q);
				
				System.out.println("Table Created Succesfully...!");
				
				
			} catch (Exception e) {
				System.out.println("Already table will be Created..! Can't Create Again....");
			}finally {
				try {
					con.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		

	   public static void insertDataInTable() throws SQLException, IOException {// insert records // we can throw multiple exception at one time
					
			PreparedStatement psmt = con.prepareStatement("insert into Student_Applications_records values(?,?,?,?,?,?,?,?)");
			
			while(true) {
			System.out.println("Enter your Correct Data As ASked in Below : ");
			System.out.println("ENter Your Registration Id Here...");
			int id = sc.nextInt();
			
			System.out.println("Enter your Name...");
			String name = br.readLine();
			
			System.out.println("Enter your University Name...");
			String university = br.readLine();
			
			System.out.println("Enter your College Name...");
			String collegename = br.readLine();
			
			
			System.out.println("Enter your Course...");
			String course = br.readLine();
			
			System.out.println("Enter your Department Name...");
			String dept = br.readLine();
			
			System.out.println("Enter your Phone Number...");
			long PhoneNumber = sc.nextLong();
			
			System.out.println("Enter your Email Address...");
			String email = br.readLine();
			
			psmt.setInt(1,id);
			psmt.setString(2, name);		
			psmt.setString(3, university);
			psmt.setString(4, collegename);
			psmt.setString(5, course);
			psmt.setString(6, dept);
			psmt.setLong(7,PhoneNumber);
			psmt.setString(8,email);
			
			
			int count = psmt.executeUpdate();
			if(count>0) {
				System.out.println(count+" record Inserted..!");
			}else {
				System.out.println("No record Inserted...!");
			}
			
			
			
			System.out.println("Do you Want to Enter More Record [yes/no]..!");
			String yn = sc.next();
			
			if(yn.equalsIgnoreCase("no")) {
				
				System.out.println("you are Out Now..!");
				break;
			}
			
			}
			con.close();
		}
		 
		
	   public static void updateDataFromTable() throws SQLException {// Update records
	    	
	    	System.out.println("Enter Id of student whoose record you want to Update : ");
	    	int idforupdate=sc.nextInt();
	    	String fetchquery ="select * from Student_Applications_Records where Registration_id="+idforupdate;
	    	PreparedStatement psmt=con.prepareStatement(fetchquery);
	    	
	    	ResultSet rs = psmt.executeQuery();
	    	
	    	if(rs.next()) {
	    		int Reg_id=rs.getInt(1);
	    		String Sname=rs.getString(2);
	    		String Course=rs.getString(3);
	    		String University_Name=rs.getString(4);
	    		String College_Name=rs.getString(5);
	    		String Dept_Name=rs.getString(6);
	    		long Ph_Number=rs.getLong(7);
	    		String Email=rs.getString(8);
	    		
	    		System.out.println("Your REgistration Id : "+Reg_id);
	    		System.out.println("Your Student Name : "+Sname);
	    		System.out.println("Your Course Name : "+Course);
	    		System.out.println("Your University Name : "+University_Name);
	    		System.out.println("Your College Name : "+College_Name);
	    		System.out.println("Your Department Name : "+Dept_Name);
	    		System.out.println("Your Phone Number : "+Ph_Number);
	    		System.out.println("Your Email address : "+ Email);
	    		
	    		System.out.println("You can Update Only Your Phone Number or Email Address for Other Visit Your Principle Office : ");
	    		System.out.println("Enter What do you Want to Update :\n 1: Phone Number \n 2: Email Address\n");
	    		int choice = sc.nextInt();
	    		String sqlupdateQuery ="update Student_Applications_Records set  ";
	    		switch(choice) {
	    		case 1: {
	    			
	    			sqlupdateQuery =sqlupdateQuery+"Phone_Number=?"+"where Registration_id="+idforupdate;
	    			PreparedStatement psmt_PhNo =con.prepareStatement(sqlupdateQuery);
	        		
	    			System.out.println("enter new Phone Number to update : ");
	    			long newnumber=sc.nextLong();
	    			psmt_PhNo.setLong(1, newnumber);
	    			int rows=psmt_PhNo.executeUpdate();
	    			if(rows > 0 ) {
	    				System.out.println("Your Phone Number Will be Updated...!");
	    				System.out.println("Your New Phone Number will be :"+newnumber);
	    			}
	    		}
	    		break;
	    		case 2: {
	    			sqlupdateQuery =sqlupdateQuery+"Email_Address=?"+"where Registration_id="+idforupdate;
	    			PreparedStatement psmt_Email =con.prepareStatement(sqlupdateQuery);
	    			
	    			System.out.println("enter new email Address to update : ");
	    			String newEmail=sc.next();
	    			psmt_Email.setString(1, newEmail);
	    			
	    			int rows=psmt_Email.executeUpdate();
	    			if(rows > 0 ) {
	    				System.out.println("Your Email Address Will be Updated...!");
	    				System.out.println("Your New Email Address will be :"+newEmail);
	    	    		
	    			}
	    		}
	    		break;
	    		default :{
	    			System.out.println("Wrong Option choosed Please try Again ...!");
	    		}
	    		}
	    		
	    	}else {
	    		System.out.println("Record Not Found ....Entered "+idforupdate+" id is Not Available...!");
	    	}
	    	
			con.close();
		}// update method end
	    
	    
	   public static void fetchAllDataFromTable() throws SQLException {// fetch records

	    	String fetchquery ="select * from Student_Applications_Records";
	    	PreparedStatement psmt=con.prepareStatement(fetchquery);
	    	
	    	ResultSet rs = psmt.executeQuery();
	    	
	        	
	    	System.out.println("Your Table records Are Follows : \n");
	    	while(rs.next()) {
	    		int Reg_id=rs.getInt(1);
	    		String Sname=rs.getString(2);
	    		String Course=rs.getString(3);
	    		String University_Name=rs.getString(4);
	    		String College_Name=rs.getString(5);
	    		String Dept_Name=rs.getString(6);
	    		long Ph_Number=rs.getLong(7);
	    		String Email=rs.getString(8);
	    		
	    		
	    		System.out.println("Your REgistration Id : "+Reg_id);
	    		System.out.println("Your Student Name : "+Sname);
	    		System.out.println("Your Course Name : "+Course);
	    		System.out.println("Your University Name : "+University_Name);
	    		System.out.println("Your College Name : "+College_Name);
	    		System.out.println("Your Department Name : "+Dept_Name);
	    		System.out.println("Your Phone Number : "+Ph_Number);
	    		System.out.println("Your Email address : "+ Email);
	    		System.out.println("----------------------------------------------------");
	    	}
			con.close();
		}
	   
	   
	   public static void fetchDataByRegNumberFromTable() throws SQLException {//fetch by id
		   System.out.println("fetch data by registrstion number");
		   
		System.out.println("Enter Id of student whoose record you want to Fetch : ");
	   	int idforupdate=sc.nextInt();
	   	String fetchquery ="select * from Student_Applications_Records where Registration_id="+idforupdate;
	   	PreparedStatement psmt=con.prepareStatement(fetchquery);
	   	
	   	ResultSet rs = psmt.executeQuery();
	   	
	   	if(rs.next()) {
	   		int Reg_id=rs.getInt(1);
	   		String Sname=rs.getString(2);
	   		String Course=rs.getString(3);
	   		String University_Name=rs.getString(4);
	   		String College_Name=rs.getString(5);
	   		String Dept_Name=rs.getString(6);
	   		long Ph_Number=rs.getLong(7);
	   		String Email=rs.getString(8);
	   		
	   		System.out.println("Your REgistration Id : "+Reg_id);
	   		System.out.println("Your Student Name : "+Sname);
	   		System.out.println("Your Course Name : "+Course);
	   		System.out.println("Your University Name : "+University_Name);
	   		System.out.println("Your College Name : "+College_Name);
	   		System.out.println("Your Department Name : "+Dept_Name);
	   		System.out.println("Your Phone Number : "+Ph_Number);
	   		System.out.println("Your Email address : "+ Email);
	   		
	   	}else {
	   		System.out.println("Record Not Found ....Entered "+idforupdate+" id is Not Available...!");
	   	}
		  con.close(); 
	   }
	   
	   
	   public static void deleteDataByRegNumberfromTable() throws SQLException {// delete by reg id
			
			System.out.println("delete by reg no");
			
			System.out.println("Enter Id of student whoose record you want to Delete : ");
		   	int idfordelete=sc.nextInt();
		   	String deletequery ="delete from Student_Applications_Records where Registration_id="+idfordelete;
		   	PreparedStatement psmt=con.prepareStatement(deletequery);
		   	
		     int rows=psmt.executeUpdate();
		     if(rows>0) {
		    	 System.out.println("your Record deleted Successfully..!");
		     }
		   	con.close();
		}
	   
	   
	   public static void deleteAllDatafromTable() throws SQLException {// Truncate table
		   
		   
		   String truncatequery="truncate table Student_Applications_Records";
		   Statement smt = con.createStatement();
		   int rows = smt.executeUpdate(truncatequery);
		  
		   System.out.println("your All Record's truncated Successfully..!");
		   con.close();      
	   }
	   
	   
	   public static void deleteTableStructure() throws SQLException {// Drop Table
		   
		 
		   String dropquery="drop table Student_Applications_Records";
		   Statement smt = con.createStatement();
		   int rows = smt.executeUpdate(dropquery);
		   
		   System.out.println("your table Structure dropped Successfully..!");
		  con.close();
	   }
	   
	}//class end


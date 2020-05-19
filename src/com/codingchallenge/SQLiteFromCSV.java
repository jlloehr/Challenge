package com.codingchallenge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;//just import what i need

//This program parses through a csv file, submitting all valid records into a SQLite database, and all invalid records to a csv file.
//It also finally provides statistics to a log file.
//Written by Jacob Loehr

public class SQLiteFromCSV {
	private static String JDBC_CONNECTION_URL = 
			"jdbc:sqlite:/Users/jacobloehr/sqlite/challenge.db";
	
	public static void main(String[] args) {
		
		//reading and parsing csv file
		String csvFile = "/Users/jacobloehr/eclipse-workspace/com.codingchallenge/Challenge.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] row = null;
		int validColumnNumber = 10;
		int totalRows = 0;
		int validRows = 0;
		int invalidRows = 0;
		Connection connection = null;
		String badRecords = ""; //this string will store all the invalid records
		String statistics = "";
		
		//creating output file for bad data
		try {
		      File myObj = new File("/Users/jacobloehr/eclipse-workspace/com.codingchallenge/challenge-bad.csv");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } 
		      else {
		        System.out.println("Bad records file already exists");
		      }
		    } 
		catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		//creating output file for statistics 
		try {
		      File myObj = new File("/Users/jacobloehr/eclipse-workspace/com.codingchallenge/challenge.log");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } 
		      else {
		        System.out.println("Statistics file already exists");
		      }
		    } 
		catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		//setup SQLite connection
		try {
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL);
			//if rerunning program, drop and create new table
			connection.createStatement().execute("drop table customer");
			connection.createStatement().execute("create table customer(A,B,C,D,E,F,G,H,I,J)");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	totalRows++;
                // use comma as separator            	
                row = line.split(cvsSplitBy);
                
                //since there will be a comma in row E, it will be split into 11 entries instead of 10  
                if(row.length > 0 && row.length == validColumnNumber +1 ) {                	
                	validRows++;
                	
                	try {                       		
                		//insert the records into the SQLite database
                		PreparedStatement statement = connection.prepareStatement("insert into customer(A,B,C,D,E,F,G,H,I,J) values (?,?,?,?,?,?,?,?,?,?)");
                		                		
                		statement.setString(1, row[0]);
                		statement.setString(2, row[1]);
                		statement.setString(3, row[2]);
                		statement.setString(4, row[3]);
                		//For the E column, joining indexes 4 and 5 back together because the E column contains a comma was split into 2  
                		statement.setString(5, (row[4]+","+row[5]));
                		statement.setString(6, row[6]);
                		statement.setString(7, row[7]);
                		statement.setString(8, row[8]);
                		statement.setString(9, row[9]);
                		statement.setString(10, row[10]);
                		statement.executeUpdate();
                		
                	}
                	catch (SQLException e) {
            			e.printStackTrace();
            		}
                	
                }
                else {
                	invalidRows++;
                	//append to the string containing the invalid records
                	badRecords += (line + "\n");              	
                }
            }
            
            //write the invalid records to the previously created csv file
            try {
      	      FileWriter myWriter = new FileWriter("/Users/jacobloehr/eclipse-workspace/com.codingchallenge/challenge-bad.csv");      	      
      	      myWriter.write(badRecords);
      	      myWriter.close();
      	      System.out.println("Successfully wrote to the bad records file.");
      	    } catch (IOException e) {
      	      System.out.println("An error occurred.");
      	      e.printStackTrace();
      	    }
            
            //write the statistics to the log file
            try {
    	      FileWriter myWriter = new FileWriter("/Users/jacobloehr/eclipse-workspace/com.codingchallenge/challenge.log");
    	      statistics = ("Number of records received: " + (totalRows -1) + "\nNumber of successful records: "+(validRows-1)+"\nNumber of failed records: "+(invalidRows));
    	      myWriter.write(statistics);
    	      myWriter.close();
    	      System.out.println("Successfully wrote to the statistics file.");
    	    } catch (IOException e) {
    	      System.out.println("An error occurred.");
    	      e.printStackTrace();
    	    }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }		
	}
}

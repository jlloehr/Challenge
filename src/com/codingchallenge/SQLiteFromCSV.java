package com.codingchallenge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.sql.*; //just import what i need

public class SQLiteFromCSV {
	private static String JDBC_CONNECTION_URL = 
			"jdbc:sqlite:/Users/jacobloehr/sqlite/challenge.db";

	
	public static void main(String[] args) {
		
		System.out.println("Hello Eclipse!");
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
		String A,B,C,D,E,F,G,H,I,J = "";
		Connection connection = null;
		
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
            while ((line = br.readLine()) != null && totalRows < 6) {
            	totalRows++;
                // use comma as separator
            	
                row = line.split(cvsSplitBy);
                //since there will be a comma in row E, it will be split into 11 entries instead of 10  
                if(row.length > 0 && row.length == validColumnNumber +1 ) {
                	//System.out.println(row[0]);
                	
                	validRows++;
                	try {
                		A = row[0];                		
                		B = row[1];                		
                		C = row[2];                		
                		D = row[3];      
                		//joining indexes 4 and 5 because they were previously separated by a comma  
                		E = row[4] + "," + row[5];               		
                		F = row[6];
                		G = row[7];
                		H = row[8];
                		I = row[9];
                		J = row[10];
                		
                		PreparedStatement statement = connection.prepareStatement("insert into customer(A,B,C,D,E,F,G,H,I,J) values (?,?,?,?,?,?,?,?,?,?)");
                		                		
                		statement.setString(1, A);
                		statement.setString(2, B);
                		statement.setString(3, C);
                		statement.setString(4, D);
                		statement.setString(5, E);
                		statement.setString(6, F);
                		statement.setString(7, G);
                		statement.setString(8, H);
                		statement.setString(9, I);
                		statement.setString(10, J);
                		statement.executeUpdate();
                		
                	}
                	catch (SQLException e) {
            			e.printStackTrace();
            		}
                	
                }
                else {
                	invalidRows++;
                	//write to csv file
                	System.out.println(line);
                }

            }
            System.out.println(validRows);
            System.out.println(invalidRows);
            System.out.println(totalRows);
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

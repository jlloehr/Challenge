package com.codingchallenge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*; //just import what i need

public class SQLiteFromCSV {
	private static String JDBC_CONNECTION_URL = 
			"jdbc:sqlite:/Users/jacobloehr/sqlite/challenge.db";
		//jdbc:sqlite:C:/sqlite/db/chinook.db

	
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
		String a,b,c,d,e_,f,g,h,i,j = "";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL);
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
                System.out.println(row.length);
                //since there will be a comma in row E, it will be split into 11 entries instead of 10  
                if(row.length > 0 && row.length == validColumnNumber +1 ) {
                	//System.out.println(row[0]);
                	
                	validRows++;
                	try {
                		a = row[0];
                		System.out.println(a);
                		b = row[1];
                		System.out.println(b);
                		c = row[2];
                		System.out.println(c);
                		d = row[3];
                		System.out.println(d);
                		e_ = row[4] + "," + row[5];
                		System.out.println(e_);
                		f = row[6];
                		System.out.println(f);
                		g = row[7];
                		h = row[8];
                		i = row[9];
                		j = row[10];
                		//PreparedStatement statement = connection.prepareStatement("insert into customer(A,B,C,D,E,F,G,H,I,J) values (row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9])");
                		//PreparedStatement statement = connection.prepareStatement("insert into customer(A,B,C,D,E,F,G,H,I,J) values ('q','q','q','q','q','q','q','q','q','q')");
                		PreparedStatement statement = connection.prepareStatement("insert into customer(A,B,C,D,E,F,G,H,I,J) values (a,b,c,d,e_,f,g,h,i,j)");
                		statement.executeUpdate();
                	}
                	catch (SQLException e) {
            			e.printStackTrace();
            		}
                	
                }
                else {
                	invalidRows++;
                	//write to csv file
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

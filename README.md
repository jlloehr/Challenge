# Coding Challenge
The purpose of this application is to consume a CSV file, parse the data, and insert valid
records into a SQLite database. 

Furthermore, all invalid records that do not contain entries for each column (from A-J) will be written to a csv file. 

Finally, a .log file will be created that contains statistics including the total number of records received, as well as
the number of successful and failed records.



In order to run the application, you need to download an SQLite-JDBC jar file from the following link:
https://bitbucket.org/xerial/sqlite-jdbc/downloads/

Then, right click on your project, click 'Build Path,' click 'Configure Build Path,' click the 'Libraries' tab.

Finally, click 'Add external JARs' and choose the JAR file that you just downloaded, and then click apply.



Overall, my approach is an iterative solution that works for the given csv file.

I ended up storing the bad records into a string, so that I only had to write to the output csv file one time. 

A couple assumptions were made. First, I assumed that there are 10 columns in the csv file. 

Also, I assumed that for each successful record, the E column has a comma in it, so it will always be split when
I am splitting the csv file into an array. To fix this, I joined the split entries back together.

Overall, my solution works well for the given problem, but would require a few changes to be more suitable for a general case where the amount of columns are not given prior.



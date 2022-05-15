
package model;

import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Ebill
{ private PreparedStatement preparedStmt;// common method to connect to the DB
private Connection connect()
{
Connection con = null;
try
{
Class.forName("com.mysql.jdbc.Driver");
//Provide the correct details: DBServer/DBName, username, password
con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebill", "root", "");
}
catch (Exception e)
{e.printStackTrace();}
return con;
}//method to insert ebill
public String insertEbill(String id, String name, String units,String amount, String meterreading, String accno) {
Connection conn = connect();
String Output = "";
try {
if (conn == null) {
return "Error occuring to the database for inserting";
}
//SQL query
String query = " insert into ebill_structure values (?, ?, ?, ?, ?,?)";
PreparedStatement preparedStatement = conn.prepareStatement(query);
//binding data to SQL query
preparedStmt.setInt(1, 0);
preparedStmt.setString(2, name);
preparedStmt.setString(3, units);
preparedStmt.setString(4, amount);
preparedStmt.setString(5, meterreading);
preparedStmt.setString(6, accno);
//execute the SQL statement
preparedStatement.execute();
conn.close(); String newEbills = readEbills();
Output = "{\"status\":\"success\", \"data\": \"" + newEbills + "\"}";
} catch(Exception e) {
Output = "{\"status\":\"error\", \"data\": \"Failed to insert the bill\"}";
System.err.println(e.getMessage());
}
return Output;
}//method to read bill details
public String readEbills()
{
String output = "";
try
{
Connection con = connect();
if (con == null)
{
return "Error while connecting to the database for reading.";
}
// Prepare the html table to be displayed
output = "<table border='1'><tr><th>Bill ID</th>"
+ "<th>Bill ACC no</th><th>Bill Amount</th>"
+ "<th>Start Date</th>"
+ "<th>End Date</th>"+ "<th>Reference No</th>"
+ "<th>Update</th><th>Remove</th></tr>";

String query = "select * from ebill_structure";
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
// iterate through the rows in the result set
while (rs.next())
{
int id = rs.getInt("id");
String name = rs.getString("name");
String units = rs.getString("units");
String amount = rs.getString("amount");
String meterreading = rs.getString("meterreading");
String accno = rs.getString("accno");
// Add into the html table
output += "<tr style=\"border: 1px solid #ddd; padding: 8px;\"><td style=\"padding-top: 6px; padding-bottom: 6px; text-align: center; color: Blue;\">" + id + "</td>";
output += "<td style=\"padding-top: 6px; padding-bottom: 6px; text-align: center; color: #3B3B3B;\">" + name + "</td>";
output += "<td style=\"padding-top: 6px; padding-bottom: 6px; text-align: center; color: #3B3B3B;\">" + units + "</td>";
output += "<td style=\"padding-top: 6px; padding-bottom: 6px; text-align: center; color: #3B3B3B;\">" + amount + "</td>";
output += "<td style=\"padding-top: 6px; padding-bottom: 6px; text-align: center; color: #3B3B3B;\">" + meterreading + "</td>";
output += "<td style=\"padding-top: 6px; padding-bottom: 6px; text-align: center; color: #3B3B3B;\">" + accno + "</td>";
// buttons
output += "<td><input name='btnUpdate' type='button' value='Update' "
+ "class='btnUpdate btn btn-secondary' data-id='" + id + "'></td>"
+ "<td><input name='btnRemove' type='button' value='Remove' "
+ "class='btnRemove btn btn-danger' data-id='" + id + "'></td></tr>";
}
con.close();
// Complete the html table
output += "</table>";
}
catch (Exception e)
{
output = "Error while reading the ebills.";
System.err.println(e.getMessage());
}
return output;
}//method to update ebill details
public String updateEbill(String id, String name, String units, String amount, String meterreading, String accno ) {
Connection conn = connect();
String Output = "";
try {
if (conn == null) {
return "Database connection error ";
}
//SQL query
String query = "UPDATE ebill_structure SET id=?, name=?, amount=?, units=?, meterreading=?, accno=? WHERE id=?";
//binding data to SQL query
PreparedStatement preparedStatement = conn.prepareStatement(query);
preparedStmt.setString(1, id);
preparedStmt.setString(2, name);
preparedStmt.setString(3, amount);
preparedStmt.setString(4, units);
preparedStmt.setString(5, meterreading);
preparedStmt.setString(6, accno);
//execute the SQL statement
preparedStatement.executeUpdate();
conn.close();
String newEbills = readEbills;
Output = "{\"status\":\"success\", \"data\": \"" + newEbills + "\"}";
} catch(Exception e) {
Output = "{\"status\":\"error\", \"data\":\"Failed to update ebill.\"}";
System.err.println(e.getMessage());
}
return Output;
}//method to delete data
public String deleteEbill(String id) {
String Output = "";
Connection conn = connect();
try {
if (conn == null) {
return "Database connection error";
}
//SQL query
String query = "DELETE FROM ebill_structure WHERE id = ?";
//binding data to the SQL query
PreparedStatement preparedStatement = conn.prepareStatement(query);
preparedStatement.setInt(1, Integer.parseInt(id));
//executing the SQL statement
preparedStatement.execute();
conn.close();
String newEbills = readEbills();
Output = "{\"status\":\"success\", \"data\": \"" + newEbills + "\"}";
} catch(Exception e) {
Output = "{\"status\":\"error\", \"data\":\"Failed to delete ebill.\"}";
System.err.println(e.getMessage());
}
return Output;
}
}
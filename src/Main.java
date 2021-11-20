import java.sql.*;
public class Main{
    public static void main(String[] args) {
        System.out.println("\n\n***** MySQL JDBC Testing *****");
        Connection conn = null;
        try
        {
            String url = "jdbc:MySQL://localhost:3306/StudentDatabase?characterEncoding=utf8";
            String userName = "root";
            String password = "nekochan1";
            // File > Project Structure > Dependencies > + > Jar File
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url,userName,password);

            System.out.println ("\nDatabase Connection Established...");

            Statement stmt=conn.createStatement();

            // This code will be used to find student name and such
            ResultSet rs=stmt.executeQuery("select * from Student WHERE studentID = 1 ");

            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            int numberOfColumn = rsmd.getColumnCount();
            System.out.println(numberOfColumn);


            while(rs.next())
                System.out.println(rs.getString(1)+","+rs.getString(2));

            conn.close();
        }
        catch (Exception ex)
        {
            System.err.println ("Cannot connect to database server");
            ex.printStackTrace();
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DatabaseDriver extends JFrame implements ActionListener {

    private String database = "StudentDatabase";
    private String userName = "root";
    private String password = "nekochan1";
    private String url = "jdbc:MySQL://localhost:3306/" + database + "?characterEncoding=utf8";

    private Connection connection;
    private Statement queryStatement;
    private ResultSet queryOutput;

    private JButton button;
    private JTextField textField;

    public DatabaseDriver(){
        initializeSwing();
        connectToDB();
    }

    // Set up Swing utilities
    private void initializeSwing() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        button = new JButton("Submit");
        button.addActionListener(this);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setFont(new Font("Consolas", Font.PLAIN, 35));
        textField.setForeground(new Color(0x00FF00));
        textField.setBackground(Color.BLACK);
        textField.setCaretColor(Color.WHITE);

        this.add(button);
        this.add(textField);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    // Connect to the database :: Close the application if it fails to connect
    private void connectToDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url ,userName, password);

            System.out.println ("Database Connection Established...");
            queryStatement = connection.createStatement();
        }catch (Exception ex) {
            System.err.println ("Cannot connect to database server");
            System.exit(1);
        }
    }

    // Detect the user clicking the button to call a query to the database
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == button) {
                getStudentNameWithID(Integer.parseInt(textField.getText()));
            }
        }catch(NumberFormatException ex){
            System.out.println("Type a number");
        }
    }

    // Allow you to send a value and have it check the database
    private void getStudentNameWithID(int studentID){
        try{
            queryOutput = queryStatement.executeQuery("select * from Student WHERE studentID = " + studentID);

            // Allows us to get the number of column
            //ResultSetMetaData getMetaData = (ResultSetMetaData) queryOutput.getMetaData();
            //int numberOfColumn = getMetaData.getColumnCount();
            //System.out.println(numberOfColumn);

            while(queryOutput.next())
                System.out.printf("ID: %s\tName: %s\n", queryOutput.getString(1), queryOutput.getString(2));

        }catch (Exception ex) {
            System.err.println ("Cannot connect to database server");
            ex.printStackTrace();
        }
    }
}


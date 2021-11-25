import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DatabaseDriver extends JPanel implements ActionListener {

    private String database = "VaccineStatusDB";
    private String userName = "root";
    private String password = "nekochan1";
    private String url = "jdbc:MySQL://localhost:3306/" + database + "?characterEncoding=utf8";

    private Connection connection;
    private Statement queryStatement;
    private ResultSet queryOutput;



    private JButton jcomp1;
    private JLabel informUserLabel;
    private JTextField jcomp3;
    private JLabel jcomp4;
    private JLabel jcomp5;
    private JLabel jcomp6;
    private JLabel jcomp7;
    private JLabel jcomp8;
    private JLabel jcomp9;
    private JLabel jcomp10;
    private JLabel jcomp11;
    private JLabel jcomp12;
    private JLabel jcomp13;
    private JLabel jcomp14;
    private JLabel jcomp15;
    private JLabel jcomp16;

    private String msg = "";


    public DatabaseDriver(){
        jcomp1 = new JButton ("Button 3");
        jcomp1.addActionListener(this);
        informUserLabel = new JLabel ("Eneter a PersonID");
        jcomp3 = new JTextField (5);
        jcomp4 = new JLabel ("Name:");
        jcomp5 = new JLabel ("First Shot:");
        jcomp6 = new JLabel ("Second Shot:");
        jcomp7 = new JLabel ("Booster Shot:");
        jcomp8 = new JLabel ("Vaccine Name:");
        jcomp9 = new JLabel ("personName");
        jcomp10 = new JLabel ("NameOfVaccine");
        jcomp11 = new JLabel ("Date");
        jcomp12 = new JLabel ("Granted Exemption:");
        jcomp13 = new JLabel ("1Shot");
        jcomp14 = new JLabel ("2Shot");
        jcomp15 = new JLabel ("BShot");
        jcomp16 = new JLabel ("N/A");
        initializeSwing();
        connectToDB();
    }

    // Set up Swing utilities
    private void initializeSwing() {
        setPreferredSize (new Dimension (538, 272));
        setLayout (null);

        //add components
        add (jcomp1);
        add (informUserLabel);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);
        add (jcomp7);
        add (jcomp8);
        add (jcomp9);
        add (jcomp10);
        add (jcomp11);
        add (jcomp12);
        add (jcomp13);
        add (jcomp14);
        add (jcomp15);
        add (jcomp16);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (210, 220, 140, 20);
        informUserLabel.setBounds (200, 130, 240, 30);
        jcomp3.setBounds (130, 170, 275, 35);
        jcomp4.setBounds (10, 20, 55, 20);
        jcomp5.setBounds (335, 30, 85, 25);
        jcomp6.setBounds (315, 55, 95, 25);
        jcomp7.setBounds (310, 85, 100, 25);
        jcomp8.setBounds (10, 55, 110, 25);
        jcomp9.setBounds (70, 20, 165, 20);
        jcomp10.setBounds (125, 55, 115, 25);
        jcomp11.setBounds (395, 5, 45, 25);
        jcomp12.setBounds (10, 90, 145, 30);
        jcomp13.setBounds (420, 30, 100, 25);
        jcomp14.setBounds (420, 55, 100, 25);
        jcomp15.setBounds (420, 85, 100, 25);
        jcomp16.setBounds (160, 90, 100, 25);



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
            if (e.getSource() == jcomp1) {
                getPersonNameWithID(Integer.parseInt(jcomp3.getText()));
            }
        }catch(NumberFormatException ex){
            System.out.println("Type a number");
        }
    }

    // Allow you to send a value and have it check the database
    private void getPersonNameWithID(int personID){
        
        try{
            if(personID <= 0){
                System.out.println("ID cannot be less than one!");
                return;
            }
            queryOutput = queryStatement.executeQuery(
                    "SELECT p.PersonName, v.FirstShot FROM PERSON p\n" +
                    "INNER JOIN VACCINATION_HISTORY v USING(PersonID)\n" +
                    "WHERE PersonID = " + personID);
            boolean hasQuery = queryOutput.next();
        
            if(!hasQuery) {
                System.out.println("Test");
                informUserLabel.setText("ID not found");
                return;
            }

            // 1) Name :: 2) Date of First Shot :: 3) Date of second shot :: 4) Booster shot
            if(queryOutput.getString(2) == null){
                msg = String.format("%s isn't vaccinated", queryOutput.getString(1));
                informUserLabel.setText(msg);

                return;
            }

            System.out.printf("%s WAS vaccinated on %s\n", queryOutput.getString(1), queryOutput.getString(2));

        }catch (Exception ex) {
            System.err.println ("Cannot connect to database server");
            ex.printStackTrace();
        }
    }
}


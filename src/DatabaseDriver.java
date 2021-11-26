import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DatabaseDriver extends JPanel implements ActionListener {

    // Connect to the database and JDBC variables
    private String database = "VaccineStatusDB";
    private String userName = "root";
    private String password = "nekochan1";
    private String url = "jdbc:MySQL://localhost:3306/" + database + "?characterEncoding=utf8";

    private Connection connection;
    private Statement queryStatement;
    private ResultSet queryOutput;

    // Swing variables
    private final int SCREEN_WIDTH = 538;
    private final int SCREEN_HEIGHT = 272;


    private JButton sendQueryButton;
    private JLabel informUserLabel;
    private JTextField inputField;

    private final JLabel NAME_LABEL = new JLabel ("Name:");
    private JLabel nameData;

    private final JLabel DATE_LABEL = new JLabel ("Date");

    private final JLabel FIRST_SHOT_LABEL = new JLabel ("First Shot:");
    private JLabel firstShotDate;

    private final JLabel SECOND_SHOT_LABEL = new JLabel ("Second Shot:");
    private JLabel secondShotDate;

    private final JLabel BOOSTER_SHOT_LABEL = new JLabel ("Booster Shot:");
    private JLabel boosterShotDate;

    private final JLabel VACCINE_NAME_LABEL = new JLabel ("Vaccine Name:");
    private JLabel vaccineNameData;

    private final JLabel EXEMPTION_LABEL = new JLabel ("Granted Exemption:");
    private JLabel boosterShotExemptionInfo;

    // WIP MAY BE CHANGED LATER AS WE GO ON -- MAY BE LEGACY CODE FOR CONSOLE DISPLAY
    private String msg = "";


    public DatabaseDriver(){
        sendQueryButton = new JButton ("Submit Query");
        sendQueryButton.addActionListener(this);

        informUserLabel = new JLabel ("Enter a PersonID");

        inputField = new JTextField (5);

        nameData = new JLabel ("");
        vaccineNameData = new JLabel ("");
        firstShotDate = new JLabel ("");
        secondShotDate = new JLabel ("");
        boosterShotDate = new JLabel ("");
        boosterShotExemptionInfo = new JLabel ("N/A");

        // Adds them to the panel
        initializeSwing();

        // Connects to mySQL
        connectToDB();
    }

    // Set up Swing utilities
    private void initializeSwing() {
        setPreferredSize (new Dimension (SCREEN_WIDTH, SCREEN_HEIGHT));
        setLayout (null);

        //add components
        add (sendQueryButton);
        add (informUserLabel);
        add (inputField);
        add (NAME_LABEL);
        add (FIRST_SHOT_LABEL);
        add (SECOND_SHOT_LABEL);
        add (BOOSTER_SHOT_LABEL);
        add (VACCINE_NAME_LABEL);
        add (nameData);
        add (vaccineNameData);
        add (DATE_LABEL);
        add (EXEMPTION_LABEL);
        add (firstShotDate);
        add (secondShotDate);
        add (boosterShotDate);
        add (boosterShotExemptionInfo);

        // Absolute Positioning componenets - Used a program
        sendQueryButton.setBounds (210, 220, 140, 20);
        informUserLabel.setBounds (200, 130, 240, 30);
        inputField.setBounds (130, 170, 275, 35);

        NAME_LABEL.setBounds (10, 20, 55, 20);
        nameData.setBounds (70, 20, 165, 20);

        VACCINE_NAME_LABEL.setBounds (10, 55, 110, 25);
        vaccineNameData.setBounds (125, 55, 115, 25);

        EXEMPTION_LABEL.setBounds (10, 90, 145, 30);

        DATE_LABEL.setBounds (395, 5, 45, 25);
        FIRST_SHOT_LABEL.setBounds (335, 30, 85, 25);
        firstShotDate.setBounds (420, 30, 100, 25);

        SECOND_SHOT_LABEL.setBounds (315, 55, 95, 25);
        secondShotDate.setBounds (420, 55, 100, 25);

        BOOSTER_SHOT_LABEL.setBounds (310, 85, 100, 25);
        boosterShotDate.setBounds (420, 85, 100, 25);
        boosterShotExemptionInfo.setBounds (160, 93, 100, 25);
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
            if (e.getSource() == sendQueryButton) {
                getPersonNameWithID(Integer.parseInt(inputField.getText()));
            }
        }catch(NumberFormatException ex){
            // User typed characters
            informUserLabel.setText("Type a PersonID, not characters");
        }catch(SQLException ex){
            System.err.println ("Cannot connect to database server");
            System.exit(1);
        }
    }

    // Allow you to send a value and have it check the database
    private void getPersonNameWithID(int personID) throws SQLException {

        // User typed a personID below 0
        if(personID <= 0){
            msg = String.format("ID Cannot be less than one!");
            informUserLabel.setText(msg);
            return;
        }

        queryOutput = queryStatement.executeQuery(
                "SELECT p.PersonName, vt.VaccineName, v.FirstShot, v.SecondShot, \n" +
                        "v.BoosterShot FROM PERSON p\n" +
                        "INNER JOIN VACCINATION_HISTORY v USING(PersonID)\n" +
                        "INNER JOIN VACCINE vt USING(VaccineID)\n" +
                        "WHERE PersonID = " + personID);


        boolean hasQuery = queryOutput.next();

        // User typed a number that isn't in the database
        if(!hasQuery) {
            clearData("PersonID not in the database");
            return;
        }

        // 1) Name :: 2) Date of First Shot :: 3) Date of second shot :: 4) Booster shot
        if(queryOutput.getString(3) == null){
            //msg = String.format("%s isn't vaccinated", queryOutput.getString(1));
            //informUserLabel.setText(msg);
            parseDate(queryOutput.getString(1), null, null, null, null, false);
            return;
        }

        // If a person has at least one shot, they will end up here
        parseDate(queryOutput.getString(1), queryOutput.getString(2), queryOutput.getString(3), queryOutput.getString(4), queryOutput.getString(5), true);
    }

    private void parseDate(String name, String vaccineName, String firstDate, String secondDate, String boosterDate, Boolean isVaccinated){
        nameData.setText(name);
        vaccineNameData.setText(vaccineName);
        firstShotDate.setText(firstDate);
        secondShotDate.setText(secondDate);
        boosterShotDate.setText(boosterDate);

        if(isVaccinated){
            msg = String.format("%s is vaccinated",name);
            informUserLabel.setText(msg);
            return;
        }

        msg = String.format("%s isn't vaccinated",name);
        informUserLabel.setText(msg);
    }

    private void clearData(String customMsg){
        nameData.setText("");
        vaccineNameData.setText("");
        firstShotDate.setText("");
        secondShotDate.setText("");
        boosterShotDate.setText("");
        if(customMsg.length() > 1) {
            informUserLabel.setText(customMsg);
            return;
        }
        informUserLabel.setText("Enter a PersonID");
    }
}


import javax.swing.*;
import java.sql.*;
public class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame ("JDBC Group 3");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new DatabaseDriver());
        frame.pack();
        frame.setVisible (true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
    }
}

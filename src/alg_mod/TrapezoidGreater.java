/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg_mod;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.*;
import java.text.DecimalFormat;

import javax.swing.*;
/**
 *
 * @author Kaqm
 */
public class TrapezoidGreater extends JFrame 
{
    public static void main(String[]args)
    {
        TrapezoidGreater tg = new TrapezoidGreater();
    }
    static int amountOfRows; //based on Z score
    GridBagConstraints  gridConstraints = new GridBagConstraints();
    final int amountOfColumns = 11; //top row
    JTextField[] columns = new JTextField[amountOfColumns]; 
    JTextField[][] areaFields; //for matrix of text fields
    JPanel mainPanel = new JPanel(new GridBagLayout());
    DecimalFormat fiveDP = new DecimalFormat("0.00000");
    DecimalFormat oneDP = new DecimalFormat("0.0");
    double mean = Master.meanInput; //so these do not have to keep being accessed statically later on
    double sd = Master.sdInput;
    double xInput = Double.parseDouble(Master.greaterTextField.getText());
    double zScore = (xInput - mean) / sd;    
    double dx = 0.01;
    double halfArea = Calculate.calcHalfAreaTrapezoid(mean, sd, dx);  //area of half of graph 
    double finalArea = 0.0;
    JScrollPane jsp; 
    public static JTextArea jta;
    public TrapezoidGreater()
    {
        jta = new JTextArea(30, 20);
        jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);      
        setTitle("Z Scores");
        add(jsp);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        double ans = 0; //stores answer for area calculations
        if (zScore != 0) //if area will not equal half
        {
            if (zScore > 0) //test as +ve and -ve processed differently
            {
                ans = Calculate.trapezoidRule(zScore); //process as it is
            }
            else
            {
                ans = Calculate.trapezoidRule(zScore*-1); //convert to positive value                   
            }
            if (zScore < 0)
            {
                finalArea = ans; //same as area calculated
            }
            else
            {
                finalArea = 1 - ans; //subtract to get remaining area               
            }
        }
        else
        {
            finalArea = halfArea; //using variable calcuated from separate method
            amountOfRows = 40; //amount of rows used to create table
            for (int n = 0; n < Calculate.areas.length; n++)
            {
                double area = Calculate.areas[n];
                String str2 = fiveDP.format(area);
                jta.append("Strip " + n +": " + str2 + "\n");
            }
        }   
        String str = fiveDP.format(finalArea);
        System.out.println(str);
        Master.probTextField.setText(str);
        for (int m = 0; m < Calculate.areas.length; m++)
        {
            double area = Calculate.areas[m];
            String str2 = fiveDP.format(area);
            jta.append("Strip " + m +": " + area + "\n");
        }      
    }    
}
    

        /*setVisible(true);
        setTitle("Z Score Table");
        setMaximumSize(new Dimension(600,500));
        setPreferredSize(new Dimension(600,500));
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        Calculate.setAmountOfRows(40); //sets the amount of rows based on Z Score
        setTitle("Z Score Table");
        setLayout(new BorderLayout());
        add("Center", setScroller(createTable()));        
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);*/   
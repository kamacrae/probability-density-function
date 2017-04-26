/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg_mod;
/**
 *
 * @author Kirsty MacRae
 * 
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.*;

public class TrapezoidLess extends JFrame 
{
    public static void main(String[] args)
    {
        TrapezoidLess tl = new TrapezoidLess();
    }
    
    static int amountOfRows; //based on Z score
    GridBagConstraints  gridConstraints = new GridBagConstraints();
    final int amountOfColumns = 11; //top row
    JTextField[] columns = new JTextField[amountOfColumns]; 
    JTextField[][] areaFields; //for matrix of text fields
    JPanel mainPanel = new JPanel(new GridBagLayout());
    double mean = Master.meanInput; //so these do not have to keep being accessed statically later on
    double sd = Master.sdInput;
    double xInput = Double.parseDouble(Master.lessTextField.getText());
    double zScore = (xInput - mean) / sd; //standardise X value  
    double finalArea = 0.0;
    final double DX = 0.01; //delta x
    //area of half of graph        
    final double halfArea = Calculate.calcHalfAreaTrapezoid(mean, sd, DX);
    DecimalFormat fiveDP = new DecimalFormat("0.00000"); //for z score
    DecimalFormat oneDP = new DecimalFormat("0.0"); //for column labels
    JScrollPane jsp; 
    public static JTextArea jta;
    public TrapezoidLess()
    {
        /*jta = new JTextArea(30, 20);
        jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);      
        setTitle("Z Scores");
        add(jsp);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);*/
        setMaximumSize(new Dimension(600,500));
        setPreferredSize(new Dimension(600,500));
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        Calculate.setAmountOfRows(40); //default
        setTitle("Z Score Table");
        setLayout(new BorderLayout());
        System.out.println("Table: " + System.currentTimeMillis());
        add("Center", setScroller(createTable()));
        //add("Center", setScroller(createTextArea()));
        System.out.println("Table: " + System.currentTimeMillis());        
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
        
        double ans = 0; //stores answer for area calculations
        if (zScore != 0) //if area will not equal half
        {
            if (zScore > 0) //test as +ve and -ve processed differently
            {
                ans = Calculate.trapezoidRule(zScore); 
            }
            else
            {
                ans = Calculate.trapezoidRule(zScore*-1); //convert to positive value               
            }
            if (zScore > 0) //test as the calculation is different for negative values
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
            finalArea = halfArea;
            amountOfRows = 40;
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
            jta.append("Strip " + m +": " + str2 + "\n");
        }
    }
    //See calculate class for further comments for methods below (not implemented statically yet)
    private void setColumns() 
    {
        gridConstraints.gridy = 0;
        gridConstraints.gridx = 0;
        for(int i = 0; i < amountOfColumns; i++) 
        {
            columns[i] = new JTextField(4);
            if(i == 0) 
            {
               columns[i].setText("Z");
            }
            else 
            {               
               gridConstraints.gridx = i;
               columns[i].setText("0.0" + (i-1));                
            }
            mainPanel.add(columns[i], gridConstraints);
        }
    }
    
    private void setRows() //create text fields for first column 
    {
        gridConstraints.gridx = 0;
        for(int i = 0; i < amountOfRows; i++) 
        {
            gridConstraints.gridy = 1 + i;
            JTextField temp = new JTextField(4);
            temp.setText((double) i /10 + "");  
            //setText - has to check z 
            mainPanel.add(temp, gridConstraints);
        }
    }
    private JPanel createTable() //create remaining textfields from matrix
    {
        setColumns();
        setRows();
        //create text ields for all but first column, and for the full amount of rows
        areaFields = new JTextField[amountOfColumns-1][amountOfRows]; 
        for(int i = 0; i < amountOfRows; i++) 
        {
            gridConstraints.gridy = 1 + i;
            for(int j = 0; j < amountOfColumns-1; j++) 
            {
                areaFields[j][i] = new JTextField(4);
                gridConstraints.gridx = 1 + j;
                //areaFields[j][i].setText(Calculate.areas[i][j]);  //change so that the areas display
                mainPanel.add(areaFields[j][i], gridConstraints);
            }
        }
        JTextArea text = new JTextArea();
        mainPanel.add(text);
        return mainPanel;
    }   
    
    public JScrollPane setScroller(JPanel input)
    {
        JScrollPane scroll = new JScrollPane(input);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        input.setAutoscrolls(true);
        scroll.setWheelScrollingEnabled(true);
        return scroll;
    }
} 

        
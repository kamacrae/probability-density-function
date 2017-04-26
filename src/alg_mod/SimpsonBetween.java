/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg_mod;
/**
 *
 * @author Kaqm
 */
import static alg_mod.SimpsonLess.amountOfRows;
import java.text.DecimalFormat;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Kaqm
 */
public class SimpsonBetween extends JFrame
{
    static int amountOfRows; //based on Z score
    GridBagConstraints  gridConstraints = new GridBagConstraints();
    final int amountOfColumns = 11; //top row
    JTextField[] columns = new JTextField[amountOfColumns]; 
    JTextField[][] areaFields; //for matrix of text fields
    JPanel mainPanel = new JPanel(new GridBagLayout());
    double mean = Master.meanInput; //so these do not have to keep being accessed statically later on
    double sd = Master.sdInput;
    double xInput1 = Double.parseDouble(Master.between1TF.getText());
    double xInput2 = Double.parseDouble(Master.between2TF.getText());
    double zScoreLower = (xInput1 - mean) / sd;
    double zScoreUpper = (xInput2 - mean) / sd;   
    double finalArea = 0;
    double finalArea1 = 0;
    double finalArea2 = 0;
    final double DX = 0.01; //delta x
    //area of half of graph        
    final double halfArea = Calculate.simpsonRule(4); //area of half of graph      
    DecimalFormat fiveDP = new DecimalFormat("0.00000"); //for z score
    DecimalFormat oneDP = new DecimalFormat("0.0"); //for column labels
    JTextArea jta;
    JScrollPane jsp;
    public static void main(String[] args)
    {
        SimpsonBetween sb = new SimpsonBetween();
    }
    
    public SimpsonBetween()
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
        
        double ans = 0; //store answers for area
        if ((zScoreLower != 0 && zScoreUpper != 4) || (zScoreLower != -4 && zScoreUpper != 0)) //if area not equal to half
        {
            //section below calculates area less than lower limit
            if (zScoreLower > 0) 
            {
                ans = Calculate.simpsonRule(zScoreLower);
            }
            else
            {
                ans = Calculate.simpsonRule(zScoreLower*-1); //convert to positive value             
            }
            if (zScoreLower > 0)
            {
                finalArea1 = ans; 
            }
            else
            {
                finalArea1 = 1 - ans;                
            }
            //section below calculates area less than upper limit
            if (zScoreUpper > 0)
            {
                ans = Calculate.simpsonRule(zScoreUpper);
            }
            else
            {
                ans = Calculate.simpsonRule(zScoreUpper*-1);                
            }
            if (zScoreUpper > 0)
            {
                finalArea2 = ans;
            }
            else
            {
                finalArea2 = 1 - ans;                
            }
            System.out.println("" + finalArea);
            Master.probTextField.setText(finalArea+ "");
        }
        else
        {
            finalArea2 = halfArea;
            amountOfRows = 40;
            for (int m = 0; m < Calculate.areas.length; m++)
            {
                double area = Calculate.areas[m];
                String str2 = fiveDP.format(area);
                jta.append("Strip " + m +": " + area + "\n");
            }
        }
        
        finalArea = finalArea2 - finalArea1; //substract lower from upper to get area betwee
        System.out.println("" + finalArea);
        Master.probTextField.setText(finalArea+ ""); //display in GUI
        for (int m = 0; m < Calculate.areas.length; m++)
        {
            double area = Calculate.areas[m];
            String str2 = fiveDP.format(area);
            jta.append("Strip " + m +": " + area + "\n");
        }
        
    }
    
}
        /*setMaximumSize(new Dimension(600,500));
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
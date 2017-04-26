/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg_mod;
/**
 *
 * @author Kirsty MacRae
 */
//import static alg_mod.SimpsonLess.amountOfRows;
import java.text.DecimalFormat;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Kaqm
 */
public class SimpsonGreater extends JFrame
{
    static int amountOfRows; //based on Z score
    GridBagConstraints  gridConstraints = new GridBagConstraints();
    final int amountOfColumns = 11; //top row
    JTextField[] columns = new JTextField[amountOfColumns]; 
    JTextField[][] areaFields; //for matrix of text fields
    JPanel mainPanel = new JPanel(new GridBagLayout());
    double mean = Master.meanInput; //so these do not have to keep being accessed statically later on
    double sd = Master.sdInput;
    double xInput = Double.parseDouble(Master.greaterTextField.getText());
    double zScore = (xInput - mean) / sd; //standardise X value  
    double z = zScore * 100; //convert for pixels       
    double finalArea = 0.0;
    final double DX = 0.01; //delta x      
    final double halfArea = Calculate.calcHalfAreaSimpson(mean, sd, DX); //area of half of graph      
    DecimalFormat fiveDP = new DecimalFormat("0.00000"); //for z score
    DecimalFormat oneDP = new DecimalFormat("0.0"); //for column labels
    JTextArea jta;
    JScrollPane jsp;
    
    public static void main(String[] args)
    {
        SimpsonGreater sg = new SimpsonGreater();
    }
    
    public SimpsonGreater()
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
                ans = Calculate.simpsonRule(zScore); //process as it is
            }
            else
            {
                ans = Calculate.simpsonRule(zScore*-1); //convert to positive value                 
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
            for (int m = 0; m < Calculate.areas.length; m++)
            {
                double area = Calculate.areas[m];
                String str2 = fiveDP.format(area);
                jta.append("Strip " + m +": " + area + "\n");
            }
        }    
        String str = fiveDP.format(finalArea);
        Master.probTextField.setText(str);
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
        
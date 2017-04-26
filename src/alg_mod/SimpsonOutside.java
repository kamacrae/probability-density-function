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
import java.text.DecimalFormat;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Kaqm
 */
public class SimpsonOutside extends JFrame
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
    final double halfArea = Calculate.calcHalfAreaSimpson(mean, sd, DX); //area of half of graph      
    DecimalFormat fiveDP = new DecimalFormat("0.00000"); //for z score
    DecimalFormat oneDP = new DecimalFormat("0.0"); //for column labels
    JTextArea jta;
    JScrollPane jsp;
    public static void main(String[] args)
    {
        SimpsonOutside s1 = new SimpsonOutside();
    }
    
    public SimpsonOutside()
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
        
        double ans;
        if ((zScoreLower != 0 && zScoreUpper != 4) || (zScoreLower != -4 && zScoreUpper != 0)) //test to see if area will = half
        {    
            if (zScoreLower > 0)
            {
                ans = Calculate.simpsonRule(zScoreLower);
                finalArea1 = ans;
            }
            else
            {
                ans = Calculate.simpsonRule(zScoreLower*-1);    
                finalArea1 = 1 - ans;
            }
            
            if (zScoreUpper > 0)
            {
                ans = Calculate.simpsonRule(zScoreUpper*-1); 
                finalArea2 = 1 - ans;
            }
            else
            {
                ans = Calculate.simpsonRule(zScoreUpper);
                finalArea2 = ans;
            }
            finalArea = finalArea1 + finalArea2;
        }
        else
        {
            finalArea = halfArea;
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
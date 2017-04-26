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
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class TrapezoidOutside extends JFrame
{
    public static void main(String[] args)
    {
        TrapezoidOutside t1 = new TrapezoidOutside();
    }
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
    double finalArea1 = 0.0;
    double finalArea2 = 0.0;
    double finalArea = 0.0;
    double dx = 0.01; 
    double halfArea = Calculate.calcHalfAreaTrapezoid(mean, sd, dx);  //area under half of curve
    DecimalFormat fiveDP = new DecimalFormat("0.00000"); //for z score
    DecimalFormat oneDP = new DecimalFormat("0.0"); //for column labels
    JTextArea jta;
    JScrollPane jsp;
    public TrapezoidOutside()
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
            if (zScoreLower >= 0)
            {
                ans = Calculate.trapezoidRule(zScoreLower);
                finalArea1 = ans;// + halfArea;
            }
            else
            {
                ans = Calculate.trapezoidRule(zScoreLower*-1);    
                finalArea1 = 1 - ans;
            }
            
            if (zScoreUpper >= 0)
            {
                ans = Calculate.trapezoidRule(zScoreUpper*-1); 
                finalArea2 = 1 - ans;
            }
            else
            {
                ans = Calculate.trapezoidRule(zScoreUpper);
                finalArea2 = ans;
            }
          
            Master.probTextField.setText(finalArea+ "");
            finalArea = finalArea1 + finalArea2;
        }
        else
        {
            finalArea = halfArea;
            for (int n = 0; n < Calculate.yHalf.length; n++)
            {
                double area = Calculate.yHalf[n];
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
            jta.append("Strip " + m +": " + area + "\n");
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
    }    
}

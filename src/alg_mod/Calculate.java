/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg_mod;

import static alg_mod.TrapezoidLess.amountOfRows;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel; 
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Kirsty MacRae
 */
public class Calculate extends JPanel
{ 
    //int arrSize = (int)Master* 1000;
    public static double areas[] = new double[4000];
    static DecimalFormat fiveDP = new DecimalFormat("0.00000"); //for z score
     public static void main(String[] args)
    {
        double test3 = calcHalfAreaTrapezoid(0, 1, 0.001);
        //double test5 = trapezoidRule(1);
        //System.out.println("" + test3);
        //double test4 = 1 - test3;
        //System.out.println("" + test4);
    }
    //method is used to calculate the value of f(x) when mean, standard deviation and x are entered
    public static double calcY(double mean, double sd, double x) 
    {
        double fHalf = 1.0 / (sd * (Math.sqrt(2*Math.PI)));
        double sHalf = (((x - mean)*(x-mean)))/(2.0*(sd*sd))*-1;  
        double r = fHalf * Math.exp(sHalf);
        return r;
    }
    
    //method is called from all of the Trapezoid classes to calculate the area
    public static double trapezoidRule(double size)
    {
        double count = 0;
        double total = 0;
        double finalTotal = 0;
        double finalResult = 0;
        double first = calcY(0, 1, count); //get answer for f(x)1
        double last = calcY(0, 1, size); //get answer for f(x)n-1
        int n = (int)(size / 0.001); //get amount of strips, multiply n by 1000 for 1000 strips per standard deviation
        
        for (int i = 0; i < n; i++)
        {
            count = i * 0.001; //get each incremental value to put through p(x) function
            total += calcY(0,1,count) * 2.0; //running total
            double incArea = 0; //will store area for each iteration of the loop
            incArea = (0.001 / 2.0) * total + 0.5;
            areas[i] = incArea; 
        }
        finalTotal = total + first + last; //total all
        finalResult = (0.001 / 2.0) * finalTotal + 0.5; //final area
        return finalResult;
    }
    //method is called from all of the Simpson classes to calculate the area
    public static double simpsonRule(double size) //mean, standard deviation, acmount of strips
    {
        double count = 0;
        double finalTotal = 0; 
        double finalResult = 0;
        double totalOdd = 0;
        double totalEven = 0;
        double first = calcY(0, 1, count); //get answer for f(x)1
        double last = calcY(0,1,size); //get answer for f(x)n-1
        int n = (int)(size / 0.001); //get amount of strips, multiply n by 1000 for 1000 strips per standard deviation
        boolean odd = true; //use to test index 
        for(int i = 0; i < n; i++) 
        {
            count = i * 0.001; //get each incremental value to put through p(x) function
            if(odd) 
            {
                totalOdd += calcY(0,1,count) * 4.0; //running total
                double res  = (0.001 / 3.0) * totalOdd + 0.5; //area for less than this strip
                areas[i] = res; //store in array which will be accessed to display data
                odd = false; //change so that it will alternate
            } 
            else 
            {
                totalEven += calcY(0,1,count) * 2.0; //running total
                double res  = (0.001 / 3.0) * totalEven + 0.5; //area for less than this strip
                areas[i] = res; //store in array which will be accessed to display data
                odd = true; //change so that it will alternate
            }
        }
        finalTotal = totalEven + totalOdd + first + last; //total all      
        finalResult = (0.001 / 3.0) * finalTotal+0.5; //final area
        return finalResult;
    }
    public static double[] yHalf = new double[400]; //stores f(x) values
    /*Method or use where the area requested = the area of half the graph and Trapezoidal is selected.
    Can be improved by adapting to the same approach as methods above if more time*/
    public static double calcHalfAreaTrapezoid(double mean, double sd, double dx)
    {
        double areas[] = new double[4000];
        double totalIntermediateHalf = 0.0;
        double test = 0;
        for (int a = 0; a < 400; a++) //400 strips for half graph
        {
            double x = a / 100.0; //get each x value for p(x) function
            double fx = calcY(mean, sd, x); 
            yHalf[a] = fx; //has to be stored to access first and last  
        }
       
        for (int j = 1; j < yHalf.length - 1; j++) //from second to penultimate value of n
        {                
            double fx2 = yHalf[j];//get value from array
            double intermediate = 2.0 * fx2; 
            totalIntermediateHalf += intermediate; //running total
            double res = (dx / 2.0) * totalIntermediateHalf; //area for less than this strip
            areas[j] = res; //store in array which will be accessed to display data
        }           
        double halfFirst = yHalf[0]; //get value for f(x)1
        double halfLast = yHalf[399]; //get value for f(x)n-1
        double halfTotal = totalIntermediateHalf + halfFirst + halfLast;
        double halfResult = (dx / 2.0) * halfTotal;  
        
        return halfResult;
    }
   
    //method or use where the area requested = the area of half the graph and Simpson is selected
    public static double calcHalfAreaSimpson(double mean, double sd, double dx)
    {
        double[] odd = new double[200]; //store all odd values
        double[] even = new double[200]; //store all even values
        double totalEven = 0;
        double totalOdd = 0;
        double result = 0;
        //probability density function
        for (int a = 0; a < 400; a++)
        {
            double x = a / 100.0;
            double fx = calcY(mean, sd, x); 
            yHalf[a] = fx; //stored to access first and last  
        }
        //even * 2, odd * 4
        for (int j = 1; j < yHalf.length - 1; j++)
        {
            if (j % 2 == 0)
            {
                double fx = yHalf[j];
                int a = j / 2; //Prevents ArrayIndexOutOfBounds, was thrown when i = 200 as both arrays were incrementing with i
                even[a] = fx*2.0;               
            }
            else
            {
                double fx = yHalf[j];
                int a = j / 2; //as above
                odd[a] = fx*4.0;            
            }
        }
        for (int m = 0; m < even.length; m++) //take totals for even
        {
            double answer = even[m];
            totalEven += answer;
            double res = (1 / 3) * dx * totalEven + 0.5;  //calculate area 
            areas[m] = res;
        }
        for (int k = 0; k < odd.length; k++) //take totals for odd, could not put in same loop as arrays are different lengths
        {
            double answer2 = odd[k];
            totalOdd += answer2;        
            double res = (1 / 3) * dx * totalOdd + 0.5;
            areas[k] = res;
        }
        double first = yHalf[0]; //get value for f(x)1
        double last = yHalf[399]; //get value for f(x)n-1
        double finalTotal = totalEven + totalOdd + first + last; 
        result = (1 / 3) * dx * finalTotal + 0.5;  //calculate area     
        
        return result;
    }
    public static void setColumns(JTextField[] inputArray, JFrame frame)
    {
        int count = 50;
        for(int i = 0 ; i < 10; i++) 
        {
            inputArray[i].setText("0.0" + i);
            frame.add(inputArray[i]);
            inputArray[i].setBounds(50 + count, 10, 50, 20);
            count += 50;
        }
    } 
    public static void setRows(ArrayList<JTextField> inputFields, JFrame frame)
    {
        int count = 30;
        //for(int j = 0; j < amountOfRows; j++)
        {
            JTextField temp = new JTextField(4);
            inputFields.add(temp);
            frame.add(temp);
            //temp.setText("" + j);
            temp.setBounds(10, (30 + count), 40, 20);
            count += 30;
        }
    }     
    public static void setAmountOfRows(int size) //sets the amount of rows based on zScore
    {
        amountOfRows = size;
    }
    public JScrollPane setScroller(JPanel input)
    {
        JScrollPane scroll = new JScrollPane(input);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        input.setAutoscrolls(true);
        scroll.setWheelScrollingEnabled(true);
        return scroll;
    }
    /*private JPanel createTable() //create remaining textfields from matrix
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
    }*/ 
}

        
    
    


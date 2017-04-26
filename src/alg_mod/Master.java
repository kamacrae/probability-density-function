/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *///http://www.danielsoper.com/statcalc/calculator.aspx?id=56
package alg_mod;
/**
 *
 * @author Kaqm
 */ 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

public class Master extends JFrame implements ActionListener 
{
    DecimalFormat dp = new DecimalFormat("0.00000");
    DecimalFormat dp2 = new DecimalFormat("0.00");
    public static double meanInput;
    public static double sdInput;
    //when button combinations considered, textfield contents will create static variables
    JLabel meanLabel = new JLabel("Enter Mean:");
    JTextField meanTextField = new JTextField(4);
    JLabel stdDevLabel = new JLabel(" Enter Standard Deviation:");
    JTextField stdDevTextField = new JTextField(4);
    public static JTextField greaterTextField = new JTextField(4);
    public static JTextField lessTextField = new JTextField(4);
    public static JTextField between1TF = new JTextField(4);
    JLabel and1 = new JLabel("&");
    public static JTextField between2TF = new JTextField(4);
    public static JTextField outside1TF = new JTextField(4);
    JLabel and2 = new JLabel("&");
    public static JTextField outside2TF = new JTextField(4);
    JButton probButton = new JButton("View Probability");
    public static JTextField probTextField = new JTextField(7); 
    JButton updateButton = new JButton("Update");
    Graph normDist = new Graph(0.0,1.0);          
    ButtonGroup buttons = new ButtonGroup();
    //buttons will be accessed from other classes
    JRadioButton lessBtn = new JRadioButton("Less than: ", true);
    JRadioButton greaterBtn = new JRadioButton("Greater than: ", false);
    JRadioButton betweenBtn = new JRadioButton("Between: ", false);
    JRadioButton outsideBtn = new JRadioButton("Outside of: ", false);
    ButtonGroup buttonsTop = new ButtonGroup();
    JRadioButton simpson = new JRadioButton("Simpson's Rule", true);
    JRadioButton trapezoid = new JRadioButton("Trapezoidal Rule", false);
    double[] y = new double[401];
    
    //axis labels 
    JTextField meanTF = new JTextField(5);
    JTextField x1TF = new JTextField(5);
    JTextField x2TF = new JTextField(5);
    JTextField x3TF = new JTextField(5);
    JTextField x4TF = new JTextField(5);
    JTextField minusX1TF = new JTextField(5);
    JTextField minusX2TF = new JTextField(5);
    JTextField minusX3TF = new JTextField(5);
    JTextField minusX4TF = new JTextField(5);
    JTextField y1TF = new JTextField(7);
    JTextField y2TF = new JTextField(7);
    JTextField y3TF = new JTextField(7);
    JTextField y4TF = new JTextField(7);
    
    public static void main(String[] args)
    {
        System.out.println("M" + System.currentTimeMillis());
        Master main = new Master(); 
        System.out.println("M" + System.currentTimeMillis());
    }
    
    public Master()
    {
        setLayout(new BorderLayout());
        //this.setBackground(Color.WHITE); //WHY IS THIS NOT WORKING?
        setVisible(true);
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Probability Density Function");
        add("Center", normDist);     //Add graph to frame
        JPanel top = new JPanel();
        add("North", top);
        JPanel bottom = new JPanel();
        add("South", bottom);
        top.setLayout(new FlowLayout());
        bottom.setLayout(new FlowLayout());
        top.add(meanLabel);
        top.add(meanTextField);       
        top.add(stdDevLabel);
        top.add(stdDevTextField);
        buttonsTop.add(simpson);
        buttonsTop.add(trapezoid);       
        top.add(updateButton);
        updateButton.addActionListener(this);
        top.add(simpson);
        top.add(trapezoid);
        buttons.add(lessBtn);
        buttons.add(greaterBtn);
        buttons.add(betweenBtn);
        buttons.add(outsideBtn);
        bottom.add(lessBtn);
        bottom.add(lessTextField);
        lessTextField.addActionListener(this);
        bottom.add(greaterBtn);       
        bottom.add(greaterTextField);
        greaterTextField.addActionListener(this);
        bottom.add(betweenBtn);
        bottom.add(between1TF);
        bottom.add(and1);
        bottom.add(between2TF);
        bottom.add(outsideBtn);
        bottom.add(outside1TF);
        bottom.add(and2);
        bottom.add(outside2TF);
        bottom.add(probButton);
        bottom.add(probTextField);
        probButton.addActionListener(this);
    }

    public class Graph extends JPanel
    {
        private double mean;
        private double standard;
        Graph(double m, double sd)
        {
            mean = m;
            standard = sd;
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g); 
            g.setColor(Color.BLACK);
            g.drawLine(50, 450, 850, 450); //x axis
            g.drawLine(450, 450, 450, 50);   //y axis
            //x axis markers, 100 pixels per 1 standard deviation
            g.drawLine(450, 450, 450, 455);//midpoint/mean
            g.drawLine(650, 450, 650, 455);//2 standard deviations
            g.drawLine(850, 450, 850, 455);//4 standard deviations
            g.drawLine(250, 450, 250, 455);//-2 standard deviations
            g.drawLine(350, 450, 350, 455);//-1 standard deviation
            g.drawLine(550, 450, 550, 455);//1 standard deviation
            g.drawLine(750, 450, 750, 455);//3 standard deviations
            g.drawLine(150, 450, 150, 455);//-3 standard deviations
            g.drawLine(50, 450, 50, 455); //-4 standard deviations
            //y axis markers - 100 pixels between each marker, each marker represents 0.1 increments
            g.drawLine(450, 350, 455, 350); 
            g.drawLine(450, 250, 455, 250); 
            g.drawLine(450, 150, 455, 150); 
            g.drawLine(450, 50, 455, 50);   
              
            Graphics2D g2 = (Graphics2D) g;
            int i = 0;
            int j = 0;
            double x = 0.00;
            double firstHalf = 1.0 / (standard * (Math.sqrt(2*Math.PI)));
            //double firstHalf = 1.0 / (sdInput * (Math.sqrt(2*Math.PI)));
            double counter = 450.0;
            double counter2 = 450.0;
            double[] results = new double[401];
            System.out.println("" + System.currentTimeMillis());
            for(i = 0; i < 401; i++)  //j is actual x coordinates
            {
                //x values from 0.0 to 4.0 to put through formula 
                x = i / 100.0;           
                double secondHalf = (((x-mean)*(x-mean))/((2.0*standard)*(2.0*standard)))*-1;  
                double result = firstHalf * Math.exp(secondHalf);
                results[i] = result;
                //scaling
                double yCoordinates = result * 1000 + 50; //*1000 to scale to frame
                double firstY = 50.0; //top most pixel on y axis
                double differenceMinus = yCoordinates - 450;  
                double difference = Math.abs(differenceMinus);  
                double yPixelsActual = firstY + difference; //so it increcements downwards on the frame
                y[i] = yPixelsActual; 
                double y1 = y[i]; 
                double y2 = y[i++];
                g2.draw(new Line2D.Double(counter, y1, counter++, y2)); //curve for 0 to 4 standard deviations
                counter++;
                //use same y values
                g2.draw(new Line2D.Double(counter2, y1, counter2--, y2)); //curve for 0 to -4 standard deviations
                counter2--;                
            } 
            System.out.println("" + System.currentTimeMillis());
            this.add(meanTF);
            meanTF.setBounds(437, 455, 40, 20);
            this.add(x1TF);
            x1TF.setBounds(537, 455, 40, 20);
            this.add(x2TF);
            x2TF.setBounds(637, 455, 40, 20);
            this.add(x3TF);
            x3TF.setBounds(737, 455, 40, 20);
            this.add(x4TF);
            x4TF.setBounds(837, 455, 40, 20);
            this.add(minusX1TF);
            minusX1TF.setBounds(337, 455, 40, 20);
            this.add(minusX2TF);
            minusX2TF.setBounds(237, 455, 40, 20);
            this.add(minusX3TF);
            minusX3TF.setBounds(137, 455, 40, 20);
            this.add(minusX4TF);
            minusX4TF.setBounds(37, 455, 40, 20);
            this.add(y1TF);
            y1TF.setBounds(455, 335, 50, 20);
            this.add(y2TF);
            y2TF.setBounds(455, 235, 50, 20);
            this.add(y3TF);
            y3TF.setBounds(455, 135, 50, 20);
            this.add(y4TF);
            y4TF.setBounds(455, 35, 50, 20);
            setLabels(mean, standard);
        }
    }
   
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == updateButton)
        {
           //update markers             
            meanInput = Double.parseDouble(meanTextField.getText());
            sdInput = Double.parseDouble(stdDevTextField.getText());
            System.out.println("L" + System.currentTimeMillis());
            setLabels(meanInput, sdInput);
            System.out.println("L" + System.currentTimeMillis());
        }
      
        if (e.getSource() == probButton) 
        {
                       
            if (simpson.isSelected())
            {
                if (lessBtn.isSelected())
                {
                    SimpsonLess lessThan = new SimpsonLess();
                    
                    
                }
                if (greaterBtn.isSelected())
                {
                    SimpsonGreater greaterThan = new SimpsonGreater();
                   
                }        
                if (betweenBtn.isSelected())
                {
                    SimpsonBetween between = new SimpsonBetween();
                    
                }
                if (outsideBtn.isSelected())
                {
                    JOptionPane.showMessageDialog(null, "Not currently working.");
                    SimpsonOutside outside = new SimpsonOutside();                    
                }
            }
            if (trapezoid.isSelected())
            {
                if (lessBtn.isSelected())
                {
                    TrapezoidLess lessThan = new TrapezoidLess();
                }
                if (greaterBtn.isSelected())
                {
                    TrapezoidGreater greaterThan = new TrapezoidGreater();                 
                }        
                if (betweenBtn.isSelected())
                {
                    TrapezoidBetween between = new TrapezoidBetween();                   
                }
                if (outsideBtn.isSelected())
                {
                    JOptionPane.showMessageDialog(null, "Not currently working.");
                    TrapezoidOutside outside = new TrapezoidOutside();                    
                }
            }  
            
        }
           
    }
    public void setLabels(double mean, double sd)
    {
        //y labels
        double yLabel4 = Calculate.calcY(mean, sd, mean); //second mean = x
        double yLabel2 = yLabel4 / 2.0;
        double yLabel1 = yLabel2 / 2.0;
        double yLabel3 = yLabel2 + yLabel1;
        y1TF.setText("" + dp.format(yLabel1));
        y2TF.setText("" + dp.format(yLabel2));
        y3TF.setText("" + dp.format(yLabel3));
        y4TF.setText("" + dp.format(yLabel4));
        //x labels
        meanTF.setText("" + dp2.format(mean));
        x1TF.setText("" + (dp2.format(mean + sd)));
        x2TF.setText("" + (dp2.format(mean + 2.0*sd)));
        x3TF.setText("" + (dp2.format(mean + 3.0* sd)));
        x4TF.setText("" + (dp2.format(mean + 4.0*sd)));
        minusX1TF.setText("-" + dp2.format(mean + sd));
        minusX2TF.setText("-" + (dp2.format(mean + 2.0 * sd)));
        minusX3TF.setText("-" + (dp2.format(mean + 3.0* sd)));
        minusX4TF.setText("-" + (dp2.format(mean + 4.0*sd)));
    }
}

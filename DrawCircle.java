import javax.swing.*;
import java.awt.*;

public class DrawCircle extends JLabel{ //creating the object class which extends JLabel{ 
  
  private int x=15;   // leftmost pixel in circle has this x-coordinate
  private int y=15;   // topmost  pixel in circle has this y-coordinate
  Color circleColor = Color.white;
  
  public DrawCircle() {   //this is the constructor
    setSize(150,150);
    setLocation(50,50);      //setting the size, location
    setVisible(true);
    
  }
  
  public Color getColor(){
    return circleColor;
  }
  
  public void setColor(Color Scircle){       //takes in a variable of type Color, and stores it into circle
    circleColor = Scircle;
  }
  
  // paint is called automatically when program begins 
  public void paint(Graphics g) {              //using Graphics java library
    g.setColor(circleColor);                    //setting the color based on the setter method above
    
    
    g.fillOval(x,y,60,60);//change the last two numbers (60, 60) to a smaller value for better results on smaller laptops;    //painting the circle
    
  }
}
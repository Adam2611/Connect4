/* Connect 4 Project
 * by Adam Lam, Andrew Yue, Vanessa Ho
 * For Mr. Andanarjan
 * January 14th, 2019
 * 
 * This program creates and maintains and GUI application, modelling the well-known game of Connect 4. This program has many features. It allows the user to choose Player vs Player or Player vs AI, colour customization, and even who would
 * like to go first. It checks intuitively if the user has won. And all of this, is packed into a java program. 
 * 
 * Please enjoy this program! 
 * 
 * 
 * Please check the ReadMe file for more details regarding this program.
 */


import javax.swing.*;           //Importing all necessary libraries
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.sound.sampled.*;

public class Connect4Final extends JFrame implements ActionListener   //Class definition
{  
  
  //Global Variables
  static final int ROW = 6;              //final variables
  static final int COL = 7;
  
  static JPanel welcome = new JPanel();
  static JPanel board = new JPanel();      //panels
  
  
  JLabel welcomelabel = new JLabel("Welcome to the game of Connect 4!", JLabel.CENTER);
  static JLabel instructions = new JLabel("Please enter your name",JLabel.CENTER);     //labels
  JButton[] columnarray = new JButton[7];
  
  static DrawCircle[][] arrayCircles = new DrawCircle[ROW][COL];
  
  JButton okButton = new JButton("OK");                                     //buttons
  JButton pvp = new JButton("Player vs Player");    
  JButton pvai = new JButton("Player vs AI");                               //each button does something specific when pressed, thats why there are so many
  static  JButton black = new JButton("Black");
  static JButton red = new JButton("Red");                          //colours
  static JButton yellow = new JButton("Yellow");
  static JButton green = new JButton("Green");
  static JButton okayButton = new JButton("Okay");                 
  static JButton p1first = new JButton("");                         //will be defined later in the class
  static JButton p2first = new JButton("");
  static JButton hORt = new JButton("Flip a coin");               //flips coin to decide who goes first
  static JButton easyAI = new JButton("Easy AI");
  static JButton hardAI = new JButton("Hard AI");
  static JButton again = new JButton("Yes");
  static JButton notAgain = new JButton("No");
  
  static JTextField input = new JTextField(20);      //textfields - only need one since we can erase and reuse the same textfield
  
  static Font f = new Font("Times New Roman", Font.PLAIN, 20);  //font for the text
  static Font small = new Font("Courier", Font.PLAIN, 12);
  static Random r = new Random();                             //other variables (static because we want to use them in the actionlistener AND in other methods
  static String name1;                          //for p1
  static String name2 = "AI";       //for p2, setting auto to AI for the AI
  static Color color1;     //for p1
  static Color color2=Color.magenta;      //for p2; setting auto to the ai color's
  static int choice = 1;                  //1 for player 1 choice, 2 for player 2 choice
  static int mode = 1;                  //1 for pvp, 2 for pvai
  static int randomnum;
  static int columnnumber;
  static int whoseTurn = 1;          //1 for p1, 2 for p2
  static int numTurns = 0;         //total number of turns that have passed
  static boolean canGo = false;             //boolean that represents when the user may play a checker
  static Color rgb = new Color(97, 155, 249);   //able to customize background with any colour
  static boolean choseEasyAI;             //determines which mode of ai they picked
  static int AINumber = 3;           //setting default to the easyAI unless they change it to hard
  static int erase = 0;              //this variable is for which column the ai is checking to see whether it should place it here or not
  static boolean AIPlayed = false;         //to see if the AI has won or not
  static boolean GameWon = false;
  static boolean playAgain = false;
  static int userWins = 0;
  static int [] shouldNotPlace = new int[7];  //the column number that the AI should not place its checker in
  static int timesChecked = 0;      //number of times the AI checks the future state of the game
  static int counter=0;            //for the shouldNotPlace array to increment on
  static boolean canPlace=true;       //if the AI canPlace a move without losing
  static int mustPlace = -1;             //mustPlace - when the AI must make a move or else the opponent can get a double trap
  static boolean trapBlocked = false;   //if the AI has or has not blocked the trap on the bottom
  
  JLabel winCounter = new JLabel("User Win Counter: "+userWins, JLabel.LEFT);    //for the user win counter
  
  //START OF CONSTRUCTOR
  //=============================================================================================--------------end of attributes and beginning of constructor
  
  public Connect4Final()  {         //building the GUI
    
    setTitle("Connect 4");             //title
    setSize(1366, 700);                     //size of frame
    setResizable(false);                //setting this to false because if resizeable, the board may become unproportional
    
    GridLayout layout2 = new GridLayout(7, 9);            //layouts
    BoxLayout layout3 = new BoxLayout(welcome, BoxLayout.Y_AXIS);
    
    setLayout(new GridBagLayout());   //setting the Layout of the entire frame to a gridBagLayout
    board.setLayout(layout2);    //setting layouts of the 2 panels
    welcome.setLayout(layout3);
    
    welcome.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));  //setting border around the panel
    
    winCounter.setFont(small);
    welcome.add(winCounter);
    winCounter.setAlignmentX(Component.CENTER_ALIGNMENT);
    winCounter.add(Box.createRigidArea(new Dimension(0,75)));
    
    welcomelabel.setFont(f);          //setting fonts
    welcome.add(welcomelabel);
    welcomelabel.setAlignmentX(Component.CENTER_ALIGNMENT);       //adding the labels one by one and modifying them -- alignment, extra spacing, size etc.
    welcome.add(Box.createRigidArea(new Dimension(0,75)));
    
    instructions.setFont(f);          //setting fonts
    welcome.add(instructions);
    instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
    welcome.add(Box.createRigidArea(new Dimension(0,75)));   //creates extra space after each label
    
    welcome.add(input);   
    welcome.add(Box.createRigidArea(new Dimension(0,75)));
    input.setMaximumSize(new Dimension(200, 25));              //sets the maximum size of textfield
    
    welcome.add(okButton);
    welcome.add(Box.createRigidArea(new Dimension(0,75)));
    okButton.setAlignmentX(Component.CENTER_ALIGNMENT);         //aligns all the components by their center on the x axis of the panel
    
    for(int i = 0; i<7; i++){
      columnarray[i] = new JButton("         "+Integer.toString(i+1)); //adding the column numbers as buttons to the board panel and formatting them preoperly
      columnarray[i].setHorizontalAlignment(SwingConstants.LEFT);
      columnarray[i].addActionListener(this);
      columnarray[i].setBackground(rgb);                                 
      columnarray[i].setBorderPainted(false);
      board.add(columnarray[i]);
    }
    
    for(int i = 0; i<ROW; i++){
      for(int j = 0; j<COL; j++){
        arrayCircles[i][j] = new DrawCircle();                  //adding the circles to the board panel
        board.add(arrayCircles[i][j]);
      }
    }
    
    okButton.addActionListener(this);                                 //adding all the actionlisteners
    pvp.addActionListener(this);
    pvai.addActionListener(this);
    black.addActionListener(this);
    red.addActionListener(this);
    yellow.addActionListener(this);
    green.addActionListener(this);
    okayButton.addActionListener(this);
    easyAI.addActionListener(this);
    hardAI.addActionListener(this);
    again.addActionListener(this);
    notAgain.addActionListener(this);
    
    add(welcome, makeConstraints(0,0,3,20));                       //adding the panels to the frame with specified parameters on where they go
    add(board, makeConstraints(3,0,10,20));
    
    welcome.setPreferredSize(new Dimension(3, 30));         //setting the preferred size of the welcome panel
    
    board.setBackground(rgb);              //setting the background of the board to be a specific customizable colour
    
    setVisible(true);                //setting the frame to be visible
  }
  //end of Constructor 
  
  public GridBagConstraints makeConstraints(int x, int y, int width, int height){       //gridbag constraints method is used to help specify where things go on the frame
    GridBagConstraints g = new GridBagConstraints();
    g.gridx = x;                    
    g.gridy = y;
    g.gridwidth = width;
    g.gridheight = height;
    g.fill = GridBagConstraints.BOTH;                              //used for the welcome and board panels to help portion out the sizes
    g.weightx = 1;
    g.weighty = 1;
    return g;
  }
  
  
  //START OF ACTION PERFORMED METHOD
  //=========================================================================================================================================================================================================================--
  
  public void actionPerformed(ActionEvent event)  {         //action performed method (goes here when a button is pressed); most of the logic goes inside here because the buttons dictate the flow of execution
    String command = event.getActionCommand();        //storing the text of the button into a string variable
    
    if(command.equals("OK")){
      name1 = input.getText();                  //getting the name and storing it when the first button, OK, is pressed
      welcome.remove(okButton);
      welcome.remove(welcomelabel);            //removing unecessary labels
      
      instructions.setText(("<html> Hi " + "</br>"+name1 + ", which mode would you like to play? </html>"));     //changing the text of the label
      
      welcome.add(pvp);
      welcome.add(Box.createRigidArea(new Dimension(0,75)));          //adding the 2 buttons and formatting them 
      pvp.setAlignmentX(Component.CENTER_ALIGNMENT); 
      
      welcome.add(pvai);                                         
      pvai.setAlignmentX(Component.CENTER_ALIGNMENT); 
    }
    
    if(command.equals("Player vs Player")){        //if pvp button was clicked
      instructions.setText("<html> You chose Player vs Player! " +"</br>"+ name1 + ", which colour would you like your checker to be? </html>"); 
      mode =1;
      welcome.remove(pvp);
      welcome.remove(pvai);                      //removing the buttons from the screen and updating the user interface
      welcome.updateUI(); 
      addColor();
    }
    
    else if(command.equals("Player vs AI")){       //if pvai button was clicked
      instructions.setText("<html> You chose Player vs AI! " + "</br>"+name1 + ", which colour would you like your checker to be? </html>"); 
      mode = 2;
      welcome.remove(pvp);
      welcome.remove(pvai);                  
      welcome.updateUI();
      addColor();                            //calling the method which puts the color options on to the screen
    }
    
    
    //========================================================PVP BUTTONS==========================================================================
    //===================================================================================================================================
    if(command.equals("         1")){  //the extra spaces are because of the formatting of the columnnumbers
      columnnumber=1;
      command = "Enter";
    }
    if(command.equals("         2")){
      columnnumber=2;
      command = "Enter";
    }
    if(command.equals("         3")){
      columnnumber=3;
      command = "Enter";
    }
    if(command.equals("         4")){       //depending on which button they press, change the columnnumber. Then send it to the if statement that runs when command = entered
      columnnumber=4;
      command = "Enter";
    }
    if(command.equals("         5")){
      columnnumber=5;
      command = "Enter";
    }
    if(command.equals("         6")){
      columnnumber=6;
      command = "Enter";
    }
    if(command.equals("         7")){
      columnnumber=7;
      command = "Enter";
    }
    
    
    //======================================when player 1 makes their choice of the pvp mode ==================================================================
    if(command.equals("Black")&&choice == 1 &&mode ==1){             // determining which button was pressed and which player pressed it
      instructions.setText("Great choice! Now, what is Player 2's name?"); 
      color1 = Color.black;                                         //for player 1
      removeColor();
      welcome.updateUI();
      welcome.add(okayButton);
      input.setText("");
      okayButton.setAlignmentX(Component.CENTER_ALIGNMENT);     //centering the component 
    }
    
    if(command.equals("Red")&&choice == 1 &&mode ==1){                     //if the colour was pressed by the first player
      instructions.setText("Great choice! Player 2, what is your name?");
      color1 = Color.red;
      removeColor();     //removing the other colors from screen                                         //red
      welcome.updateUI();
      welcome.add(okayButton);
      input.setText("");             //clearing the text field
      okayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    if(command.equals("Yellow")&&choice == 1 &&mode ==1){   
      instructions.setText("Great choice! Player 2, what is your name?");
      color1 = Color.yellow;
      removeColor();                                              //yellow
      welcome.updateUI();
      welcome.add(okayButton);
      input.setText("");
      okayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    if(command.equals("Green")&&choice == 1 &&mode ==1){   
      instructions.setText("Great choice! Player 2, what is your name?");          //greem
      color1 = Color.green;
      removeColor(); 
      welcome.updateUI();
      welcome.add(okayButton);
      input.setText("");
      okayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    //======================================================================================================================================================
    //2nd users info in pvp mode
    
    if(command.equals("Okay")){          //if they press okay --> asks for the 2nd users info
      name2 = input.getText();
      instructions.setText("<html>Hello " + "</br>" +name2 + ", choose a colour for your checker. </html>");    //move on to the 2nd player options
      welcome.remove(input);                             //^^using html to print out new lines if someone's name is too long
      welcome.updateUI();
      
      welcome.add(black);
      black.setAlignmentX(Component.CENTER_ALIGNMENT);
      black.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
      welcome.add(red);
      red.setAlignmentX(Component.CENTER_ALIGNMENT);
      red.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      //readding all the color buttons again
      welcome.add(yellow);
      yellow.setAlignmentX(Component.CENTER_ALIGNMENT);
      yellow.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
      welcome.add(green);
      green.setAlignmentX(Component.CENTER_ALIGNMENT);
      green.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
      choice = 2;                      //showing that it is the 2nd user's turn now
      
      if(color1==Color.black){
        welcome.remove(okayButton);
        welcome.remove(black);
      }
      if(color1==Color.red){
        welcome.remove(okayButton);
        welcome.remove(red);                //depending on which colour was chosen by the first player, take it out of the options for the 2nd player
      }
      if(color1==Color.yellow){
        welcome.remove(okayButton);
        welcome.remove(yellow);
      }
      if(color1==Color.green){
        welcome.remove(okayButton);
        welcome.remove(green);
      }
    }  
    
    if(command.equals("Black")&&choice == 2&&mode==1){                                   //once player 2 picks their colour
      instructions.setText("Interesting choice! Let's begin the game!");
      color2 = Color.black;
      removeColor(); 
      welcome.updateUI();
      okayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      goesFirst();                   //method that determines who goes first
    }
    
    if(command.equals("Red")&&choice == 2&&mode==1){   
      instructions.setText("Interesting choice! Let's begin the game!");
      color2 = Color.red;
      removeColor();             //removing the unneccessary colour buttons
      welcome.updateUI();
      okayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      goesFirst();                             
    }
    
    if(command.equals("Yellow")&&choice == 2&&mode==1){   
      instructions.setText("Interesting choice! Let's begin the game!");
      color2 = Color.yellow;
      removeColor(); 
      welcome.updateUI();
      okayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      goesFirst();                                                 //method that determines who goes first
    }
    
    if(command.equals("Green")&&choice == 2&&mode==1){   
      instructions.setText("Interesting choice! Let's begin the game!");
      color2 = Color.green;
      removeColor(); 
      welcome.updateUI();
      okayButton.setAlignmentX(Component.CENTER_ALIGNMENT);        //aligning the buttons
      goesFirst();    
    }
    
    if(command.equals(name1)&&mode==1){    //if p1 goes first
      PVP(name1);                          //if they want name1 to go first, send it to the method with the correct parameter
    }
    
    else if(command.equals(name2)&&mode==1){   //if p2 goes first
      PVP(name2);                               //sending to PVP method which allows user to start game
    }
    
    else if(command.equals("Flip a coin")&&mode==1){
      
      randomnum = (int)(r.nextInt(2));
      
      if(randomnum == 0){              //using randomnum to simulate the flipping of a coin
        PVP(name1);
      }
      else{                            //randomly decides who goes first
        PVP(name2);
      }
    }
    
    
    //logic of the connect 4 game goes here --> when they press enter==============================================================---------============================
    //when they click a button
    if(command.equals("Enter")&&whoseTurn==1&&canGo==true){                    //WHEN PLAYER 1 GOES
      
      if(validInput(columnnumber)){
        erase = 0;
        changeBoard(columnnumber, whoseTurn, color1, color2);   //sending the columnnumber picked to the changeBoard method, which changes the colour of things on the screen
        input.setText("");
        whoseTurn=2;
        
        if(fullBoard()){
          whoseTurn = 0;                     //checking if the result of the game is a draw
          repeatGui(""); 
        }
        
        numTurns++;         //accumulating the total amount of turns
        
        if(hasCheckingCondition(4, color1)){    //if they win the game; this is checked by sending the current state of the board to the hasCheckingCondition
          userWins++;         //incrementing UserWin counter
          whoseTurn = 0;       //make it no one's turn now because the game is over
          GameWon = true;     //making it so that no one can go anymore because the game is over
          canGo = false;
          playSound();            //plays a winning tune if the user ever wins
          repeatGui(name1);      //this method gives the user the option to restart the game
        }
        
        else{        //if player 1 has not won the game
          
          if(fullBoard()){     //if the board is full
            repeatGui(name1);   //if they draw the game, ask if they want to repeat the GUI
            
          }
          else{
            if(!name2.equals("AI.BOT")){    //else, then it is player 2's turn. If it isnt the ai, then outprint the below
              instructions.setText("<html>"+name2 + "<html>'s turn. <br><br> Please click the column number you wish to place your checker in. </html>");
            }
          }
        }
        if(mode==2){
          whoseTurn=AINumber; //if the mode is 2 that means they chose, PvAI, so we make whoseTurn to AINumber, which is 3 or 4 depending on hard or easy mode
        }
      }
      else{
        instructions.setText("Please click a valid column number."); //if that column is full, they must reclick another valid column
      }
    }
    
    else if(command.equals("Enter")&&whoseTurn ==2&&canGo==true){           //WHEN PLAYER 2 GOES
      
      if(validInput(columnnumber)){              //same if statements as above, just that player 2 is now playing
        erase = 0;
        changeBoard(columnnumber, whoseTurn, color1, color2);      //calling the changeBoard method which changes the coour of the index based on the user and his pick
        input.setText("");
        whoseTurn=1;             //this will make this section and the above 'if' section alternate
        numTurns++;         //accumulating the total amount of turns
        
        if(hasCheckingCondition(4, color2)){
          GameWon = true;
          canGo = false;
          whoseTurn = 0;
          playSound();
          repeatGui(name2);      //repeating the GUI
        }
        else{
          
          if(fullBoard()){                      //look at comments above for reference
            repeatGui(name2);
          }
          
          else{
            instructions.setText("<html>"+name1 + "<html>'s turn. <br><br> Please click the column number you wish to place your checker in. </html>");
          }
        }
      }
      else{
        instructions.setText("Please click a valid column.");
      }
    }
    
    //for the easy ai======================================================================================
    if(whoseTurn == 3 && command.equals("Enter") &&GameWon == false){                         //WHEN THE EASY AI GOES
      
      instructions.setText("<html>Please click the column number"+"</br>"+" you wish to place your checker in. </html>");  //using html once again
      
      AIPlayed = false;               //boolean that keeps track of if the AI has made a move or not
      for(int i = 1; i<=7; i++){
        
        if(fullBoard()){
          repeatGui("");                //giving user option to play again if the result is a draw
          break;
        }
        
        if(validInput(i)){
          changeBoard(i, AINumber, color1, color2);                 //put a checker in every possible column
          if(hasCheckingCondition(4, color2)){                      //then check if any of those checker can win the game
            GameWon = true;
            AIPlayed = true;             //a  tracker of if the AI has played or not
            whoseTurn = 0;
            canGo = false;
            repeatGui("AI");    //send this to the method saying that the AI won the game. The method will then ask the user if they would like to play again
            break;
          }
          
          else if(hasCheckingCondition(4, color2)==false){         //if it cant win the game
            erase = i;                                //changing all the checkers placed back to white
            changeBoard(erase, AINumber, color1, color2);   //this method can either add or erase checkers; in this case, erase
            erase = 0;
            
            if(fullBoard()){
              repeatGui("");          //if the result of the game is a tie, break and send it to the repeatGUI method
              break;
            }
            
            continue;
          }
        }
      }
      
      //if the opponent(user) can win
      if(AIPlayed ==false){                  //if the AI still hasnt made a move
        for(int i = 1; i<=7; i++){
          if(validInput(i)){
            erase = 0;                          //resetting this variable (*IMPORTANT*)
            changeBoard(i, 1, color1, color2);       //place a checker of the opponents colour in every slot
            if(hasCheckingCondition(4, color1)){     //if the opponent can win, then place a checker of the AI's colour to block it
              erase = i;
              changeBoard(erase, 1, color1, color2);  //change back the checker 
              erase = 0;
              changeBoard(i, 3, color1, color2);     //put a checker of the AI's colour to block it
              AIPlayed = true;
              numTurns++;
              whoseTurn = 1;      //giving the turn back to the user and breaking out of the loop once it has blocked the user
              break;
            }
            
            else if(hasCheckingCondition(4, color1)==false){    //changing the colors back to what it was before
              erase = i;
              changeBoard(erase, 1, color1, color2);
              erase = 0;
              continue;
            }
          }
        }
      }
      
      erase = 0; //must keep resetting this or else the changeBoard method will think it has to erase the checker instead of placing it
      
      if(AIPlayed == false){
        randomnum = (int)(r.nextInt(7)+1);
        
        while(validInput(randomnum)==false){            //if the AI cant win or block the opponent, it chooses a random slot and places it there
          randomnum = (int)(r.nextInt(7)+1);
        }
        
        changeBoard(randomnum, AINumber, color1,  color2);    //using a randomnum as long as it is a valid column
        numTurns++;
        whoseTurn = 1;
        
        if(fullBoard()){
          whoseTurn = 0;
          repeatGui(""); 
        }
        
      }
    }//end of easy ai========================================================
    
    
    //=========================//FOR THE HARD AI============================//
    
    if(whoseTurn == 4 &&command.equals("Enter")&&GameWon==false){                     //start of Hard AI
      //need to check the same things as the EasyAI at first - if the AI can win or if the USER can win
      
      instructions.setText("<html>Please click the column number"+"</br>"+" you wish to place your checker in. </html>");  //using html once again
      
      AIPlayed = false;               //boolean that keeps track of if the AI has made a move or not
      canPlace = true;               //boolean that keeps track if the AI should place a checker there
      
      for(int a = 0; a<7; a++){
        shouldNotPlace[a]=-1;                    //resetting the array each time so that it can accumulate the moves properly
      }
      
      //CHECKING TO SEE IF THE AI CAN WIN====================================
      for(int i = 1; i<=7; i++){
        if(fullBoard()){
          repeatGui("");                //if the result is a draw
          break;
        }
        
        if(validInput(i)){
          changeBoard(i, AINumber, color1, color2);                 //put a checker in every possible column
          if(hasCheckingCondition(4, color2)){                      //then check if any of those checker can win the game
            GameWon = true;
            AIPlayed = true;
            whoseTurn = 0;
            canGo = false;
            repeatGui("AI");
            playSound();
            break;
          }
          else if(hasCheckingCondition(4, color2)==false){         //if it cant win the game
            erase = i;                                //changing all the checkers placed back to white
            changeBoard(erase, AINumber, color1, color2);
            erase = 0;
            continue;
          }
        }
      }
      
      //CHECKING IF THE USER CAN WIN THE GAME, IF SO THEN BLOCK
      if(AIPlayed ==false){                  //if the AI still hasnt made a move
        for(int i = 1; i<=7; i++){
          if(validInput(i)){  //this must be a valid column to hypothetically place a checker in
            erase = 0;
            changeBoard(i, 1, color1, color2);       //place a checker of the opponents colour in every slot
            if(hasCheckingCondition(4, color1)){     //if the opponent can win, then place a checker of the AI's colour to block it
              erase = i;
              changeBoard(erase, 1, color1, color2); //erasing the checker just placed that was used to see if black could win
              erase = 0;
              changeBoard(i, AINumber, color1, color2);//changing the colour of it to magenta to block the opponent
              AIPlayed = true;
              numTurns++;
              if(fullBoard()){
                whoseTurn = 0;
                repeatGui(""); 
              }
              whoseTurn = 1;      //giving the turn back to the user and breaking out of the loop once it has blocked the user
              break;
            }
            
            else if(hasCheckingCondition(4, color1)==false){    //changing the colors back to what it was before
              erase = i;
              changeBoard(erase, 1, color1, color2);   //removing the checker placed at the beginning to its original game state
              erase = 0;
              continue;
            }
          }
        }
      }
      
      
      //CHECKING IF THE USER CAN GET A DOUBLE TRAP ON THE BOTTOM ROW (hard coding it just for the bottom row)=============
      
      if(AIPlayed == false&&trapBlocked == false){                          //only needs to run once
        if(arrayCircles[5][3].getColor()==color1){    //if they have a checker in the middle column
          if(arrayCircles[5][4].getColor()==color1){    //checker to the right
            if(arrayCircles[5][2].getColor()==Color.white){
              erase = 0;
              changeBoard(3, AINumber, color1, color2);    //changing the colour of it to magenta to block the opponent from a double on the bottom
              AIPlayed = true;
              numTurns++;
              whoseTurn = 1;      //giving the turn back to the user and breaking out of the loop once it has blocked the user
              trapBlocked = true;
            }
          }
          if(arrayCircles[5][2].getColor()==color1){    //checker to the left
            if(arrayCircles[5][4].getColor()==Color.white){
              erase = 0;
              changeBoard(5, AINumber, color1, color2);    //changing the colour of it to magenta to block the opponent from a double on the bottom
              AIPlayed = true;
              numTurns++;
              whoseTurn = 1;      //giving the turn back to the user and breaking out of the loop once it has blocked the user
              trapBlocked = true;
            }
          }
        }
      }
      
      //CHECKING WHICH MOVES THE AI SHOULD NOT PLACE THAT WOULD LEAD TO AN IMMEDIATE WIN FOR THE USER============================
      
      if(AIPlayed==false){
        counter = 0;
        for(int i = 1; i<=7; i++){
          if(validInput(i)){              //making sure that column is valid
            erase = 0;
            changeBoard(i, AINumber, color1, color2);           //simulating every possible move of the AI
            for(int j = 1; j<=7; j++){
              if(validInput(j)){
                erase = 0;
                changeBoard(j, 1, color1, color2);              //then seeing if the user can win after any one of those moves
                if(hasCheckingCondition(4, color1)==true){      
                  shouldNotPlace[counter]=i;                //if so, store it in the array so the AI knows to not place it there ever in the future
                  counter++;
                }
                erase = j;
                changeBoard(erase, 1, color1, color2); //erasing previous moves
              }
            }
            erase = i;
            changeBoard(erase, AINumber, color1, color2);     //erasing previous moves
          }
        }
      }
      
     //SETTING UP THE DOUBLE TRAP
       if(AIPlayed==false){        //this checks if the AI can make a move to double trap the user
        //looks ahead to the future state of the game and determines which column is best to win
        canPlace = true;
        mustPlace = -1;
        for(int i = 1; i<=7; i++){
          if(validInput(i)&&canPlace==true){ 
            erase = 0;                            //adding a move
            changeBoard(i, AINumber, color1, color2);           //simulating every possible move of the AI
            for(int j = 1; j<=7; j++){
              if(validInput(j)&&canPlace == true){
                erase = 0;                             //adding another AI move
                changeBoard(j, AINumber, color1, color2);              //then seeing if the AI can win after any one of those moves
                if(hasCheckingCondition(4, color2)==true){
                  erase = j;
                  changeBoard(erase, AINumber, color1, color2);     //erasing the magenta one for the black one
                  erase=0;
                  changeBoard(j, 1, color1, color2);   //placing a black checker here for the future game board possibilities after this move
                  
                  for(int z = 1; z<=7; z++){
                    if(validInput(z)&&canPlace==true){
                      erase = 0;
                      changeBoard(z, AINumber, color1, color2);    //adding a move
                      if(hasCheckingCondition(4, color2)==true){//that means they can get a double win because the user blocked one win, but the AI can still make another
                        mustPlace = i;             //the move that can win the game by setting up a double win for the AI
                        for(int x =0; x<7; x++){
                          if(i==shouldNotPlace[i]){  //if mustPlace equals a immediate losing move, continue the loop to find the next one which results in a valid move
                            canPlace = true;
                            continue;
                          }
                        }
                        canPlace = false; //putting it here so that the loop doesnt run again after its found, but the current iteration finishes
                      }
                      erase = z;
                      changeBoard(erase, AINumber, color1, color2);  //subtracting the checkers
                    }
                  }
                  erase = j;                                //erasing if the USER placed something
                  changeBoard(erase, 1, color1, color2);
                }
                else if(hasCheckingCondition(4, color1)==false){
                  erase = j;
                  changeBoard(erase, AINumber, color1, color2);  //erasing the moves just placed by the AI
                }
              }
            }
            erase = i;
            changeBoard(erase, AINumber, color1, color2);    //erasing the moves just placed by the AI
          }
        }
        
        if(mustPlace>-1){
          canPlace = true;
          for(int i = 0; i<7; i++){
            if(mustPlace == shouldNotPlace[i]){      //making sure that this projected move is not gonna lead immediately to a loss
              canPlace = false;
            }
          }
          
          if(canPlace==true){
            erase=0;
            changeBoard(mustPlace, AINumber, color1, color2);   //placing the move
            AIPlayed = true;
            numTurns++;
            if(fullBoard()){
              whoseTurn = 0;
              repeatGui(""); 
            }
            canGo = true;  //making sure that it is the users turn now, and the AI cannot play anymore until the next turn
            whoseTurn = 1;
          }
        }
      }
      
      //BLOCKING A DOUBLE TRAP
      //it works by considering all opssible moves the opponent can make; then, it will again simulate moves that the user can make (2 moves in a row for the user)
      //then, if the user can win within two moves, block its second move by replacing the 2nd move with a magenta colour; now, simulate all possible moves of black
      //again, and see if the user can win. If so, then that means the user has a double trap potential depending on the AI's move THIS turn
      //at the end of this part of code, the AI should block the user from initially making the connect 4, blocking the double
      
      if(AIPlayed==false){        //this blocks the opponent from forcing the AI into making a move; essentially stops the double win before it happens
        //looks ahead to the future state of the game and determines which column is best to block the opponent
        canPlace = true;
        mustPlace = -1;
        for(int i = 1; i<=7; i++){
          if(validInput(i)&&canPlace==true){ 
            erase = 0;                            //adding a move
            changeBoard(i, 1, color1, color2);           //simulating every possible move of the user
            for(int j = 1; j<=7; j++){
              if(validInput(j)&&canPlace == true){
                erase = 0;                             //adding another user move
                changeBoard(j, 1, color1, color2);              //then seeing if the user can win after any one of those moves
                if(hasCheckingCondition(4, color1)==true){
                  erase = j;
                  changeBoard(erase, 1, color1, color2);     //erasing the black one for a magenta one
                  erase=0;
                  changeBoard(j, AINumber, color1, color2);   //placing a magenta checker here for the future game board possibilities
                  
                  for(int z = 1; z<=7; z++){
                    if(validInput(z)&&canPlace==true){
                      erase = 0;
                      changeBoard(z, 1, color1, color2);    //adding
                      if(hasCheckingCondition(4, color1)==true){//that means they can get a double win soon
                        mustPlace = i;                //the move that must be placed to block a double move is i
                        for(int x =0; x<7; x++){
                          if(i==shouldNotPlace[i]){  //if mustPlace equals a immediate losing move, continue the loop to find the next one which results in a valid move
                            canPlace = true;
                            continue;
                          }
                        }
                        canPlace = false; //putting it here so that the loop doesnt run again after its found, but the current iteration finishes
                      }
                      erase = z;
                      changeBoard(erase, 1, color1, color2);  //subtracting the checker
                    }
                  }
                  erase = j;                                //erasing if the AI placed something
                  changeBoard(erase, AINumber, color1, color2);
                }
                else if(hasCheckingCondition(4, color1)==false){
                  erase = j;
                  changeBoard(erase, 1, color1, color2);  //erasing the moves just placed
                }
              }
            }
            erase = i;
            changeBoard(erase, 1, color1, color2);    //erasing the moves just placed
          }
        }
        
        if(mustPlace>-1){
          canPlace = true;
          for(int i = 0; i<7; i++){
            if(mustPlace == shouldNotPlace[i]){      //making sure that this projected move is not gonna lead immediately to a loss
              canPlace = false;
            }
          }
          
          if(canPlace==true){
            erase=0;
            changeBoard(mustPlace, AINumber, color1, color2);
            AIPlayed = true;
            numTurns++;
            if(fullBoard()){
              whoseTurn = 0;
              repeatGui(""); 
            }
            canGo = true;  //making sure that it is the users turn now, and the AI cannot play anymore until the next turn
            whoseTurn = 1;
          }
        }
      }
      
      if(AIPlayed==false){       //if the AI still hasnt made a move because none of the above is possible, then place in the center or middle columns if possible
        if(validInput(4)==true){
          canPlace=true;
          for(int i = 0; i<7; i++){
            if(shouldNotPlace[i]==4){
              canPlace=false;            //if column 4 ever results in a losing move, dont place it there
              break;
            }
          }
          if(canPlace==true){
            if(arrayCircles[1][3].getColor()==Color.white){   //the AI shouldnt place it in the top-top row in the 4th column all the time
            erase = 0;
            changeBoard(4, AINumber, color1, color2);        //for the center or middle column
            numTurns++;        
            if(fullBoard()){
              whoseTurn = 0;                     //checking if the result of the game is a draw
              repeatGui(""); 
            }
            whoseTurn=1;
            AIPlayed = true;  //making sure that it is the users turn now, and the AI cannot play anymore until the next turn
            canGo=true;
            }
          }
        }
      }
      
      if(AIPlayed==false){                   //this statement is so that the AI places a checker in the 3rd or 5th column provided that it isnt an immediate losing move
        outer:   for(int i = 2; i<5; i++){          //if the AI still hasnt played, put it in a position at the bottom of the column
          for(int a = 0; a<7; a++){
            if(i+1==shouldNotPlace[a]){             //checking if the columnnumber equals to any part of the shouldNotPlace array
              canPlace = false;
              break outer;                     //breaking the outer loop for efficiency
            }
          }
        }
        if(canPlace==true&&AIPlayed == false){   // 
          randomnum = (int)(r.nextInt(2));
          if(randomnum==0){
            if(validInput(3)){       //making all the possible moves are at least valid first
              erase=0;
              changeBoard(3, AINumber, color1, color2);        //put it at the bottom of the more centered rows
              numTurns++; 
              if(fullBoard()){
                whoseTurn = 0;                     //checking if the result of the game is a draw
                repeatGui(""); 
              }
              whoseTurn=1;
              AIPlayed = true;  //making sure that it is the users turn now, and the AI cannot play anymore until the next turn
              canGo=true;
            }
          }
          else if (randomnum==1){
            if(validInput(5)){
              erase=0;
              changeBoard(5, AINumber, color1, color2);        //put it at the bottom of the more centered rows
              numTurns++; 
              if(fullBoard()){
                whoseTurn = 0;                     //checking if the result of the game is a draw
                repeatGui(""); 
              }
              whoseTurn=1;
              AIPlayed = true;  //making sure that it is the users turn now, and the AI cannot play anymore until the next turn
              canGo=true;
            }
          }
        }
      }
      
      if(AIPlayed == false){    //if all of the above is not possible, then make a random choice that will not lose the game the next turn
        do{
          randomnum = (int)(r.nextInt(7)+1);
          
          timesChecked++;
          
          if(timesChecked>1000){         //if it runs 1000 times, meaning that there is only one spot to play which will result in a loss
            for(int i = 1; i<=7; i++){
              if(validInput(i)== true){
                randomnum = i;            //setting the random number to be the only column left available
              }
            }
            break;                  //breaking out of the loop if there is no other choice for a checker
          }
          
          for(int i = 0; i<7; i++){
            if(shouldNotPlace[i]==randomnum){
              randomnum=100;                    //causing the loop to run again
              break;
            }
          }
          
        }while(validInput(randomnum)==false);         //if the AI cant win or block the opponent, it chooses a random slot and places it there
        
        erase = 0;
        changeBoard(randomnum, AINumber, color1,  color2);  //changing the board here and the variables below then make it the users turn
        numTurns++;
        if(fullBoard()){
          whoseTurn = 0;                     //checking if the result of the game is a draw
          repeatGui(""); 
        }
        whoseTurn = 1;
        AIPlayed = true;          //making sure that it is the users turn now, and the AI cannot play anymore until the next turn
        canGo=true;
      }
      
    }//END OF HARD AI METHOD=====================
    
    ///========================================================//////PVAI BUTTONS///////=======================================================================
    if(command.equals("Black")&&mode==2){   
      instructions.setText("<html>Interesting choice!"+"</br>" +" Would you like to play vs the Easy AI or the Hard AI?");
      color1 = Color.black;                           
      removeColor(); 
      addAiButtons();                      
    }
    
    if(command.equals("Red")&&mode==2){   
      instructions.setText("<html>Interesting choice!"+"</br>" + " Would you like to play vs the Easy AI or the Hard AI?");
      color1 = Color.red;
      removeColor(); 
      addAiButtons();                                                         //depending on which colour they press
    }
    
    if(command.equals("Yellow")&&mode==2){   
      instructions.setText("<html>Interesting choice!"+"</br>" +" Would you like to play vs the Easy AI or the Hard AI?");
      color1 = Color.yellow;
      removeColor(); 
      addAiButtons();                      
    }
    
    if(command.equals("Green")&&mode==2){    
      instructions.setText("<html>Interesting choice!"+"</br>" +" Would you like to play vs the Easy AI or the Hard AI?");
      color1 = Color.green;
      removeColor(); 
      addAiButtons();                   
    }
    
    if(command.equals("Easy AI")){
      choseEasyAI = true;
      welcome.remove(easyAI);         //sending it to the appropriate methods after they pressed the respective buttons
      welcome.remove(hardAI);
      goesFirst();
    }
    else if(command.equals("Hard AI")){  //if they choose the hardAI
      choseEasyAI = false;               
      welcome.remove(easyAI);
      welcome.remove(hardAI);
      goesFirst();
    }
    
    if(mode==2&&command.equals(name1)){
      PVAI(name1);                          //if they want name1 to go first, send it to the method with the correct parameter
    }
    
    else if(command.equals(name2)&&mode==2){   //if name 2 wants to go first
      PVAI(name2);
    }
    
    else if(command.equals("Flip a coin")&&mode==2){ //if they desire to flip a coin to determine who goes first
      
      randomnum = (int)(r.nextInt(2));
      
      if(randomnum == 0){          //using randomnum to simulate the flipping of a coin
        PVAI(name1);
      }
      else{                            //randomly decides who goes first
        PVAI(name2);
      }
    }
    
    //if they would like to play again; the buttons appear at the end
    if(command.equals("Yes")){
      playAgain = true;   //if they want to play again
      repeatGui("");     //repeating the GUI method
    }
    else if(command.equals("No")){
      this.dispose();            //getting rid of the frame; essentially closing it
    }
  }
//END OF THE ACTION LISTENER METHOD================================================================
  
  
//=========================================================================================================================================================================================================================--------------
//this method adds the color buttons to the screen
  
  public static void addColor(){                 
    
    welcome.add(black);
    black.setAlignmentX(Component.CENTER_ALIGNMENT);
    black.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
    welcome.add(red);
    red.setAlignmentX(Component.CENTER_ALIGNMENT);
    red.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));                                //adding some buttons, formatting them, and aligning them
    
    welcome.add(yellow);
    yellow.setAlignmentX(Component.CENTER_ALIGNMENT);
    yellow.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
    welcome.add(green);
    green.setAlignmentX(Component.CENTER_ALIGNMENT);
    green.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
  }
  
  public static void removeColor(){
    welcome.remove(black);                                //method that removes all the colour buttons
    welcome.remove(red);
    welcome.remove(yellow);
    welcome.remove(green);
  }
  
  public static void addAiButtons(){
    welcome.add(easyAI);
    welcome.add(hardAI);                                 //method that adds the AI buttons to the screen; used to increase readability of code
    welcome.updateUI();
    easyAI.setAlignmentX(Component.CENTER_ALIGNMENT);
    hardAI.setAlignmentX(Component.CENTER_ALIGNMENT); 
  }
//=========================================================================================================================================================
//this method helps determine who goes first --- it outputs the buttons player 1, player 2 or flip a coin to determine the first player
  
  public void goesFirst(){
    instructions.setText("Now, who would you like to go first?");
    
    welcome.remove(easyAI);
    welcome.remove(hardAI);
    welcome.remove(p1first);                    //removing necessary buttons
    welcome.remove(p2first);
    welcome.remove(hORt);
    
    welcome.updateUI();
    
    p1first.setText(name1);                                //setting the text to the buttons
    p2first.setText(name2);
    
    welcome.add(p1first);
    p1first.setAlignmentX(Component.CENTER_ALIGNMENT);                         //non-static method adds the buttons that determine which player goes first
    
    welcome.add(p2first); 
    p2first.setAlignmentX(Component.CENTER_ALIGNMENT);               //formatting the buttons
    
    welcome.add(hORt);
    hORt.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    p1first.addActionListener(this);
    p2first.addActionListener(this);                            //adding the actionlisteners AFTER the names are stored
    hORt.addActionListener(this);
  }  
  
//=========================================================================================================================================================
//this method outputs the enter button so the player can input their column choice. It also outputs whoever the first player is
  
  public static void PVP(String name){           //for player vs player mode
    
    welcome.remove(p1first); 
    welcome.remove(p2first);
    welcome.remove(hORt);                                 //reformatting
    welcome.updateUI();
    
    if(name.equals(name1)&&numTurns==0){      //user 1 going first
      instructions.setText("<html>" + name1 + "<html> you are going first! <br><br> Please click the column number you wish to place your checker in. </html>");
      whoseTurn = 1;
      welcome.remove(input);
      welcome.updateUI();
      canGo = true;            //now, when the user clicks the column button, a checker may actually be placed
    }
    if(name.equals(name2)&&numTurns==0){        //user 2 going first
      instructions.setText("<html>" + name2 + "<html> you are going first! <br><br> Please click the column number you wish to place your checker in. </html>");
      whoseTurn = 2;
      welcome.remove(input);
      welcome.updateUI();
      canGo = true;
    }
  }
  
//=========================================================================================================================================================
//this method makes sure that the user inputs a valid columnnumber
  
  public static boolean validInput(int columnnumber){  //determine if column chosen is valid, and if user has won or not  
    if(columnnumber>=0&&columnnumber<8){
      for(int i = 0; i<=5; i++){
        if(arrayCircles[i][columnnumber-1].getColor()==Color.white){  //if there is still open space left in the column, return true
          return true;
        }
      }
    }
    return false;
  }//end of validInput method
  
//==================================================================================================================================
//method determines if the outcome of the match is a draw 
  public static boolean fullBoard(){
    if(numTurns==42){
      return true;          //determines if the outcome of the game is a draw
    }
    return false;
  }
  
//=========================================================================================================================================================
//this method changes the colour of the circles on the board based on the correct colour that the user picked
  
  public void changeBoard(int columnnumber, int whoseTurn, Color color1, Color color2){             //changes the colour of the circles on the board
    
    if(whoseTurn==1){  
      if(erase>0){                 //this if statement runs if a checker needs to be erased; (because AI was checking future game states)
        for(int i = 0; i<=5; i++){   //going from top to bottom to change the last entered checker
          if(arrayCircles[i][erase-1].getColor()==color1){
            arrayCircles[i][erase-1].setColor(Color.white);        //removing the checker that was previously placed
            repaint();
            break;
          }
        }
      }
      else{
        for(int i = 5; i>=0; i--){
          if(arrayCircles[i][columnnumber-1].getColor()==Color.white){                 //starting from row 5 (the bottom one) see if any of those within the column is null
            arrayCircles[i][columnnumber-1].setColor(color1);               //if so, set that value to the colour of the player who inputted that columnnumber
            repaint();                                               //repainting the board so that it updates and is visible to viewers
            break;                                //break once the colour has been changed and a move has been made
          }
        }
      }
    }
    
    else if(whoseTurn==2){
      for(int i = 5; i>=0; i--){
        if(arrayCircles[i][columnnumber-1].getColor()==Color.white){   //if there is an open space, set its colour to the desired colour 
          arrayCircles[i][columnnumber-1].setColor(color2);
          repaint();
          break;
        }
      }
    }
    
    else if(whoseTurn==3||whoseTurn==4){      //either the easyAI or the hardAI
      
      
      if(erase>0){           //this if statement erases the last placed coloured checker
        for(int i = 0; i<=5; i++){   //going from top to bottom to change the last entered checker to white
          if(arrayCircles[i][erase-1].getColor()==Color.magenta){
            arrayCircles[i][erase-1].setColor(Color.white);      //sets it back to white
            repaint();               //repaints and breaks
            break;
          }
        }
      }
      else{
        for(int i = 5; i>=0; i--){                     //setting the colour of the column to magenta (for the AI)
          if(arrayCircles[i][columnnumber-1].getColor()==Color.white){
            arrayCircles[i][columnnumber-1].setColor(color2);
            repaint();
            break;
          }
        }
      } 
    }
  }//end of changeboard method
  
//=========================================================================================================================================================
//this method checks if on the current board, there is a match of the condition stated by in the parameters. Ex. condition = 4; checks if there are 4 checkers in a 
//row anywhere
  
  public static boolean hasCheckingCondition(int condition, Color colorCheck){    //maybe instead of checking for all each time, just check based on the disc placed
    int rownum;
    int columnnum;
    boolean found = false;
    int inARow = 0;
    
    if(numTurns>=(condition+(condition-1))-2){    //can only have connect 4 if there are a total of 7 total moves at the least
      //checking vertically-------------
      
      for(int i = 0; i<7; i++){        //for each column
        
        if(arrayCircles[5-(7-condition)][i].getColor()==Color.white){     //say if in the column, there are not even 4 checkers. This way, the checking algorithm is much more efficient
          continue;    //checks the next column now
        }
        
        inARow=0;                      //resseting inARow to 0 when the column is done seraching
        for(int j = 5; j>=0; j--){    //for each row; 0 check going bottom to top
          
          if(arrayCircles[j][i].getColor()==colorCheck){
            inARow++;
            
            if(inARow == condition){        
              found = true;                         //returning here because the condition has been found
              return found;
            }
            if(j>0&&arrayCircles[j-1][i].getColor()!=colorCheck){    //if the subsequent chip is not of the same color
              inARow = 0;                                          //reset the inARow counter
            }
          }
        }
      }      
      
      //checking horizontally------
      for(int i = 5; i>=0; i--){ //for each row; going from bottom-top           
        inARow = 0;                   //resetting inARow to 0 when the row is finished
        
        for(int j = 0; j<7; j++){   //for each column - check going across
          
          if(arrayCircles[i][j].getColor()==colorCheck){
            inARow++;
            if(inARow == condition){
              found = true;                           //setting found to true because the condition has been met
              return found;
            }
            if(j<6&&arrayCircles[i][j+1].getColor()!=colorCheck){        //if the next one is not the colour, reset the counter
              inARow=0; 
            }
          }
        }
      }
      
      //checking diagonally now --starting from the bottom left and searching to top right
      for(int i = 0; i<7; i++){      //for each column
        for(int j = 5; j>=0; j--){   //for each row going bottom to top
          
          rownum = j;          //keeping these variables as j and i but manipulating them when checking
          columnnum = i;            //[rownum][columnnum] = the starting chip
          
          if(arrayCircles[j][i].getColor()==colorCheck){   //making sure there is indeed a checker there
            
            inARow = 0;               //resestting in a row counter each time we switch the starting chip
            
            for(int z = 0; z<6; z++){ //max diagonal is 6 long
              
              if(rownum>=0&&rownum<=5&&columnnum>=0&&columnnum<=6){
                
                if(arrayCircles[rownum][columnnum].getColor() == colorCheck){
                  inARow++;
                  
                  rownum--;       //moving one up and one right whenever inARow is incremented  
                  columnnum++;
                  
                  if(inARow == condition){      //if the condition is met, then return that the player won
                    found = true;
                    return found;
                  }
                }
                
                else{      //if that  chip is not equal to the color of the player
                  inARow = 0;
                  break; //break out of loop
                }
              }
            }
          }
        }
      }
      //checking diagonal still but from bottom right to top left; same code as above but different rownum and columnnum
      
      for(int i = 6; i>=0; i--){      //for each column
        for(int j = 5; j>=0; j--){   //for each row going bottom to top
          
          rownum = j;          //keeping these variables as j and i but manipulating them when checking
          columnnum = i;            //[rownum][columnnum] = the starting chip
          
          if(arrayCircles[j][i].getColor()==colorCheck){   //making sure there is indeed a checker there of the right color
            
            inARow = 0;               //resetting in a row counter each time we switch the starting chip
            
            for(int z = 0; z<6; z++){ //max diagonal is 6 long
              
              if(rownum>=0&&rownum<=5&&columnnum>=0&&columnnum<=6){
                
                if(arrayCircles[rownum][columnnum].getColor() == colorCheck){  //if the colour matches that of the row and column
                  inARow++;     //incrementing the inARow counter
                  
                  rownum--;       //moving one up and one right whenever inARow is incremented  
                  columnnum--;
                  
                  if(inARow == condition){   //if condition is met, then return true because person has won
                    found = true;
                    return found;
                  }
                }
                
                else{      //if that chip is not the same colour
                  // rownum--;       //moving one up and one right        //maybe keep this not sure
                  //  columnnum--;
                  //   if(rownum>=0&&rownum<=5&&columnnum>=0&&columnnum<=6&&arrayCircles[rownum][columnnum].getColor()!=colorCheck){   //checking the next one, if so then break out of loop
                  inARow = 0;
                  break; //break out of loop
                  //  }
                }
              }
            }
          }
        }
      }
    }
    return found;      //if none of the above returns, it will return found, which is false      
  }//end of method
  
//AI METHOD-====================================================================================================================
  public void PVAI(String name){                                    //for player vs ai
    
    welcome.remove(p1first);
    welcome.remove(p2first);          //removing all necessary buttons
    welcome.remove(hORt);
    welcome.updateUI();
    
    if(choseEasyAI == true){     //for easy ai
      if(name.equals(name1)&&numTurns==0){
        instructions.setText("<html>" + name1 + "<html> you are going first! <br><br> Please click the column number you wish to place your checker in. </html>");
        whoseTurn = 1;                   //when the user goes first
        welcome.remove(input);
        welcome.updateUI();
        canGo = true;
      }
      else if(numTurns==0){
        changeBoard(4, 3, color1, color2);      //if the AI were to go first, set its checker in the middle (because thats always the best move)
        welcome.remove(input);
        welcome.updateUI();
        whoseTurn = 1;                       //AI's turn
        canGo = true;
        instructions.setText("<html>Please click the column number"+"</br>"+" you wish to place your checker in. </html>");
      }
    }
    //FOR THE HARD AI
    else{
      AINumber = 4;                                //if the user chose HARD AI, and the user is going first
      if(name.equals(name1)&&numTurns==0){
        instructions.setText("<html>" + name1 + "<html> you are going first! <br><br> Please click the column number you wish to place your checker in. </html>");
        whoseTurn = 1;
        welcome.remove(input);
        welcome.updateUI();
        canGo = true;
      }
      else{
        changeBoard(4,3,color1,color2);
        welcome.remove(input);
        welcome.updateUI();
        whoseTurn = 1;                       //users turn
        canGo = true;
        instructions.setText("<html>Please click the column number"+"</br>"+" you wish to place your checker in. </html>");
      }
    }
  }
//==================================method that allows the user to replay the game if they wish to do so============================================
  public void repeatGui(String name){
    winCounter.setText("User Win Counter: "+userWins);
    
    if(playAgain==false){       //if they don't want to play again
      if(name.equals("AI")&&fullBoard()==false){
        instructions.setText("<html> The AI has won the game. "+"</br> Better luck next time. </br> Would you like to play again? </html>");
      }
      else if(fullBoard()==true){
        instructions.setText("<html>Wow!, It's a tie! "+"</br> Would you like to play again?");
      }
      else{
        instructions.setText("<html>Congratulations! "+"</br>" +name+ ", you have won the game!</br> Would you like to play again? </html>");
      }
      
      welcome.add(again);
      welcome.add(notAgain);
      again.setAlignmentX(Component.CENTER_ALIGNMENT);
      notAgain.setAlignmentX(Component.CENTER_ALIGNMENT);       //adding the buttons that let the user decide whtehr to play again
      welcome.remove(input);
    }
    
    if(playAgain==true){                //if they want to play again
      super.dispose();
      name1="";                          
      name2 = "AI";         
      choice = 1;                 
      mode = 1;                                  //resetting all necessary variables and restarting the GUI
      whoseTurn = 1;          
      numTurns = 0;        
      canGo = false;             
      randomnum = -1;
      AINumber = 3;           
      erase = 0; 
      color2=Color.magenta;
      AIPlayed = false;        
      GameWon = false;
      playAgain = false;
      canPlace = true;
      trapBlocked = false;
      mustPlace = -1;
      
      for(int i = 0; i<7; i++){               //resetting everything so that the next time the GUI runs, things are not messed up
        shouldNotPlace[i] = -1;
      }
      
      okButton.removeActionListener(this);              //removing all the actionlisteners becauase i dont want to have two action listeners when the gui is run again
      pvp.removeActionListener(this);
      pvai.removeActionListener(this);
      black.removeActionListener(this);
      red.removeActionListener(this);
      yellow.removeActionListener(this);
      green.removeActionListener(this);
      okayButton.removeActionListener(this);
      easyAI.removeActionListener(this);
      hardAI.removeActionListener(this);
      again.removeActionListener(this);
      notAgain.removeActionListener(this);
      p1first.removeActionListener(this);
      p2first.removeActionListener(this);
      hORt.removeActionListener(this);
      
      instructions.setText("Please enter your name");     //the original thing that it's supposed to say
      
      for(int i = 0; i<6; i++){
        for(int j = 0; j<7; j++){
          arrayCircles[i][j].setColor(Color.white);
        }
      }
      
      welcome.removeAll();
      board.removeAll();           //removing all components from the panels
      
      remove(board);          //removing the panels
      remove(welcome);
      
      Connect4Final frame1 = new Connect4Final();    //restarting the Frame with a new instance
    }
  }
  
  //this method plays a short wav file when the user wins. It is a cheering sound to add features to the game
  public static void playSound(){
    try{
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(new File("Cheering.wav")));
      clip.start();
    }
    catch (Exception exc){
      exc.printStackTrace(System.out);
    }  
  }
  
  public static void main(String[] args) {
    //main method where I instantiate the GUI to start the frame 
    Connect4Final frame1 = new Connect4Final();
  }
}//end of class
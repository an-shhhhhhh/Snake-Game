import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;
// import java.util.Timer;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics.*;
// Constructors of JPanel 
// JPanel(): creates a new panel with a flow layout
// JPanel(LayoutManager l): creates a new JPanel with specified layoutManager
// JPanel(boolean isDoubleBuffered): creates a new JPanel with a specified buffering strategy
// JPanel(LayoutManager l, boolean isDoubleBuffered): creates a new JPanel with specified layoutManager and a specified buffering strategy
// Commonly used Functions of JPanel 
// add(Component c): Adds a component to a specified container
// setLayout(LayoutManager l): sets the layout of the container to the specified layout manager
// updateUI(): resets the UI property with a value from the current look and feel.
// setUI(PanelUI ui): sets the look and feel of an object that renders this component.
// getUI(): returns the look and feel object that renders this component.
// paramString(): returns a string representation of this JPanel.
// getUIClassID(): returns the name of the Look and feel class that renders this component.
// getAccessibleContext(): gets the AccessibleContext associated with this JPanel.

//creating the game panel
//
class Gamepanel extends JPanel implements ActionListener, KeyListener{

    //for storing the length of the snake when it eats food in x and y direction and its total length
    private int[] snake_x_length = new int[750];
    private int[] snake_y_length = new int[750];
    private int snake_length = 3; //initial length of snake is 3, 2 body and 1 head 

    //for the valid positions of the enemy
    private int[] xPos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700, 725, 750, 775, 800};
    private int[] yPos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random = new Random(); //for selecting any position of the enemy randomly
    private int enemyX,enemyY;
    private int score;
    boolean gameover = false;
    //initial initialization of the head of the snake, by default we are facing the snake's head towards right, and all the other faces are false
    boolean left = false;
    boolean right = true;
    boolean up = false;
    boolean down = false;

    int moves = 0; // initially the snake will stand at 0th position 

    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon body = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));

    private Timer timer;
    private int delay = 100;


    //construtor
    Gamepanel() {
        addKeyListener(this); //The Java KeyListener is notified whenever you change the state of key. It is notified against KeyEvent. 
        setFocusable(true); //You can use setFocusable(boolean n), it´s mainly used to activate or deactivate the focus event (component of the graphical user interface that is selected to receive the input) of the view, both in the tactile / mouse mode, and in the keyboard (cursor) mode.
        setFocusTraversalKeysEnabled(true); //setFocusTraversalKeysEnabled() decides whether or not focus traversal keys (TAB key, SHIFT+TAB, etc.) are allowed to be used when the current Component has focus.
        Timer t = new Timer(delay, this);
        t.start();    
        
        newEnemy();
    }
    


    private void newEnemy() { //selecting enemy randomly in x or y direction
        enemyX = xPos[random.nextInt(32)]; // 34 since, we have total 34 values in xPos array
        enemyY = yPos[random.nextInt(23)];
        for(int i=snake_length-1; i>=0; i--){
            if(snake_x_length[i] == enemyX && snake_y_length[i] == enemyY){ //if the enemy is coming in the way of the snake or its body, then create a new enemy 
                newEnemy();
            }
        }
    }



    @Override //The @Override annotation denotes that the child class method overrides the base class method.
    public void paint(Graphics g) {

        super.paint(g); //Paints the container. This forwards the paint to any lightweight components that are children of this container.
        g.setColor(Color.white);
        g.drawRect(24, 8, 845, 55); // draws a rectangle which will contain the name of the game
        g.drawRect(24, 80, 845, 550); //draws a rectangle which will contain the actual game

        snaketitle.paintIcon(this, g, 16, 11);
        g.setColor(Color.black);
        g.fillRect(25, 81, 844, 549);

        if(moves == 0){ //at initial position
            snake_x_length[0] = 100;//position of head 100px
            snake_x_length[1] = 75; //75 pixel
            snake_x_length[2] = 50;
            snake_y_length[0] = 100;
            snake_y_length[1] = 100;
            snake_y_length[2] = 100;
        } 
        //for drawing snake's head
        if(left){
            leftmouth.paintIcon(this, g, snake_x_length[0], snake_y_length[0]);
        }
        if(right){
            rightmouth.paintIcon(this, g, snake_x_length[0], snake_y_length[0]);
        }
        if(up){
            upmouth.paintIcon(this, g, snake_x_length[0], snake_y_length[0]);
        }
        if(down){
            downmouth.paintIcon(this, g, snake_x_length[0], snake_y_length[0]);
        }

        //for drawing snake's body
        for(int i=0; i<snake_length;i++){
            body.paintIcon(this, g, snake_x_length[i], snake_y_length[i]);
        }

        enemy.paintIcon(this, g, enemyX, enemyY); //enemy will be drawn at a random position

        //to draw gameover
        if(gameover){
            g.setColor(Color.WHITE);
            Font stringFont = new Font( "SansSerif", Font.BOLD, 100 ); 
            g.drawString("Game Over", 500, 300);
            // Font stringFont1 = new Font( "SansSerif", Font.PLAIN, 20 ); 
            g.drawString("Press Space To Restart", 20, 350);
        }
        g.setColor(Color.white);
        Font stringFont = new Font( "SansSerif", Font.BOLD, 14 ); 
        g.drawString("Score: "+score, 750, 30);
        g.drawString("Length: "+snake_length, 750, 50);

        g.dispose(); //dispose(); causes the JFrame window to be destroyed and cleaned up by the operating system. According to the documentation, this can cause the Java VM to terminate if there are no other Windows available, but this should really just be seen as a side effect rather than the norm.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = snake_length - 1; i >0; i-- ){
            snake_x_length[i] = snake_x_length[i-1];
            snake_y_length[i] = snake_y_length[i-1];
        }
        if(left){
            snake_x_length[0] = snake_x_length[0] - 25; //we are subtracting 25 since we have considered 1 box or 1 body as 25 pixel  
        }
        if(right){
            snake_x_length[0] = snake_x_length[0] + 25; 
        }
        if(up){
            snake_y_length[0] = snake_y_length[0] - 25; 
        }
        if(down){
            snake_y_length[0] = snake_y_length[0] + 25; 
        } 

        if(snake_x_length[0] > 850){
            snake_x_length[0] = 25; //if snake moves from left to right, then when it reaches the right boundary, then again it will start from left boundary
        }
        if(snake_x_length[0] <25){
            snake_x_length[0] = 850;
        }
        if(snake_y_length[0] > 625){
            snake_y_length[0] = 75;
        }
        if(snake_y_length[0] < 75){
            snake_y_length[0] = 625;
        }

        //to eat the enemy
        collidesWithEnemy();
        collidesWithBody(); //then game over

        repaint(); // At the point when we believe that a part should repaint itself, we have to call the repaint() method.In the event that we have made changes to the presence of a part but have not rolled out any improvements to its size, then, at that point, we can call the repaint() method to refresh the new appearance of the part on the graphical UI.
    }

    private void collidesWithBody() {
        for(int i = snake_length-1; i>0; i--){
            if(snake_x_length[i] == snake_x_length[0] && snake_y_length[i] == snake_y_length[0]){
                timer.stop();
                gameover = true;
            }
        }
    }



    private void collidesWithEnemy() {
        //checks whether the snake collides with the enemy or not
        //if it collides, then new position is to be given to the enemy
        if(snake_x_length[0] == enemyX && snake_y_length[0] == enemyY){
            newEnemy();
            snake_length++;
            score++;
        }
    }



    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            restart();
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && (!right)){
            left = true;
            right = false;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && (!left)){
            left = false;
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP && (!down)){
            left = false;
            right = false;
            up = true;
            down = false;
            moves++;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && (!up)){
            left = false;
            right = false;
            up = false;
            down = true;
            moves++;
        }
    }

    private void restart() {
        gameover = false;
        moves = 0;
        score = 0;
        snake_length = 3;
        left = false;
        right = true;
        up = false;
        down = false;
        timer.start();
        repaint();
    }



    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

}

public class game{

    public static void main(String[] args) {

        // creating the frame of the game
        JFrame frame = new JFrame("Snake Game", null);
        frame.setBounds(10, 10, 905, 700);
        frame.setResizable(false); //user cannot set the size of the frame by himself
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        Gamepanel panel = new Gamepanel();
        panel.setBackground(Color.black);
        frame.add(panel);

        frame.setVisible(true); //frame is by defuault invisible, so making it visible

    }
}
package flappy;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Timer;
import javax.swing.JFrame;


public class FlappyBird implements ActionListener, KeyListener{
    public static FlappyBird flappy;
    public final int width = 1200, height = 800;
    public Render rend;
    public Rectangle Birdie;
    public ArrayList<Rectangle> column;
    public boolean Gameover, start;
    public Random random;
    public int tick, ymovement, score;

    public FlappyBird(){
        JFrame frame = new JFrame();
        Timer time = new Timer(20, this);


        rend = new Render();
        random = new Random();
        
        frame.add(rend);
        frame.addKeyListener(this);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("FlappyBird");

        Birdie = new Rectangle(width / 2 - 10, height / 2 - 10, 20, 20);
        column = new ArrayList<Rectangle>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);



        time.start();
    }

   public void addColumn(boolean start){
    int spaces = 300;
    int Width = 100;
    int Height = 50 + random.nextInt(300);

    if(start){
    column.add(new Rectangle(width + Width + column.size() * 300, height - Height - 120, Width, Height));
    column.add(new Rectangle(width + Width + (column.size() - 1) * 300, 0, Width, height - Height - spaces));
    } else {
        column.add(new Rectangle(column.get(column.size() - 1).x + 600, height - Height - 120, Width, Height));
        column.add(new Rectangle(column.get(column.size() - 1).x, 0, Width, height - Height - 120 - spaces));
    }
   }
   
    public void paintColumns(Graphics e, Rectangle column){
        e.setColor(Color.BLUE);
        e.fillRect(column.x, column.y, column.width, column.height);
    }
public void jump(){
    if(Gameover){
        Birdie = new Rectangle(width / 2 - 10, height / 2 - 10, 20, 20);
        column.clear();
        ymovement = 0;
        score = 0;

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        Gameover = false;
    }

    if(!start){
        start = true;
    }else if(!Gameover){
        if(ymovement > 0){
            
            ymovement = 0;
        }
        ymovement -= 10;
    }
}

   
    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        tick++;
        
        
        if(start){

            if(score >= 10){
                speed = 15;  
            } 

        for(int i = 0; i < column.size(); i++){
          Rectangle columns = column.get(i);
          columns.x -= speed;
        }

       if(tick % 2 == 0 && ymovement < 15){
            ymovement += 2;
       }

       for(int i = 0; i < column.size(); i++){
        Rectangle columns = column.get(i);
        
        if(columns.x + columns.width < 0){
            column.remove(columns);

            if(columns.y == 0){
            addColumn(false);
 
            }
            
        }

      
      }

        Birdie.y += ymovement;

        for(Rectangle columns : column){
            if(columns.y == 0 && Birdie.x + Birdie.width / 2 > columns.x + columns.width/ 2 - 10 && Birdie.x + Birdie.width / 2 < columns.x + columns.width/ 2 + 10){
                score++;
            }

         if(columns.intersects(Birdie)){
            Gameover = true;
            Birdie.x = columns.x - Birdie.width;
         }   
        }

        if(Birdie.y > height - 120 || Birdie.y < 0){
            Gameover = true;

            if(Birdie.y + ymovement >= height - 120){
                Birdie.y = height - 120 - Birdie.height;
            }      
        }

        
    }
    rend.repaint();
    }

    public void repaint(Graphics e) {
        e.setColor(Color.cyan);
        e.fillRect(0,0, width, height);

        e.setColor(Color.orange);
        e.fillRect(0, height - 120, width, 120);

        e.setColor(Color.green);
        e.fillRect(0, height - 120, width, 20);

        e.setColor(Color.red);
        e.fillRect(Birdie.x, Birdie.y, Birdie.width, Birdie.height);

        for(Rectangle columns : column){
            paintColumns(e, columns);
        }

        e.setColor(Color.BLACK);
        e.setFont(new Font("Arial", 1, 100));
        
        
        if(!start){
        e.drawString("Click to play!", 100, height / 2 - 50 );
    }

    if(Gameover){
        e.drawString("Game Over :(", 100, height / 2 - 50 );
    }

    if(!Gameover && start){
     
        e.drawString(String.valueOf(score), width / 2 - 25, 100);
    }
   
}
  
public static void main(String[] args){
    flappy = new FlappyBird();
 }

    @Override
    public void keyTyped(KeyEvent e) {
        jump();
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
     
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

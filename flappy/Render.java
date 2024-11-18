package flappy;

import javax.swing.JPanel;
import java.awt.Graphics;

public class Render extends JPanel{

    private static final long serialVerisonUID = 1L;


@Override
    protected void paintComponent(Graphics e){
        super.paintComponent(e);
        FlappyBird.flappy.repaint(e);
    }
}
 
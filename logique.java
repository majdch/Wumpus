
        import javax.imageio.ImageIO;
        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.KeyEvent;
        import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
 import java.io.File;
import java.io.IOException;
@SuppressWarnings({ "serial", "unused" })
public class logique extends JPanel implements KeyListener
{
    private BufferedImage floor = null;
    private BufferedImage arrow = null;
    @SuppressWarnings("unused")
	private BufferedImage fog = null;
    private BufferedImage gold = null;
    private BufferedImage ladder = null;
    private BufferedImage pit = null;
    private BufferedImage breeze = null;
    private BufferedImage wumpus = null;
    private BufferedImage black = null;
    private BufferedImage deadWumpus = null;
    private BufferedImage stench = null;
    private BufferedImage playerUp = null;
    private BufferedImage playerDown = null;
    private BufferedImage playerLeft = null;
    private BufferedImage playerRight = null;

    public static final int PLAYING=0;
    public static final int DEAD=1;
    public static final int WON=2;
    public static boolean wall=false;
    public static int score=0;
    private int status;
    
    private carte wm;
    private Agent wp;
    private ressource ws;

    private int w=1;
    private int ww=0;
    private int h=1;
    private int d=0;
    private int f=0;
    private int ff=0;
    private int a=0;


    public logique()
    {
        super();
        setSize(500,615);
        wm = new carte();
        wp = new Agent();
        ws = new ressource();

        status = PLAYING;

        addKeyListener(this);
        try
        {
            floor = ImageIO.read((new File("images/Floor.gif")));
            ladder = ImageIO.read((new File("images/ladder.gif")));
            arrow = ImageIO.read((new File("images/fleche.png")));
            breeze = ImageIO.read((new File("images/brise.png")));
            gold = ImageIO.read((new File("images/gold.png")));
            pit = ImageIO.read((new File("images/puit.png")));
            wumpus = ImageIO.read((new File("images/wumpus.png")));
            deadWumpus = ImageIO.read((new File("images/wumpus_dead.png")));
            stench = ImageIO.read((new File("images/Puanteur.png")));
            black = ImageIO.read((new File("images/black.gif")));
            playerUp = ImageIO.read((new File("images/hero_up.png")));
            playerDown = ImageIO.read((new File("images/hero_down.png")));
            playerLeft = ImageIO.read((new File("images/hero_left.png")));
            playerRight = ImageIO.read((new File("images/hero_right.png")));
        }
        catch(Exception e)
        {
            System.out.println("Erreur de chargement des images: " + e.getMessage());
        }
    }
    public void reset()
    {
    	try {
    		 playerUp = ImageIO.read((new File("images/hero_up.png")));
             playerDown = ImageIO.read((new File("images/hero_down.png")));
             playerLeft = ImageIO.read((new File("images/hero_left.png")));
             playerRight = ImageIO.read((new File("images/hero_right.png")));
    	}catch(Exception e) {
    		  System.out.println("Erreur de chargement des images: " + e.getMessage());
    	}
score=0;
        wm.createMap();
        wp.setRowPosition(0);
        wp.setColPosition(9);
        status=PLAYING;
        wp.setGold(false);
        wp.setArrow(true);
        h=1;
        d=0;

    }

    public int getW() {
        return w;
    }

    public int getWw() {
        return ww;
    }

    @SuppressWarnings("static-access")
	public void paint(Graphics g)
    {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 500, 500);
        g.setColor(Color.WHITE);
        g.fillRect(0, 500, 500, 115);
        for (int x = 0; x < 500; x += 50) {
            for (int y = 0; y < 500; y += 50) {
             
                System.out.print(g.drawImage(floor, x, y, null));
            }
        }

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {


                if (wm.getSquare(x, y).isPit())
                    System.out.print(g.drawImage(pit, x * 50, y * 50, null));

                if (wm.getSquare(x, y).isBreeze())
                    System.out.print(g.drawImage(breeze, x * 50, y * 50, null));


                if (wm.getSquare(x, y).isWumpus() )
                    System.out.print(g.drawImage(wumpus, x * 50, y * 50, null));

                if (wm.getSquare(x, y).isDeadWumpus() )
                    System.out.print(g.drawImage(deadWumpus, x * 50, y * 50, null));

                if (wm.getSquare(x, y).isStench())
                    System.out.print(g.drawImage(stench, x * 50, y * 50, null));


                if (wm.getSquare(x, y).isLadder()) {
                    System.out.print(g.drawImage(ladder, x * 50, y * 50, null));
                    if (w == 1) {
                    	wp.setRowPosition(0);
                        wp.setColPosition(9);
                        w--;
                    }
                }

                if (wm.getSquare(x, y).isGold() && h == 1)
                    System.out.print(g.drawImage(gold, x * 50, y * 50, null));


            }
        }
        if (wp.getDirection() == wp.NORTH) {
            System.out.print(g.drawImage(playerUp, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
            wm.getSquare(wp.getRowPosition(), wp.getColPosition()).setBlack(false);
            wm.getSquare(wp.getRowPosition(), wp.getColPosition()).setVisited(true);
           
        }
        if (wp.getDirection() == wp.WEST) {
            System.out.print(g.drawImage(playerLeft, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
            wm.getSquare(wp.getRowPosition(), wp.getColPosition()).setBlack(false);
            wm.getSquare(wp.getRowPosition(), wp.getColPosition()).setVisited(true);
          

        }
        if (wp.getDirection() == wp.EAST) {
            System.out.print(g.drawImage(playerRight, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
            wm.getSquare(wp.getRowPosition(), wp.getColPosition()).setBlack(false);
            wm.getSquare(wp.getRowPosition(), wp.getColPosition()).setVisited(true);
        
        }
        if (wp.getDirection() == wp.SOUTH) {
            System.out.print(g.drawImage(playerDown, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
            wm.getSquare(wp.getRowPosition(), wp.getColPosition()).setBlack(false);
            wm.getSquare(wp.getRowPosition(), wp.getColPosition()).setVisited(true);
           
        }
        for (int d = 0; d < 10; d++) {
            for (int dd = 0; dd < 10; dd++) {
                if (wm.getSquare(d, dd).isBlack() == true) {
                    System.out.print(g.drawImage(black, d * 50, dd * 50, null));
                }
            }
        }
        g.setColor(Color.RED);
        
       

        g.setColor(Color.BLUE);
       
  

        if (!wm.getSquare(wp.getRowPosition(), wp.getColPosition()).isBreeze() && !wm.getSquare(wp.getRowPosition(), wp.getColPosition()).isStench() && !wm.getSquare(wp.getRowPosition(), wp.getColPosition()).isGold()  && !wm.getSquare(wp.getRowPosition(), wp.getColPosition()).isLadder() && !wm.getSquare(wp.getRowPosition(), wp.getColPosition()).isDeadWumpus()) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 500, 250, 115);
            g.setColor(Color.RED);
         
        }
        if (h == 1) {
            if (wp.getGold() == true) {
                System.out.print(g.drawImage(floor, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
                if (wm.getSquare(wp.getRowPosition(), wp.getColPosition()).isBreeze()) {
                    System.out.print(g.drawImage(breeze, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
                }
                if (wm.getSquare(wp.getRowPosition(), wp.getColPosition()).isStench()) {
                    System.out.print(g.drawImage(stench, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
                }
                if (wp.getDirection() == wp.NORTH) {
                    System.out.print(g.drawImage(playerUp, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
                } else if (wp.getDirection() == wp.EAST) {
                    System.out.print(g.drawImage(playerRight, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
                } else if (wp.getDirection() == wp.SOUTH) {
                    System.out.print(g.drawImage(playerDown, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
                } else if (wp.getDirection() == wp.WEST) {
                    System.out.print(g.drawImage(playerLeft, wp.getRowPosition() * 50, wp.getColPosition() * 50, null));
                }
                h--;
            }
        }
        if (d == 1) {
            wp.setArrow(false);
            for(int x=0;x<10;x++)
            {
                if(wp.getColPosition()-x>=0) {
                    if (wm.getSquare(wp.getRowPosition(), wp.getColPosition() - x).isWumpus()) {
                        System.out.print(g.drawImage(floor, wp.getRowPosition() * 50, (wp.getColPosition() - x) * 50, null));
                        wm.getSquare(wp.getRowPosition(), wp.getColPosition() - x).setDeadWumpus(true);
                        wm.getSquare(wp.getRowPosition(), wp.getColPosition() - x).setWumpus(false);
                        if (wm.getSquare(wp.getRowPosition(), wp.getColPosition() - x).isBreeze()) {
                            System.out.print(g.drawImage(breeze, wp.getRowPosition() * 50, (wp.getColPosition() - x) * 50, null));
                        }
                        if (wm.getSquare(wp.getRowPosition(), wp.getColPosition() - 1).isStench()) {
                            System.out.print(g.drawImage(stench, wp.getRowPosition() * 50, (wp.getColPosition() - x) * 50, null));
                        }
                        System.out.print(g.drawImage(deadWumpus, wp.getRowPosition() * 50, (wp.getColPosition() - x) * 50, null));
                        f = wp.getRowPosition();
                        ff = (wp.getColPosition() - x);
                        d = 5;
                        g.drawString("vous avez entendu un cri", 50, 525);

                        System.out.print(g.drawImage(black, wp.getRowPosition() * 50, (wp.getColPosition() - x) * 50, null));

                        break;
                    }
                }

            }
            d = 6;
        }
        if (d == 2) {
            wp.setArrow(false);
            for(int x=0;x<10;x++) {
                if (wp.getColPosition() + x <=9) {
                    if (wm.getSquare(wp.getRowPosition(), wp.getColPosition() + x).isWumpus()) {
                        System.out.print(g.drawImage(floor, wp.getRowPosition() * 50, (wp.getColPosition() + x) * 50, null));
                        wm.getSquare(wp.getRowPosition(), wp.getColPosition() + x).setDeadWumpus(true);
                        wm.getSquare(wp.getRowPosition(), wp.getColPosition() + x).setWumpus(false);
                        if (wm.getSquare(wp.getRowPosition(), wp.getColPosition() + x).isBreeze()) {
                            System.out.print(g.drawImage(breeze, wp.getRowPosition() * 50, (wp.getColPosition() + x) * 50, null));
                        }
                        if (wm.getSquare(wp.getRowPosition(), wp.getColPosition() + x).isStench()) {
                            System.out.print(g.drawImage(stench, wp.getRowPosition() * 50, (wp.getColPosition() + x) * 50, null));
                        }
                        System.out.print(g.drawImage(deadWumpus, wp.getRowPosition() * 50, (wp.getColPosition() + x) * 50, null));
                        f = wp.getRowPosition();
                        ff = (wp.getColPosition() + x);
                        d = 5;
                        g.drawString("vous avez entendu un cri", 50, 525);
                        System.out.print(g.drawImage(black, wp.getRowPosition() * 50, (wp.getColPosition() + x) * 50, null));
                        break;

                    }
                }
            }
            d = 6;
        }
        if (d == 3) {
            wp.setArrow(false);
            for(int x=0;x<10;x++) {
                if (wp.getRowPosition() - x >=0) {

                    if (wm.getSquare(wp.getRowPosition() - x, wp.getColPosition()).isWumpus()) {
                        System.out.print(g.drawImage(floor, (wp.getRowPosition() - x) * 50, wp.getColPosition() * 50, null));
                        wm.getSquare((wp.getRowPosition() - x), wp.getColPosition()).setDeadWumpus(true);
                        wm.getSquare((wp.getRowPosition() - x), wp.getColPosition()).setWumpus(false);
                        if (wm.getSquare((wp.getRowPosition() - x), wp.getColPosition()).isBreeze()) {
                            System.out.print(g.drawImage(breeze, (wp.getRowPosition() - x) * 50, wp.getColPosition() * 50, null));
                        }
                        if (wm.getSquare(wp.getRowPosition() - x, wp.getColPosition()).isStench()) {
                            System.out.print(g.drawImage(stench, (wp.getRowPosition() - x) * 50, wp.getColPosition() * 50, null));
                        }
                        System.out.print(g.drawImage(deadWumpus, (wp.getRowPosition() - x) * 50, wp.getColPosition() * 50, null));
                        f = wp.getRowPosition() - x;
                        ff = wp.getColPosition();
                        d = 5;
                        g.drawString("vous avez entendu un cri", 50, 525);
                        System.out.print(g.drawImage(black, (wp.getRowPosition() - x) * 50, wp.getColPosition() * 50, null));

                        break;
                    }
                }
            }
            d = 6;
        }
        if (d == 4) {
            wp.setArrow(false);
            for(int x=0;x<10;x++) {
                if (wp.getRowPosition() + x<=9) {
                    if (wm.getSquare(wp.getRowPosition() + x, wp.getColPosition()).isWumpus()) {
                        System.out.print(g.drawImage(floor, (wp.getRowPosition() + x) * 50, wp.getColPosition() * 50, null));
                        wm.getSquare((wp.getRowPosition() + x), wp.getColPosition()).setDeadWumpus(true);
                        wm.getSquare((wp.getRowPosition() + x), wp.getColPosition()).setWumpus(false);
                        if (wm.getSquare((wp.getRowPosition() + x), wp.getColPosition()).isBreeze()) {
                            System.out.print(g.drawImage(breeze, (wp.getRowPosition() + x) * 50, wp.getColPosition() * 50, null));
                        }
                        if (wm.getSquare(wp.getRowPosition() + x, wp.getColPosition()).isStench()) {
                            System.out.print(g.drawImage(stench, (wp.getRowPosition() + x) * 50, wp.getColPosition() * 50, null));
                        }
                        System.out.print(g.drawImage(deadWumpus, (wp.getRowPosition() + x) * 50, wp.getColPosition() * 50, null));
                        f = wp.getRowPosition() + x;
                        ff = wp.getColPosition();
                        d = 5;
                        g.drawString("vous avez entendu un cri", 50, 525);
                        System.out.print(g.drawImage(black, (wp.getRowPosition() + x) * 50, wp.getColPosition() * 50, null));

                        break;
                    }
                }
            }
            d = 6;
        }
        if (d == 5) {
            System.out.print(g.drawImage(deadWumpus, f * 50, ff * 50, null));
            wm.getSquare(f, ff).setDeadWumpus(true);
            wm.getSquare(f, ff).setWumpus(false);
        }
        if (wp.getArrow() == true) {
            System.out.print(g.drawImage(arrow, 450, 550, null));
        }
        if (wp.getGold() == true) {
            System.out.print(g.drawImage(gold, 400, 550, null));
        }
if(wall==true) {
	 g.drawString("vous avez frappé un mur", 50, 525);
	 wall=false;
}

        if (status == DEAD && wm.getSquare(wp.getRowPosition(), wp.getColPosition()).isPit()) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 500, 250, 100);
            g.setColor(Color.BLUE);

            g.drawString("GAME OVER Appuiez sur 'n' pour une nouvelle partie votre score est : "+score, 50, 525);
            
        }
        if (status == DEAD && wm.getSquare(wp.getRowPosition(), wp.getColPosition()).isWumpus()) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 500, 250, 100);
            g.setColor(Color.BLUE);
            g.drawString("GAME OVER Appuiez sur 'n' pour une nouvelle partie votre score est : "+score, 50, 525);
          
            
        }
        if (status == WON) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 500, 250, 100);
            g.setColor(Color.BLUE);
            g.drawString("Partie Gagner voici votre Score :"+score+"  appuiez sur 'n' pour rejouer", 50, 525);
         
        
        }

    }
    public void keyTyped(KeyEvent e)
    {

    }
    @SuppressWarnings("static-access")
	public void keyPressed(KeyEvent e)
    {
 
        if(status==PLAYING)
        {
            if (e.getKeyChar() == 'z')
            {
                wp.setDirection(wp.NORTH);
                score=score-1;
                if (wp.getColPosition() - 1 >= 0) {
                    wp.setColPosition(wp.getColPosition() - 1);}else {
                    	wall=true;
                    }
                if(wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isWumpus() && d!=5|| wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isPit()) {
                 score=score-1000;
                    status = DEAD;
                    repaint();
                }
         
            }
            if (e.getKeyChar() == 's') {
                wp.setDirection(wp.SOUTH);
                score=score-1;
                if (wp.getColPosition() + 1 <= 9) {
                    wp.setColPosition(wp.getColPosition() + 1);}else {
                    	wall=true;
                    }
                if(wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isWumpus() && d!=5|| wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isPit()) {
                	score=score-1000;
                    status = DEAD;
                    repaint();

                }

            }
            if (e.getKeyChar() == 'q') {
                wp.setDirection(wp.WEST);
                score=score-1;
                if (wp.getRowPosition() - 1 >= 0) {
                    wp.setRowPosition(wp.getRowPosition() - 1);}else {
                    	wall=true;
                    }
                if(wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isWumpus() && d!=5|| wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isPit()) {
                	score=score-1000;
                    status = DEAD;
                    repaint();

                }
            }
            if (e.getKeyChar() == 'd') {
                wp.setDirection(wp.EAST);
                score=score-1;
                if (wp.getRowPosition() + 1 <= 9) {
                    wp.setRowPosition(wp.getRowPosition() + 1);}else {
                    	wall=true;
                    }
                if(wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isWumpus() && d!=5|| wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isPit()) {
                	score=score-1000;
                    status = DEAD;
                    repaint();

                }
            }
            if(e.getKeyChar() == 'p' && wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isGold())
            {
                wp.setGold(true);
                score=score+1000;
                try {
					playerUp = ImageIO.read((new File("images/goldhero_up.png")));
					   playerDown = ImageIO.read((new File("images/goldhero_down.png")));
		                playerLeft = ImageIO.read((new File("images/goldhero_left.png")));
		                playerRight = ImageIO.read((new File("images/goldhero_right.png")));
				}catch(Exception e1)
                {
		            System.out.println("Erreur de chargement des images: " + e1.getMessage());
		        }
             
                
            }

            if( wp.getArrow() == true && e.getKeyChar() == 'i')
            {
            	 score=score-10;
                d=1;
            }
            if( wp.getArrow() == true && e.getKeyChar() == 'k')
            {
            	score=score-10;
                d=2;
            }
            if( wp.getArrow() == true && e.getKeyChar() == 'j')
            {
            	score=score-10;
                d=3;
            }
            if( wp.getArrow() == true && e.getKeyChar() == 'l')
            {
            	score=score-10;
                d=4;
            }
            if(e.getKeyChar() == '*')
            {
       
                if(a%2==0)
                {
                    for(int x=0;x<10;x++)
                    {
                        for(int y=0;y<10;y++)
                        {
                            if(wm.getSquare(x,y).isBlack())
                            {
                                wm.getSquare(x,y).setBlack(false);
                            }
                        }
                    }
                }
                else
                {
                    for(int x=0;x<10;x++)
                    {
                        for(int y=0;y<10;y++)
                        {
                            if(!wm.getSquare(x,y).isVisited())
                            {
                                wm.getSquare(x,y).setBlack(true);
                            }
                        }
                    }
                }
                a++;
            }
            if(e.getKeyChar() == 'c' && wm.getSquare(wp.getRowPosition(),wp.getColPosition()).isLadder() && wp.getGold()==true)
            {
            	score=score+10;
                status = WON;
                
                repaint();
            }
        }
        if (e.getKeyChar() == 'n' && status == DEAD || e.getKeyChar() == 'n' && status == WON) {
            reset();
        }
        repaint();
    }
    public void keyReleased(KeyEvent e)
    {

    }
    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
}
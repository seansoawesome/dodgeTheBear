/**
 * The main class of the game. This class has code for the keyboard movements. It also has code for background music and bckground pictures. 
 *
 * @author Sean Chen
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import sun.audio.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
public class playDodgeTheBear extends JPanel implements KeyListener
{
    private final int x,y;//window size 
    private JFrame board;//the main board
    private JLabel pig;//the pigman
    private JLabel bigBear;
    private JLabel bigBear2;
    private JLabel scoreLabel;
    private Player p1;//reference to the pigman
    private dangerBear bear;//the dangerous bear that mauls you to death if it mauls you
    private dangerBear bear2;
    private ImageIcon congrats;//background image for winning
    private ImageIcon background;//default background image 
    private ImageIcon gameOverBackground;
    private boolean up,down;//used to make pigman stay within the borders
    private boolean setloc;//allows the object to be placed in the JFrame
    private boolean wallpaper;//overrides paintcomponent to make it draw the background image on to JFrame.
    private boolean move;//stops everyone moving except for bear if you either win or die
    private boolean bear2move;
    private boolean win;
    private AudioStream BGM;//music
    private AudioStream BGM1;//souneffect
    private int succession;
    private int hard;
    private int soundLim;
    public static void main(String args[])
    {
        playDodgeTheBear play= new playDodgeTheBear();
    }

    public playDodgeTheBear()
    {
        board = new JFrame("Dodge The Bear");//creates a new board
        board.setSize(1330,700 );
        x=1300;//actual size of jframe without borders
        y=500;
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//closes JFrame when exit by clicking the close button
        background=new ImageIcon("data/grassland1.png");//image of the background
        gameOverBackground=new ImageIcon("data/gameOver2.png");
        congrats= new ImageIcon("data/congrats.png");
        board.add(this);//adds JPanel to JFrame
        p1=new Player(10,200);//creates new reference to player
        bear=new dangerBear(1340,0);//creates new reference to the bear
        bear2= new dangerBear (1340,200);
        board.addKeyListener(this);//KeyListener to JPanel
        ImageIcon pigPic = new ImageIcon("data/runningPig2.gif");//player
        ImageIcon bearPic= new ImageIcon("data/bearPic4.gif");//bear
        pig=new JLabel(pigPic);//used Jlabel to be able to use setLocation
        bigBear=new JLabel(bearPic);
        bigBear2=new JLabel(bearPic);
        //adds JLabel to JPanel
        this.add(pig);
        this.add(bigBear);
        this.add(bigBear2);
        bear.contact=false;
        bear2.contact=false;
        move=true;
        setloc=true;
        wallpaper=true;
        bear2move=false;
        win=false;
        soundLim=0;
        succession=0;
        hard=0;
        music();
        board.setVisible(true);//sets board to visible
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                canMove();
                if (move==true)
                {
                    difficulty();
                    bear.moveforward();
                    bigBear.setLocation(bear.getXBear(),bear.getYBear());
                    if(succession>4)
                    {
                        bear2.moveforward();
                        bigBear2.setLocation(bear2.getXBear(),bear2.getYBear());
                    }
                }
                if(setloc==true)
                {
                    placeObjects();
                }
                if(bigBear.getX()<10)
                {
                    soundLim++;
                    if(soundLim==3)
                    {
                        soundEffects();
                        soundLim=0;
                    }

                }

                if((succession==50)&&(win==false))
                {
                    win=true;
                    repaint();
                    gameOver();
                }
                contactBear();
                changeLanes();
            }
        }, 0, 80);
    }
    //sets locations of the objects
    public void placeObjects()
    {
        p1.override(20,5);
        pig.setLocation(p1.getPlayerX(),p1.getPlayerY());
        bear.override(1300,0);
        bear2.override(1300,200);
        bigBear.setLocation(bear.getXBear(),bear.getYBear());
        bigBear2.setLocation(bear2.getXBear(),bear2.getYBear());
        setloc=false;
    }

    public void difficulty()
    {
        if(succession<4)
        {
            bear.choice=10;
        }
        if((succession>3)&&(succession<11))
        {
            bear.choice=20;
            bear2.choice=20;
        }
        if((succession>10)&&(succession<18))
        {
            bear.choice=40;
            bear2.choice=40;
        }
        if((succession>17)&&(succession<25))
        {
            bear.choice=60;
            bear2.choice=60;
        }
        if((succession>24)&&(succession<32))
        {
            bear.choice=80;
            bear2.choice=80;
        }
        if((succession>31)&&(succession<39))
        {
            bear.choice=100;
            bear2.choice=100;
        }
        if((succession>38)&&(succession<49))
        {
            bear.choice=140;
            bear2.choice=140;
        }
        if(succession>48)
        {
            bear.choice=180;
            bear2.choice=185;
        }
    }

    public void contactBear()
    {
        if((Math.abs(pig.getX()-bigBear.getX()))<95&&(Math.abs(pig.getY()-bigBear.getY())<121))
        {
            bear.contact=true;
            gameOver();
        }else{
            bear.contact=false;
        }
        if((Math.abs(pig.getX()-bigBear2.getX()))<95&&(Math.abs(pig.getY()-bigBear2.getY())<121))
        {
            bear2.contact=true;
            gameOver();
        }else{
            bear2.contact=false;
        }
    }

    public void changeLanes()
    {
        int random=(int)(Math.random()*3);
        if(bigBear.getX()<1)
        {
            succession++;
            if(random==0)
            {
                bear.override(1300,0);
                bigBear.setLocation(1300,0);
                if(succession>4)
                {
                    bear2.override(1300,200);
                    bigBear2.setLocation(1300,200);
                }
            }
            if(random==1)
            {
                bear.override(1300,200);
                bigBear.setLocation(1300,200);
                if(succession>4)
                {
                    bear2.override(1300,430);
                    bigBear2.setLocation(1300,430);
                }
            }
            if(random==2)
            {
                bear.override(1300,430);
                bigBear.setLocation(1300,430);
                if( succession>4)
                {
                    bear2.override(1300,0);
                    bigBear2.setLocation(1300,0);
                }
            }
        }
    }
    //plays the music , different musics for different consequences
    public void music()
    {
        AudioPlayer MGP = AudioPlayer.player;
        AudioData MD;
        ContinuousAudioDataStream loop = null;
        try
        {
            InputStream main = new FileInputStream("data/daFunk.wav");
            InputStream champ= new FileInputStream("data/champions.wav");
            InputStream loser=new FileInputStream("data/gameOver.wav");
            if(move==true)
            {
                BGM= new AudioStream(main);
            }else
            {
                BGM=new AudioStream(loser);
            }
            if(win==true)
                BGM= new AudioStream(champ);

            AudioPlayer.player.start(BGM);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        //MGP.start(loop);
    }

    public void soundEffects()
    {
        int rand=(int)(Math.random()*8);
        AudioPlayer MGP = AudioPlayer.player;
        AudioData MD;
        ContinuousAudioDataStream loop = null;
        try
        {
            InputStream effect1 = new FileInputStream("data/bebai.wav");
            InputStream effect2= new FileInputStream("data/lihai.wav");
            InputStream effect3=new FileInputStream("data/lihaieh.wav");
            InputStream effect4=new FileInputStream("data/sui.wav");
            InputStream effect5=new FileInputStream("data/jiayou.wav");
            InputStream effect6=new FileInputStream("data/jiayoug.wav");
            InputStream effect7=new FileInputStream("data/wah.wav");
            InputStream effect8=new FileInputStream("data/zan.wav");
            if(rand==0)
            {
                BGM1= new AudioStream(effect1);
            }else if(rand==1)
            {
                BGM1=new AudioStream(effect2);
            }else if (rand==2)
            {
                BGM1=new AudioStream(effect3);
            }else if (rand==3)
            {
                BGM1=new AudioStream(effect4);
            }else if (rand==4)
            {
                BGM1=new AudioStream(effect5);
            }else if (rand==5)
            {
                BGM1=new AudioStream(effect6);
            }else if (rand==6)
            {
                BGM1=new AudioStream(effect7);
            }else
            {
                BGM1=new AudioStream(effect8);
            }
            AudioPlayer.player.start(BGM1);
        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
    }
    //Called when the key is pressed down.
    public void keyPressed(KeyEvent e)
    {
        if(move==true)
        {
            //moves player up if pressing keyboard up
            if((e.getKeyCode() == KeyEvent.VK_UP)&&(up==true))
            {
                p1.setY( 250,"-");
                pig.setLocation(p1.getPlayerX(),p1.getPlayerY());
            }
            if((e.getKeyCode() == KeyEvent.VK_DOWN)&&(down==true))
            {
                p1.setY(250 ,"+");
                pig.setLocation(p1.getPlayerX(),p1.getPlayerY());
            }
            if(bear2move==false)
            {
                bigBear2.setLocation(1340,200);
                bear2move=true;
            }
        }
    }
    //Called when the key is released   
    public void keyReleased(KeyEvent e){
        // System.out.println("Location:"+pig.getX()+","+pig.getY());
        // KeyCodeT.setText("Key Code:" + e.getKeyCode());//displays the key code in the text box
    }
    //Called when a key is typed
    public void keyTyped(KeyEvent e){
    }
    //stops player from moving out the borders
    public void canMove()
    {
        up=true;
        down=true;
        if(pig.getY()>485)
            down=false;
        if(pig.getY()<15)
            up=false;
    }
    //paints background
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(wallpaper==true)
            g.drawImage(background.getImage(), 0,0,null);
        if((bear.contact==true)||(bear2.contact==true))
            g.drawImage(gameOverBackground.getImage(), 0,0,null);
        if(win==true)
            g.drawImage(congrats.getImage(), 0,0,null);
    }
    //checks contact of bear

    public void gameOver()
    {
        AudioPlayer.player.stop(BGM);
        move=false;
        music();
        repaint();
        if (JOptionPane.showConfirmDialog(null, "play again?","message",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            reset();
        }
        else
        {
            bigBear.setLocation(1300,0);
            bear.override(1300,0);
            bigBear2.setLocation(1300,200);
            bear2.override(1300,200);
            JOptionPane.showMessageDialog(board, "enjoy the music");
        }

    }

    //resets entire game
    public void reset()
    {
        AudioPlayer.player.stop(BGM);
        setloc=true;
        wallpaper=true;
        bear.contact=false;
        bear2.contact=false;
        move=true;
        win=false;
        succession=0;
        soundLim=0;
        music();
        repaint();
        placeObjects();
    }

    public void test()
    {
        System.out.println("jlabel"+pig.getY());
        System.out.println("p1"+p1.getPlayerY());
    }

}
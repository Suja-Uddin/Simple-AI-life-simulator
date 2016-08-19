package ailife;

import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


class Point
{
    int x,y;
    Point(int a,int b)
    {
        x=a;y=b;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}

class mainFunction extends JPanel implements Runnable 
{
    
    public static int[] pos_x=new int[100];
    public static int[] pos_y=new int[100];
    public static boolean[] isRemove=new boolean[100];
    public static int[] imageSize={50,30,15,15,25,30};
    
    public static int catNum,birdNum,bugNum,plantNum,treeNum,rockNum;
    public static int catId=0,birdId=1,bugId=2,plantId=3,treeId=4,rockId=5;
    
    public int totalNum;
    
    Image catImg,bugImg,birdImg,plantImg,treeImg,rockImg;
    
    int width,height;
    
    mainFunction(int a,int b)
    {
        //setBackground(Color.GRAY);
        imageInitialize();
        variableInitialize();
        width=a;height=b;
        
        for(int i=0;i<totalNum;i++)
        {
            int x=randInt(imageSize[getId(i)], width);
            int y=randInt(imageSize[getId(i)], height);
            boolean flag=false;
            for(int j=i-1;j>=0;j--)
            {
                if(x>=pos_x[j]&&x<=pos_x[j]+50 && (y>=pos_y[j]&&y<=pos_y[j]+50))
                {
                    flag=true;
                    i--;
                    break;
                }
            }
            if (flag==true) continue;
            pos_x[i]=x;
            pos_y[i]=y;
        }
        new Thread(this).start();
        
        for(int i=0;i<catNum+birdNum+bugNum;i++)
            new Thread(new myThread(i)).start();
    
    }
    
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    void variableInitialize()
    {
        catNum=2;birdNum=15;bugNum=25;plantNum=40;treeNum=10;rockNum=0;
        totalNum=catNum+birdNum+bugNum+plantNum+treeNum+rockNum;
        for(int i=0;i<=totalNum;i++)
            isRemove[i]=false;
    }
    
    void imageInitialize()
    {
       ImageIcon ii = new ImageIcon("resources\\cat.png"); 
       catImg=ii.getImage();
       
       ImageIcon ii1 = new ImageIcon("resources\\bird.png"); 
       birdImg=ii1.getImage();
       
       ImageIcon ii2 = new ImageIcon("resources\\bug.png"); 
       bugImg=ii2.getImage();
       
       ImageIcon ii3 = new ImageIcon("resources\\plant.png"); 
       plantImg=ii3.getImage();
       
       ImageIcon ii4 = new ImageIcon("resources\\tree.png"); 
       treeImg=ii4.getImage();
       
       ImageIcon ii5 = new ImageIcon("resources\\rock.png"); 
       rockImg=ii5.getImage();
    }
            
    public static int getId(int x)
    {
        if (x>=0 && x<catNum) return 0;
        if (x>=catNum && x<catNum+birdNum) return 1;
        
        if (x>=catNum+birdNum && x<catNum+birdNum+bugNum) return 2;
        
        if (x>=catNum+birdNum+bugNum && x<catNum+birdNum+bugNum+plantNum) return 3;
        
        if (x>=catNum+birdNum+bugNum+plantNum && x<catNum+birdNum+bugNum+plantNum+treeNum) return 4;
        
        if (x>=catNum+birdNum+bugNum+plantNum+treeNum && x<catNum+birdNum+bugNum+plantNum+treeNum+rockNum) return 5;
        
        return -1;
    }
    
    public void paintComponent(Graphics g)
   {
        super.paintComponent(g);
        
        checkIfEaten();
        
        int last=catNum;
        for(int i=0;i<last;i++)
        {
            if (isRemove[i]==false)
            g.drawImage(catImg,pos_x[i],pos_y[i],this);
        }
        last+=birdNum;
        for(int i=last-birdNum;i<last;i++){
            if (isRemove[i]==false)
                g.drawImage(birdImg,pos_x[i],pos_y[i],this);
        }
        last+=bugNum;
        for(int i=last-bugNum;i<last;i++){
            if (isRemove[i]==false)
                g.drawImage(bugImg,pos_x[i],pos_y[i],this);
        }
        last+=plantNum;
        for(int i=last-plantNum;i<last;i++){
            if (isRemove[i]==false) 
                g.drawImage(plantImg,pos_x[i],pos_y[i],this);
        }
        last+=treeNum;
        for(int i=last-treeNum;i<last;i++){
            if (isRemove[i]==false) g.drawImage(treeImg,pos_x[i],pos_y[i],this);
        }
        last+=rockNum;
        for(int i=last-rockNum;i<last;i++){
            if (isRemove[i]==false) 
                g.drawImage(rockImg,pos_x[i],pos_y[i],this);
        }
   }
    
    static boolean check(int i,int j)
    {
        int x=pos_x[i],y=pos_y[i];
        int a=pos_x[j],b=pos_y[j];

        int sz_i=imageSize[getId(i)],sz_j=imageSize[getId(j)];

        if ((x>=a && x<=a+sz_j) &&(y>=b && y<=b+sz_j)) return true;
        x+=sz_i;
        if ((x>=a && x<=a+sz_j) &&(y>=b && y<=b+sz_j)) return true;
        y+=sz_i;
        if ((x>=a && x<=a+sz_j) &&(y>=b && y<=b+sz_j)) return true;
        x-=sz_i;
        if ((x>=a && x<=a+sz_j) &&(y>=b && y<=b+sz_j)) return true;
        return false;
    }
    public static void checkIfEaten()
    {
        for(int i=catNum;i<catNum+birdNum;i++) //bird
        {
            if (isRemove[i]==true) continue;     
            boolean eaten=false;
            for(int j=0;j<catNum;j++) //cat
            {
                if (isRemove[j]==false && check(i,j)==true)
                {
                    eaten=true;
                  //  System.out.println(i);
                    break;
                }
            }
            isRemove[i]=eaten;
        }
        for(int i=catNum+birdNum;i<catNum+birdNum+bugNum;i++) //bug
        {
            if (isRemove[i]==true) continue;
            boolean eaten=false;
            for(int j=catNum;j<catNum+birdNum;j++) //bird
            {
                if (isRemove[j]==false&& check(i,j)==true)
                {
                    eaten=true;
                    break;
                }
            }
            isRemove[i]=eaten;
        }
        
        for(int i=catNum+birdNum+bugNum;i<catNum+birdNum+bugNum+plantNum;i++) //plant
        {
            if (isRemove[i]==true) continue;
            
            boolean eaten=false;
            for(int j=catNum+birdNum;j<catNum+birdNum+bugNum;j++) //bug
            {
                if (isRemove[j]==false && check(i,j)==true)
                {
                    eaten=true;
                    break;
                }
            }
            isRemove[i]=eaten;
        }
    }
    @Override
    public void run() {
        while(true)
            repaint();
    }

}

class myThread implements Runnable
 {

    double angle;
    int id;     
    int d=3;
    public static int mod=5;
    
    int width=1200,height=700;
    int DELAY=80;
    static boolean simulation=true;
    
    myThread(int id)
    {
        angle=0;
        this.id=id;
    }
    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        int cnt=0;
        while(true)
        {
            if (simulation==false) 
            {   
                continue;
            }
            int id1=mainFunction.getId(id);
            if (id1==mainFunction.plantId)
            {
                if (mainFunction.isRemove[id]==true)
                {
                    mainFunction.pos_x[id]=mainFunction.randInt(mainFunction.imageSize[id1], width);
                    mainFunction.pos_y[id]=mainFunction.randInt(mainFunction.imageSize[id1], height);
                    mainFunction.isRemove[id]=false;
                }
                continue;
            }
            if (cnt>30)
            {
                cnt=0;
                angle=(Math.random()*360)%360;
                do
                {
                    d=(int) ((Math.random()*mod)%mod);
                }while(d<3);
            }
                
            cnt++;
            mainFunction.pos_x[id]+=Math.cos(angle)*d;
            mainFunction.pos_y[id]+=Math.sin(angle)*d;
            
            
            if (mainFunction.pos_x[id]+mainFunction.imageSize[mainFunction.getId(id)]>width)
            {
                cnt=0;
                mainFunction.pos_x[id]=width-mainFunction.imageSize[mainFunction.getId(id)];
                angle=(Math.random()*360)%360;
            }
            else if (mainFunction.pos_y[id]+mainFunction.imageSize[mainFunction.getId(id)]>height)
            {
                cnt=0;
                mainFunction.pos_y[id]=height-mainFunction.imageSize[mainFunction.getId(id)];
                angle=(Math.random()*360)%360;
            }
            if (mainFunction.pos_x[id]<0)
            {
                mainFunction.pos_x[id]=0;
                angle=(Math.random()*360)%360;
            }
            if (mainFunction.pos_y[id]<0) 
            {
                mainFunction.pos_y[id]=0;
                angle=(Math.random()*360)%360;
            }
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
            
        }
    }
}
         
public class DrawImageOnJFrame extends JFrame
{
    public DrawImageOnJFrame()
    {
    super("Artificial Life Simulator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200,700);
    setResizable(false);
    setVisible(true);
    add(new mainFunction(1200,700));
    JMenuBar menubar=new JMenuBar();
    
    JMenu simulation=new JMenu("Simulation");
    JMenu help=new JMenu("Help");
    JMenuItem startPause=new JMenuItem("Start/Pause Simulation");
    JMenuItem speed=new JMenuItem("Increase speed");
    JMenuItem speed1=new JMenuItem("Decrease speed");
    
    startPause.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("hh");
            if (myThread.simulation==true) myThread.simulation=false;
            else myThread.simulation=true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    });
    
    speed.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
             System.out.println("hh");
            myThread.mod+=2;
            JOptionPane.showMessageDialog(null,"speed is now "+myThread.mod+" unit");
        }
    });
    speed1.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
             System.out.println("hh");
            if (myThread.mod>=2)
            myThread.mod-=2;
            else JOptionPane.showMessageDialog(null,"Can't decrease");
        }
    });
    
    help.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent evt)
        {
            JOptionPane.showMessageDialog(null,"Tiger eat birds\nbird eat bug\nbug eat plants");
        }
    });
    
    
    simulation.add(startPause);
    simulation.add(speed);
    simulation.add(speed1);
    
    menubar.add(simulation);
    menubar.add(help);
    
    setJMenuBar(menubar);
   }



   
}
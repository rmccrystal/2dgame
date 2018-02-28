package mccrystal.ryan;

import mccrystal.ryan.entities.Ground;
import mccrystal.ryan.entities.Player;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable, Renderable {
    private JFrame frm = new JFrame();

    public static final int DEFAULT_WINDOW_LENGTH = 1920; //Default window height and length if no init value is given
    public static final int DEFAULT_WINDOW_HEIGHT = 1080;
    public static final String WINDOWTITLE = "Platformer";

    public static final long TICKRATE = 60; //Should be 60
    public static int ticks = 0;

    private int windowLength = DEFAULT_WINDOW_LENGTH;
    private int windowHeight = DEFAULT_WINDOW_HEIGHT;

    private KeyHandler keyHandler = new KeyHandler();

    private World currentWorld = new World(this, true, 0.5f, Color.BLACK);

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    private boolean isRunning = false; //Bool if game is running
    private Thread thread;
    private Thread renderThread;

    public Game() {
        this.setPreferredSize(new Dimension(DEFAULT_WINDOW_LENGTH, DEFAULT_WINDOW_HEIGHT));
        this.setFocusable(true);
        this.requestFocus();

        frm.setTitle(WINDOWTITLE);
        frm.setSize(DEFAULT_WINDOW_LENGTH, DEFAULT_WINDOW_HEIGHT);
        frm.add(this);
        frm.setResizable(true);
        frm.pack();
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        currentWorld.render(g2d);
        this.render(g2d);
    }

    public void run() {
        init();
        while(isRunning) {
            long time = System.nanoTime();
            long sleepAmount = 1000 / Game.TICKRATE;
            tick();
            renderAll();
            long msPassed = (System.nanoTime() - time)/1000000;
            sleepAmount = sleepAmount - msPassed;  //Subtract the number of ms it took to tick and render to make it have consistent tickrate
            sleepAmount = (sleepAmount > 0) ? sleepAmount : 0;  //If it took longer than one tick, set the delay to zero.
            try {
                Thread.sleep(sleepAmount);
            } catch(InterruptedException e) {
                stopGame();
            }
            //System.out.println(sleepAmount + msPassed);
//            long renderTime = (System.nanoTime() - time)/1000000;
//            System.out.println("Tick and render took " + renderTime + " ms. Should take " + totalSleepAmount + " ms.\n" +
//            "Tick and render was " + Math.abs(renderTime - sleepAmount) + " ms off\n");
        }
    }

    private void init() {
        entityInit();
        addKeyListener(getKeyHandler());
    }

    private void entityInit() {
        float friciton = 0.9f;
        Entity player = new Player(500, 100, 100, 200);
        currentWorld.addEntity(player);//Create a new player object for testing
        currentWorld.addEntity(new Ground(0, 900, 2000, 60, friciton,  Color.GREEN));
        currentWorld.addEntity(new Ground(1200, 700, 200, 20, friciton, Color.MAGENTA));
        currentWorld.addEntity(new Ground(600, 500, 200, 10, friciton, Color.YELLOW));
    }

    private void tick() {
        currentWorld.tick();
        ticks++;
    }
    private void renderAll() {
        Toolkit.getDefaultToolkit().sync(); //Running this fixes framerate issues on Linux
        repaint();
    }
    public void render(Graphics2D g) {
        int a = 50;
        for(Integer i : getKeyHandler().getKeysDown()) {
            g.setColor(Color.WHITE);
            g.drawString(i.toString(), 100, a);
            a+=10;
        }
    }
    public synchronized void runGame() {
        if(!this.isRunning) { //Only start if game is not already running
            this.isRunning = true;
            this.thread = new Thread(this); //Create new thread of the run() function
            this.thread.start();
            /*
            this.renderThread = new Thread(new Runnable() { //New thread to render everything much faster than tickrate
                @Override
                public void run() {
                    while(isRunning) {          //This code is only necissary if frames change every tick
                        render();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            isRunning = false;
                        }
                    }
                }
            });
            this.renderThread.start();
            */
        }
    }
    public synchronized void stopGame() {
        isRunning = false;
        this.thread.interrupt();
    }
}
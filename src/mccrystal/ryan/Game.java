package mccrystal.ryan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mccrystal.ryan.entities.Ground;
import mccrystal.ryan.entities.Player;
import mccrystal.ryan.worlds.TestLevel;

public class Game extends JPanel implements Runnable { //TODO: Make window resizable using FrameBuffers
    public JFrame frm = new JFrame();

    public RenderManager getRenderManager() {
        return renderManager;
    }

    public Player player = new Player(500, 100, 100, 200);

    public RenderManager renderManager = new RenderManager(1f, this);

    public static final boolean DEBUG = true;

    public static final int DEFAULT_WINDOW_LENGTH = 1920; //Default window height and length if no init value is given
    public static final int DEFAULT_WINDOW_HEIGHT = 1080;
    public static final String WINDOWTITLE = "Platformer";

    public static final long TICKRATE = 60; //Should be 60
    public static int ticks = 0;

    private int windowLength = DEFAULT_WINDOW_LENGTH;
    private int windowHeight = DEFAULT_WINDOW_HEIGHT;

    private KeyHandler keyHandler = new KeyHandler();

    private World currentWorld = new TestLevel(this, true);

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

    /** This runs when the game starts */
    public void run() {
        init(); //Initalize the game
        while(isRunning) {
            mainLoop(); //Keep on running the main loop
        }
    }
    /** Main loop of the Game. Handles the ticks rate */
    private void mainLoop() {
            long time = System.nanoTime();
            long sleepAmount = getTickDelay();
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

    private int getTickDelay() {
        long delay = 1000 / Game.TICKRATE;
        return (int) delay;
    }

    private void init() {
        entityInit();
        addKeyListener(getKeyHandler());
        zoomKeysInit();
    }

    private void zoomKeysInit() {
        float zoomAmount = 0.2f;
        getKeyHandler().addEvent(61, () -> {
            getRenderManager().setScale(getRenderManager().getScale()+zoomAmount); //+ key
        });
        getKeyHandler().addEvent(45, () -> {
            getRenderManager().setScale(getRenderManager().getScale()-zoomAmount); //- key
        });
    }

    private void entityInit() {
        currentWorld.addEntity(player);//Create a new player object for testing
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
        if(DEBUG) {
            int a = 50;
            for (Integer i : getKeyHandler().getKeysDown()) {
                g.setColor(Color.WHITE);
                g.drawString(i.toString(), 100, a);
                a += 10;
            }
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

    public static void debugPrint(Object text) {
        if(Game.DEBUG) {
            System.out.println(text);
        }
    }
}
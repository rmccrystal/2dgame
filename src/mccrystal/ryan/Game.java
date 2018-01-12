package mccrystal.ryan;

import mccrystal.ryan.entities.Player;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
    private JFrame frm = new JFrame();

    public static final int DEFAULT_WINDOW_LENGTH = 1920; //Default window height and length if no init value is given
    public static final int DEFAULT_WINDOW_HEIGHT = 1080;
    public static final String WINDOWTITLE = "Platformer";

    public static final long TICKRATE = 2;
    public static int ticks = 0;
    private int windowLength = DEFAULT_WINDOW_LENGTH;
    private int windowHeight = DEFAULT_WINDOW_HEIGHT;

    private World currentWorld = new World(true, 1, Color.BLACK) {
        @Override
        public void render(Graphics2D g) {
            super.render(g);
        }
    };

    private boolean isRunning = false; //Bool if game is running
    private Thread thread;
    private Thread renderThread;

    public Game() {
        frm.setTitle(WINDOWTITLE);
        frm.setSize(DEFAULT_WINDOW_LENGTH, DEFAULT_WINDOW_HEIGHT);
        frm.add(this);
        frm.setResizable(false);
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        currentWorld.render(g2d);
        for (Entity e : currentWorld.getEntityList()) {
            e.render(g2d);
        }
    }

    public void run() {
        init();
        while(isRunning) {
            tick();
            try {
                this.thread.sleep((long) (1.0 / Game.TICKRATE) * 1000); //TODO: Fix this running super fast
            } catch(InterruptedException e) {
                stopGame();
            }
        }
    }
    private void init() {
        Entity player = new Player(500, 800, 100, 200);
        currentWorld.addEntity(player);//Create a new player object for testing
    }
    private void tick() {
        currentWorld.tick();
        for(Entity e : currentWorld.getEntityList()) {
            e.tick();
        }
        System.out.println(ticks++);
    }
    private void render() {
        repaint();
    }

    public synchronized void runGame() {
        if(!this.isRunning) { //Only start if game is not already running
            this.isRunning = true;
            this.thread = new Thread(this); //Create new thread of the run() function
            this.thread.start();
            this.renderThread = new Thread(new Runnable() { //New thread to render everything much faster than tickrate
                @Override
                public void run() {
                    while(isRunning) {
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
        }
    }
    public synchronized void stopGame() {
        isRunning = false;
        this.thread.interrupt();
    }
}
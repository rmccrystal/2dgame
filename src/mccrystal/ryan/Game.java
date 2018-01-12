package mccrystal.ryan;

public class Game implements Runnable {

    public static final int DEFAULT_WINDOW_LENGTH = 1920; //Default window height and length if no init value is given
    public static final int DEFAULT_WINDOW_HEIGHT = 1080;

    public static final int TICKRATE = 16;

    private int windowLength = DEFAULT_WINDOW_LENGTH;
    private int windowHeight = DEFAULT_WINDOW_HEIGHT;
    private String windowTitle = "Platformer";

    private boolean isRunning = false; //Bool if game is running
    private Thread thread;

    public Game(int windowLength, int windowHeight, String title) {
        this.windowLength = windowLength;
        this.windowHeight = windowHeight;
        this.windowTitle = title;
    }

    public Game() {}

    public void run() {
        this.init();
        while(isRunning) {
            tick(); //TODO: Make tick rate run at constant speed and render rate run as fast as it can
            render(); //TODO: Only do if something is changing between ticks (ex. Animations, etc.)
            try {
                Thread.sleep((1 / Game.TICKRATE) * 1000);
            } catch(InterruptedException e) {
                this.stopGame();
            }
        }
    }
    private void init() {}
    private void tick() {}
    private void render() {}

    public synchronized void runGame() {
        if(!this.isRunning) { //Only start if game is not already running
            this.isRunning = true;
            this.thread = new Thread(this); //Create new thread of the run() function
            this.thread.start();
        }
    }
    public synchronized void stopGame() {
        isRunning = false;
    }
}
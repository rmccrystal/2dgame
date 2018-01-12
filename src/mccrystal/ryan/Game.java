package mccrystal.ryan;

public class Game implements Runnable {

    public static final int DEFAULT_WINDOW_LENGTH = 1920; //Default window height and length if no init value is given
    public static final int DEFAULT_WINDOW_HEIGHT = 1080;

    public int windowLength = DEFAULT_WINDOW_LENGTH;
    public int windowHeight = DEFAULT_WINDOW_HEIGHT;
    public int windowTitle = "2dgame"

    public boolean isRunning = false; //Bool if game is running
    private Thread thread;

    public Game(int windowLength, int windowHeight, String title) {
        this.windowLength = windowLength;
        this.windowHeight = windowHeight;
        this.windowTitle = title;
    }

    public Game() {}

    public void run() {}

    private void init() {}
    private void tick() {}
    private void render() {}

    public synchronized void runGame() {
        if(!running) { //Only start if game is not already running
            running = true;
            this.thread = new Thread(this); //Create new thread of the run() function
            this.thread.
        }
    }
    public synchronized void stopGame() {}
}
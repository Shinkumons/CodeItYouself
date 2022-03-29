package CodeItYourself;

import org.lwjgl.glfw.GLFW;
import engine.io.Window;
import engine.io.Input;

public class App implements Runnable {
    public Thread game;
    public Window window;
    public int frame;
    public long time;
    
    
    public void start () {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        System.out.println("Initializing game !");
        window = new Window(1280,720,"CodeItYourself");
        window.setBackgroundColor(1.0f,0,0);
        window.create();
        time = System.currentTimeMillis();
    }

    public void run() {
        init();
        while(!window.actionClose()){
            update();
            render();
            if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) return;

            if(Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
        }

        window.destroyAllCallback();
    }

    private void update() {
        window.update();
        frame++;
        if (System.currentTimeMillis() > (time + 1000)) {
            System.out.println(frame);
            time = System.currentTimeMillis();
            frame=0;
        }

        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.setBackgroundColor(0,1.0f,0);
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) window.setBackgroundColor(1.0f,0,0);
    }

    private void render() {
        window.swapBuffers(); 
    }

    public static void main (String[] args) {
        new App().start();
    }
}


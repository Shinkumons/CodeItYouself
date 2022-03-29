package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {

    private String title;
    private int width, height;
    public static long window;
    public Input input;
    private float backgroundR, backgroundG, backgroundB;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR : Something wrong happen, please restart the game\n GLFW doesn't initialized correctly");
            return;
        }

        input = new Input();

        window = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0, 0);

        if (window == 0) {
            System.err.println("ERROR : Something wrong happen, please restart the game\n Window doesn't initialized correctly");
            return;
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videoMode.width() - this.width)/2, (videoMode.height() - this.height)/2);
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMousePosCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        
        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1);
    }

    public void destroyAllCallback() {
        input.destroyCallbacks();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
    }

    public void update() {
        GL11.glClearColor(backgroundR, backgroundG, backgroundB, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GLFW.glfwPollEvents();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public void setBackgroundColor(float r, float g, float b){
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
    }

    public boolean actionClose() {
        return GLFW.glfwWindowShouldClose(window);
    }
}

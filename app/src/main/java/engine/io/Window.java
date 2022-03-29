package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.glfw.GLFWWindowSizeCallback;


public class Window {

    private String title;
    private int width, height;
    public static long window;
    public Input input;
    private float backgroundR, backgroundG, backgroundB;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResize;
    private boolean isfullscreen = false;
    private int[] windowPosX, windowPosY;
        
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

        window = GLFW.glfwCreateWindow(this.width, this.height, this.title, isFullscreen() ? GLFW.glfwGetPrimaryMonitor() : 0, 0);

        if (window == 0) {
            System.err.println("ERROR : Something wrong happen, please restart the game\n Window doesn't initialized correctly");
            return;
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videoMode.width() - this.width)/2, (videoMode.height() - this.height)/2);
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        
        createCallbacks();
        
        GLFW.glfwShowWindow(window);

        GLFW.glfwSwapInterval(1);
    }

    private void createCallbacks(){
        sizeCallback = new GLFWWindowSizeCallback() {
                public void invoke(long window, int w, int h) {
                    width = w;
                    height = h;
                    isResize = true;
                }
            };
        
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMousePosCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }
    

    public void destroyAllCallback() {
        input.destroyCallbacks();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
    }

    public void update() {

        if (isResize){
            GL11.glViewport(0, 0, width, height);
            isResize = false;
        }
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

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public String getTitle(){
        return title;
    }

    public static long getWindow(){
        return window;
    }

    public boolean isFullscreen(){
        return isfullscreen;
    }

    public void setResolution(int w, int h){
        width = w;
        height = h;
        if (isFullscreen()) {
                GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
                GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        } else {
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }
    }

    public void setFullscreen(boolean val){
        isfullscreen = val;
        isResize = true;

        width = 1920;
        height = 1080;

        if (isFullscreen()) {
                GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
                GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
            } else {
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
            }
    }

}

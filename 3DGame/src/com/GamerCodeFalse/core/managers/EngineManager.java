package com.GamerCodeFalse.core.managers;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.GamerCodeFalse.main.Game;
import com.GamerCodeFalse.main.Main;

public class EngineManager {
	public static final long NANOSECOND = 1000000000L;
	
	public static int FPS;
	public static int UPS;
	
	private static final int FPS_LIMIT = 120;
	private static final int UPS_LIMIT = 200;
	
	private boolean isRunning;
	
	private WindowManager windowManager;
	private GLFWErrorCallback errorCallback;
	private Game game;
	
	private void init() throws Exception{
		GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
		windowManager = Main.getWindowManager();
		windowManager.init();
		
		game = new Game();
		game.init();
	}

	public void start() throws Exception{
		init();
		
		if(isRunning) return;
		run();
	}
	
	public void stop() {
		if(!isRunning) return;
		isRunning = false;
	}

	public void run() { 
		this.isRunning = true;
		
		long initialTime = System.nanoTime();
        final double timeU = 1000000000.0 / UPS_LIMIT;
        final double timeF = 1000000000.0 / FPS_LIMIT;
        double deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        while (this.isRunning) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;
            
            input();
            
            if(windowManager.windowShouldClose()) stop();

            if (deltaU >= 1) {
                update();
                ticks++;
                deltaU--;
            }

            if (deltaF >= 1) {
            	render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                
                FPS = frames;
                UPS = ticks;
                
                windowManager.setTitle("3D Game"+ String.format(" [FPS: %1$s, UPS: %2$s]", frames, ticks));
                
                frames = 0;
                ticks = 0;
            }
        }	
		
		clean();
	}

	private void render() {
		game.render();
		windowManager.update();
	}
	private void update() {
		game.update();
	}
	
	public void input() {
		game.input();
	}
	
	private void clean() {
		game.clean();
		windowManager.clean();
		errorCallback.free();
		GLFW.glfwTerminate();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
}

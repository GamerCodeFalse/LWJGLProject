package com.GamerCodeFalse.core.managers;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class WindowManager {
	public static final float FOV = (float)(Math.toRadians(90));
	public static final float Z_NEAR = 0.01f;
	public static final float Z_FAR = 1000f;
	
	private String title;
	
	private int width, height;
	private long window;
	
	private boolean resize, vSync;
	
	private final Matrix4f projectionMatrix;
	
	public WindowManager(String title, int width, int height, boolean vSync) {
		this.title = title;
		
		this.setWidth(width);
		this.setHeight(height);
		this.setvSync(vSync);
		
		projectionMatrix = new Matrix4f();
	}
	
	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!GLFW.glfwInit()) throw new IllegalStateException("Unable to Initiliaze GLFW");
		
		GLFW.glfwDefaultWindowHints();
		
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		
		boolean maximized = false;
		if(getWidth() == 0 || getHeight() == 0) {
			setWidth(400);
			setHeight(400);
			GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
			maximized = true;
 		}
		
		window = GLFW.glfwCreateWindow(getWidth(), getHeight(), getTitle(), MemoryUtil.NULL, MemoryUtil.NULL);
		
		if(window == MemoryUtil.NULL) throw new RuntimeException("Failed to Create GLFW Window");
		
		GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
			this.setWidth(width);
			this.setHeight(height);
			
			this.setResize(true);
		});
		
		GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(window, true);
		});
		
		if(maximized) GLFW.glfwMaximizeWindow(window);
		else {
			GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			GLFW.glfwSetWindowPos(window, (vidMode.width() - getWidth())/2, (vidMode.height() - getHeight())/2);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		
		if(isvSync()) GLFW.glfwSwapInterval(1);
		
		GLFW.glfwShowWindow(window);
		GL.createCapabilities();
		
		GL11.glClearColor(0, 0, 0, 0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public void setClearColor(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
	}
	
	public void update() {
		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}
	
	public Matrix4f updateProjectionMatrix() {
		float aspectRatio = (float) width / height;
		return projectionMatrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
	} 
	
	
	public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
		float aspectRatio = (float) width / height;
		return matrix.setPerspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
	} 
	
	public void clean() {
		GLFW.glfwDestroyWindow(window);
	}
	
	public boolean isKeyPressed(int keyCode) {
		return GLFW.glfwGetKey(window, keyCode) == GLFW.GLFW_PRESS;
	}
	
	public boolean windowShouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}

	public boolean isResize() {
		return resize;
	}

	public void setResize(boolean resize) {
		this.resize = resize;
	}

	public boolean isvSync() {
		return vSync;
	}

	public void setvSync(boolean vSync) {
		this.vSync = vSync;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
		GLFW.glfwSetWindowTitle(window, title);
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}

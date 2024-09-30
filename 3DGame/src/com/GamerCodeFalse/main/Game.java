 package com.GamerCodeFalse.main;

import org.joml.Math;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.GamerCodeFalse.core.entity.Model;
import com.GamerCodeFalse.core.entity.ObjectLoader;
import com.GamerCodeFalse.core.utilz.GameLogic;

public class Game implements GameLogic {

	private int direction = 0;
	private float color = 0;
	
	private final ObjectLoader loader;
	
	private Model model;
	
	public Game() {
		loader = new ObjectLoader();
				
		model = loader.loadModel(
				new float[] {
	                -0.5f, 0.5f, 0f,
	                -0.5f, -0.5f, 0f,
	                0.5f, -0.5f, 0f,
	                0.5f, -0.5f, 0f,
	                0.5f, 0.5f, 0f,
	                -0.5f, 0.5f, 0f
				},
				new int[] {
						0, 1, 3,
						3, 1, 2
				}
			);
	}
	
	@Override
	public void init() throws Exception {
		rendererManager.init();

	}

	@Override
	public void input() {
		if(windowManager.isKeyPressed(GLFW.GLFW_KEY_UP)) direction = 1;
		else if(windowManager.isKeyPressed(GLFW.GLFW_KEY_DOWN)) direction = -1;
		else direction = 0;
	}

	@Override
	public void update() {
		color += direction * 0.01f;
		color = Math.clamp(0.0f, 1.0f, color);
	}

	@Override
	public void render() {
		if(windowManager.isResize()) {
			GL11.glViewport(0, 0, windowManager.getWidth(), windowManager.getHeight());
			windowManager.setResize(true);		
		}
		
		windowManager.setClearColor(color, color, color, 0.0f);
		rendererManager.render(model);
	}

	@Override
	public void clean() {
		rendererManager.clean();
		loader.clean();
	}

}

package com.GamerCodeFalse.core.managers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.GamerCodeFalse.core.entity.Model;
import com.GamerCodeFalse.core.utilz.Utilz;
import com.GamerCodeFalse.main.Main;

public class RendererManager {
	private WindowManager windowManager;
	private ShaderManager shaderManager;
	
	public RendererManager() {
		setWindowManager(Main.getWindowManager());
	}
	
	public void init() throws Exception {
		shaderManager = new ShaderManager();
		
		shaderManager.createVertexShader(Utilz.loadResource("./assets/shaders/vertex.vs"));
		shaderManager.createFragmentShader(Utilz.loadResource("./assets/shaders/fragment.fs"));
		
		shaderManager.link();
	}
	
	public void render(Model model) {
		clear();
		
		shaderManager.bind();
		
		GL30.glBindVertexArray(model.getId());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shaderManager.unbind();
	}
	
	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void clean() {
		shaderManager.clean();
	}

	public WindowManager getWindowManager() {
		return windowManager;
	}

	public void setWindowManager(WindowManager windowManager) {
		this.windowManager = windowManager;
	}
}

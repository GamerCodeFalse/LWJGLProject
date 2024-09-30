package com.GamerCodeFalse.core.utilz;

import com.GamerCodeFalse.core.managers.RendererManager;
import com.GamerCodeFalse.core.managers.WindowManager;
import com.GamerCodeFalse.main.Main;

public interface GameLogic {
	
	final RendererManager rendererManager = new RendererManager();
	final WindowManager windowManager = Main.getWindowManager();
	
	void init() throws Exception;
	void input();
	void update();
	void render();
	void clean();	
}

package com.GamerCodeFalse.main;

import org.lwjgl.Version;

import com.GamerCodeFalse.core.managers.EngineManager;
import com.GamerCodeFalse.core.managers.WindowManager;

public class Main {

	private static WindowManager windowManager;
	private static EngineManager engineManager;
	
	public static void main(String[] args) {
		System.out.println(Version.getVersion());
		
		windowManager = new WindowManager("3D Game", 1600, 900, false);
		engineManager = new EngineManager();
		
		try {
			engineManager.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static WindowManager getWindowManager() {
		return windowManager;
	}

	public static void setWindowManager(WindowManager windowManager) {
		Main.windowManager = windowManager;
	}

}

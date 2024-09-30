package com.GamerCodeFalse.core.utilz;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.lwjgl.system.MemoryUtil;

public class Utilz {
	public static FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
		buffer.put(data).flip();
		
		return buffer;
	}
	
	public static IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
		buffer.put(data).flip();
		
		return buffer;
	}
	
	public static String loadResource(String file) throws Exception {
		String toReturn;
		try(InputStream in = new FileInputStream(new File(file)); ){
			Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name());
			toReturn = scanner.useDelimiter("\\A").next();
		}
		return toReturn;
	}
}

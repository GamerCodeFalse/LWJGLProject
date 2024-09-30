package com.GamerCodeFalse.core.entity;

public class Model {
	private int id;
	private int vertexCount;
	
	public Model(int id, int vertexCount) {
		this.setId(id);
		this.setVertexCount(vertexCount);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}
}

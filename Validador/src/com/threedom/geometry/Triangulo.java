package com.threedom.geometry;

public class Triangulo {
	
	private Vertice verticeA;
	private Vertice verticeB;
	private Vertice verticeC;
	
	public Triangulo() {
		
	}

	public Triangulo(Vertice verticeA, Vertice verticeB, Vertice verticeC) {
		super();
		this.verticeA = verticeA;
		this.verticeB = verticeB;
		this.verticeC = verticeC;
	}

	public Vertice getVerticeA() {
		return verticeA;
	}

	public void setVerticeA(Vertice verticeA) {
		this.verticeA = verticeA;
	}

	public Vertice getVerticeB() {
		return verticeB;
	}

	public void setVerticeB(Vertice verticeB) {
		this.verticeB = verticeB;
	}

	public Vertice getVerticeC() {
		return verticeC;
	}

	public void setVerticeC(Vertice verticeC) {
		this.verticeC = verticeC;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s",this.verticeA,this.verticeB,this.verticeC);
	}
}

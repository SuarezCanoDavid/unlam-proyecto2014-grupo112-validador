package com.threedom;

import java.util.ArrayList;
import java.util.Iterator;

public class Objeto3D {
	
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private ArrayList<Triangulo> triangulos = new ArrayList<Triangulo>();
	
	public Objeto3D() {
		
	}
	
	public void addTriangulo(Triangulo triangulo) {
		if(!vertices.contains(triangulo.getVerticeA())) {
			vertices.add(triangulo.getVerticeA());
		}
		
		if(!vertices.contains(triangulo.getVerticeB())) {
			vertices.add(triangulo.getVerticeB());
		}
		
		if(!vertices.contains(triangulo.getVerticeC())) {
			vertices.add(triangulo.getVerticeC());
		}
		
		triangulos.add(triangulo);
	}
	
	public void addTriangulo(Vertice verticeA, Vertice verticeB, Vertice verticeC) {
		Triangulo triangulo = new Triangulo();
		
		if(!vertices.contains(verticeA)) {
			vertices.add(verticeA);
			triangulo.setVerticeA(verticeA);
		} else {
			triangulo.setVerticeA(vertices.get(vertices.indexOf(verticeA)));
		}
		
		if(!vertices.contains(verticeB)) {
			vertices.add(verticeB);
			triangulo.setVerticeB(verticeB);
		} else {
			triangulo.setVerticeB(vertices.get(vertices.indexOf(verticeB)));
		}
		
		if(!vertices.contains(verticeC)) {
			vertices.add(verticeC);
			triangulo.setVerticeC(verticeC);
		} else {
			triangulo.setVerticeC(vertices.get(vertices.indexOf(verticeC)));
		}
		
		triangulos.add(triangulo);
	}
	
	public ArrayList<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertice> vertices) {
		this.vertices = vertices;
	}

	public ArrayList<Triangulo> getTriangulos() {
		return triangulos;
	}

	public void setTriangulos(ArrayList<Triangulo> triangulos) {
		this.triangulos = triangulos;
	}

	public void imprimir() {
		Iterator<Triangulo> it = triangulos.iterator();
		int i = 0;
		
		while(it.hasNext())
		{
			System.out.printf("Triangulo %d: %s\n", i, it.next());
			
			++i;
		}
	}
}

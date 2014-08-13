package com.threedom.geometry;

import com.threedom.algebra.Vector;

public class Triangulo {
	
	private Vertice verticeA;
	private Vertice verticeB;
	private Vertice verticeC;
    private Vector normal = null;
	
	public Triangulo() {
		
	}

    public Triangulo(Triangulo triangulo2) {
        this.verticeA = new Vertice(triangulo2.verticeA);
        this.verticeB = new Vertice(triangulo2.verticeB);
        this.verticeC = new Vertice(triangulo2.verticeC);
        
        this.calcularNormal();
    }
    
	public Triangulo(Vertice verticeA, Vertice verticeB, Vertice verticeC) {
		super();
		this.verticeA = verticeA;
		this.verticeB = verticeB;
		this.verticeC = verticeC;
        
        this.calcularNormal();
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

    public Vector getNormal() {
        return normal;
    }

    public void setNormal(Vector normal) {
        this.normal = normal;
    }

    private void calcularNormal() {
        Vector A = new Vector(verticeA.getX(),verticeA.getY(),verticeA.getZ());
        Vector B = new Vector(verticeB.getX(),verticeB.getY(),verticeB.getZ());
        Vector C = new Vector(verticeC.getX(),verticeC.getY(),verticeC.getZ());
        
        Vector AB = B.restar(A);
        Vector AC = C.restar(A);
        
        normal = AB.productoVectorial(AC);
        
        normal.normalizar();
    }
    
	@Override
	public String toString() {
		return String.format("%s %s %s %s",this.verticeA,this.verticeB,this.verticeC, this.normal);
	}
}

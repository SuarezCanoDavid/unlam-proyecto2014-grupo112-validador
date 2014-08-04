package com.threedom.geometry;

import java.util.ArrayList;
import java.util.Iterator;
import com.threedom.stlhelper.STLFileReader;
import com.threedom.algebra.Vector;

public class Objeto3D {
	
	private ArrayList<Vertice> vertices = new ArrayList<>();
	private ArrayList<Triangulo> triangulos = new ArrayList<>();
    private ArrayList<Vector> normales = new ArrayList<>();
    
    public Objeto3D(String nombreDeArchivo) {
        STLFileReader stlInput = new STLFileReader(nombreDeArchivo);
        
        for(int i = 0; i < stlInput.getCantidadDeTriangulos(); ++i) {
            stlInput.skipNormal();
            this.addTriangulo(stlInput.readNextTriangulo());
        }
        
        stlInput.close();
    }
	
	public void addTriangulo(Triangulo triangulo) {
		if(vertices.contains(triangulo.getVerticeA())) {
            triangulo.setVerticeA(vertices.get(vertices.indexOf(triangulo.getVerticeA())));
        } else {
            vertices.add(triangulo.getVerticeA());
        }
        
        if(vertices.contains(triangulo.getVerticeB())) {
            triangulo.setVerticeB(vertices.get(vertices.indexOf(triangulo.getVerticeB())));
        } else {
            vertices.add(triangulo.getVerticeB());
        }
        
        if(vertices.contains(triangulo.getVerticeC())) {
            triangulo.setVerticeC(vertices.get(vertices.indexOf(triangulo.getVerticeC())));
        } else {
            vertices.add(triangulo.getVerticeC());
        }
        
        if(normales.contains(triangulo.getNormal())) {
            triangulo.setNormal(normales.get(normales.indexOf(triangulo.getNormal())));
        } else {
            normales.add(triangulo.getNormal());
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

    public ArrayList<Vector> getNormales() {
        return normales;
    }

    public void setNormales(ArrayList<Vector> normales) {
        this.normales = normales;
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

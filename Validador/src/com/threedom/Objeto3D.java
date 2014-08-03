package com.threedom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Objeto3D {
	
	private ArrayList<Vertice> vertices = new ArrayList<>();
	private ArrayList<Triangulo> triangulos = new ArrayList<>();
	
	public Objeto3D() {
		
	}
    
    public Objeto3D(String nombreDeArchivo) throws FileNotFoundException, IOException {
        DataInputStream stlInput = new DataInputStream(
                new BufferedInputStream(new FileInputStream(nombreDeArchivo)));
        Vertice verticeA = new Vertice();
        Vertice verticeB = new Vertice();
        Vertice verticeC = new Vertice();
        int cantTriangulos = 0;
        byte[] buffer = new byte[4];
        
        stlInput.skipBytes(80);
        
        stlInput.read(buffer, 0, 4);
        
        cantTriangulos = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
        
        for(int i = 0; i < cantTriangulos; ++i) {
            stlInput.skipBytes(12);
            
            verticeA.setX(stlInput.readFloat());
            verticeA.setY(stlInput.readFloat());
            verticeA.setZ(stlInput.readFloat());
            
            verticeB.setX(stlInput.readFloat());
            verticeB.setY(stlInput.readFloat());
            verticeB.setZ(stlInput.readFloat());
            
            verticeC.setX(stlInput.readFloat());
            verticeC.setY(stlInput.readFloat());
            verticeC.setZ(stlInput.readFloat());
            
            this.addTriangulo(verticeA, verticeB, verticeC);
            
            stlInput.skipBytes(2);
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
		
		triangulos.add(triangulo);
	}
	
	public void addTriangulo(Vertice verticeA, Vertice verticeB, Vertice verticeC) {
		Triangulo triangulo = new Triangulo();
		
		if(vertices.contains(verticeA)) {
            triangulo.setVerticeA(vertices.get(vertices.indexOf(verticeA)));
        } else {
            vertices.add(verticeA);
            triangulo.setVerticeA(verticeA);
        }
		
		if(vertices.contains(verticeB)) {
            triangulo.setVerticeB(vertices.get(vertices.indexOf(verticeB)));
        } else {
            vertices.add(verticeB);
            triangulo.setVerticeB(verticeB);
        }
        
        if(vertices.contains(verticeC)) {
            triangulo.setVerticeC(vertices.get(vertices.indexOf(verticeC)));
        } else {
            vertices.add(verticeC);
            triangulo.setVerticeC(verticeC);
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

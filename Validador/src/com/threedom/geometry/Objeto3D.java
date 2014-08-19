package com.threedom.geometry;

import com.threedom.algebra.Matriz;
import java.util.ArrayList;
import java.util.Iterator;
import com.threedom.algebra.Vector;

public class Objeto3D {
	
	private ArrayList<Vertice> vertices = new ArrayList<>();
	private ArrayList<Triangulo> triangulos = new ArrayList<>();
    private ArrayList<Vector> normales = new ArrayList<>();
    private double IDM;
    
    public Objeto3D() {
        
    }
	
    public Objeto3D(Objeto3D obj2) {
        Iterator<Triangulo> itTriangulos = obj2.triangulos.iterator();
        
        while(itTriangulos.hasNext()) {
            this.addTriangulo(new Triangulo(itTriangulos.next()));
        }
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
	
    public void cargarConValoresDe(Objeto3D objeto2) {
        int cantidadDeVertices = objeto2.vertices.size();
        int cantidadDeNormales = objeto2.normales.size();
        
        for(int i = 0; i < cantidadDeVertices; ++i) {
            this.vertices.get(i).cargarConValoresDe(objeto2.vertices.get(i));
        }
        
        for(int i = 0; i < cantidadDeNormales; ++i) {
            this.normales.get(i).cargarConValoresDe(objeto2.normales.get(i));
        }
    }
    
    private Vertice determinarOrigen() {
        double maxX = this.vertices.get(0).getX();
        double minX = this.vertices.get(0).getX();
        double maxY = this.vertices.get(0).getY();
        double minY = this.vertices.get(0).getY();
        double minZ = this.vertices.get(0).getZ();
  
        for(Vertice v : this.vertices) {
            if(v.getX() > maxX) {
                maxX = v.getX();
            }
            
            if(v.getX() < minX) {
                minX = v.getX();
            }
            
            if(v.getY() > maxY) {
                maxY = v.getY();
            }
            
            if(v.getY() < minY) {
                minY = v.getY();
            }
            
            if(v.getZ() < minZ) {
                minZ = v.getZ();
            }
        }
        
        return new Vertice(minX+(maxX-minX)/2,minY+(maxY-minY)/2,minZ);
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

    public double getIDM() {
        return IDM;
    }

    public boolean intentarRotarSegunTriangulo(Triangulo triangulo) {        
        Matriz matrizDeCambioDeBase1 = Matriz.crearMatrizDeCambioDeBaseDeCanonicaA(triangulo);
        
        Vertice menorVerticeA = matrizDeCambioDeBase1.multiplicarYCrear(triangulo.getVerticeA());
        
        boolean rotacionOK = true;
        
        Iterator<Vertice> itVertices = this.vertices.iterator();
        Iterator<Vector> itNormales = this.normales.iterator();
        Vertice verticeAux;
   
        while(itVertices.hasNext() && rotacionOK) {
            verticeAux = itVertices.next();
            
            matrizDeCambioDeBase1.multiplicarYReemplazar(verticeAux);
            
            if(verticeAux.esMenorQue(menorVerticeA)) {
                rotacionOK = false;
            }
        }
        
        if(rotacionOK) {
            while(itNormales.hasNext()) {
                matrizDeCambioDeBase1.multiplicarYReemplazar(itNormales.next());
            }
        }
        
        return rotacionOK;
    }
    
    public void trasladarAOrigen() {
        Vertice origen = this.determinarOrigen();
        
        for(Vertice v : vertices) {
            v.restarYReemplazar(origen);
        }
    }
    
    public void calcularIDM() {
        Vertice verticeEnBase = new Vertice();
        Vertice verticeEnMax = new Vertice();
        double areaEnBase = 0;
        double areaEnMax = 0;
        double alturaTotal = 0;
        double alturaEnMax = 0;
        double xAux;
        double yAux;
        double zAux;
        double areaAux;
        
        verticeEnBase.cargarConValoresDe(vertices.get(0));
        verticeEnMax.cargarConValoresDe(vertices.get(0));
        
        for(Vertice v : vertices) {
            xAux = Math.abs(v.getX());
            yAux = Math.abs(v.getY());
            zAux = v.getZ();
            
            if(xAux > yAux) {
                areaAux = 4 * xAux * xAux;
            } else {
                areaAux = 4 * yAux * yAux;
            }
            
            if(areaAux >= areaEnMax) {
                areaEnMax = areaAux;
                alturaEnMax = zAux;
            }
            
            if(zAux == 0) {
                if(areaAux > areaEnBase) {
                    areaEnBase = areaAux;
                }
            }
            
            if(zAux > alturaTotal) {
                alturaTotal = zAux;
            }
        }
        
        this.IDM = (areaEnMax * (alturaEnMax/alturaTotal)) / areaEnBase;
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

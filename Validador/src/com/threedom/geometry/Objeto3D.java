package com.threedom.geometry;

import com.threedom.algebra.Matriz;
import java.util.ArrayList;
import java.util.Iterator;
import com.threedom.algebra.Vector;
import java.util.Collections;

public class Objeto3D {
	
	private ArrayList<Vertice> vertices = new ArrayList<>();
	private ArrayList<Triangulo> triangulos = new ArrayList<>();
    private ArrayList<Vector> normales = new ArrayList<>();
    private double IE;
    private double planoInferior;
    private double planoSuperior;
    
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
    
    public void ordenarTriangulos() {
        Collections.sort(triangulos, new ComparadorDeAreaDeTriangulo());
    }
    
    private Vertice determinarOrigen() {
        double maxX = this.vertices.get(0).getX();
        double minX = this.vertices.get(0).getX();
        double maxY = this.vertices.get(0).getY();
        double minY = this.vertices.get(0).getY();
        double maxZ = this.vertices.get(0).getZ();
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
            
            if(v.getZ() > maxZ) {
                maxZ = v.getZ();
            }
            
            if(v.getZ() < minZ) {
                minZ = v.getZ();
            }
        }
        
        planoInferior = minZ;
        planoSuperior = maxZ;
        
        return new Vertice(minX+(maxX-minX)/2,minY+(maxY-minY)/2,minZ);
    }
    
	public ArrayList<Vertice> getVertices() {
		return vertices;
	}

	public ArrayList<Triangulo> getTriangulos() {
		return triangulos;
	}

    public ArrayList<Vector> getNormales() {
        return normales;
    }

    public double getIE() {
        return IE;
    }

    public double getPlanoInferior() {
        return planoInferior;
    }

    public double getPlanoSuperior() {
        return planoSuperior;
    }

    public boolean rotarSegunTriangulo(Triangulo triangulo) {        
        Matriz matrizDeCambioDeBase = Matriz.crearMatrizDeCambioDeBaseDeCanonicaA(triangulo);
        
        Vertice menorVertice = matrizDeCambioDeBase.multiplicarYCrear(triangulo.getVerticeA());
        
        boolean sePuedeRotar = true;
        
        Iterator<Vertice> itVertices = this.vertices.iterator();
        Iterator<Vector> itNormales = this.normales.iterator();
        Vertice verticeAux;
   
        while(itVertices.hasNext()) {
            verticeAux = itVertices.next();
            
            matrizDeCambioDeBase.multiplicarYReemplazar(verticeAux);
            
            if(verticeAux.esMenorQue(menorVertice)) {
                sePuedeRotar = false;
            }
        }
        
        while(itNormales.hasNext()) {
            matrizDeCambioDeBase.multiplicarYReemplazar(itNormales.next());
        }
        
        return sePuedeRotar;
    }
    
    public void trasladarAOrigen() {
        Vertice origen = this.determinarOrigen();
        
        for(Vertice v : vertices) {
            v.restarYReemplazar(origen);
        }
        
        planoInferior -= origen.getZ();
        planoSuperior -= origen.getZ();
        
        determinarIE();
    }
    
    private void determinarIE() {
        Vertice verticeEnBase = new Vertice();
        Vertice verticeEnMax = new Vertice();
        double areaEnBase = 0;
        double areaEnMax = 0;
        double alturaTotal = planoSuperior - planoInferior;
        double alturaEnMax = planoInferior;
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
            
            if(zAux == planoInferior) {
                if(areaAux > areaEnBase) {
                    areaEnBase = areaAux;
                }
            }
        }
        
        this.IE = (areaEnMax * (alturaEnMax/alturaTotal)) / areaEnBase;
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

package com.threedom.geometry;

import com.threedom.algebra.Matriz;
import java.util.ArrayList;
import java.util.Iterator;
import com.threedom.algebra.Vector;
import com.threedom.algebra.MatrizDeRotacion;

public class Objeto3D {
	
	private ArrayList<Vertice> vertices = new ArrayList<>();
	private ArrayList<Triangulo> triangulos = new ArrayList<>();
    private ArrayList<Vector> normales = new ArrayList<>();
    private Vertice centro = null;
    
    public Objeto3D() {
        
    }
	
    public Objeto3D(Objeto3D obj2) {
        Iterator<Triangulo> itTriangulos = obj2.triangulos.iterator();
        
        while(itTriangulos.hasNext()) {
            this.addTriangulo(new Triangulo(itTriangulos.next()));
        }
        
        this.trasladarAOrigen();
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
        
        this.centro.cargarConValoresDe(objeto2.centro);
    }
    
    private Vertice determinarCentro() {
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
        
        return new Vertice(minX+(maxX-minX)/2,minY+(maxY-minY)/2,minZ+(maxZ-minZ)/2);
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
    
    public boolean intentarRotarSegunTriangulo(Triangulo triangulo) {
        /*Vector versorK = new Vector(0.0f,0.0f,-1.0f);
        
        Vector vectorDeRotacion = triangulo.getNormal().productoVectorial(versorK);
        
        double angulo = triangulo.getNormal().getAnguloEntre(versorK);
        
        System.out.printf("Vector: %s. Angulo: %f\n", vectorDeRotacion,Math.toDegrees(angulo));
        
        Matriz matrizDeRotacion = Matriz.crearMatrizDeRotacion(vectorDeRotacion, angulo);*/
        
        Matriz matrizDeRotacion = Matriz.crearMatrizDeCambioDeBaseDeCanonicaA(triangulo);
        
        /*Vertice menorVerticeA = matrizDeRotacion.multiplicarYCrear(triangulo.getVerticeA());
        Vertice menorVerticeB = matrizDeRotacion.multiplicarYCrear(triangulo.getVerticeB());
        Vertice menorVerticeC = matrizDeRotacion.multiplicarYCrear(triangulo.getVerticeC());*/
        
        boolean rotacionOK = true;
        
        Iterator<Vertice> itVertices = this.vertices.iterator();
        Iterator<Vector> itNormales = this.normales.iterator();
        Vertice verticeAux;
   
        while(itVertices.hasNext() && rotacionOK) {
            verticeAux = itVertices.next();
            
            matrizDeRotacion.multiplicarYReemplazar(verticeAux);
            
            /*if(verticeAux.esMenorQue(menorVerticeA)) {
                rotacionOK = false;
            }*/
        }
        
        if(rotacionOK) {
            while(itNormales.hasNext()) {
                matrizDeRotacion.multiplicarYReemplazar(itNormales.next());
            }
        }
        
        return rotacionOK;
    }
    
    public void trasladarAOrigen() {
        
        if(centro == null) {
            centro = determinarCentro();
        }
        
        for(Vertice v : vertices) {
            v.restarYReemplazar(centro);
        }
        
        centro.setX(0.0f);
        centro.setY(0.0f);
        centro.setZ(0.0f);
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

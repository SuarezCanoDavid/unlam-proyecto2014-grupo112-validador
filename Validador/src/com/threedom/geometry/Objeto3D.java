package com.threedom.geometry;

import com.threedom.algebra.Matriz;
import java.util.ArrayList;
import java.util.Iterator;
import com.threedom.algebra.Vector;
import java.util.Collections;

public class Objeto3D {
	
    private ArrayList<Vertice> vertices;
    private ArrayList<Triangulo> triangulos;
    private ArrayList<Vector> normales;
    private double IE;
    private double planoInferior;
    private double planoSuperior;
    private int proximoIndiceVertice = 0;
    private int proximoIndiceNormal = 0;
    
    public Objeto3D() {
        
    }
	
    public Objeto3D(Objeto3D obj2) {
        inicializarListas(obj2.getTriangulos().size());
        
        Iterator<Triangulo> itTriangulos = obj2.triangulos.iterator();
        
        while(itTriangulos.hasNext()) {
            this.addTriangulo(new Triangulo(itTriangulos.next()));
        }
    } 
    
    public void inicializarListas(int cantidadDeTriangulos) {
        triangulos = new ArrayList<Triangulo>(cantidadDeTriangulos);
        vertices = new ArrayList<Vertice>(cantidadDeTriangulos*3);
        normales = new ArrayList<Vector>(cantidadDeTriangulos);
    }
    
    public void addTriangulo(Triangulo triangulo) {
        int indice;
        
        indice = getIndiceDelVertice(triangulo.getVerticeA());
        
        if(indice >= 0) {
            triangulo.setVerticeA(vertices.get(indice));
        } else {
            vertices.add(triangulo.getVerticeA());
            ++proximoIndiceVertice;
        }

        indice = getIndiceDelVertice(triangulo.getVerticeB());
        
        if(indice >= 0) {
            triangulo.setVerticeB(vertices.get(indice));
        } else {
            vertices.add(triangulo.getVerticeB());
            ++proximoIndiceVertice;
        }

        indice = getIndiceDelVertice(triangulo.getVerticeC());
        
        if(indice >= 0) {
            triangulo.setVerticeC(vertices.get(indice));
        } else {
            vertices.add(triangulo.getVerticeC());
            ++proximoIndiceVertice;
        }

        indice = getIndiceDeLaNormal(triangulo.getNormal());
        
        if(indice >= 0) {
            triangulo.setNormal(normales.get(indice));
        } else {
            normales.add(triangulo.getNormal());
            ++proximoIndiceNormal;
        }

        triangulos.add(triangulo);
    }
    
    private int getIndiceDelVertice(Vertice vertice) {
        int indice;
        
        for(indice = proximoIndiceVertice-1; indice >= 0 && !vertices.get(indice).equals(vertice); --indice);
        
        return indice;
    }
    
    private int getIndiceDeLaNormal(Vector normal) {
        int indice;
        
        for(indice = proximoIndiceNormal-1; indice >= 0 && !normales.get(indice).equals(normal); --indice);
        
        return indice;
    }
    
    public void cargarConValoresDe(Objeto3D objeto2) {
        int cantidadDeTriangulos = objeto2.triangulos.size();
        
        for(int i = 0; i < cantidadDeTriangulos; ++i) {
            this.triangulos.get(i).cargarConValoresDe(objeto2.triangulos.get(i));
        }
    }
    
    public void ordenarTriangulos() {
        Collections.sort(triangulos, new ComparadorDeTriangulos());
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
        
        try {
            determinarIE();
        } catch(IndexOutOfBoundsException e){
            String mensaje = e.getMessage();
        }
        
    }
    
    private void determinarIE() throws IndexOutOfBoundsException {
        double areaEnBase = 0;
        double areaEnMax = 0;
        double alturaTotal = planoSuperior - planoInferior;
        double alturaEnMax = planoInferior;
        double xAux;
        double yAux;
        double zAux;
        double areaAux;
        int i;
        int cantVerticesInferior = 0;
        Vertice verticeCentro = null;
        
        for(i = 0; i < vertices.size() && vertices.get(i).getZ() != planoInferior; ++i);
        
        verticeCentro = new Vertice(vertices.get(i));
        
        for(++i; i < vertices.size(); ++i) {
            if(vertices.get(i).getZ() == planoInferior) {
                verticeCentro.sumarYReemplazar(vertices.get(i));
                ++cantVerticesInferior;
            }
        }
        
        verticeCentro.setX(verticeCentro.getX()/cantVerticesInferior);
        verticeCentro.setY(verticeCentro.getY()/cantVerticesInferior);
        verticeCentro.setZ(verticeCentro.getZ()/cantVerticesInferior);
        
        for(Vertice v : vertices) {
            xAux = v.getX()-verticeCentro.getX();
            yAux = v.getY()-verticeCentro.getY();
            zAux = v.getZ();
            
            areaAux = Math.PI * (xAux*xAux + yAux*yAux);
            
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
}

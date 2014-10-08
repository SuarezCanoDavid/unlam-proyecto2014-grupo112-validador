package com.threedom.geometry;

import com.threedom.algebra.Vector;

public class Triangulo {
	
    private Vertice verticeA;
    private Vertice verticeB;
    private Vertice verticeC;
    private Vector normal = null;
    private double area;


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

    public void cargarConValoresDe(Triangulo triangulo2) {
        this.verticeA.cargarConValoresDe(triangulo2.verticeA);
        this.verticeB.cargarConValoresDe(triangulo2.verticeB);
        this.verticeC.cargarConValoresDe(triangulo2.verticeC);
        this.normal.cargarConValoresDe(triangulo2.normal);
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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    private void calcularNormal() {
        Vector A = new Vector(verticeA.getX(),verticeA.getY(),verticeA.getZ());
        Vector B = new Vector(verticeB.getX(),verticeB.getY(),verticeB.getZ());
        Vector C = new Vector(verticeC.getX(),verticeC.getY(),verticeC.getZ());
        
        Vector AB = B.restar(A);
        Vector AC = C.restar(A);
        
        normal = AB.productoVectorial(AC);
        
        area = normal.getModulo() / 2;
        
        normal.normalizar();
    }
    
    public Vertice getMaxVerticeEnZ() {
        Vertice maxVerticeEnZ = verticeA;
        
        if(verticeB.getZ() > maxVerticeEnZ.getZ()) {
            maxVerticeEnZ = verticeB;
        }
        
        if(verticeC.getZ() > maxVerticeEnZ.getZ()) {
            maxVerticeEnZ = verticeC;
        }
        
        return maxVerticeEnZ;
    }
    
    public double getMaxValorEnZ() {
        return getMaxVerticeEnZ().getZ();
    }
    
    public Vertice getMinVerticeEnZ() {
        Vertice minVerticeEnZ = verticeA;
        
        if(verticeB.getZ() < minVerticeEnZ.getZ()) {
            minVerticeEnZ = verticeB;
        }
        
        if(verticeC.getZ() < minVerticeEnZ.getZ()) {
            minVerticeEnZ = verticeC;
        }
        
        return minVerticeEnZ;
    }
    
    public double getMinValorEnZ() {
        return getMinVerticeEnZ().getZ();
    }
    
    private Vertice getVerticeFaltante(Vertice vertice1, Vertice vertice2) {
        Vertice verticeFaltante = null;
        
        if(!verticeA.equals(vertice1) && !verticeA.equals(vertice2)) {
            verticeFaltante = verticeA;
        }
        
        if(!verticeB.equals(vertice1) && !verticeB.equals(vertice2)) {
            verticeFaltante = verticeB;
        }
        
        if(!verticeC.equals(vertice1) && !verticeC.equals(vertice2)) {
            verticeFaltante = verticeC;
        }
            
        return verticeFaltante;
    }
    
    public ConjuntoDeTriangulos dividirSegunPlanoDeCorte(double planoDeCorte) {
        ConjuntoDeTriangulos conjunto = new ConjuntoDeTriangulos();
        
        Vertice verticeSuperior = getMaxVerticeEnZ();
        Vertice verticeInferior = getMinVerticeEnZ();
        Vertice verticeIntermedio = getVerticeFaltante(verticeSuperior,verticeInferior);
        Vertice verticePuntaAPunta;
        Vertice verticeIntermedioAPunta;
        
        if(verticeIntermedio.getZ() < planoDeCorte) {
            verticePuntaAPunta = getVerticeEntreRectaYPlano(verticeSuperior,verticeInferior,planoDeCorte);
            verticeIntermedioAPunta = getVerticeEntreRectaYPlano(verticeSuperior,verticeIntermedio,planoDeCorte);
            
            conjunto.setTrianguloA(new Triangulo(verticeSuperior,verticeIntermedioAPunta,verticePuntaAPunta));
            
            if(conjunto.getTrianguloA().getNormal().getAnguloEntreNormalizado(this.normal) > Math.PI/16) {
                conjunto.getTrianguloA().setVerticeA(verticePuntaAPunta);
                conjunto.getTrianguloA().setVerticeC(verticeSuperior);
                conjunto.getTrianguloA().calcularNormal();
            }
            
            conjunto.setTrianguloB(new Triangulo(verticeIntermedioAPunta,verticeIntermedio,verticeInferior));
            
            if(conjunto.getTrianguloB().getNormal().getAnguloEntreNormalizado(this.normal) > Math.PI/16) {
                conjunto.getTrianguloB().setVerticeA(verticeInferior);
                conjunto.getTrianguloB().setVerticeC(verticeIntermedioAPunta);
                conjunto.getTrianguloB().calcularNormal();
            }
            
            conjunto.setTrianguloC(new Triangulo(verticePuntaAPunta,verticeIntermedioAPunta,verticeInferior));
            
            if(conjunto.getTrianguloC().getNormal().getAnguloEntreNormalizado(this.normal) > Math.PI/16) {
                conjunto.getTrianguloC().setVerticeA(verticeInferior);
                conjunto.getTrianguloC().setVerticeC(verticePuntaAPunta);
                conjunto.getTrianguloC().calcularNormal();
            }
            
            conjunto.setTipo(ConjuntoDeTriangulos.TRES_TRIANGULOS_UNO_SUPERIOR);
        }
        
        if(verticeIntermedio.getZ() > planoDeCorte) {
            verticePuntaAPunta = getVerticeEntreRectaYPlano(verticeSuperior,verticeInferior,planoDeCorte);
            verticeIntermedioAPunta = getVerticeEntreRectaYPlano(verticeIntermedio,verticeInferior,planoDeCorte);
            
            conjunto.setTrianguloA(new Triangulo(verticeInferior,verticeIntermedioAPunta,verticePuntaAPunta));
            
            if(conjunto.getTrianguloA().getNormal().getAnguloEntreNormalizado(this.normal) > Math.PI/16) {
                conjunto.getTrianguloA().setVerticeA(verticePuntaAPunta);
                conjunto.getTrianguloA().setVerticeC(verticeInferior);
                conjunto.getTrianguloA().calcularNormal();
            }
            
            conjunto.setTrianguloB(new Triangulo(verticeIntermedioAPunta,verticeIntermedio,verticeSuperior));
            
            if(conjunto.getTrianguloB().getNormal().getAnguloEntreNormalizado(this.normal) > Math.PI/16) {
                conjunto.getTrianguloB().setVerticeA(verticeSuperior);
                conjunto.getTrianguloB().setVerticeC(verticeIntermedioAPunta);
                conjunto.getTrianguloB().calcularNormal();
            }
            
            conjunto.setTrianguloC(new Triangulo(verticeSuperior,verticePuntaAPunta,verticeIntermedioAPunta));
            
            if(conjunto.getTrianguloC().getNormal().getAnguloEntreNormalizado(this.normal) > Math.PI/16) {
                conjunto.getTrianguloC().setVerticeA(verticeIntermedioAPunta);
                conjunto.getTrianguloC().setVerticeC(verticeSuperior);
                conjunto.getTrianguloC().calcularNormal();
            }
            
            conjunto.setTipo(ConjuntoDeTriangulos.TRES_TRIANGULOS_UNO_INFERIOR);
        }
        
        if(verticeIntermedio.getZ() == planoDeCorte) {
            verticePuntaAPunta = getVerticeEntreRectaYPlano(verticeInferior,verticeSuperior,planoDeCorte);
            
            conjunto.setTrianguloA(new Triangulo(verticeSuperior,verticeIntermedio,verticePuntaAPunta));
            
            if(conjunto.getTrianguloA().getNormal().getAnguloEntreNormalizado(this.normal) > Math.PI/16) {
                conjunto.getTrianguloA().setVerticeA(verticePuntaAPunta);
                conjunto.getTrianguloA().setVerticeC(verticeSuperior);
                conjunto.getTrianguloA().calcularNormal();
            }
            
            conjunto.setTrianguloB(new Triangulo(verticeInferior,verticePuntaAPunta,verticeIntermedio));
            
            if(conjunto.getTrianguloB().getNormal().getAnguloEntreNormalizado(this.normal) > Math.PI/16) {
                conjunto.getTrianguloB().setVerticeA(verticeIntermedio);
                conjunto.getTrianguloB().setVerticeC(verticeInferior);
                conjunto.getTrianguloB().calcularNormal();
            }
            
            conjunto.setTipo(ConjuntoDeTriangulos.DOS_TRIANGULOS);
        }
        
        return conjunto;
    }
    
    public boolean perteneceAlPlano(double planoInferior) {
        boolean esTrianguloBase = true;
        
        if(verticeA.getZ() != planoInferior) {
            esTrianguloBase = false;
        }
        
        if(verticeB.getZ() != planoInferior) {
            esTrianguloBase = false;
        }
        
        if(verticeC.getZ() != planoInferior) {
            esTrianguloBase = false;
        }
        
        return esTrianguloBase;
    }
    
    private Vertice getVerticeEntreRectaYPlano(Vertice verticeInicial, Vertice verticeFinal, double k) {
        Vertice verticeAux = new Vertice();
        
        Vector vectorInicial = new Vector(verticeInicial.getX(),verticeInicial.getY(),verticeInicial.getZ());
        Vector vectorFinal = new Vector(verticeFinal.getX(),verticeFinal.getY(),verticeFinal.getZ());
        
        Vector vectorParalelo = vectorFinal.restar(vectorInicial);
        
        double t = (k - vectorInicial.getZ()) / vectorParalelo.getZ();
        
        verticeAux.setX(vectorInicial.getX() + vectorParalelo.getX() * t);
        verticeAux.setY(vectorInicial.getY() + vectorParalelo.getY() * t);
        verticeAux.setZ(k);
        
        return verticeAux;
    }
}

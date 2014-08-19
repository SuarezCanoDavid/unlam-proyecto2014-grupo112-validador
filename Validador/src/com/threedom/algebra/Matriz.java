/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threedom.algebra;

import com.threedom.geometry.*;


/**
 *
 * @author david
 */
public class Matriz {
    
    protected double[][] matriz;
    protected int filas;
    protected int columnas;
    private static Matriz identidad;
    
    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        
        matriz = new double[filas][columnas];
        
        for(int i = 0; i < filas; ++i) {
            for(int j = 0; j < columnas; ++j) {
                matriz[i][j] = 0.0f;
            }
        }
    }
    
    public Vector multiplicarYCrear(Vector vector) {
        Vector resultado = new Vector();
        
        resultado.setX(matriz[0][0] * vector.getX() + matriz[0][1] * vector.getY() + matriz[0][2] * vector.getZ());
        resultado.setY(matriz[1][0] * vector.getX() + matriz[1][1] * vector.getY() + matriz[1][2] * vector.getZ());
        resultado.setZ(matriz[2][0] * vector.getX() + matriz[2][1] * vector.getY() + matriz[2][2] * vector.getZ());
        
        return resultado;
    }
    
    public void multiplicarYReemplazar(Vector vector) {
        double vectorX = vector.getX();
        double vectorY = vector.getY();
        double vectorZ = vector.getZ();
        
        vector.setX(matriz[0][0] * vectorX + matriz[0][1] * vectorY + matriz[0][2] * vectorZ);
        vector.setY(matriz[1][0] * vectorX + matriz[1][1] * vectorY + matriz[1][2] * vectorZ);
        vector.setZ(matriz[2][0] * vectorX + matriz[2][1] * vectorY + matriz[2][2] * vectorZ);
    }
        
    public Vertice multiplicarYCrear(Vertice vertice) {
        Vertice resultado = new Vertice();
        
        resultado.setX(matriz[0][0] * vertice.getX() + matriz[0][1] * vertice.getY() + matriz[0][2] * vertice.getZ());
        resultado.setY(matriz[1][0] * vertice.getX() + matriz[1][1] * vertice.getY() + matriz[1][2] * vertice.getZ());
        resultado.setZ(matriz[2][0] * vertice.getX() + matriz[2][1] * vertice.getY() + matriz[2][2] * vertice.getZ());
        
        return resultado;
    }
    
    public void multiplicarYReemplazar(Vertice vertice) {
        double verticeX = vertice.getX();
        double verticeY = vertice.getY();
        double verticeZ = vertice.getZ();
        
        vertice.setX(matriz[0][0] * verticeX + matriz[0][1] * verticeY + matriz[0][2] * verticeZ);
        vertice.setY(matriz[1][0] * verticeX + matriz[1][1] * verticeY + matriz[1][2] * verticeZ);
        vertice.setZ(matriz[2][0] * verticeX + matriz[2][1] * verticeY + matriz[2][2] * verticeZ);
    }
    
    public Matriz multiplicar(Matriz matriz2) {
        Matriz resultado = new Matriz(3,3);
        int i,j,k;
        
        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 3; ++j) {
                for(k = 0; k < 3; ++k) {
                    resultado.matriz[i][j] += this.matriz[i][k] * matriz2.matriz[k][j];
                }
            }
        }
        
        return resultado;
    }
    
    public Matriz multiplicar(double k) {
        Matriz resultado = new Matriz(3,3);
        
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                resultado.matriz[i][j] = k * this.matriz[i][j];
            }
        }
        
        return resultado;
    }
    
    public Matriz sumar(Matriz matriz2) {
        Matriz resultado = new Matriz(3,3);
        
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                resultado.matriz[i][j] = this.matriz[i][j] + matriz2.matriz[i][j];
            }
        }
        
        return resultado;
    }
    
    public static Matriz crearMatrizIdentidad() {
        if(identidad == null) {
            identidad = new Matriz(3,3);
            
            identidad.matriz[0][0] = 1.0f;
            identidad.matriz[1][1] = 1.0f;
            identidad.matriz[2][2] = 1.0f;
        }
    
        return identidad;
    }
    
    public double determinante() {
        double a = 0;
        double b = 0;
        
        a += matriz[0][0] * matriz[1][1] * matriz[2][2];
        a += matriz[0][1] * matriz[1][2] * matriz[2][0];
        a += matriz[0][2] * matriz[1][0] * matriz[2][1];
        
        b += matriz[2][0] * matriz[1][1] * matriz[0][2];
        b += matriz[2][1] * matriz[1][2] * matriz[0][0];
        b += matriz[2][2] * matriz[1][0] * matriz[0][1];
        
        return a - b;
    }
    
    public Matriz inversa() {
        Matriz aux = new Matriz(3,3);
        
        aux.matriz[0][0] =  matriz[1][1]*matriz[2][2] - matriz[2][1]*matriz[1][2];
        aux.matriz[0][1] =  matriz[0][2]*matriz[2][1] - matriz[0][1]*matriz[2][2];
        aux.matriz[0][2] = -matriz[0][2]*matriz[1][1] + matriz[0][1]*matriz[1][2];
        
        aux.matriz[1][0] = -matriz[1][0]*matriz[2][2] + matriz[2][0]*matriz[1][2];
        aux.matriz[1][1] = -matriz[0][2]*matriz[2][0] + matriz[0][0]*matriz[2][2];
        aux.matriz[1][2] =  matriz[0][2]*matriz[1][0] - matriz[0][0]*matriz[1][2];
        
        aux.matriz[2][0] =  matriz[1][0]*matriz[2][1] - matriz[2][0]*matriz[1][1];
        aux.matriz[2][1] =  matriz[0][1]*matriz[2][0] - matriz[0][0]*matriz[2][1];
        aux.matriz[2][2] =  matriz[0][0]*matriz[1][1] - matriz[0][1]*matriz[1][0];
        
        return aux.multiplicar(1/this.determinante());
    }
    
    public static Matriz crearMatrizDeCambioDeBaseDeCanonicaA(Matriz nuevaBase) {
        Matriz inversa = nuevaBase.inversa();
        
        Vector vec1 = inversa.multiplicarYCrear(new Vector(1,0,0));
        Vector vec2 = inversa.multiplicarYCrear(new Vector(0,1,0));
        Vector vec3 = inversa.multiplicarYCrear(new Vector(0,0,1));
        
        return Matriz.crearMatrizDeBase(vec1, vec2, vec3);
    }
    
    public static Matriz crearMatrizDeCambioDeBaseDeCanonicaA(Triangulo triangulo) {
        Vector A = new Vector(triangulo.getVerticeA().getX(),triangulo.getVerticeA().getY(),triangulo.getVerticeA().getZ());
        Vector B = new Vector(triangulo.getVerticeB().getX(),triangulo.getVerticeB().getY(),triangulo.getVerticeB().getZ());
        Vector C = new Vector(triangulo.getVerticeC().getX(),triangulo.getVerticeC().getY(),triangulo.getVerticeC().getZ());
        
        Vector AB = B.restar(A);
        Vector AC = C.restar(A);
        
        Vector vec1 = AC.productoVectorial(AB);
        Vector vec2 = vec1.productoVectorial(AC);
        Vector vec3 = AC;
        
        return Matriz.crearMatrizDeCambioDeBaseDeCanonicaA(Matriz.crearMatrizDeBase(vec2, vec3, vec1));
    }
    
    public static Matriz crearMatrizDeBase(Vector vec1, Vector vec2, Vector vec3) {
        Matriz base = new Matriz(3,3);
        
        vec1.normalizar();
        vec2.normalizar();
        vec3.normalizar();
        
        base.matriz[0][0] = vec1.getX();
        base.matriz[1][0] = vec1.getY();
        base.matriz[2][0] = vec1.getZ();
        
        base.matriz[0][1] = vec2.getX();
        base.matriz[1][1] = vec2.getY();
        base.matriz[2][1] = vec2.getZ();
        
        base.matriz[0][2] = vec3.getX();
        base.matriz[1][2] = vec3.getY();
        base.matriz[2][2] = vec3.getZ();
        
        return base;
    }
}

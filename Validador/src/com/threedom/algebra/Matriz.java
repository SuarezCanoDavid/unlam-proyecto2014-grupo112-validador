/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threedom.algebra;

import com.threedom.geometry.Vertice;

/**
 *
 * @author david
 */
public class Matriz {
    
    protected float[][] matriz;
    protected int filas;
    protected int columnas;
    
    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        
        matriz = new float[filas][columnas];
    }
    
    public Vector multipicarPorVector(Vector vector) {
        Vector resultado = new Vector();
        
        resultado.setX(matriz[0][0] * vector.getX() + matriz[0][1] * vector.getY() + matriz[0][2] * vector.getZ());
        resultado.setY(matriz[1][0] * vector.getX() + matriz[1][1] * vector.getY() + matriz[1][2] * vector.getZ());
        resultado.setZ(matriz[2][0] * vector.getX() + matriz[2][1] * vector.getY() + matriz[2][2] * vector.getZ());
        
        return resultado;
    }
    
    public Vertice multipicarPorVertice(Vertice vertice) {
        Vertice resultado = new Vertice();
        
        resultado.setX(matriz[0][0] * vertice.getX() + matriz[0][1] * vertice.getY() + matriz[0][2] * vertice.getZ());
        resultado.setY(matriz[1][0] * vertice.getX() + matriz[1][1] * vertice.getY() + matriz[1][2] * vertice.getZ());
        resultado.setZ(matriz[2][0] * vertice.getX() + matriz[2][1] * vertice.getY() + matriz[2][2] * vertice.getZ());
        
        return resultado;
    }
}

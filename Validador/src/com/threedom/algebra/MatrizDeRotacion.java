/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threedom.algebra;

/**
 *
 * @author david
 */
public class MatrizDeRotacion extends Matriz {

    /**
     * Matriz de rotacion
     * @param vector Vector alrededor del cual se realiza la rotacion.
     * @param angulo Angulo de rotacion especificado en radianes.
     */
    public MatrizDeRotacion(Vector vector, float angulo) {
        super(3,3);
        
        float nx = vector.getX();
        float ny = vector.getY();
        float nz = vector.getZ();
        float cosenoDeTita = (float) Math.cos(angulo);
        float senoDeTita = (float) Math.sin(angulo);
        
        matriz[0][0] = (1-nx*nx)*cosenoDeTita + nx*nx;
        matriz[0][1] = -nx*ny*cosenoDeTita - nz*senoDeTita;
        matriz[0][2] = -nx*nz*cosenoDeTita + ny*senoDeTita;
        
        matriz[1][0] = -nx*ny*cosenoDeTita + nz*senoDeTita;
        matriz[1][1] = (1-ny*ny)*cosenoDeTita + ny*ny;
        matriz[1][2] = -ny*nz*cosenoDeTita - nx*senoDeTita;
        
        matriz[2][0] = -nx*nz*cosenoDeTita - ny*senoDeTita;
        matriz[2][1] = -ny*nz*cosenoDeTita + nx*senoDeTita;
        matriz[2][2] = (1-nz*nz)*cosenoDeTita + nz*nz;
    }
    
    
}

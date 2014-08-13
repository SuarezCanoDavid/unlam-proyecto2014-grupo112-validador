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
    public MatrizDeRotacion(Vector vector, double angulo) {
        super(3,3);
        
        double nx = vector.getX();
        double ny = vector.getY();
        double nz = vector.getZ();
        double cosenoDeTita = Math.cos(angulo);
        double senoDeTita = Math.sin(angulo);
        
        matriz[0][0] = cosenoDeTita + nx*nx*(1-cosenoDeTita);
        matriz[0][1] = nx*ny*(1-cosenoDeTita)-nz*senoDeTita;
        matriz[0][2] = nx*nz*(1-cosenoDeTita) + ny*senoDeTita;
        
        matriz[1][0] = ny*nx*(1-cosenoDeTita + nz*senoDeTita);
        matriz[1][1] = cosenoDeTita + ny*ny*(1-cosenoDeTita);
        matriz[1][2] = ny*ny*(1-cosenoDeTita) - nx*senoDeTita;
        
        matriz[2][0] = nz*nx*(1-cosenoDeTita) - ny*senoDeTita;
        matriz[2][1] = nz*ny*(1-cosenoDeTita) + nx*senoDeTita;
        matriz[2][2] = cosenoDeTita + nz*nz*(1-cosenoDeTita);
    }
}

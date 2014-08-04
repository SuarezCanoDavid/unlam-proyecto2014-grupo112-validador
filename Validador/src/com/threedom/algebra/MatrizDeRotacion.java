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

    public MatrizDeRotacion(Vector vector, float angulo) {
        super(3,3);
        
        double nx = vector.getX();
        double ny = vector.getY();
        double nz = vector.getZ();
        double tita = angulo;
        
        matriz[0][0] = (float)((1-nx*nx)*Math.cos(tita)+nx*nx);
        matriz[0][1] = (float)(-nx*ny*Math.cos(tita)-nz*Math.sin(tita));
        matriz[0][2] = (float)(-nx*nz*Math.cos(tita)+ny*Math.sin(tita));
        
        matriz[1][0] = (float)(-nx*ny*Math.cos(tita)+nz*Math.sin(tita));
        matriz[1][1] = (float)((1-ny*ny)*Math.cos(tita)+ny*ny);
        matriz[1][2] = (float)(-ny*nz*Math.cos(tita)-nx*Math.sin(tita));
        
        matriz[2][0] = (float)(-nx*nz*Math.cos(tita)-ny*Math.sin(tita));
        matriz[2][1] = (float)(-ny*nz*Math.cos(tita)+nx*Math.sin(tita));
        matriz[2][2] = (float)((1-nz*nz)*Math.cos(tita)+nz*nz);
    }
    
    
}

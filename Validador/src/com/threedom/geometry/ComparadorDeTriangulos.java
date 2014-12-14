/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threedom.geometry;

import java.util.Comparator;

/**
 *
 * @author david
 */
public class ComparadorDeTriangulos implements Comparator<Triangulo> {

    @Override
    public int compare(Triangulo t1, Triangulo t2) {
        int comparacion = 0;
        
        if(t1.getArea() < t2.getArea()) {
            comparacion = 1;
        } else if(t1.getArea() > t2.getArea()){
            comparacion = -1;
        }
        
        return comparacion;
    }
    
}

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
public class ComparadorDeAreaDeTriangulo implements Comparator<Triangulo> {

    @Override
    public int compare(Triangulo t1, Triangulo t2) {
        if(t1.getArea() < t2.getArea()) {
            return 1;
        }
        
        if(t1.getArea() == t2.getArea()) {
            return 0;
        }
        
        return -1;
    }
    
}

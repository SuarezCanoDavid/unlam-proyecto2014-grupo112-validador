/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threedom.geometry;

/**
 *
 * @author david
 */
public class ConjuntoDeTriangulos {
    
    private Triangulo trianguloA;
    private Triangulo trianguloB;
    private Triangulo trianguloC;
    private int tipo;
    
    public static final int TRES_TRIANGULOS_UNO_SUPERIOR = 1;
    public static final int TRES_TRIANGULOS_UNO_INFERIOR = 2;
    public static final int DOS_TRIANGULOS = 3;
    

    public Triangulo getTrianguloA() {
        return trianguloA;
    }

    public void setTrianguloA(Triangulo trianguloA) {
        this.trianguloA = trianguloA;
    }

    public Triangulo getTrianguloB() {
        return trianguloB;
    }

    public void setTrianguloB(Triangulo trianguloB) {
        this.trianguloB = trianguloB;
    }

    public Triangulo getTrianguloC() {
        return trianguloC;
    }

    public void setTrianguloC(Triangulo trianguloC) {
        this.trianguloC = trianguloC;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}

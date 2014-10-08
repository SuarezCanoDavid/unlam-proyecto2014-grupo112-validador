/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.geometry.Objeto3D;
/**
 *
 * @author david
 */
public class Solucion {
    private boolean solucionRotarAlcanzada = false;
    private Objeto3D solucionRotar = null;
    private boolean solucionDividirAlcanzada = false;
    private Objeto3D solucionADividir = null;
    private double planoDeCorte = 0;
    private Objeto3D solucionDividirSuperior = null;
    private Objeto3D solucionDividirInferior = null;
    
    public Solucion() {
        
    }
    
    public synchronized boolean setSolucionRotar(Objeto3D objetoRotado) {
        if(!solucionRotarAlcanzada) {
            solucionRotar = new Objeto3D(objetoRotado);
            solucionRotarAlcanzada = true;
            
            return true;
        } else {
            return false;
        }
    }
    
    public synchronized boolean setSolucionADividir(Objeto3D objetoADividir, double corte) {
        if(!solucionDividirAlcanzada) {
            solucionADividir = new Objeto3D(objetoADividir);
            planoDeCorte = corte;
            solucionDividirAlcanzada = true;
            
            return true;
        } else {
            return false;
        }
    }
    
    public synchronized boolean setSolucionDividir(Objeto3D inferior, Objeto3D superior) {
        if(!solucionDividirAlcanzada) {
            solucionDividirInferior = inferior;
            solucionDividirSuperior = superior;
            solucionDividirAlcanzada = true;
            
            return true;
        } else {
            return false;
        }
    }

    public void setSolucionDividirSuperior(Objeto3D solucionDividirSuperior) {
        this.solucionDividirSuperior = solucionDividirSuperior;
    }

    public void setSolucionDividirInferior(Objeto3D solucionDividirInferior) {
        this.solucionDividirInferior = solucionDividirInferior;
    }

    public Objeto3D getSolucionRotar() {
        return solucionRotar;
    }

    public Objeto3D getSolucionADividir() {
        return solucionADividir;
    }

    public double getPlanoDeCorte() {
        return planoDeCorte;
    }
    
    public Objeto3D getSolucionDividirSuperior() {
        return solucionDividirSuperior;
    }

    public Objeto3D getSolucionDividirInferior() {
        return solucionDividirInferior;
    }

    public synchronized boolean isSolucionRotarAlcanzada() {
        return solucionRotarAlcanzada;
    }

    public synchronized boolean isSolucionDividirAlcanzada() {
        return solucionDividirAlcanzada;
    }
    
    /*public synchronized void incrementarContadorDeOperaciones() {
        ++contadorDeOperaciones;
    }

    public synchronized void setPlanoDeCorteInferior(double planoDeCorteInferior) {
        this.planoDeCorteInferior = planoDeCorteInferior;
    }

    public synchronized void setPlanoInferiorDeLaCopia(double planoInferiorDeLaCopia) {
        this.planoInferiorDeLaCopia = planoInferiorDeLaCopia;
    }*/
}

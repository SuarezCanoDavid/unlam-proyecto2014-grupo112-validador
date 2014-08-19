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
    private Objeto3D solucionDividirSuperior = null;
    private Objeto3D solucionDividirInferior = null;
    
    public Solucion() {
        
    }
    
    public synchronized boolean setSolucionRotar(Objeto3D obj) {
        if(!solucionRotarAlcanzada) {
            solucionRotar = obj;
            solucionRotarAlcanzada = true;
            
            return true;
        } else {
            return false;
        }
    }
    
    public Objeto3D getSolucionRotar() {
        return solucionRotar;
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
}

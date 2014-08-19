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
public class Validador {
    public static final double DEFAULT_IDM_MAX = 1.9;
    public static final double DEFAULT_ANGULO_MAX = 45;
    public static final int DEFAULT_HILOS_MAX = Runtime.getRuntime().availableProcessors();
    
    private Objeto3D objetoOriginal;
    private Objeto3D[] objetosCopias;
    private Ejecutor[] ejecutores;
    private Boolean[] ejecutorTerminado;
    private Solucion solucion = new Solucion();
    
    private double IDMMax = Validador.DEFAULT_IDM_MAX;
    private double anguloMax = Validador.DEFAULT_ANGULO_MAX;
    private int cantHilosMax = Validador.DEFAULT_HILOS_MAX;
    
    public Validador(Objeto3D objeto) {
        objetoOriginal = objeto;
    }

    public double getIDMMax() {
        return IDMMax;
    }

    public void setIDMMax(double IDMMax) {
        this.IDMMax = IDMMax;
    }

    public double getAnguloMax() {
        return anguloMax;
    }

    public void setAnguloMax(double anguloMax) {
        this.anguloMax = anguloMax;
    }

    public int getCantHilosMax() {
        return cantHilosMax;
    }

    public void setCantHilosMax(int cantHilosMax) {
        this.cantHilosMax = cantHilosMax;
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public Objeto3D getObjetoOriginal() {
        return objetoOriginal;
    }

    public Objeto3D[] getObjetosCopias() {
        return objetosCopias;
    }

    public Boolean[] getEjecutorTerminado() {
        return ejecutorTerminado;
    }
    
    public void validar() {
        objetosCopias = new Objeto3D[cantHilosMax];
        ejecutores = new Ejecutor[cantHilosMax];
        ejecutorTerminado = new Boolean[cantHilosMax];
        
        Objeto3D objClone = new Objeto3D(objetoOriginal);
        
        boolean rotacionOK = false;
        
        for(int i = 0; i < objetoOriginal.getTriangulos().size() && !rotacionOK; ++i) {
            objClone.cargarConValoresDe(objetoOriginal);
            
            rotacionOK = objClone.intentarRotarSegunTriangulo(objetoOriginal.getTriangulos().get(i));
        }
        
        if(rotacionOK) {
            objClone.trasladarAOrigen();
        
            objClone.calcularIDM();
            
            solucion.setSolucionRotar(objClone);
        }
    }
}

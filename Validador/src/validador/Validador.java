/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.geometry.Objeto3D;
import java.util.ArrayList;
/**
 *
 * @author david
 */
public class Validador {
    
    private Objeto3D objOriginal;
    private ArrayList<Objeto3D> sulucion = new ArrayList();
    private short tipoDeSolucion;
    private Objeto3D[] objClones;
    
    private double IDMMax;
    private double anguloMax;
    private int cantHilosMax;
    
    public static final short SOLUCION_ROTAR = 0;
    public static final short SOLUCION_DIVIDIR = 1;
    public static final short SOLUCION_NO = 2;
    
    public static final double DEFAULT_IDM_MAX = 1.9;
    public static final double DEFAULT_ANGULO_MAX = 45;
    public static final int DEFAULT_HILOS_MAX = Runtime.getRuntime().availableProcessors();
    
    public Validador(Objeto3D objeto) {
        objOriginal = objeto;
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
    
    public short getTipoDeSolucion() {
        return this.tipoDeSolucion;
    }

    public ArrayList<Objeto3D> getSulucion() {
        return sulucion;
    }
    
    public void validar() {
        objClones = new Objeto3D[cantHilosMax];
        
        Objeto3D objClone = new Objeto3D(objOriginal);
        
        boolean rotacionOK = false;
        
        for(int i = 0; i < objOriginal.getTriangulos().size() && !rotacionOK; ++i) {
            objClone.cargarConValoresDe(objOriginal);
            
            rotacionOK = objClone.intentarRotarSegunTriangulo(objOriginal.getTriangulos().get(i));
        }
        
        if(rotacionOK) {
            objClone.trasladarAOrigen();
        
            objClone.calcularIDM();
            
            this.sulucion.add(objClone);
            this.tipoDeSolucion = Validador.SOLUCION_ROTAR;
        }
    }
}

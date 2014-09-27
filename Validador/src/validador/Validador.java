/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.geometry.Objeto3D;
import com.threedom.geometry.Triangulo;
import com.threedom.geometry.ConjuntoDeTriangulos;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
    private Boolean[] objetosCopiasLibres;
    private Solucion solucion = new Solucion();
    
    private double IEMax = Validador.DEFAULT_IDM_MAX;
    private double anguloMax = Validador.DEFAULT_ANGULO_MAX;
    private int cantHilosMax = Validador.DEFAULT_HILOS_MAX;
    
    public Validador(Objeto3D objeto) {
        objetoOriginal = objeto;
    }

    public double getIEMax() {
        return IEMax;
    }

    public void setIEMax(double IDMMax) {
        this.IEMax = IDMMax;
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
    
    public Objeto3D getObjetoCopia(int i) {
        return objetosCopias[i];
    }
    
    public synchronized int ocuparObjetoCopiaLibre() {
        int i;
        
        for(i = 0; i < objetosCopiasLibres.length && !objetosCopiasLibres[i]; ++i);
        
        objetosCopiasLibres[i] = false;
        
        return i;
    }
    
    public synchronized void liberarObjetoCopiaOcupado(int i) {
        objetosCopiasLibres[i] = true;
    }
    
    public void validar() {
        boolean finAntesDeTimeOut = true;
        ConjuntoDeTriangulos conjunto;
        ExecutorService threadPool = Executors.newFixedThreadPool(cantHilosMax);
        
        objetosCopias = new Objeto3D[cantHilosMax];
        objetosCopiasLibres = new Boolean[cantHilosMax];
        
        for(int i = 0; i < cantHilosMax; ++i) {
            objetosCopias[i] = new Objeto3D(objetoOriginal);
            objetosCopiasLibres[i] = true;
        }
        
        for(int i = 0; i < objetoOriginal.getTriangulos().size(); ++i) {
            threadPool.execute(new Ejecutable(this,i));
        }
        
        threadPool.shutdown();
        
        try {
            finAntesDeTimeOut = threadPool.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            
        }
        
        if(finAntesDeTimeOut) {
            if(!solucion.isSolucionRotarAlcanzada() && solucion.isSolucionDividirAlcanzada()) {
                solucion.setSolucionDividirInferior(new Objeto3D());
                solucion.setSolucionDividirSuperior(new Objeto3D());
                
                for(Triangulo t : solucion.getSolucionADividir().getTriangulos()) {
                    if(t.getMaxValorEnZ() <= solucion.getPlanoDeCorte()) {
                        solucion.getSolucionDividirInferior().addTriangulo(t);
                    }
                    
                    if(t.getMinValorEnZ() >= solucion.getPlanoDeCorte()) {
                        solucion.getSolucionDividirSuperior().addTriangulo(t);
                    }
                    
                    if(solucion.getPlanoDeCorte() < t.getMaxValorEnZ() && solucion.getPlanoDeCorte() > t.getMinValorEnZ())  {
                        conjunto = t.dividirSegunPlanoDeCorte(solucion.getPlanoDeCorte());
                        
                        switch(conjunto.getTipo()) {
                            case ConjuntoDeTriangulos.TRES_TRIANGULOS_UNO_SUPERIOR: 
                                solucion.getSolucionDividirSuperior().addTriangulo(conjunto.getTrianguloA());
                                solucion.getSolucionDividirInferior().addTriangulo(conjunto.getTrianguloB());
                                solucion.getSolucionDividirInferior().addTriangulo(conjunto.getTrianguloC());
                                break;
                              
                            case ConjuntoDeTriangulos.TRES_TRIANGULOS_UNO_INFERIOR:
                                solucion.getSolucionDividirInferior().addTriangulo(conjunto.getTrianguloA());
                                solucion.getSolucionDividirSuperior().addTriangulo(conjunto.getTrianguloB());
                                solucion.getSolucionDividirSuperior().addTriangulo(conjunto.getTrianguloC());
                                break;
                                
                            case ConjuntoDeTriangulos.DOS_TRIANGULOS:
                                solucion.getSolucionDividirSuperior().addTriangulo(conjunto.getTrianguloA());
                                solucion.getSolucionDividirInferior().addTriangulo(conjunto.getTrianguloB());
                                break;
                        }
                    }
                }
            }
        } else {
            System.exit(-1);
        }
    }
}

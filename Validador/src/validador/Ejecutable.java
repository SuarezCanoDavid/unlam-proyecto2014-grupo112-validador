/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.algebra.Vector;
import com.threedom.geometry.Objeto3D;
import com.threedom.geometry.Triangulo;
import com.threedom.geometry.Vertice;

/**
 *
 * @author david
 */
public class Ejecutable implements Runnable {
    
    private final Validador validador;
    private final Objeto3D original;
    private final Solucion solucion;
    private Objeto3D copia;
    private int indiceObjetoCopiaLibre;
    private final Triangulo trianguloDeGiro;
    private final double IEMax;
    private final double anguloLimite;
    

    public Ejecutable(Validador validador, int nroTrianguloDeGiro) {
        this.validador = validador;
        original = validador.getObjetoOriginal();
        solucion = validador.getSolucion();
        trianguloDeGiro = original.getTriangulos().get(nroTrianguloDeGiro);
        IEMax = validador.getIEMax();
        anguloLimite = Math.toRadians(90 - validador.getAnguloMax());
    }
    
    @Override
    public void run() {
        boolean sePuedeRotar;
        double planoDeCorteInferior;
        double planoDeCorteSuperior;
        Vector normalAux;
        double zAux;
        
        if(solucion.isSolucionRotarAlcanzada()) {
            return;
        }
        
        indiceObjetoCopiaLibre = validador.ocuparObjetoCopiaLibre();
        
        copia = validador.getObjetoCopia(indiceObjetoCopiaLibre);
        
        copia.cargarConValoresDe(original);
        
        sePuedeRotar = copia.rotarSegunTriangulo(trianguloDeGiro);

        copia.trasladarAOrigen();
        
        planoDeCorteInferior = copia.getPlanoInferior();
        planoDeCorteSuperior = copia.getPlanoSuperior();
        
        for(Triangulo t : copia.getTriangulos()) {
            normalAux = t.getNormal();
            
            if(normalAux.getZ() < 0) {
                if(normalAux.getAnguloEntre(Vector.versorMenosK) < anguloLimite) {
                    zAux = t.getMaxValorEnZ();
                    
                    if(zAux > planoDeCorteInferior) {
                        if(Math.abs(zAux - planoDeCorteInferior) > Vertice.DELTA) {
                            planoDeCorteInferior = zAux;
                        }
                    }
                }
            }
            
            if(normalAux.getZ() > 0) {
                if(normalAux.getAnguloEntre(Vector.versorK) < anguloLimite) {
                    zAux = t.getMinValorEnZ();
                    
                    if(zAux < planoDeCorteSuperior) {
                        if(Math.abs(zAux - planoDeCorteSuperior) > Vertice.DELTA) {
                            planoDeCorteSuperior = zAux;
                        }
                    }
                }
            }
        }

        if(sePuedeRotar) {
            if(planoDeCorteInferior == copia.getPlanoInferior()) {
                solucion.setSolucionRotar(copia);
                validador.liberarObjetoCopiaOcupado(indiceObjetoCopiaLibre);
                return;
            }
        }
        
        if(planoDeCorteInferior <= planoDeCorteSuperior) {
            solucion.setSolucionADividir(copia, (planoDeCorteInferior+planoDeCorteSuperior)/2);
        }
        
        validador.liberarObjetoCopiaOcupado(indiceObjetoCopiaLibre);
    }
}

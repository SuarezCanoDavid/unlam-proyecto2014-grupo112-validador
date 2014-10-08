/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.algebra.Vector;
import com.threedom.geometry.ConjuntoDeTriangulos;
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
        anguloLimite = Math.toRadians(90 + validador.getAnguloMax());
    }
    
    @Override
    public void run() {
        boolean sePuedeRotar;
        double planoDeCorteInferior;
        double planoDeCorteSuperior;
        Vector normalAux;
        double zAux;
        int indiceDeLaNormal;
        
        if(solucion.isSolucionRotarAlcanzada()) {
            return;
        }
        
        indiceObjetoCopiaLibre = validador.ocuparObjetoCopiaLibre();
        
        copia = validador.getObjetoCopia(indiceObjetoCopiaLibre);
        
        copia.cargarConValoresDe(original);
        
        indiceDeLaNormal = original.getNormales().indexOf(trianguloDeGiro.getNormal());
        
        sePuedeRotar = copia.rotarSegunTriangulo(trianguloDeGiro);
        
        if(!sePuedeRotar && validador.isNormalAnalizada(indiceDeLaNormal)) {
            validador.liberarObjetoCopiaOcupado(indiceObjetoCopiaLibre);
            return;
        }

        copia.trasladarAOrigen();
        
        planoDeCorteInferior = copia.getPlanoInferior();
        planoDeCorteSuperior = copia.getPlanoSuperior();
        
        for(Triangulo t : copia.getTriangulos()) {
            normalAux = t.getNormal();
            
            if(normalAux.getZ() < 0) {
                if(normalAux.getAnguloEntre(Vector.versorK) > anguloLimite) {
                    zAux = t.getMaxValorEnZ();
                    
                    if(zAux > planoDeCorteInferior) {
                        /*if(Math.abs(zAux - planoDeCorteInferior) > Vertice.DELTA) {
                            planoDeCorteInferior = zAux;
                        }*/
                        planoDeCorteInferior = zAux;
                    }
                }
            }
            
            if(normalAux.getZ() > 0) {
                if(normalAux.getAnguloEntre(Vector.versorMenosK) > anguloLimite) {
                    zAux = t.getMinValorEnZ();
                    
                    if(zAux < planoDeCorteSuperior) {
                        /*if(Math.abs(zAux - planoDeCorteSuperior) > Vertice.DELTA) {
                            planoDeCorteSuperior = zAux;
                        }*/
                        planoDeCorteSuperior = zAux;
                    }
                }
            }
        }

        if(sePuedeRotar) {
            if(planoDeCorteInferior == copia.getPlanoInferior()) {
                if(copia.getIE() <= this.IEMax) {
                    solucion.setSolucionRotar(copia);
                    validador.liberarObjetoCopiaOcupado(indiceObjetoCopiaLibre);
                    return;
                }
            }
        }
        
        if(!validador.isNormalAnalizada(indiceDeLaNormal)) {
            validador.setNormalAnalizada(indiceDeLaNormal);
            
            if(planoDeCorteInferior <= planoDeCorteSuperior) {
                if(!solucion.isSolucionRotarAlcanzada() && !solucion.isSolucionDividirAlcanzada()) {
                    Objeto3D superior = new Objeto3D();
                    Objeto3D inferior = new Objeto3D();
                    superior.inicializarListas(copia.getTriangulos().size());
                    inferior.inicializarListas(copia.getTriangulos().size());

                    ConjuntoDeTriangulos conjunto;
                    double planoDeCorte = (planoDeCorteInferior+planoDeCorteSuperior)/2;

                    for(Triangulo t : copia.getTriangulos()) {
                        if(t.getMaxValorEnZ() <= planoDeCorte) {
                            inferior.addTriangulo(new Triangulo(t));
                        }

                        if(t.getMinValorEnZ() >= planoDeCorte) {
                            superior.addTriangulo(new Triangulo(t));
                        }

                        if(planoDeCorte < t.getMaxValorEnZ() && planoDeCorte > t.getMinValorEnZ()) {
                            conjunto = t.dividirSegunPlanoDeCorte(planoDeCorte);

                            switch(conjunto.getTipo()) {
                                case ConjuntoDeTriangulos.TRES_TRIANGULOS_UNO_SUPERIOR:
                                    superior.addTriangulo(new Triangulo(conjunto.getTrianguloA()));
                                    inferior.addTriangulo(new Triangulo(conjunto.getTrianguloB()));
                                    inferior.addTriangulo(new Triangulo(conjunto.getTrianguloC()));
                                    break;

                                case ConjuntoDeTriangulos.TRES_TRIANGULOS_UNO_INFERIOR:
                                    inferior.addTriangulo(new Triangulo(conjunto.getTrianguloA()));
                                    superior.addTriangulo(new Triangulo(conjunto.getTrianguloB()));
                                    superior.addTriangulo(new Triangulo(conjunto.getTrianguloC()));
                                    break;

                                case ConjuntoDeTriangulos.DOS_TRIANGULOS:
                                    superior.addTriangulo(new Triangulo(conjunto.getTrianguloA()));
                                    inferior.addTriangulo(new Triangulo(conjunto.getTrianguloB()));
                                    break;
                            }
                        }
                    }

                    superior.trasladarAOrigen();
                    inferior.rotarSegunTriangulo(new Triangulo(new Vertice(0,0,0),new Vertice(1,0,0),new Vertice(1,1,0)));
                    inferior.trasladarAOrigen();


                    if(inferior.getIE() <= this.IEMax && superior.getIE() <= this.IEMax) {
                        solucion.setSolucionDividir(inferior, superior);
                    }
                }
            } 
        }
        
        validador.liberarObjetoCopiaOcupado(indiceObjetoCopiaLibre);
    }
}

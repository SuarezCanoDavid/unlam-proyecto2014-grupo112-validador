/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.geometry.Objeto3D;
import com.threedom.geometry.Triangulo;

/**
 *
 * @author david
 */
public class Ejecutor extends Thread {
    
    private Objeto3D copia;
    private Objeto3D original;
    private Solucion solucion;
    private Boolean terminado;
    private Triangulo trianguloDeGiro;
    private double IDMMax;
    private double anguloMax;

    public Ejecutor(Validador validador,int indiceDeAccion, int nroTrianguloDeGiro) {
        copia = validador.getObjetosCopias()[indiceDeAccion];
        original = validador.getObjetoOriginal();
        solucion = validador.getSolucion();
        terminado = validador.getEjecutorTerminado()[indiceDeAccion];
        trianguloDeGiro = original.getTriangulos().get(nroTrianguloDeGiro);
        IDMMax = validador.getIDMMax();
        anguloMax = validador.getAnguloMax();
    }
    
    @Override
    public void run() {
        try {
            consultarInterrupcion();
            copia.cargarConValoresDe(original);
        
            consultarInterrupcion();
            boolean rotacionOK = copia.intentarRotarSegunTriangulo(trianguloDeGiro);

            if(!rotacionOK) {
                terminado = true;
                return;
            }
            
            consultarInterrupcion();
            copia.trasladarAOrigen();
            
            consultarInterrupcion();
            copia.calcularIDM();
            
            if(copia.getIDM() > IDMMax) {
                terminado = true;
                return;
            }
            
            solucion.setSolucionRotar(copia);
            
            terminado = true;
            
            notificarFin();
        } catch(InterruptedException e) {
            terminado = true;
        }
    }
    
    private void consultarInterrupcion() throws InterruptedException {
        if(this.isInterrupted()) {
            throw new InterruptedException();
        }
    }
   
    private synchronized void notificarFin() {
        notifyAll();
    }
}

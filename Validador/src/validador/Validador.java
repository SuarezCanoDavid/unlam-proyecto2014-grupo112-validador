/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.geometry.Objeto3D;
import com.threedom.stlhelper.STLFileWriter;
import com.threedom.stlhelper.STLFileReader;


/**
 *
 * @author david
 */
public class Validador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Objeto3D objOriginal = new Objeto3D();
        Objeto3D objClone = null;
        int i;
        
        leerObjeto3D(objOriginal,args[0]);
        
        objClone = new Objeto3D(objOriginal);
        
        /*boolean rotacionOK = false;
        int cantidadDeTriangulos = objOriginal.getTriangulos().size();
        
        for(i = 0; i < cantidadDeTriangulos && !rotacionOK; ++i) {
            objClone.cargarConValoresDe(objOriginal);
            
            rotacionOK = objClone.intentarRotarSegunTriangulo(objOriginal.getTriangulos().get(i));
        }
        
        if(rotacionOK) {
            //objClone.intentarRotarSegunTriangulo(objOriginal.getTriangulos().get(--i));
            escribirObjeto3D(objClone,args[1]);
        }*/
        
        /*System.out.printf("Cantidad de nucleos: %d\n", Runtime.getRuntime().availableProcessors());*/
        
        objOriginal.intentarRotarSegunTriangulo(objOriginal.getTriangulos().get(0));
        escribirObjeto3D(objOriginal,args[1]);
    }
    
    private static void leerObjeto3D(Objeto3D obj, String nombreArchivo) {
        STLFileReader input = new STLFileReader(nombreArchivo);
        
        input.readObjeto3D(obj);
        
        input.close();
    }
    
    private static void escribirObjeto3D(Objeto3D obj, String nombreArchivo) {
        STLFileWriter output = new STLFileWriter(nombreArchivo);
        
        output.writeObjeto3D(obj);
        
        output.close();
    }
}

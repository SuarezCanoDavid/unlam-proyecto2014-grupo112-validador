/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.geometry.Objeto3D;
import com.threedom.algebra.Vector;
import com.threedom.algebra.MatrizDeRotacion;
import com.threedom.stlhelper.STLFileWriter;


/**
 *
 * @author david
 */
public class Validador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println(args[0]);
        
        Objeto3D obj = new Objeto3D(args[0]);
        
        STLFileWriter output = new STLFileWriter(args[1]);
        
        obj.rotar(new MatrizDeRotacion(new Vector(-1.0f,0.0f,0.0f),(float)Math.toRadians(90.0)));
        
        output.writeObjeto3D(obj);
        
        output.close();
        
        /*Vector vector1 = new Vector(-1.0f,-1.0f,-1.0f);
        Vector vector2 = new Vector(0.0f,0.0f,-1.0f);
        
        System.out.printf("Angulo: %f\n", vector1.getAnguloEntre(vector2));
        
        System.out.printf("Producto vectorial: %s\n", vector1.productoVectorial(vector2).toString());
        
        System.out.printf("Cantidad de nucleos: %d\n", Runtime.getRuntime().availableProcessors());*/
    }
    
}

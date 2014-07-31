/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.Objeto3D;
import com.threedom.Vertice;
import java.util.Iterator;

/**
 *
 * @author david
 */
public class Validador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Objeto3D obj = new Objeto3D();
		
        for(int i = 0; i < 10; ++i) {
                Vertice a = new Vertice(i,i,i);
                Vertice b = new Vertice(i+1,i+1,i+1);
                Vertice c = new Vertice(i+2,i+2,i+2);

                obj.addTriangulo(a,b,c);
        }

        obj.imprimir();

        Iterator<Vertice> it = obj.getVertices().iterator();

        while(it.hasNext()) {
                Vertice vertice = it.next();

                vertice.setX(vertice.getX()*100);
                vertice.setY(vertice.getY()*100);
                vertice.setZ(vertice.getZ()*100);
        }

        System.out.println();

        obj.imprimir();
    }
    
}

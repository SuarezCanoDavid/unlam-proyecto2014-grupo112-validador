/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validador;

import com.threedom.geometry.Objeto3D;
import com.threedom.stlhelper.STLFileReader;
import com.threedom.stlhelper.STLFileWriter;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author david
 */
public class Principal {
    
    private static String nombreObjetoOriginal;
    private static String carpetaSolucion;
    private static String nombreArchivoConfiguracion;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Objeto3D objeto = new Objeto3D();
        Validador validador;
        
        if(args.length < 2) {
            System.exit(-1);
        } else {
            nombreObjetoOriginal = args[0];
            carpetaSolucion = args[1];
            
            if(args.length == 3) {
                nombreArchivoConfiguracion = args[2];
            } else {
                nombreArchivoConfiguracion = "Validador.config";
            }
        }
        
        leerObjeto3D(objeto,nombreObjetoOriginal);
        
        validador = new Validador(objeto);
        
        setParametrosDelValidador(validador);
        
        System.out.printf("IDM: %f\nAnguloMax: %f\nHilos: %d\n", 
                validador.getIDMMax(),validador.getAnguloMax(),validador.getCantHilosMax());
        
        validador.validar();
        
        if(validador.getTipoDeSolucion() == Validador.SOLUCION_ROTAR) {
            escribirObjeto3D(validador.getSulucion().get(0),Principal.carpetaSolucion);
        }
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
    
    private static void setParametrosDelValidador(Validador validador) {
        File config = new File(Principal.nombreArchivoConfiguracion);
        Document doc = null;
        Double IDMMax;
        Double anguloMax;
        Integer cantHilosMax;
        
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(config);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.exit(-1);
        }
        
        doc.getDocumentElement().normalize();
        
        Element keys = (Element) doc.getElementsByTagName("keys").item(0);
        
        try {
            IDMMax = Double.parseDouble(keys.getElementsByTagName("idm-max").item(0).getTextContent());
        } catch(NumberFormatException ex) {
            IDMMax = Validador.DEFAULT_IDM_MAX;
        }
        
        try {
            anguloMax = Double.parseDouble(keys.getElementsByTagName("angulo-max").item(0).getTextContent());
        } catch(NumberFormatException ex) {
            anguloMax = Validador.DEFAULT_ANGULO_MAX;
        }
        
        try {
            cantHilosMax = Integer.parseInt(keys.getElementsByTagName("hilos-max").item(0).getTextContent());
        } catch(NumberFormatException ex) {
            cantHilosMax = Validador.DEFAULT_HILOS_MAX;
        }
        
        validador.setIDMMax(IDMMax);
        validador.setAnguloMax(anguloMax);
        validador.setCantHilosMax(cantHilosMax);
    }
}

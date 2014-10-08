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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
    private static String solucionRotacion;
    private static String solucionDivisionSuperior;
    private static String solucionDivisionInferior;
    private static String descripcionSolucionAlcanzada;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Objeto3D objeto = new Objeto3D();
        Validador validador;
        
        if(comprobarArgumentos(args)) {
            nombreObjetoOriginal = args[0];
            carpetaSolucion = args[1];
            
            if(args.length == 3) {
                nombreArchivoConfiguracion = args[2];
            } else {
                nombreArchivoConfiguracion = "Validador.config";
            }
        } else {
            System.exit(-1);
        }
        
        leerObjeto3D(objeto,nombreObjetoOriginal);
        
        validador = new Validador(objeto);
        
        setParametrosDelValidador(validador);
        
        validador.validar();
        
        prepararArchivosDeSalida();
        
        if(validador.getSolucion().isSolucionRotarAlcanzada()) {
            escribirObjeto3D(validador.getSolucion().getSolucionRotar(),
                    Principal.solucionRotacion);
            
            describirSolucionRotar();
            
        } else if(validador.getSolucion().isSolucionDividirAlcanzada()) {
            escribirObjeto3D(validador.getSolucion().getSolucionDividirSuperior(),
                    Principal.solucionDivisionSuperior);
            
            escribirObjeto3D(validador.getSolucion().getSolucionDividirInferior(),
                    Principal.solucionDivisionInferior);
            
            describirSolucionDividir();
        } else {
            describirSolucionRechazo();
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
        Integer timeOut;
        
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
        
        try {
            timeOut = Integer.parseInt(keys.getElementsByTagName("time-out").item(0).getTextContent());
        } catch(NumberFormatException ex) {
            timeOut = Validador.DEFAULT_TIME_OUT;
        }
        
        validador.setIEMax(IDMMax);
        validador.setAnguloMax(anguloMax);
        validador.setCantHilosMax(cantHilosMax);
        validador.setTimeOut(timeOut);
    }
    
    private static boolean comprobarArgumentos(String[] args) {
        File archivoObjeto;
        File carpetaDestino;
        File archivoConfig;
        
        if(args.length < 2) {
            return false;
        }
        
        archivoObjeto = new File(args[0]);
        carpetaDestino = new File(args[1]);
        
        if(!archivoObjeto.exists()) {
            return false;
        } else {
            if(!archivoObjeto.isFile()) {
                return false;
            }
        }
        
        if(!carpetaDestino.exists()) {
            return false;
        } else {
            if(!carpetaDestino.isDirectory()) {
                return false;
            }
        }
        
        if(args.length == 3) {
            archivoConfig = new File(args[2]);
            
            if(!archivoConfig.exists()) {
                return false;
            } else {
                if(!archivoConfig.isFile()) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private static void describirSolucionRotar() {
        Document doc = null;
        Transformer transformer = null;
        
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            System.exit(-1);
        }
        
        Element root = doc.createElement("descripcion");
        doc.appendChild(root);
        
        Element objetoOriginal = doc.createElement("objeto-original");
        objetoOriginal.setAttribute("ruta", Principal.nombreObjetoOriginal);
        root.appendChild(objetoOriginal);
        
        Element solucion = doc.createElement("solucion");
        solucion.setAttribute("estado", "VALIDADA");
        solucion.setAttribute("tipo", "ROTACION");
        root.appendChild(solucion);
        
        Element archivo = doc.createElement("archivo");
        archivo.setAttribute("ruta", Principal.solucionRotacion);
        solucion.appendChild(archivo);
        
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ex) {
            System.exit(-1);
        }
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(
                new File(String.format("%s/descripcion.xml", Principal.carpetaSolucion)));
        
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.exit(-1);
        }
    }
    
    private static void describirSolucionDividir() {
        Document doc = null;
        Transformer transformer = null;
        
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            System.exit(-1);
        }
        
        Element root = doc.createElement("descripcion");
        doc.appendChild(root);
        
        Element objetoOriginal = doc.createElement("objeto-original");
        objetoOriginal.setAttribute("ruta", Principal.nombreObjetoOriginal);
        root.appendChild(objetoOriginal);
        
        Element solucion = doc.createElement("solucion");
        solucion.setAttribute("estado", "VALIDADA");
        solucion.setAttribute("tipo", "DIVISION");
        root.appendChild(solucion);
        
        Element superior = doc.createElement("superior");
        superior.setAttribute("ruta", Principal.solucionDivisionSuperior);
        solucion.appendChild(superior);
        
        Element inferior = doc.createElement("inferior");
        inferior.setAttribute("ruta", Principal.solucionDivisionInferior);
        solucion.appendChild(inferior);
        
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ex) {
            System.exit(-1);
        }
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(
                new File(String.format("%s/descripcion.xml", Principal.carpetaSolucion)));
        
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.exit(-1);
        }
    }
    
    private static void describirSolucionRechazo() {
        Document doc = null;
        Transformer transformer = null;
        
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            System.exit(-1);
        }
        
        Element root = doc.createElement("descripcion");
        doc.appendChild(root);
        
        Element objetoOriginal = doc.createElement("objeto-original");
        objetoOriginal.setAttribute("ruta", Principal.nombreObjetoOriginal);
        root.appendChild(objetoOriginal);
        
        Element solucion = doc.createElement("solucion");
        solucion.setAttribute("estado", "RECHAZADA");
        root.appendChild(solucion);
        
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ex) {
            System.exit(-1);
        }
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(
                new File(String.format("%s/descripcion.xml", Principal.carpetaSolucion)));
        
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.exit(-1);
        }
    } 
    
    private static void prepararArchivosDeSalida() {
        String nombreAux = Principal.nombreObjetoOriginal.substring(
                Principal.nombreObjetoOriginal.lastIndexOf("/")+1,
                Principal.nombreObjetoOriginal.lastIndexOf(".stl"));
        
        Principal.solucionRotacion = String.format("%s/%sOutRot.stl",
                Principal.carpetaSolucion, nombreAux);
        
        Principal.solucionDivisionSuperior = String.format("%s/%sOutSup.stl", 
                Principal.carpetaSolucion, nombreAux);
        
        Principal.solucionDivisionInferior = String.format("%s/%sOutInf.stl", 
                Principal.carpetaSolucion, nombreAux);
        
        Principal.descripcionSolucionAlcanzada = String.format("%s/descripcion.xml", 
                Principal.carpetaSolucion);
    }
}

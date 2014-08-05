/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threedom.stlhelper;

import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.threedom.geometry.Vertice;
import com.threedom.geometry.Triangulo;

/**
 *
 * @author david
 */
public class STLFileReader {
    
    private static final int BUFFER_SIZE = 4;
    private DataInputStream input;
    private int cantidadDeTriangulos;
    private int cantidadTriangulosLeidos;
    private byte[] buffer = new byte[BUFFER_SIZE];
    
    
    public STLFileReader(String nombreArchivo) {
        try {
            input = new DataInputStream(new BufferedInputStream(new FileInputStream(nombreArchivo)));
        } catch (FileNotFoundException ex) {
            System.exit(-1);
        }
        
        try {
            input.skipBytes(80);
            
            cantidadDeTriangulos = this.readNextInt();
        } catch (IOException ex) {
            System.exit(-1);
        }
        
        cantidadTriangulosLeidos = 0;
    }
    
    public void close() {
        try {
            input.close();
        } catch (IOException ex) {
            System.exit(-1);
        }
    }
    
    private int readNextInt() {
        try {
            input.read(buffer,0,BUFFER_SIZE);
        } catch (IOException ex) {
            System.exit(-1);
        }
        
        return ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
    
    private float readNextFloat() {
        try {
            input.read(buffer,0,BUFFER_SIZE);
        } catch (IOException ex) {
            System.exit(-1);
        }
        
        return ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }
    
    public int getCantidadDeTriangulos() {
        return this.cantidadDeTriangulos;
    }
    
    public Vertice readNextVertice() {
        Vertice verticeAux = new Vertice();
        
        verticeAux.setX(this.readNextFloat());
        verticeAux.setY(this.readNextFloat());
        verticeAux.setZ(this.readNextFloat());
        
        return verticeAux;
    }
    
    public Triangulo readNextTriangulo() {
        Triangulo trianguloAux = null;
                
        if(cantidadTriangulosLeidos < cantidadDeTriangulos) {
            this.skipNormal();
        
            Vertice verticeA = this.readNextVertice();
            Vertice verticeB = this.readNextVertice();
            Vertice verticeC = this.readNextVertice();

            trianguloAux = new Triangulo(verticeA,verticeB,verticeC);

            try {
                input.skipBytes(2);
            } catch (IOException ex) {
                System.exit(-1);
            }
            
            ++cantidadTriangulosLeidos;
        } else {
            trianguloAux = null;
        }
        
        return trianguloAux;
    }
    
    public void skipNormal() {
        try {
            input.skipBytes(12);
        } catch (IOException ex) {
            System.exit(-1);
        }
    }
}

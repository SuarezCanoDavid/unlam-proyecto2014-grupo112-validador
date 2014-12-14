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
import com.threedom.geometry.Objeto3D;

/**
 *
 * @author david
 */
public class STLFileReader {
    
    private static final int BUFFER_SIZE = 4;
    private DataInputStream input;
    private byte[] buffer = new byte[BUFFER_SIZE];
    
    public STLFileReader(String nombreDeArchivo) {
        try {
            input = new DataInputStream(new BufferedInputStream(new FileInputStream(nombreDeArchivo)));
        } catch (FileNotFoundException ex) {
            System.exit(-1);
        }
    }
    
    public void readObjeto3D(Objeto3D obj) {
        int cantidadDeTriangulos;
        
        this.skipFirst80Bytes();
        
        cantidadDeTriangulos = this.readCantidadDeTriangulos();
        
        obj.inicializarListas(cantidadDeTriangulos);
        
        for(int i = 0; i < cantidadDeTriangulos; ++i) {
            obj.addTriangulo(this.readTriangulo());
        }
        
        obj.ordenarTriangulos();
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
    
    private Vertice readVertice() {
        Vertice verticeAux = new Vertice();
        
        verticeAux.setX(this.readNextFloat());
        verticeAux.setY(this.readNextFloat());
        verticeAux.setZ(this.readNextFloat());
        
        return verticeAux;
    }
    
    private Triangulo readTriangulo() {
        this.skipNormal();
        
        Vertice verticeA = this.readVertice();
        Vertice verticeB = this.readVertice();
        Vertice verticeC = this.readVertice();

        Triangulo trianguloAux = new Triangulo(verticeA,verticeB,verticeC);
        
        this.skipAttributeByteCount();
        
        return trianguloAux;
    }
    
    private void skipNormal() {
        try {
            input.skipBytes(12);
        } catch (IOException ex) {
            System.exit(-1);
        }
    }
    
    private void skipFirst80Bytes() {
        try {
            input.skipBytes(80);
        } catch (IOException ex) {
            System.exit(-1);
        }
    }
    
    private void skipAttributeByteCount() {
        try {
            input.skipBytes(2);
        } catch (IOException ex) {
            System.exit(-1);
        }
    }
    
    private int readCantidadDeTriangulos() {
        return this.readNextInt();
    }
}

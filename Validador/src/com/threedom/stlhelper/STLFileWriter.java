/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threedom.stlhelper;

import java.util.Iterator;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.threedom.algebra.Vector;
import com.threedom.geometry.Vertice;
import com.threedom.geometry.Triangulo;
import com.threedom.geometry.Objeto3D;

/**
 *
 * @author david
 */
public class STLFileWriter {
    
    private static final int BUFFER_SIZE = 4;
    private DataOutputStream  output;
    private byte[] buffer = new byte[BUFFER_SIZE];
    
    public STLFileWriter(String nombreDeArchivo) {
        try {
            output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nombreDeArchivo)));
        } catch (FileNotFoundException ex) {
            System.exit((-1));
        }
    }
    
    public void writeObjeto3D(Objeto3D obj) {
        this.writeFirst80Bytes();
        this.writeCantidadDeTriangulos(obj.getTriangulos().size());
        
        Iterator<Triangulo> it = obj.getTriangulos().iterator();
        
        while(it.hasNext()) {
            this.writeTriangulo(it.next());
        }
    }
    
    private void writeInt(int value) {
        buffer = ByteBuffer.allocate(BUFFER_SIZE).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
        
        try {
            output.write(buffer,0,BUFFER_SIZE);
        } catch (IOException e) {
            System.exit(-1);
        }
    }
    
    private void writeFloat(float value) {
        buffer = ByteBuffer.allocate(BUFFER_SIZE).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array();
        
        try {
            output.write(buffer,0,BUFFER_SIZE);
        } catch (IOException e) {
            System.exit((-1));
        }
    }
    
    private void writeFirst80Bytes() {
        for(int i = 0; i < 20; ++i) {
            this.writeInt(0);
        }
    }
    
    private void writeCantidadDeTriangulos(int cantidadDeTriangulos) {
        this.writeInt(cantidadDeTriangulos);
    }
    
    private void writeAttributeByteCount() {
        short aux = 0;
        byte[] bufferShort = new byte[2];
        
        bufferShort = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(aux).array();
        
        try {
            output.write(bufferShort,0,2);
        } catch (IOException e) {
            System.exit((-1));
        }
    }
    
    private void writeNormal(Vector normal) {
        this.writeFloat((float)normal.getX());
        this.writeFloat((float)normal.getY());
        this.writeFloat((float)normal.getZ());
    }
    
    private void writeVertice(Vertice vertice) {
        this.writeFloat((float)vertice.getX());
        this.writeFloat((float)vertice.getY());
        this.writeFloat((float)vertice.getZ());
    }
    
    private void writeTriangulo(Triangulo triangulo) {
        this.writeNormal(triangulo.getNormal());
        this.writeVertice(triangulo.getVerticeA());
        this.writeVertice(triangulo.getVerticeB());
        this.writeVertice(triangulo.getVerticeC());
        this.writeAttributeByteCount();
    }
    
    public void close() {
        try {
            output.close();
        } catch (IOException ex) {
            System.exit(-1);
        }
    }
}

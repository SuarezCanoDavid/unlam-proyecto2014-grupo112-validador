/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threedom.algebra;

/**
 *
 * @author david
 */
public class Vector {
    
    private float x;
    private float y;
    private float z;
    
    public Vector() {
        
    }

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
    
    public Vector restar(Vector vectorSustraendo) {
        Vector vectorResultado = new Vector();
        
        vectorResultado.x = this.x - vectorSustraendo.x;
        vectorResultado.y = this.y - vectorSustraendo.y;
        vectorResultado.z = this.z - vectorSustraendo.z;
        
        return vectorResultado;
    }
    
    public float getModulo() {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }
    
    public float productoEscalar(Vector vector2) {
        return (this.x * vector2.x) + (this.y * vector2.y) + (this.z * vector2.z);
    }
    
    public Vector productoVectorial(Vector vector2) {
        Vector vectorResultado = new Vector();
        
        vectorResultado.x = (this.y * vector2.z) - (this.z * vector2.y);
        vectorResultado.y = (this.z * vector2.x) - (this.x * vector2.z);
        vectorResultado.z = (this.x * vector2.y) - (this.y * vector2.x);

        return vectorResultado;
    }
    
    public void normalizar() {
        float modulo = this.getModulo();
        
        x /= modulo;
        y /= modulo;
        z /= modulo;
    }
    
    public float getAnguloEntre(Vector vector2) {
        return (float) Math.toDegrees(Math.acos(this.productoEscalar(vector2)/
                (this.getModulo()*vector2.getModulo())));
    }

    @Override
    public String toString() {
        return String.format("[%f;%f;%f]", x, y, z);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Float.floatToIntBits(this.x);
        hash = 37 * hash + Float.floatToIntBits(this.y);
        hash = 37 * hash + Float.floatToIntBits(this.z);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector other = (Vector) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
            return false;
        }
        return true;
    }
    
    
 }

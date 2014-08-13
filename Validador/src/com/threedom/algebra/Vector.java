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
    
    private double x;
    private double y;
    private double z;
    
    public Vector() {
        
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    public Vector restar(Vector vector2) {
        Vector vectorResultado = new Vector();
        
        vectorResultado.x = this.x - vector2.x;
        vectorResultado.y = this.y - vector2.y;
        vectorResultado.z = this.z - vector2.z;
        
        return vectorResultado;
    }
    
    public Vector sumar(Vector vector2) {
        Vector vectorResultado = new Vector();
        
        vectorResultado.x = this.x + vector2.x;
        vectorResultado.y = this.y + vector2.y;
        vectorResultado.z = this.z + vector2.z;
        
        return vectorResultado;
    }
    
    public double getModulo() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }
    
    public double productoEscalar(Vector vector2) {
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
        double modulo = this.getModulo();
        
        x /= modulo;
        y /= modulo;
        z /= modulo;
    }
    
    public double getAnguloEntre(Vector vector2) {
        return Math.acos(this.productoEscalar(vector2)/
                (this.getModulo()*vector2.getModulo()));
    }
    
    public void cargarConValoresDe(Vector vector2) {
        this.x = vector2.x;
        this.y = vector2.y;
        this.z = vector2.z;
    }
    
    @Override
    public String toString() {
        return String.format("[%f;%f;%f]", x, y, z);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
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
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        return true;
    }
 }

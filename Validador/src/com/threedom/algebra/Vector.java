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
    
    public static Vector versorI = new Vector(1,0,0);
    public static Vector versorMenosI = new Vector(-1,0,0);
    public static Vector versorJ = new Vector(0,1,0);
    public static Vector versorMenosJ = new Vector(0,-1,0);
    public static Vector versorK = new Vector(0,0,1);
    public static Vector versorMenosK = new Vector(0,0,-1);
    
    public static final double DELTA = 0.001;
    
    
    public Vector() {
        
    }

    public Vector(double x, double y, double z) {
        /*this.x = x;
        this.y = y;
        this.z = z;*/
        
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        double superior = Math.ceil(x);
        double inferior = Math.floor(x);
        
        if((superior-x) < Vector.DELTA) {
            x = superior;
        }
        
        if((x-inferior) < Vector.DELTA) {
            x = inferior;
        }
        
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        double superior = Math.ceil(y);
        double inferior = Math.floor(y);
        
        if((superior-y) < Vector.DELTA) {
            y = superior;
        }
        
        if((y-inferior) < Vector.DELTA) {
            y = inferior;
        }
        
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        double superior = Math.ceil(z);
        double inferior = Math.floor(z);
        
        if((superior-z) < Vector.DELTA) {
            z = superior;
        }
        
        if((z-inferior) < Vector.DELTA) {
            z = inferior;
        }
        
        this.z = z;
    }
    
    public Vector restar(Vector vector2) {
        Vector vectorResultado = new Vector();
        
        vectorResultado.x = this.x - vector2.x;
        vectorResultado.y = this.y - vector2.y;
        vectorResultado.z = this.z - vector2.z;
        
        /*vectorResultado.setX(this.x-vector2.x);
        vectorResultado.setY(this.y-vector2.y);
        vectorResultado.setZ(this.z-vector2.z);*/
        
        return vectorResultado;
    }
    
    public Vector sumar(Vector vector2) {
        Vector vectorResultado = new Vector();
        
        vectorResultado.x = this.x + vector2.x;
        vectorResultado.y = this.y + vector2.y;
        vectorResultado.z = this.z + vector2.z;
        
        /*vectorResultado.setX(this.x+vector2.x);
        vectorResultado.setY(this.y+vector2.y);
        vectorResultado.setZ(this.z+vector2.z);*/
        
        return vectorResultado;
    }
    
    public double getModulo() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }
    
    public double productoEscalar(Vector vector2) {
        double productoEscalar = (this.x * vector2.x) + (this.y * vector2.y) + (this.z * vector2.z);
        return productoEscalar;
    }
    
    public Vector productoVectorial(Vector vector2) {
        Vector vectorResultado = new Vector();
        
        vectorResultado.x = (this.y * vector2.z) - (this.z * vector2.y);
        vectorResultado.y = (this.z * vector2.x) - (this.x * vector2.z);
        vectorResultado.z = (this.x * vector2.y) - (this.y * vector2.x);
        
        /*vectorResultado.setX((this.y * vector2.z) - (this.z * vector2.y));
        vectorResultado.setY((this.z * vector2.x) - (this.x * vector2.z));
        vectorResultado.setZ((this.x * vector2.y) - (this.y * vector2.x));*/

        return vectorResultado;
    }
    
    public void normalizar() {
        double modulo = this.getModulo();
        
        x /= modulo;
        y /= modulo;
        z /= modulo;
        
        /*this.setX(x/modulo);
        this.setY(y/modulo);
        this.setZ(z/modulo);*/
    }
    
    public double getAnguloEntre(Vector vector2) {
        return Math.acos(this.productoEscalar(vector2)/
                (this.getModulo()*vector2.getModulo()));
    }
    
    public double getAnguloEntreNormalizado(Vector vector2) {
        double productoEscalar = this.productoEscalar(vector2);
        double inferior = Math.floor(productoEscalar);
        double superior = Math.ceil(productoEscalar);
        double angulo;
        
        if((superior-productoEscalar) < Vector.DELTA) {
            productoEscalar = superior;
        }
        
        if((productoEscalar-inferior) < Vector.DELTA) {
            productoEscalar = inferior;
        }
        
        angulo = Math.acos(productoEscalar);
        return angulo;
    }
    
    public void cargarConValoresDe(Vector vector2) {
        this.x = vector2.x;
        this.y = vector2.y;
        this.z = vector2.z;
        
        /*this.setX(vector2.x);
        this.setY(vector2.y);
        this.setZ(vector2.z);*/
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

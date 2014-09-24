package com.threedom.geometry;

import com.threedom.algebra.MatrizDeRotacion;

public class Vertice {
	
	private double x;
	private double y;
	private double z;
    
    public static final double DELTA = 0.0001;
	
    public Vertice() {
        
    }
    
    public Vertice(Vertice vertice2) {
        this.x = vertice2.x;
        this.y = vertice2.y;
        this.z = vertice2.z;
    }

    public Vertice(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        double superior = Math.ceil(x);
        double inferior = Math.floor(x);
        
        if((superior-x) < Vertice.DELTA) {
            x = superior;
        }
        
        if((x-inferior) < Vertice.DELTA) {
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
        
        if((superior-y) < Vertice.DELTA) {
            y = superior;
        }
        
        if((y-inferior) < Vertice.DELTA) {
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
        
        if((superior-z) < Vertice.DELTA) {
            z = superior;
        }
        
        if((z-inferior) < Vertice.DELTA) {
            z = inferior;
        }
        
        this.z = z;
    }

    public boolean esMenorQue(Vertice vertice2) {
        if(Math.abs(this.z-vertice2.z) > Vertice.DELTA) {
            return this.z < vertice2.z;
        }
        
        return false;
    }
    
    public void rotar(MatrizDeRotacion matrizDeRotacion) {
        matrizDeRotacion.multiplicarYReemplazar(this);
    }
    
    public void cargarConValoresDe(Vertice vertice2) {
        this.x = vertice2.x;
        this.y = vertice2.y;
        this.z = vertice2.z;
    }
    
    public void restarYReemplazar(Vertice vertice2) {
        this.x -= vertice2.x;
        this.y -= vertice2.y;
        this.z -= vertice2.z;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
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
        final Vertice other = (Vertice) obj;
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

	@Override
	public String toString() {
		return String.format("(%f,%f,%f)",this.x,this.y,this.z);
	}
}

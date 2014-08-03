package com.threedom.geometry;

public class Vertice {
	
	private float x;
	private float y;
	private float z;
	
    public Vertice() {
        
    }

    public Vertice(float x, float y, float z) {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Float.floatToIntBits(this.x);
        hash = 11 * hash + Float.floatToIntBits(this.y);
        hash = 11 * hash + Float.floatToIntBits(this.z);
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

	@Override
	public String toString() {
		return String.format("(%f,%f,%f)",this.x,this.y,this.z);
	}
}

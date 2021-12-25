package agh.ics.oop.dataTypes;

import java.util.Objects;

// Data type for storing position on a 2d plane
public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," +y +")";
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(x+other.x, y+other.y);
    }
    public boolean equals(Object other){
        if (other instanceof Vector2d){
            return x== ((Vector2d) other).x && y==((Vector2d) other).y;
        }
        return false;
    }
    public Vector2d opposite(){
        return new Vector2d(-x, -y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

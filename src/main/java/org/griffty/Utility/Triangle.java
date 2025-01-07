package org.griffty.Utility;

import java.util.ArrayList;

public class Triangle {
    public Vector3 x;
    public Vector3 y;
    public Vector3 z;
    public Triangle(Vector3 x, Vector3 y, Vector3 z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Triangle(Triangle t){
        this.x = t.x;
        this.y = t.y;
        this.z = t.z;
    }
    public Triangle(){
        this.x = new Vector3();
        this.y = new Vector3();
        this.z = new Vector3();
    }
    public static ArrayList<Triangle> parseMultipleTriangles(String string){
        ArrayList<Triangle> triangles = new ArrayList<>();
        String s = "";
        for (int i = 0; i < string.length(); i++){
            if (string.charAt(i) != ' '){
                if (string.charAt(i) == ';'){
                    triangles.add(Triangle.parseTriangle(s));
                    s = "";
                }else {
                    s += string.charAt(i);
                }
            }
        }
        triangles.add(Triangle.parseTriangle(s));
        return triangles;
    }
    public static Triangle parseTriangle(String string) {
        Triangle triangle = new Triangle();
        String s = "";
        int c = 0;
        for (int i = 0; i < string.length(); i++){
            if (string.charAt(i) != ' '){
                if (string.charAt(i) == ':'){
                    if (c==0){
                        triangle.x = Vector3.parseVector3(s);
                    }else if(c==1){
                        triangle.y = Vector3.parseVector3(s);
                    }
                    c++;
                    s = "";
                }else{
                    s += string.charAt(i);
                }
            }
        }
        triangle.z = Vector3.parseVector3(s);
        return triangle;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}

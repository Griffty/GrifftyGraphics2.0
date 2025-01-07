package org.griffty.Utility;

public class Vector3 {
    public float x;
    public float y;
    public float z;
    public Vector3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3(Vector3 v){
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    public Vector3(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public void add(Vector3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }

    public void subtract(Vector3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
    }

    public static Vector3 normalize(Vector3 vec) {
        float length = (float) Math.sqrt(vec.x*vec.x + vec.y*vec.y + vec.z*vec.z);
        return new Vector3(vec.x / length, vec.y / length, vec.z / length);
    }

    /**
     *  Parse {@code String} to {@code Vector3} object.
     *
     * @param string String format: x,y,z. Not sensitive to spaces.
     *
     * */
    public static Vector3 parseVector3(String string) {
        Vector3 vector = new Vector3();
        String s = "";
        int c = 0;
        for (int i = 0; i < string.length(); i++){
            if (string.charAt(i) != ' '){
                if (string.charAt(i) == ','){
                    if (c == 0){
                        vector.x = Float.parseFloat(s);
                    }else if(c == 1){
                        vector.y = Float.parseFloat(s);
                    }
                    s = "";
                    c++;
                }else{
                    s += string.charAt(i);
                }
            }
        }
        vector.z = Float.parseFloat(s);
        return vector;
    }

    @Override
    public String toString() {
        return "V3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}

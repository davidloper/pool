package utils;

import com.badlogic.gdx.math.Vector2;

public class MyMath {

    public static double distance(Vector2 v1,Vector2 v2){
        return Math.sqrt(Math.pow((v2.x - v1.x),2) + Math.pow((v2.y - v1.y),2));
    }

    public static double angle(Vector2 v1,Vector2 v2){

//        double theta = Math.toDegrees(Math.atan2(v1.y - v2.y, v1.x - v2.x));
        double theta = Math.toDegrees(Math.atan2(v2.y - v1.y, v2.x - v1.x));
        if(theta >= 0)
            theta -= 180;
        else
            theta += 180;

        return theta;
    }
}

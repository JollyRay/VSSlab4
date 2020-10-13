package ru.ray.jolly.Accountant;

public class Functions {
    private Functions(){}

    //y'=sin(x)-y
    private static double fun0(double x, double y){
        return Math.sin(x)-y;
    }
    //y'=e^x-y
    private static double fun1(double x, double y){
        return Math.exp(x)-y;
    }
    //y'=4y-x
    private static double fun2(double x, double y){
        return 4*y-x;
    }
    //y'=4x
    private static double fun3(double x){
        return 4*x;
    }

    public static double chooserFunction(int id, double x, double y){
        switch (id){
            case (0): return fun0(x, y);
            case (1): return fun1(x, y);
            case (2): return fun2(x, y);
            case (3): return fun3(x);
        }
        return 0;
    }
}

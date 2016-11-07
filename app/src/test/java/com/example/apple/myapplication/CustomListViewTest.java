package com.example.apple.myapplication;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by apple on 16-1-4.
 * 泛型测试,泛型可作用于class，function，interface
 */
public class CustomListViewTest {
    private Point p;

    @org.junit.Before
    public void setUp() throws Exception {
        p = new Point<String, Integer>();
    }

    /**
     * 测试带泛型的class
     */
    @Test
    public void testFXInClass() {
        p = new Point<String, Integer>();
//        p.setVar(12);//编译不通过，在实例化该类时，泛型已经确定
        p.setVar("xdd");
        System.out.println(p.getVar());
        Point p2 = new Point();//实例化时未确定泛型类型
        p2.setVar(1);//首次调用时确定
        System.out.println(p2.getVar());
    }

    @Test
    public void testFxInFun() {
        p.fxInFun(1);
        p.fxInFun("hh");
        p.fxInFun(p);

    }

    @Test
    public void testFxInInterface() {
        FXInterface fxInterface = new FXInterface<Integer>() {
            @Override
            public <String> Integer getVar(String k) {
                System.out.println(k);
                return 0;//自动装箱
            }
        };
        fxInterface.getVar("hello");
    }

    public void test3() {
        //用泛形时，如果两边都使用到的泛形，两边必须要一样

        //ArrayList<Object> list = new ArrayList<String>();
        //ArrayList<String> list = new ArrayList<Object>();

        ArrayList<String> list = new ArrayList();
        ArrayList list1 = new ArrayList<String>();


    }

    @Test
    public void testClassLoader() {
        System.out.println(System.getProperty("sun.boot.class.path"));
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    class Point<T, V> {       // 此处可以随便写标识符号，T是type的简称,泛型只在编译期间起作用，生成class时被擦除
        private T var; // var的类型由T指定，即：由外部指定

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        private V value;

        public T getVar() {  // 返回值的类型由外部决定
            return var;
        }

        public void setVar(T var) {  // 设置的类型也由外部决定
            this.var = var;
        }

        public <K> void fxInFun(K value) {//函数的泛型作用域只存在于一次调用
            System.out.println(value);
        }
    }

    interface FXInterface<T> {  // 在接口上定义泛型
        public <K> T getVar(K k); // 定义抽象方法，抽象方法的返回值就是泛型类型
    }
}
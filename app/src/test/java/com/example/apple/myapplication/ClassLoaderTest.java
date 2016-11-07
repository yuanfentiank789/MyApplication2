package com.example.apple.myapplication;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by apple on 16-1-8.
 * 什么是ClassLoader?
 * <p/>
 * 大家都知道，当我们写好一个Java程序之后，不是管是CS还是BS应用，都是由若干个.class文件组织而成的一个完整的Java应用程序，
 * 当程序在运行时，即会调用该程序的一个入口函数来调用系统的相关功能，而这些功能都被封装在不同的class文件当中，所以经常要从
 * 这个class文件中要调用另外一个class文件中的方法，如果另外一个文件不存在的，则会引发系统异常。而程序在启动的时候，并不会
 * 一次性加载程序所要用的所有class文件，而是根据程序的需要，通过Java的类加载机制（ClassLoader）来动态加载某个class文件
 * 到内存当中的，从而只有class文件被载入到了内存之后，才能被其它class所引用。所以ClassLoader就是用来动态加载class文件到内存当中用的
 * Java默认提供的三个ClassLoader:
 * 1 Bootstrap ClassLoader：称为启动类加载器，是Java类加载层次中最顶层的类加载器，负责加载JDK中的核心类库，如：rt.jar、resources.jar、charsets.jar等
 * 2 Extension ClassLoader：称为扩展类加载器，负责加载Java的扩展类库，默认加载JAVA_HOME/jre/lib/ext/目下的所有jar。
 * 3 App ClassLoader：称为系统类加载器，负责加载应用程序classpath目录下的所有jar和class文件。
 */
public class ClassLoaderTest {

    /**
     * 验证ClassLoader加载类的原理：
     * <p/>
     * 测试1：打印ClassLoader类的层次结构，请看下面这段代码：
     * 打印结果：
     * sun.misc.Launcher$AppClassLoader@14ae5a5
     * sun.misc.Launcher$ExtClassLoader@3b07d329
     * null
     * 第一行结果说明：ClassLoaderTest的类加载器是AppClassLoader。
     * <p/>
     * 第二行结果说明：AppClassLoader的类加器是ExtClassLoader，即parent=ExtClassLoader。
     * <p/>
     * 第三行结果说明：ExtClassLoader的类加器是Bootstrap ClassLoader，因为Bootstrap ClassLoader
     * 不是一个普通的Java类，所以ExtClassLoader的parent=null，所以第三行的打印结果为null就是这个原因
     */

    /**
     * 通过如下程序获得该类加载器从哪些地方加载了相关的jar或class文件：
     * URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
     * for (int i = 0; i < urls.length; i++) {
     * System.out.println(urls[i].toExternalForm());
     * }或者
     * <p/>
     * System.out.println(System.getProperty("sun.boot.class.path"));
     * <p/>
     * 打印结果如下:
     * /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/lib/resources.jar:
     * /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/lib/rt.jar:
     * /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/lib/sunrsasign.jar:
     * /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/lib/jsse.jar:
     * /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/lib/jce.jar:
     * /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/lib/charsets.jar:
     * /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/lib/jfr.jar:
     * /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/classes
     */

    @Test
    public void getBootStrapLoadPath() {
        System.out.println(System.getProperty("sun.boot.class.path"));
    }

    @Test
    public void getLoaders() {
        ClassLoader loader = ClassLoaderTest.class.getClassLoader();    //获得加载ClassLoaderTest.class这个类的类加载器
        while (loader != null) {
            System.out.println(loader);
            loader = loader.getParent();    //获得父类加载器的引用
        }
        System.out.println(loader);
    }

    /**
     * Java中提供的默认ClassLoader，只加载指定目录下的jar和class，如果我们想加载其它位置的类或jar时，比如：
     * 我要加载网络上的一个class文件，通过动态加载到内存之后，要调用这个类中的方法实现我的业务逻辑。在这样的情况下，
     * 默认的ClassLoader就不能满足我们的需求了，所以需要定义自己的ClassLoader。
     * 定义自已的类加载器分为两步：
     * <p/>
     * 1、继承java.lang.ClassLoader
     * <p/>
     * 2、重写父类的findClass方法
     */
    @Test
    public void customLoader() {
        try {
            String rootUrl = "/Users/apple/development/android/AsProjects/Huasheng/app/build/intermediates/classes/google/release";
            NetworkClassLoader networkClassLoader = new NetworkClassLoader(rootUrl);
            String classname = "com.vread.hs.HSApplication";
            Class clazz = networkClassLoader.loadClass(classname);
            System.out.println(clazz.getName());
            System.out.println(clazz.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载网络class的ClassLoader
     */
    public class NetworkClassLoader extends ClassLoader {

        private String rootUrl;

        public NetworkClassLoader(String rootUrl) {
            this.rootUrl = rootUrl;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            Class clazz = null;//this.findLoadedClass(name); // 父类已加载
            //if (clazz == null) {  //检查该类是否已被加载过
            byte[] classData = getClassData(name);  //根据类的二进制名称,获得该class文件的字节码数组
            if (classData == null) {
                throw new ClassNotFoundException();
            }
            clazz = defineClass(name, classData, 0, classData.length);  //将class的字节码数组转换成Class类的实例
            //}
            return clazz;
        }

        private byte[] getClassData(String name) {
            InputStream is = null;
            try {
                String path = classNameToPath(name);
//                URL url = new URL(path);
                byte[] buff = new byte[1024 * 4];
                int len = -1;
//                is = url.openStream();
                is = new FileInputStream(new File(path));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((len = is.read(buff)) != -1) {
                    baos.write(buff, 0, len);
                }
                return baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        private String classNameToPath(String name) {
            return rootUrl + "/" + name.replace(".", "/") + ".class";
        }

    }

    @Test
    public void test() {
//        Assert.assertEquals(3, 2 + 4);
        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("name", "彭广杰");
            jsonObject.put("url", "http://www.codeforge.cn/article/248517");
            System.out.println(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

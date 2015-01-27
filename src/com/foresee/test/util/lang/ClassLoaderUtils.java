package com.foresee.test.util.lang;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Javen
 *
 */
public class ClassLoaderUtils {

    public static Class loadClass(String className, Class callingClass) throws ClassNotFoundException {
        return loadClass(className, callingClass.getClassLoader());
    }

    public static Class loadClass(String className, ClassLoader callingClassLoader) throws ClassNotFoundException {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException ex) {
                try {
                    return ClassLoaderUtils.class.getClassLoader().loadClass(className);
                } catch (ClassNotFoundException exc) {
                }
            }
        }
        return callingClassLoader.loadClass(className);
    }

    public static URL getResource(String resourceName, Class callingClass) {
        URL url = null;

        url = Thread.currentThread().getContextClassLoader().getResource(resourceName);

        if (url == null) {
            url = ClassLoaderUtils.class.getClassLoader().getResource(resourceName);
        }

        if (url == null) {
            url = callingClass.getClassLoader().getResource(resourceName);
        }
        return url;
    }

    public static ResourceBundle getBundle(String resourceName, Locale locale, Class callingClass) {
        ResourceBundle bundle = null;

        bundle = ResourceBundle.getBundle(resourceName, locale, Thread.currentThread().getContextClassLoader());

        if (bundle == null) {
            bundle = ResourceBundle.getBundle(resourceName, locale, ClassLoaderUtils.class.getClassLoader());
        }

        if (bundle == null) {
            bundle = ResourceBundle.getBundle(resourceName, locale, callingClass.getClassLoader());
        }
        return bundle;
    }

    public static Enumeration getResources(String resourceName, Class callingClass) throws IOException {
        Enumeration urls = Thread.currentThread().getContextClassLoader().getResources(resourceName);
        if (urls == null) {
            urls = ClassLoaderUtils.class.getClassLoader().getResources(resourceName);
            if (urls == null) {
                urls = callingClass.getClassLoader().getResources(resourceName);
            }
        }

        return urls;
    }

    public static InputStream getResourceAsStream(String resourceName, Class callingClass) {
        URL url = getResource(resourceName, callingClass);
        try {
            return ((url != null) ? url.openStream() : null);
        } catch (IOException e) {
        }
        return null;
    }

    public static void printClassLoader() {
        System.out.println("ClassLoaderUtils.printClassLoader");
        printClassLoader(Thread.currentThread().getContextClassLoader());
    }

    public static void printClassLoader(ClassLoader cl) {
        System.out.println("ClassLoaderUtils.printClassLoader(cl = " + cl + ")");
        if (cl == null)
            return;
        printClassLoader(cl.getParent());
    }
}
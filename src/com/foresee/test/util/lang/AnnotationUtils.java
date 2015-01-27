package com.foresee.test.util.lang;

import java.lang.annotation.Annotation;

/**
 * 类注解处理类
 * 
 * @author Javen
 * 
 */
public class AnnotationUtils {

    /**
     * Returns the annotation on the given class or the package of the class.
     * This searchs up the class hierarchy and the package hierarchy for the
     * closest match.
     * 
     * @param klass
     *            The class to search for the annotation.
     * @param annotationClass
     *            The Class of the annotation.
     * @return The annotation or null.
     */
    public static <T extends Annotation> T findAnnotation(Class<?> klass, Class<T> annotationClass) {
        T ann = klass.getAnnotation(annotationClass);
        while (ann == null && klass != null) {
            ann = klass.getAnnotation(annotationClass);
            if (ann == null)
                ann = klass.getPackage().getAnnotation(annotationClass);
            if (ann == null) {
                klass = klass.getSuperclass();
                if (klass != null) {
                    ann = klass.getAnnotation(annotationClass);
                }
            }
        }

        return ann;
    }
}
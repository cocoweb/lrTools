package com.foresee.test.framework.decoration;


public interface Decorator<T> extends java.io.Serializable {

    public void setModel(T model);

    //public T getModel();
}
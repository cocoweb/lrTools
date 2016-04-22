package com.foresee.xdeploy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ToOutputStream extends OutputStream {
    InputStream inputstream;
    
    
    public ToOutputStream(InputStream inputstream) {
        super();
        this.inputstream = inputstream;
    }


    @Override
    public void write(int b) throws IOException {
        // TODO Auto-generated method stub
        
    }



}

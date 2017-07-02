package lrTestool.factoryDemo;

import com.foresee.test.util.lang.ReflectUtil;

public class Gogo {

    public static void main(String[] args) throws Exception {
        DemoSuper c1 = new Class1();
        
        ((Class1)c1).showing();
        c1.showing();
        Class1.typing();
       // DemoSuper.showing();
        ReflectUtil.invokeStaticMethod("lrTestool.factoryDemo.Class1","showing");
        FactoryDemo.getDemo("1").showing();

    }

}

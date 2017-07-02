package lrTestool.factoryDemo;

public class FactoryDemo {
    public DemoSuper xdemo;
    
    public static DemoSuper getDemo(String type){
        if(type=="1"){
            return new Class1();
        }else{
            return new Class2();
        }
    }

}

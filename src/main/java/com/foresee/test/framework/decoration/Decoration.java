package com.foresee.test.framework.decoration;


import java.util.Collection;
import java.util.List;

public interface Decoration<Model, Decor extends Decorator<Model>> {

    public List<Decor> baseExchange(Collection<Model> models);

    public Decor baseExchange(Model model);

}

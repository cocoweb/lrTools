package com.foresee.test.framework.decoration;


import org.apache.struts2.json.annotations.JSON;

import com.foresee.xdeploy.utils.GenericsUtils;

@SuppressWarnings("serial")
public abstract class DecoratorModel1<Model> implements Decorator<Model> {

    private Model model;

    // protected DecoratorHelper<Model, ? extends Decorator<Model>> helper;

    /**
     * 装饰器构造函数，如果没有自动创建一个实体
     */
    @SuppressWarnings("unchecked")
    public DecoratorModel1() {
        super();
        try {
            Class<Model> cls = GenericsUtils.getSuperClassGenricType(
                    this.getClass(), 0);
            this.setModel(cls.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param model
     *            将实体封装进入装饰器
     */
    public DecoratorModel1(Model model) {
        super();
        this.setModel(model);
    }

    @JSON(serialize = false)
    @Override
    public final Model getModel() {
        return this.model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.wesley.framework.decoration.Decorator#setModel(java.lang.Object)
     * 装饰器接口，将Model注入到装饰器中
     */
    @Override
    public void setModel(Model model) {
        this.model = model;
        // helper = new DecoratorHelper<Model,Decorator<Model>>(
        // (Class<Decorator<Model>>) this.getClass());
    }

    // /*
    // * (non-Javadoc)
    // *
    // * @see
    // *
    // com.wesley.framework.decoration.Decoration#baseExchange(java.lang.Object)
    // *
    // * 反射必有参数构造函数，将实体包含在装饰器之中
    // */
    // @SuppressWarnings("unchecked")
    // @Override
    // public Decor baseExchange(Model model) {
    // Decor decor = null;
    // try {
    // decor = (Decor) this.getClass().getConstructor(model.getClass())
    // .newInstance(model);
    // } catch (InstantiationException e) {
    // e.printStackTrace();
    // } catch (IllegalAccessException e) {
    // e.printStackTrace();
    // } catch (IllegalArgumentException e) {
    // e.printStackTrace();
    // } catch (SecurityException e) {
    // e.printStackTrace();
    // } catch (InvocationTargetException e) {
    // e.printStackTrace();
    // } catch (NoSuchMethodException e) {
    // e.printStackTrace();
    // }
    // decor.setModel(model);
    // return decor;
    // }
    //
    // /*
    // * (non-Javadoc)
    // *
    // * @see
    // *
    // com.wesley.framework.decoration.Decoration#baseExchange(java.util.Collection
    // * 转换List等Collection接口数据
    // */
    // @Override
    // public List<Decor> baseExchange(Collection<Model> models) {
    // List<Decor> decorList = new ArrayList<Decor>();
    // for (Model model : models) {
    // decorList.add(this.baseExchange(model));
    // }
    // return decorList;
    // }

}

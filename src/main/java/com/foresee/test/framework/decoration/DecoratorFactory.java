package com.foresee.test.framework.decoration;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;

import com.wesley.framework.commen.ArrayUtil;
import com.wesley.framework.commen.ObjectUtil;
import com.wesley.framework.commen.reflect.ClassUtil;
import com.wesley.framework.commen.reflect.ReflectionUtils;

public class DecoratorFactory {

    @SuppressWarnings({ "unchecked"})
    public synchronized static <Model, Decor extends Decorator<Model>, SuperDec extends Decorator<?>> Decor exchange(
            SuperDec superDec, Class<Decor> clazz, String propertyName,
            String... params) {
        Decor decor = null;
        try {
            decor = ClassUtil.newInstance(clazz);
            Model model = (Model) ReflectionUtils.invokeGetterMethod(
                    superDec.getModel(), propertyName);
            if (ObjectUtil.isEmpty(model)) {
                ReflectionUtils.invokeSetterMethod(superDec, propertyName,
                        decor);
                return decor;
            }
            Map<String, String> map = ArrayUtil.toMap(params);
            if (!MapUtils.isEmpty(map))
                BeanUtils.populate(decor, map);
            decor.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decor;
    }

    public synchronized static <Model, Decor extends Decorator<Model>> Decor exchange(
            Class<Decor> clazz, Model model, String... params) {
        Decor decor = null;
        try {
            if (ObjectUtil.isEmpty(model))
                return null;
            decor = ClassUtil.newInstance(clazz);
            Map<String, String> map = ArrayUtil.toMap(params);
            if (!MapUtils.isEmpty(map))
                BeanUtils.populate(decor, map);
            decor.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decor;
    }

    @SuppressWarnings("unchecked")
    public static <Model, Decor extends Decorator<Model>> List<Decor> exchange(
            Decor dec, Collection<Model> models, String... params) {
        List<Decor> decorList = new ArrayList<Decor>();
        try {
            for (Model model : models) {
                decorList.add((Decor) DecoratorFactory.exchange(dec.getClass(),
                        model, params));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decorList;
    }

    public static <Model, Decor extends Decorator<Model>> List<Decor> exchange(
            Class<Decor> clazz, Collection<Model> models, String... params) {
        List<Decor> decorList = new ArrayList<Decor>();
        try {
            for (Model model : models) {
                decorList.add(DecoratorFactory.exchange(clazz, model, params));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decorList;
    }

    public static <Model, Decor extends Decorator<Model>> Set<Model> exchange(
            Collection<Decor> decs, String... params) {
        Set<Model> decorList = new HashSet<Model>();
        try {
            for (Decor dec : decs) {
                Model model = dec.getModel();
                Map<String, String> map = ArrayUtil.toMap(params);
                if (!MapUtils.isEmpty(map))
                    BeanUtils.populate(model, map);
                decorList.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decorList;
    }

}

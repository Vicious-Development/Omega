package com.vicious.omega.environment;

import java.util.*;
import java.util.function.Function;

public class EnviromentWrapper<T extends EnviromentWrapper<T>> {
    protected Object src;
    public EnviromentWrapper(Object src){
        //Convert Optionals to not optionals.
        if(src instanceof Optional){
            src = ((Optional<?>) src).orElse(null);
        }
        this.src=src;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()){
            return src.equals(o);
        }
        EnviromentWrapper<?> that = (EnviromentWrapper<?>) o;
        return Objects.equals(src, that.src);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src);
    }
    public <T extends EnviromentWrapper<T>> Collection<T> wrap(Collection<?> og, Function<Object,T> constructor){
        List<T> ret = new ArrayList<>();
        for (Object o : og) {
            ret.add(constructor.apply(o));
        }
        return ret;
    }

    public <T extends EnviromentWrapper<T>> Set<T> wrap(Set<?> og, Function<Object,T> constructor){
        Set<T> ret = new HashSet<>();
        for (Object o : og) {
            ret.add(constructor.apply(o));
        }
        return ret;
    }

    public <T extends EnviromentWrapper<T>> Iterable<T> wrap(Iterable<?> og, Function<Object,T> constructor){
        List<T> ret = new ArrayList<>();
        for (Object o : og) {
            ret.add(constructor.apply(o));
        }
        return ret;
    }

    public <T extends EnviromentWrapper<T>> List<T> wrapAsList(Iterable<?> og, Function<Object,T> constructor){
        List<T> ret = new ArrayList<>();
        for (Object o : og) {
            ret.add(constructor.apply(o));
        }
        return ret;
    }
    public <T extends EnviromentWrapper<T>> List<T> wrapAsList(Object[] og, Function<Object,T> constructor){
        List<T> ret = new ArrayList<>();
        for (Object o : og) {
            ret.add(constructor.apply(o));
        }
        return ret;
    }
    public boolean isNull(){
        return src == null;
    }
    public boolean notNull(){
        return src != null;
    }
    protected void nullSafe(Runnable nullSafeCode){
        if(src == null) return;
        else nullSafeCode.run();
    }
}

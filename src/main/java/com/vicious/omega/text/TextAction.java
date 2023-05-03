package com.vicious.omega.text;

public abstract class TextAction<V> extends TextComponent{
    protected V value;
    public TextAction(V value){
        this.value=value;
    }
    @Override
    public String serializeJSON() {
        return quote(jsonLabel()) + ":{" + serializeActionJSON() + "," + serializeValueJSON() + "}";
    }
    protected String serializeActionJSON(){
        return quote("action") + ":" + quote(actionName());
    }
    protected String serializeValueJSON(){
        return quote("value") + ":" + quote(value.toString());
    }
    public abstract String actionName();


    //Plaintext isn't supported by MC (i'm pretty sure)
    @Override
    public String serializePlain() {
        return "";
    }
}

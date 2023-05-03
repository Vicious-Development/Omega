package com.vicious.omega.text;

public abstract class TextComponent {
    protected abstract String jsonLabel();
    protected String value;
    public abstract String serializeJSON();
    public abstract String serializePlain();
    protected String quote(String in){
        return "\"" + in + "\"";
    }
}

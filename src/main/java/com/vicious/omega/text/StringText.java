package com.vicious.omega.text;

public class StringText extends TextComponent{
    private final String str;

    public StringText(String str){
        this.str=str;
    }
    @Override
    protected String jsonLabel() {
        return "text";
    }

    @Override
    public String serializeJSON() {
        return quote(jsonLabel()) + ":" + quote(str);
    }

    @Override
    public String serializePlain() {
        return str;
    }
}

package com.vicious.omega.text;

import java.util.Arrays;
import java.util.List;

public class Text extends TextComponent{
    private List<TextComponent> children;
    private StringText value;
    @Override
    protected String jsonLabel() {
        return "text";
    }

    //TODO:IMPL
    @Override
    public String serializeJSON() {
        return null;
    }

    @Override
    public String serializePlain() {
        return null;
    }

    public Text(String str){
        value=new StringText(str);
    }
    public Text(TextComponent... children){
        this.children = Arrays.asList(children);

    }
    public static Text of(Object... in){
        Text parent = new Text();
        for (Object obj : in) {
            if(obj instanceof TextComponent){
                parent.children.add((TextComponent) obj);
            }
            else if(obj instanceof String){
                parent.children.add(new Text((String) obj));
            }
            else{
                parent.children.add(new Text(obj.toString()));
            }
        }
        return parent;
    }
}

package com.vicious.omega.text;

import com.vicious.omega.advancement.Advancement;
import com.vicious.omega.entity.mob.Entity;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class HoverAction<V> extends TextAction<V> {
    public HoverAction(V value) {
        super(value);
    }

    @Override
    protected String jsonLabel() {
        return "hoverEvent";
    }
    public static class ShowEntity extends HoverWithContents<Entity>{
        public ShowEntity(Entity value) {
            super(value);
        }

        @Override
        public Map<String, String> getMap() {
            Map<String,String> out = new LinkedHashMap<>();
            out.put("type",value.getType().getTypeName());
            out.put("name",value.getName());
            out.put("id",value.getUUID().toString());
            return out;
        }

        @Override
        public String actionName() {
            return "show_entity";
        }
    }
    public static class Text extends HoverAction<com.vicious.omega.text.Text>{
        public Text(String value) {
            super(com.vicious.omega.text.Text.of(value));
        }
        public Text(com.vicious.omega.text.Text value){
            super(value);
        }

        @Override
        public String actionName() {
            return "show_text";
        }
    }
    public static class ShowAchievement extends HoverAction<String>{
        public ShowAchievement(String value) {
            super("achievement." + value);
        }
        public ShowAchievement(Advancement value) {
            super("achievement." + value.getId());
        }

        @Override
        public String actionName() {
            return "show_achievement";
        }
    }
    //Note in 1.12.2 this has a different format from current. TODO: In 1.16.5+ update this serializer.
    public abstract static class HoverWithContents<V> extends HoverAction<V>{
        public HoverWithContents(V value) {
            super(value);
        }

        @Override
        protected String serializeValueJSON() {
            String ret = quote("value") + ":";
            StringBuilder inner = new StringBuilder().append("{");
            Map<String,String> map = getMap();
            boolean comma = false;
            for (String s : map.keySet()) {
                if(comma) inner.append(",");
                inner.append(s).append(":").append(map.get(s));
                comma = true;
            }
            return ret + quote(inner.append("}").toString());
        }

        public abstract Map<String,String> getMap();
    }
}

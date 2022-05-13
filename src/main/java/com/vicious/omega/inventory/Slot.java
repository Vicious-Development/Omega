package com.vicious.omega.inventory;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.environment.UnsupportedEnvironmentException;

/**
 * Complete
 */
public class Slot extends Inventory {
    private int index = -1;
    public Slot(Object src) {
        super(src);
    }
    public Slot(Object src, int idx) {
        super(src);
        index=idx;
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public int getStackSize(){
        if(Environment.SPONGE.active()) return asSponge().totalItems();
        else if(Environment.BUKKIT.active()) return asBukkit().getItem(index).getAmount();
        else throw new UnsupportedEnvironmentException();
    }

}

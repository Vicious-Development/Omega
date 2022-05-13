package com.vicious.omega.inventory;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;
import com.vicious.omega.inventory.bukkit.EmptyItemStack;

//TODO WIP
public class ItemStack extends PluginAPIWrapper<ItemStack, org.spongepowered.api.item.inventory.ItemStack, org.bukkit.inventory.ItemStack> {
    public ItemStack(Object src) {
        super(src);
    }
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public static ItemStack empty(){
        if(Environment.SPONGE.active()) return new ItemStack(org.spongepowered.api.item.inventory.ItemStack.empty());
        else if(Environment.BUKKIT.active()) return new ItemStack(new EmptyItemStack());
        else throw new UnsupportedEnvironmentException();
    }

    /**
     * Yields an itemstack either storing less than or equal to the amount requested by reducing the size of another.
     * @param itemStack - The stack to pull from.
     * @param toPull - The amount to try to pull out of the stack.
     * @return the resulting itemStack extracted.
     */
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public static ItemStack extract(ItemStack itemStack, Inventory containing, int toPull) {
        if(itemStack.size() > toPull){
            itemStack.reduceBy(toPull);
            return itemStack.copy().setSize(toPull);
        }
        else if(itemStack.size() == toPull){
            containing.removeItem(itemStack);
            return itemStack.copy();
        }
        else{
            int size = itemStack.size();
            containing.removeItem(itemStack);
            return itemStack.copy().setSize(size);
        }
    }
    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public ItemStack copy(){
        if(sponge()) return new ItemStack(asSponge().copy());
        else if(bukkit()) return new ItemStack(asBukkit().clone());
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public int size(){
        if(sponge()) return asSponge().getQuantity();
        else if(bukkit()) return asBukkit().getAmount();
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public ItemStack setSize(int newSize){
        if(sponge()) asSponge().setQuantity(newSize);
        else if(bukkit()) asBukkit().setAmount(newSize);
        else throw new UnsupportedEnvironmentException();
        return this;
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public ItemStack reduceBy(int toRemove){
        if(sponge()) asSponge().setQuantity(asSponge().getQuantity()-toRemove);
        else if(bukkit()) asBukkit().setAmount(asBukkit().getAmount()-toRemove);
        else throw new UnsupportedEnvironmentException();
        return this;
    }

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public boolean isEmpty(){
        if(sponge()) return asSponge().isEmpty();
        else if(bukkit()) return src instanceof EmptyItemStack;
        else throw new UnsupportedEnvironmentException();
    }

    @EnvironmentCompatibility({Environment.BUKKIT,Environment.SPONGE})
    public ItemStack addBy(int toAdd){
        if(sponge()) asSponge().setQuantity(asSponge().getQuantity()+toAdd);
        else if(bukkit()) asBukkit().setAmount(asBukkit().getAmount()+toAdd);
        else throw new UnsupportedEnvironmentException();
        return this;
    }
}

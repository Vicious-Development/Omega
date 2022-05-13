package com.vicious.omega.inventory;

import com.vicious.omega.environment.Environment;
import com.vicious.omega.environment.annotations.EnvironmentCompatibility;
import com.vicious.omega.environment.PluginAPIWrapper;
import com.vicious.omega.environment.UnsupportedEnvironmentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//TODO WIP
public class Inventory extends PluginAPIWrapper<Inventory, org.spongepowered.api.item.inventory.Inventory, org.bukkit.inventory.Inventory> {
    public Inventory(Object src) {
        super(src);
    }
    //TODO add Sponge parent()

    //TODO add Sponge root()

    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public Iterable<Inventory> slots(){
        if(sponge()) return wrap(asSponge().slots(),this::convert);
        else if(bukkit()) {
            List<Inventory> slots = new ArrayList<>();
            org.bukkit.inventory.Inventory bukkitInv = asBukkit();
            for (int i = 0; i < bukkitInv.getContents().length; i++) {
                slots.add(new Slot(bukkitInv,i));
            }
            return slots;
        }
        else throw new UnsupportedEnvironmentException();
    }


    //TODO add Sponge first()

    //TODO add Sponge next()

    /**
     * Returns the first non-empty itemstack in the inventory else returns an empty itemstack.
     * @return
     */
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public ItemStack poll(){
        if(sponge()) return new ItemStack(asSponge().poll());
        else if(bukkit()) {
            for (org.bukkit.inventory.ItemStack content : asBukkit().getContents()) {
                if(content != null) return new ItemStack(content);
            }
            return ItemStack.empty();
        }
        else throw new UnsupportedEnvironmentException();
    }

    /**
     * Takes out the as much requested of the first item found.
     * @param limit - The maximum amount to retrieve
     * @return a new ItemStack containing an unknown amount less than or equal to the limit.
     */
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public ItemStack poll(int limit){
        if(sponge()) return new ItemStack(asSponge().poll(limit));
        else if(bukkit()) {
            ItemStack out = ItemStack.empty();
            for (org.bukkit.inventory.ItemStack content : asBukkit().getContents()) {
                if(content != null){
                    if(out.isEmpty()) {
                        out = ItemStack.extract(new ItemStack(content),this,limit);
                        if(out.size() < limit){
                            limit-=out.size();
                        }
                    }
                    else{
                        ItemStack res = ItemStack.extract(new ItemStack(content),this,limit);
                        if(res.size() < limit){
                            limit-=res.size();
                        }
                        out.addBy(res.size());
                    }
                }
            }
            return ItemStack.empty();
        }
        else throw new UnsupportedEnvironmentException();
    }

    /**
     * Removes an ItemStack only if the inventory contains an exact match of it.
     * @param item - the itemstack to locate.
     */
    @EnvironmentCompatibility({Environment.SPONGE,Environment.BUKKIT})
    public void removeItem(ItemStack item){
        if(sponge()) for (org.spongepowered.api.item.inventory.Inventory slot : asSponge().slots()) {
            if(slot instanceof org.spongepowered.api.item.inventory.Slot){
                Optional<org.spongepowered.api.item.inventory.ItemStack> stack = slot.peek();
                if(stack.isPresent()){
                    if(stack.get().equals(item.asSponge())) slot.clear();
                }
            }
        }
        else if(bukkit()){
            org.bukkit.inventory.ItemStack[] contents = asBukkit().getContents();
            for (int i = 0; i < contents.length; i++) {
                if(item.asBukkit().equals(contents[i])){
                    contents[i] = null;
                    break;
                }
            }
            asBukkit().setContents(contents);
        }
        else throw new UnsupportedEnvironmentException();
    }

    private Inventory convert(Object o){
        if(sponge()){
            if(o instanceof Slot) return new Slot(o);
        }
        return new Inventory(o);
    }
}

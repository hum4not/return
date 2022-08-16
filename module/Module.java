package org.returnclient.module;

import net.minecraft.client.Minecraft;
import org.returnclient.Return;
import org.returnclient.module.impl.visual.Overlay;

public class Module {

    public Minecraft mc = Minecraft.getMinecraft();

    private String name, suffix;

    private float x, y;

    private int bind;

    private Category category;

    private boolean toggled, hidden;

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void toggle() {
        this.toggled = !toggled;

        if(toggled) {
            Overlay.mapX.put(this, 90f);
            onEnable();
        } else {
            Overlay.mapX.remove(this);
            onDisable();
        }
    }

    public void hidden() {
        this.hidden = !hidden;
    }

    public void onEnable() {
        Return.eventManager.register(this);
    }

    public void onDisable() {
        Return.eventManager.unregister(this);
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public enum Category {

        combat("Combat"),
        movement("Movement"),
        player("Player"),
        visual("Visual"),
        misc("Misc"),
        config("Config");

        String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

}

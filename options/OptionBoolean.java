package org.returnclient.options;

import org.returnclient.module.Module;

public class OptionBoolean extends Option {

    public boolean toggled;

    public OptionBoolean(String name, Module module, boolean toggled) {
        this.name = name;
        this.module = module;
        this.toggled = toggled;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void toggle() {
        this.toggled = !toggled;
    }

}

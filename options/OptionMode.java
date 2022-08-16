package org.returnclient.options;

import org.returnclient.module.Module;

import java.util.Arrays;
import java.util.List;

public class OptionMode extends Option {

    public int index;
    public List<String> modes;

    public OptionMode(String name, Module module, String defaultMode, String[] modes) {
        this.name = name;
        this.module = module;
        this.modes = Arrays.asList(modes);
        index = this.modes.indexOf(defaultMode);
    }

    public String getMode() {
        return modes.get(index);
    }

    public boolean is(String mode) {
        return index == modes.indexOf(mode);
    }

    public void cycle() {
        if(index < modes.size() - 1) {
            index++;
        }else {
            index = 0;
        }
    }
}

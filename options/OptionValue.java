package org.returnclient.options;

import org.returnclient.module.Module;

public class OptionValue extends Option {

    public double value, min, max;

    public OptionValue(String name, Module module, double value, double min, double max) {
        this.name = name;
        this.module = module;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public double getValue() {
        return value;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setValue(double value) {
        this.value = value;
    }

}

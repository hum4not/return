package org.returnclient.options;

import java.util.ArrayList;
import java.util.List;

public class OptionManager {

    public List<Option> options = new ArrayList<Option>();

    public void addValue(OptionValue option) {
        options.add(option);
    }

    public void addBoolean(OptionBoolean option) {
        options.add(option);
    }

    public void addMode(OptionMode option) {
        options.add(option);
    }

}

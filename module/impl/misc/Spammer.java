package org.returnclient.module.impl.misc;

import org.returnclient.Return;
import org.returnclient.event.EventTarget;
import org.returnclient.event.impl.EventUpdate;
import org.returnclient.module.Module;
import org.returnclient.options.OptionValue;
import org.returnclient.utils.StringUtils;
import org.returnclient.utils.TimerUtils;

import java.util.ArrayList;
import java.util.Random;

public class Spammer extends Module {

    ArrayList<String> massages = new ArrayList<String>();

    TimerUtils timer = new TimerUtils();

    OptionValue delay;

    public Spammer() {
        super("Spammer", Category.misc);
        Return.getInstance().optionManager.addValue(delay = new OptionValue("Delay", this, 6000, 3000, 12000));

        massages.add("Please no hack guys, use return client");
        massages.add("No hax, just return client");
        massages.add("Stalin alive, use return client");
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(timer.hasTimeElapsed((long) delay.getValue(), true)) {

            Random r = new Random();
            int index = r.nextInt(massages.size());

            String massage = massages.get(index);

            mc.thePlayer.sendChatMessage("[" + mc.getSystemTime() % 1000L + StringUtils.getRandomString(3) + "] " + massage + " [" + mc.getSystemTime() % 1000L + StringUtils.getRandomString(3) + "]");
        }
    }

}

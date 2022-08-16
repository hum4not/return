package org.returnclient;

import org.lwjgl.opengl.Display;
import org.returnclient.altmanager.AltManager;
import org.returnclient.clickgui.ClickGuiScreen;
import org.returnclient.event.EventManager;
import org.returnclient.module.ModuleManager;
import org.returnclient.notifications.NotificationManager;
import org.returnclient.options.OptionManager;

public class Return {

    public static Return instance = new Return();

    public static Return getInstance() {
        return instance;
    }

    public ModuleManager moduleManager;
    public OptionManager optionManager;
    public NotificationManager notificationManager;
    public AltManager altManager;
    public static ClickGuiScreen clickGuiScreen;
    public static EventManager eventManager;

    public String getBuild() { return "230222"; }

    public void run() {
        Display.setTitle("Return retard edition");

        eventManager = new EventManager();
        optionManager = new OptionManager();
        moduleManager = new ModuleManager();
        notificationManager = new NotificationManager();
        altManager = new AltManager();
        clickGuiScreen = new ClickGuiScreen();
        eventManager.register(this);
    }

    public void shutDown() {
        eventManager.unregister(this);
    }

}

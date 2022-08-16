package org.returnclient.module;

import org.returnclient.Return;
import org.returnclient.module.impl.combat.*;
import org.returnclient.module.impl.config.OtherSettings;
import org.returnclient.module.impl.misc.*;
import org.returnclient.module.impl.movement.*;
import org.returnclient.module.impl.player.ChestStealer;
import org.returnclient.module.impl.player.NoBlindness;
import org.returnclient.module.impl.player.NoFall;
import org.returnclient.module.impl.visual.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        modules.add(new ClickGui());
        modules.add(new Overlay());
        modules.add(new Animations());
        modules.add(new NoScoreboard());
        modules.add(new Jesus());
        modules.add(new Radar());
        modules.add(new OtherSettings());
        modules.add(new NoCameraClip());
        modules.add(new NoHurtCam());
        modules.add(new Glint());
        modules.add(new NoVisualFire());
        modules.add(new NoVisualTitle());
        modules.add(new WorldTime());
        modules.add(new NetInfo());
        modules.add(new NoBlindness());
        modules.add(new Brightness());
        modules.add(new ChestStealer());
        modules.add(new Velocity());
        modules.add(new NoRender());
        modules.add(new Tags2D());
        modules.add(new ChestESP());
        modules.add(new FlagDetector());
        modules.add(new TargetHUD());
        modules.add(new ESP2D());
        modules.add(new AntiVoid());
        modules.add(new InventoryWalk());
        modules.add(new AutoPot());
        modules.add(new Scaffold());
        modules.add(new AntiBot());
        modules.add(new Sprint());
        modules.add(new Criticals());
        modules.add(new Chams());
        modules.add(new Bypass());
        modules.add(new LongJump());
        modules.add(new ArmorOverlay());
        modules.add(new TargetESP());
        modules.add(new Phase());
        modules.add(new Speed());
        modules.add(new PotionHUD());
        modules.add(new Blink());
        modules.add(new KillAura());
        modules.add(new HackerDetector());
        modules.add(new NoSlowdown());
        modules.add(new NoFall());
        modules.add(new ClickTP());
        modules.add(new Spammer());
        modules.add(new Notifications());
    }

    public static Module getModule(String name) {
        for(Module module : Return.getInstance().moduleManager.modules) {
            if(module.getName().equalsIgnoreCase(name) || module.getName().equals(name)) {
                return module;
            }
        }
        return null;
    }
}

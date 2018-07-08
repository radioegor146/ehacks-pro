/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.modulesystem.handler;

import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.modulesystem.classes.AimAssist;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AntiFire;
import ehacks.mod.modulesystem.classes.AntiKnockBack;
import ehacks.mod.modulesystem.classes.AntiPotion;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.AutoTool;
import ehacks.mod.modulesystem.classes.BlockDestroy;
import ehacks.mod.modulesystem.classes.BlockOverlay;
import ehacks.mod.modulesystem.classes.BlockSmash;
import ehacks.mod.modulesystem.classes.Breadcrumb;
import ehacks.mod.modulesystem.classes.ChestFinder;
import ehacks.mod.modulesystem.classes.ChestStealer;
import ehacks.mod.modulesystem.classes.CreativeFly;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.modulesystem.classes.DynamicFly;
import ehacks.mod.modulesystem.classes.ExtendedDestroyer;
import ehacks.mod.modulesystem.classes.ExtendedNuker;
import ehacks.mod.modulesystem.classes.FastBow;
import ehacks.mod.modulesystem.classes.FastClick;
import ehacks.mod.modulesystem.classes.FastEat;
import ehacks.mod.modulesystem.classes.FastPlace;
import ehacks.mod.modulesystem.classes.Forcefield;
import ehacks.mod.modulesystem.classes.FreeCam;
import ehacks.mod.modulesystem.classes.Fullbright;
import ehacks.mod.modulesystem.classes.Gui;
import ehacks.mod.modulesystem.classes.GuiXRaySettings;
import ehacks.mod.modulesystem.classes.HighJump;
import ehacks.mod.modulesystem.classes.ItemCreator;
import ehacks.mod.modulesystem.classes.KillAura;
import ehacks.mod.modulesystem.classes.LimitedAura;
import ehacks.mod.modulesystem.classes.MobAura;
import ehacks.mod.modulesystem.classes.MobESP;
import ehacks.mod.modulesystem.classes.NCPFly;
import ehacks.mod.modulesystem.classes.NCPSpeed;
import ehacks.mod.modulesystem.classes.NCPStep;
import ehacks.mod.modulesystem.classes.NameProtect;
import ehacks.mod.modulesystem.classes.NoFall;
import ehacks.mod.modulesystem.classes.NoWeather;
import ehacks.mod.modulesystem.classes.NoWeb;
import ehacks.mod.modulesystem.classes.Nuker;
import ehacks.mod.modulesystem.classes.PlayerESP;
import ehacks.mod.modulesystem.classes.PrivateNuker;
import ehacks.mod.modulesystem.classes.Projectiles;
import ehacks.mod.modulesystem.classes.ProphuntAura;
import ehacks.mod.modulesystem.classes.ProphuntESP;
import ehacks.mod.modulesystem.classes.Regen;
import ehacks.mod.modulesystem.classes.SeeHealth;
import ehacks.mod.modulesystem.classes.Speed;
import ehacks.mod.modulesystem.classes.Sprint;
import ehacks.mod.modulesystem.classes.Step;
import ehacks.mod.modulesystem.classes.Tracers;
import ehacks.mod.modulesystem.classes.TriggerBot;
import ehacks.mod.modulesystem.classes.WaterFall;
import ehacks.mod.modulesystem.classes.WaterWalk;
import ehacks.mod.modulesystem.classes.XRay;
import ehacks.mod.modulesystem.classes.MetaHackAdd;
import ehacks.mod.modulesystem.classes.MetaHackSub;
import ehacks.mod.modulesystem.classes.NoLimitAura;
import ehacks.mod.modulesystem.classes.NoLimitDamage;
import ehacks.mod.modulesystem.classes.NoLimitPlayers;
import ehacks.mod.modulesystem.classes.CarpenterOpener;
import ehacks.mod.modulesystem.classes.GalaxyTeleport;
import ehacks.mod.modulesystem.classes.IC2SignEdit;
import ehacks.mod.modulesystem.classes.NoLimitFire;
import ehacks.mod.modulesystem.classes.NoLimitSpin;
import ehacks.mod.modulesystem.classes.SpaceFire;
import ehacks.mod.modulesystem.classes.SpaceTransport;

public class ModuleManagement {
    public static volatile ModuleManagement INSTANCE = new ModuleManagement();

    public ModuleManagement() {
        this.initModules();
    }

    private void add(Mod mod) {
        APICEMod.INSTANCE.enable(mod);
    }

    public void initModules() {
        this.add(new MetaHackAdd());
        this.add(new MetaHackSub());
        this.add(new PrivateNuker());
        this.add(new BlockDestroy());
        this.add(new ExtendedDestroyer());
        this.add(new ExtendedNuker());
        this.add(new HighJump());
        this.add(new CreativeFly());
        this.add(new NoLimitDamage());
        this.add(new NoLimitAura());
        this.add(new NoLimitPlayers());
        this.add(new LimitedAura());
        this.add(new ItemCreator());
        this.add(new CarpenterOpener());
        this.add(new SpaceFire());
        this.add(new NoLimitFire());
        this.add(new NoLimitSpin());
        this.add(new GalaxyTeleport());
        this.add(new IC2SignEdit());
        //this.add(new SpaceTransport());
        this.add(new Step());
        this.add(new Speed());
        this.add(new NoWeb());
        this.add(new Regen());
        this.add(new Nuker());
        this.add(new Sprint());
        this.add(new NoFall());
        this.add(new FreeCam());
        this.add(new FastEat());
        this.add(new AntiFire());
        this.add(new AutoTool());
        this.add(new FastPlace());
        this.add(new AntiPotion());
        this.add(new DynamicFly());
        this.add(new ChestStealer());
        this.add(new AntiKnockBack());
        this.add(new XRay());
        this.add(new MobESP());
        this.add(new Tracers());
        this.add(new PlayerESP());
        this.add(new NoWeather());
        this.add(new BlockSmash());
        this.add(new Fullbright());
        this.add(new Breadcrumb());
        this.add(new NameProtect());
        this.add(new Projectiles());
        this.add(new ChestFinder());
        this.add(new BlockOverlay());
        this.add(new AimBot());
        this.add(new FastBow());
        this.add(new MobAura());
        this.add(new KillAura());
        this.add(new AimAssist());
        this.add(new Criticals());
        this.add(new FastClick());
        this.add(new AutoBlock());
        this.add(new SeeHealth());
        this.add(new TriggerBot());
        this.add(new Forcefield());
        this.add(new ProphuntESP());
        this.add(new ProphuntAura());
        this.add(new NCPFly());
        this.add(new NCPStep());
        this.add(new NCPSpeed());
        this.add(new WaterFall());
        this.add(new WaterWalk());
        this.add(new Gui());
        this.add(new GuiXRaySettings());
    }

    public static ModuleManagement instance() {
        return INSTANCE;
    }
}


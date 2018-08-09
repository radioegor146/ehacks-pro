package ehacks.mod.modulesystem.handler;

import ehacks.api.module.ModController;
import ehacks.api.module.Mod;
import ehacks.mod.modulesystem.classes.*;
import ehacks.mod.commands.*;
import net.minecraftforge.client.ClientCommandHandler;

public class ModuleManagement {
    public static volatile ModuleManagement INSTANCE = new ModuleManagement();

    public ModuleManagement() {
        this.initModules();
    }

    private void add(Mod mod) {
        ModController.INSTANCE.enable(mod);
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
        this.add(new RocketChaos());
        this.add(new NoLimitRocket());
        this.add(new ContainerClear());
        this.add(new ChestMagic());
        this.add(new MegaExploit());
        this.add(new JesusGift());
        this.add(new NEISelect());
        this.add(new VerticalGui());
        this.add(new VisibleHack());
        this.add(new DragonsFuck());
        this.add(new MagicGive());
        this.add(new NoLimitSpell());
        this.add(new NoLimitBuffs());
        this.add(new NoLimitClear());
        
        this.add(new PacketFlooder());
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
        this.add(new Tracers());
        this.add(new MobESP());
        this.add(new PlayerESP());
        this.add(new ItemESP());
        this.add(new EntityESP());
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
        
        ModController.INSTANCE.sort();
        
        this.add(new Gui());
        this.add(new GuiXRaySettings());
        
        ClientCommandHandler.instance.registerCommand(new ItemSelectCommand());
    }

    public static ModuleManagement instance() {
        return INSTANCE;
    }
}


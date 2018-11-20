package ehacks.mod.modulesystem.handler;

import ehacks.mod.api.Module;
import ehacks.mod.api.ModuleController;
import ehacks.mod.modulesystem.classes.keybinds.*;
import ehacks.mod.modulesystem.classes.mods.ae2.CellViewer;
import ehacks.mod.modulesystem.classes.mods.arsmagica2.*;
import ehacks.mod.modulesystem.classes.mods.bibliocraft.JesusGift;
import ehacks.mod.modulesystem.classes.mods.bibliocraft.OnlineCraft;
import ehacks.mod.modulesystem.classes.mods.bibliocraft.TableTop;
import ehacks.mod.modulesystem.classes.mods.buildcraft.PipeGive;
import ehacks.mod.modulesystem.classes.mods.carpentersblocks.CarpenterOpener;
import ehacks.mod.modulesystem.classes.mods.crayfish.ExtendedDestroyer;
import ehacks.mod.modulesystem.classes.mods.crayfish.ExtendedNuker;
import ehacks.mod.modulesystem.classes.mods.crayfish.ItemCreator;
import ehacks.mod.modulesystem.classes.mods.dragonsradio.DragonsFuck;
import ehacks.mod.modulesystem.classes.mods.dragonsradio.MusicalCrash;
import ehacks.mod.modulesystem.classes.mods.enderio.*;
import ehacks.mod.modulesystem.classes.mods.forge.PacketFlooder;
import ehacks.mod.modulesystem.classes.mods.galacticraft.GalaxyTeleport;
import ehacks.mod.modulesystem.classes.mods.galacticraft.NoLimitFire;
import ehacks.mod.modulesystem.classes.mods.galacticraft.NoLimitSpin;
import ehacks.mod.modulesystem.classes.mods.galacticraft.SpaceFire;
import ehacks.mod.modulesystem.classes.mods.ironchest.IronChestFinder;
import ehacks.mod.modulesystem.classes.mods.mfr.*;
import ehacks.mod.modulesystem.classes.mods.nei.NEISelect;
import ehacks.mod.modulesystem.classes.mods.nuclearcontrol.IC2SignEdit;
import ehacks.mod.modulesystem.classes.mods.openmodularturrets.BlockDestroy;
import ehacks.mod.modulesystem.classes.mods.openmodularturrets.PrivateNuker;
import ehacks.mod.modulesystem.classes.mods.projectred.RedHack;
import ehacks.mod.modulesystem.classes.mods.rftools.SelfRf;
import ehacks.mod.modulesystem.classes.mods.taintedmagic.*;
import ehacks.mod.modulesystem.classes.mods.thaumcraft.MagicGod;
import ehacks.mod.modulesystem.classes.mods.thaumcraft.ResearchGod;
import ehacks.mod.modulesystem.classes.mods.thaumichorizons.NowYouSeeMe;
import ehacks.mod.modulesystem.classes.mods.thaumichorizons.VerticalGui;
import ehacks.mod.modulesystem.classes.mods.thermalexpansion.HotGive;
import ehacks.mod.modulesystem.classes.mods.tinkers.CloudStorage;
import ehacks.mod.modulesystem.classes.mods.tinkers.MegaExploit;
import ehacks.mod.modulesystem.classes.mods.ztones.MetaHackAdd;
import ehacks.mod.modulesystem.classes.mods.ztones.MetaHackSub;
import ehacks.mod.modulesystem.classes.vanilla.*;

public class ModuleManagement {

    public static volatile ModuleManagement INSTANCE = new ModuleManagement();

    public ModuleManagement() {
        this.initModules();
    }

    private void add(Module mod) {
        ModuleController.INSTANCE.enable(mod);
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
        this.add(new ItemCreator());
        this.add(new RocketChaos());
        this.add(new NoLimitRocket());
        this.add(new ContainerClear());
        this.add(new ChestMagic());
        this.add(new MegaExploit());
        this.add(new NEISelect());
        this.add(new NoLimitClear());
        this.add(new CellViewer());
        this.add(new RedHack());

        this.add(new FakeDestroy());
        this.add(new Blink());
        this.add(new CreativeGive());
        //this.add(new ShowContainer());

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
        //this.add(new EntityESP());
        this.add(new NoWeather());
        this.add(new BlockSmash());
        this.add(new Fullbright());
        this.add(new Breadcrumb());
        //this.add(new NameProtect());
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
        this.add(new TriggerBot());
        this.add(new Forcefield());
        this.add(new ProphuntESP());
        this.add(new ProphuntAura());
        this.add(new NCPFly());
        this.add(new NCPStep());
        this.add(new NCPSpeed());
        this.add(new WaterFall());
        this.add(new WaterWalk());
        this.add(new DamagePopOffs());
        this.add(new GuiXRaySettings());
        this.add(new DiffRegistry());
        this.add(new ShowArmor());
        this.add(new FriendClick());
        this.add(new IC2SignEdit());
        this.add(new ResearchGod());
        this.add(new MagicGod());
        this.add(new VisualCreative());
        this.add(new NowYouSeeMe());
        this.add(new CarpenterOpener());
        this.add(new GalaxyTeleport());
        this.add(new HotGive());
        this.add(new JesusGift());
        this.add(new NoLimitFire());
        this.add(new NoLimitSpin());
        this.add(new SpaceFire());
        this.add(new VerticalGui());
        this.add(new MagicGive());
        this.add(new NoLimitBuffs());
        this.add(new NoLimitSpell());
        this.add(new SkillResearch());
        this.add(new DragonsFuck());
        this.add(new MusicalCrash());
        this.add(new EndPort());
        this.add(new Magnendo());
        this.add(new SelfEnd());
        this.add(new NBTEdit());
        this.add(new DebugMe());
        this.add(new PacketLogger());
        this.add(new PacketFlooder());

        this.add(new GiveKeybind());
        this.add(new SelectPlayerKeybind());
        this.add(new NEISelectKeybind());
        this.add(new ShowGroupsKeybind());
        this.add(new HideCheatKeybind());
        this.add(new OpenNBTEditKeybind());
        this.add(new OpenAE2ViewerKeybind());
        this.add(new OpenConsoleKeybind());
        this.add(new MagnetKeybind());
        this.add(new SingleDebugMeKeybind());
        this.add(new TickingDebugMeKeybind());

        this.add(new LimitedAura());
        this.add(new NoLimitAura());
        this.add(new NoLimitDamage());
        this.add(new NoLimitPlayers());
        this.add(new PipeGive());

        this.add(new IronChestFinder());

        this.add(new SelfRf());
        this.add(new CloudStorage());

        this.add(new TableTop());
        this.add(new OnlineCraft());

        ModuleController.INSTANCE.sort();

        this.add(new EHacksGui());
    }

    public static ModuleManagement instance() {
        return INSTANCE;
    }
}

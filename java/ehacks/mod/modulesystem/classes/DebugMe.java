/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 */
package ehacks.mod.modulesystem.classes;

import bsh.EvalError;
import bsh.Interpreter;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.Mod;
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import static ehacks.mod.modulesystem.classes.BlockDestroy.isActive;
import ehacks.mod.util.OpenFileFilter;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class DebugMe
extends Mod {
    public static File scriptFile;
    public static AtomicBoolean dialogOpened = new AtomicBoolean();
    private boolean set = false;
    
    private Interpreter interpreter;
    
    public DebugMe() {
        super(ModuleCategories.EHACKS);
        interpreter = new Interpreter();
    }

    @Override
    public String getName() {
        return "DebugMe";
    }
    
    @Override
    public boolean canOnOnStart() {
        return false;
    }
    
    private boolean prevState = false;
    
    private boolean prevState5 = false;
    
    @Override
    public void onTicks() {
        if (dialogOpened.get())
            return;
        if (!dialogOpened.get() && scriptFile == null)
        {
            this.off();
            YouAlwaysWinClickGui.log("[DebugMe] Script load canceled");
            return;
        }
        else if (!set)
        {
            YouAlwaysWinClickGui.log("[DebugMe] Script loaded: " + scriptFile.getPath());
            set = true;
        }
        boolean newState5 = Keyboard.isKeyDown(Keybinds.tickingDebug);
        if (!prevState5 && newState5)
            YouAlwaysWinClickGui.log("[DebugMe] Script tick started");
        if (!newState5 && prevState5)
            YouAlwaysWinClickGui.log("[DebugMe] Script tick stopped");
        prevState5 = newState5;
        if (newState5)
        {
            try
            {
                runScript();
            }
            catch (Exception e)
            {
                
            }
            return;
        }
        boolean newState = Keyboard.isKeyDown(Keybinds.oneDebug);
        if (!prevState && newState)
        {
            prevState = newState;
            try
            {
                YouAlwaysWinClickGui.log("[DebugMe] Script executed with result: \"" + runScript() + "\"");
            }
            catch (Exception e)
            {
                YouAlwaysWinClickGui.log("[DebugMe] Exception on eval: \"" + e.getMessage() + "\"");
            }
            return;
        }
        prevState = newState;
    }
    
    @Override
    public void onEnableMod() {
        dialogOpened.set(true);
        Thread thread = new FileOpenThread();
        thread.start();
    }
    
    @Override
    public void onDisableMod() {
        if (dialogOpened.get())
            this.enabled = true;
        else
            set = false;
    }
    
    public class FileOpenThread extends Thread {
        @Override
        public void run() {
            Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
            JFileChooser fileopen = new JFileChooser();
            fileopen.setFileFilter(new OpenFileFilter("bsh", "BSH files (*.bsh)"));
            fileopen.setAcceptAllFileFilterUsed(false);
            fileopen.setMultiSelectionEnabled(false);
            fileopen.setPreferredSize(new Dimension(scr.width - 350, scr.height - 350));
            if (fileopen.showOpenDialog(null) == 0) 
                DebugMe.scriptFile = fileopen.getSelectedFile();
            else
                DebugMe.scriptFile = null;
            dialogOpened.set(false);
        }
    }
    
    @Override
    public String getModName() {
        return "Forge";
    }
    
    private String runScript() throws FileNotFoundException, EvalError {
        Scanner sc = new Scanner(scriptFile);
        String data = "";
        while (sc.hasNextLine()) {
            data = data + sc.nextLine() + "\n";
        }
        return String.valueOf(interpreter.eval(data));
    }
}


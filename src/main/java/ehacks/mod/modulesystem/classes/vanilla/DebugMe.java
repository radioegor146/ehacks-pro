package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.bsh.EvalError;
import ehacks.bsh.Interpreter;
import ehacks.mod.api.Module;
import ehacks.mod.modulesystem.classes.keybinds.SingleDebugMeKeybind;
import ehacks.mod.modulesystem.classes.keybinds.TickingDebugMeKeybind;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.util.OpenFileFilter;
import ehacks.mod.wrapper.ModuleCategory;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFileChooser;
import org.lwjgl.input.Keyboard;

public class DebugMe
        extends Module {

    public static File scriptFile;
    public static AtomicBoolean dialogOpened = new AtomicBoolean();
    private boolean set = false;

    private final Interpreter interpreter;

    public DebugMe() {
        super(ModuleCategory.EHACKS);
        interpreter = new Interpreter();
    }

    @Override
    public String getName() {
        return "DebugMe";
    }

    @Override
    public String getDescription() {
        return "Opens dialog to select file for interactive BeanShell (c) N1nt3nd0\nUsage: \n  Numpad5 - on/off ticking performing\n  Numpad6 - single executing";
    }

    private boolean prevState = false;

    private boolean prevState5 = false;

    @Override
    public void onTicks() {
        if (dialogOpened.get()) {
            return;
        }
        if (!dialogOpened.get() && scriptFile == null) {
            this.off();
            InteropUtils.log("Script load canceled", this);
            return;
        } else if (!set) {
            InteropUtils.log("Script loaded: " + scriptFile.getPath(), this);
            set = true;
        }
        boolean newState5 = Keyboard.isKeyDown(TickingDebugMeKeybind.getKey());
        if (!prevState5 && newState5) {
            InteropUtils.log("Script tick started", this);
        }
        if (!newState5 && prevState5) {
            InteropUtils.log("Script tick stopped", this);
        }
        prevState5 = newState5;
        if (newState5) {
            try {
                runScript();
            } catch (Exception e) {

            }
            return;
        }
        boolean newState = Keyboard.isKeyDown(SingleDebugMeKeybind.getKey());
        if (!prevState && newState) {
            prevState = newState;
            try {
                InteropUtils.log("Script executed with result: \"" + runScript() + "\"", this);
            } catch (Exception e) {
                InteropUtils.log("Exception on eval: \"" + e.getMessage() + "\"", this);
            }
            return;
        }
        prevState = newState;
    }

    @Override
    public void onModuleEnabled() {
        dialogOpened.set(true);
        Thread thread = new FileOpenThread();
        thread.start();
    }

    @Override
    public void onModuleDisabled() {
        if (dialogOpened.get()) {
            this.enabled = true;
        } else {
            set = false;
        }
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
            if (fileopen.showOpenDialog(null) == 0) {
                DebugMe.scriptFile = fileopen.getSelectedFile();
            } else {
                DebugMe.scriptFile = null;
            }
            dialogOpened.set(false);
        }
    }

    @Override
    public String getModName() {
        return "Minecraft";
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

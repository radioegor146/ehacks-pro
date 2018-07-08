/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.external.config.agce.files.AGCEIntegerList;
import ehacks.mod.modulesystem.classes.XRay;
import ehacks.mod.util.Utils;
import ehacks.mod.wrapper.Wrapper;
import java.util.concurrent.CopyOnWriteArrayList;

public class ACommandXray
extends Command {
    public ACommandXray() {
        super("cexray");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        /*if (subcommands[0].equalsIgnoreCase("add")) {
            XRay.xrayList2.add(subcommands[1]);
            XRay.addDefaultList();
            Wrapper.INSTANCE.addChatMessage("Added ID from X-Ray list: " + subcommands[1]);
            CEUtility.removeDupes(XRay.xrayList2);
            AGCEIntegerList.INSTANCE.modify("CEXrayBlocks.txt", XRay.xrayList2);
            CEUtility.removeDupes(XRay.xrayList2);
            //APICEMod.INSTANCE.call(XRay.class).reset();
        } else if (subcommands[0].equalsIgnoreCase("delete")) {
            XRay.xrayList2.remove(subcommands[1]);
            XRay.removeDefaultList();
            XRay.addDefaultList();
            Wrapper.INSTANCE.addChatMessage("Added ID from X-Ray list: " + subcommands[1]);
            CEUtility.removeDupes(XRay.xrayList2);
            AGCEIntegerList.INSTANCE.modify("CEXrayBlocks.txt", XRay.xrayList2);
            CEUtility.removeDupes(XRay.xrayList2);
            //APICEMod.INSTANCE.call(XRay.class).reset();
        }*/
    }

    @Override
    public String getDescription() {
        return "Adds/removes X-Ray blocks";
    }

    @Override
    public String getSyntax() {
        return this.getCommand() + " <add/delete> <block id>";
    }
}


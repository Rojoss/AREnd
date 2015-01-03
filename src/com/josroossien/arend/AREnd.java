package com.josroossien.arend;

import com.clashwars.cwcore.utils.CWUtil;
import com.josroossien.arend.runnables.DragonRunnable;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class AREnd extends JavaPlugin {

    private static AREnd instance;

    private final Logger log = Logger.getLogger("Minecraft");


    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        deleteEnd();

        log("disabled");
    }

    @Override
    public void onEnable() {
        instance = this;

        new DragonRunnable(this).runTaskTimer(this, 0, 20);

        log("loaded successfully");
    }


    private void deleteEnd() {
        World end = getServer().getWorld("world_the_end");
        if (end == null) {
            return;
        }
        //Make sure there are no players in the end.
        if (end.getPlayers().size() > 0) {
            log("Failed at resetting the end!");
            return;
        }

        //Unload the world.
        getServer().unloadWorld(end, false);

        //Delete world.
        CWUtil.deleteDirectory(end.getWorldFolder());
        log("The end has been deleted!");
    }


    public static AREnd inst() {
        return instance;
    }

    public void log(Object msg) {
        log.info("[AREnd " + getDescription().getVersion() + "] " + msg.toString());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("end") || label.equalsIgnoreCase("arend")) {
            sender.sendMessage(CWUtil.integrateColor("&8===== &4&lArchaicRealms end plugin &8====="));
            sender.sendMessage(CWUtil.integrateColor("&6Author&8: &5worstboy32(jos)"));
            sender.sendMessage(CWUtil.integrateColor("&7End reset on server restart and dragon shoots fireballs!"));
            sender.sendMessage(CWUtil.integrateColor("&6For more information check out the wiki!"));
            sender.sendMessage(CWUtil.integrateColor("&9http://archaicrealms.com/towny#More"));
            return true;
        }
        return false;
    }

}

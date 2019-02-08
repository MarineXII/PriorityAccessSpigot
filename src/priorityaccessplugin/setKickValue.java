/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package priorityaccessplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

/**
 *
 * @author Longji Kang
 */

public class setKickValue implements CommandExecutor{
    
    private static AccessMain plInstance;
    
    private static final String PREF_TEXT = "[Priority Access]: ";
    
    public setKickValue(AccessMain pl) {
        plInstance = pl;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]) {
        if ((commandLabel.equalsIgnoreCase("priorityaccess") || commandLabel.equalsIgnoreCase("pacss")) && args[0].equalsIgnoreCase("setkickval")) {
            // /priorityaccess setKickval <player> <1/0>
            if (sender.hasPermission("priority_access.canModiy")) {
                Configuration cfg = plInstance.getConfig();
                Player target;
                if ((target = Bukkit.getPlayer(args[1])) != null) {
                    playerFound(target, cfg, Integer.parseInt(args[2]), sender);
                }
            } else {
                sender.sendMessage(PREF_TEXT + "Error, you do not have permission 'priority_acces.canModify'!");
            }
        }
        return false;
    }
    
    public void playerFound(Player id, Configuration cf, int option, CommandSender snd) {
        if (cf.getInt("players." + id + ".kickValue") != option) {
            cf.set("players." + id + ".kickValue", option);
            plInstance.saveConfig();
            
            System.out.println(PREF_TEXT + snd.getName() + " changed " + id.getName() + "'s kickValue to " + option);
        }
    }
    
}

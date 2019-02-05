package priorityaccessplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.*;

/**
 *
 * @author user1
 */
public class AccessMain extends JavaPlugin{
    
    @Override
    public void onEnable() {
        System.out.println("Priority Access is enabled!");
        getServer().getPluginManager().registerEvents(new playerJoin(), this);
    }
    
    @Override
    public void onDisable() {
        System.out.println("Priority Access is disabled!");
    }
    

}

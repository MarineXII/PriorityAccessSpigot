package priorityaccessplugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author user1
 */
public class AccessMain extends JavaPlugin{
    
    private static AccessMain plInstance;
    
    @Override
    public void onEnable() {
        plInstance = this;
        
        //Create default config.yml
        plInstance.saveDefaultConfig();
        
        System.out.println("Priority Access is enabled!");
        getServer().getPluginManager().registerEvents(new playerJoin(plInstance), this);
        
        //Set executors for commands
        getCommand("priorityaccess").setExecutor(new setKickValue(this));
    }
    
    @Override
    public void onDisable() {
        System.out.println("Priority Access is disabled!");
    }
}

//TODO
//Add commands to change whether a player can be kicked or can cause kick
package priorityaccessplugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author user1
 */
public class playerJoin implements Listener {
    
    private static AccessMain plInstance;
    
    private static final String PREF_TEXT = "[Priority Access]: ";
    private int MAX_PLAYERS;
    
    public playerJoin(AccessMain am) {
        plInstance = am;
        MAX_PLAYERS = calculateMax(plInstance.getConfig());
    }
    
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent pjEvent) {
        Configuration cfg = plInstance.getConfig();
        registerPlayer(pjEvent.getPlayer(), cfg);
        
        if (isFull()) {
            if (cfg.getInt("player.canCauseKick." + pjEvent.getPlayer().getUniqueId().toString()) == 1) {
                System.out.println(PREF_TEXT + "Server full! Kicking players with lower priority...");

                int size = Bukkit.getServer().getOnlinePlayers().size();
                boolean kicked = false; //flag to keep track if a player has been kicked
                String kickedName = "";
                Player[] players = new Player[size];

                for (int i = 0; i < size; i++) {
                    if (cfg.getInt("player." + players[i].getUniqueId().toString()) == 1) {
                        players[i].kickPlayer("You have been kicked due to the server being full and a priority member requesting access to the server!");
                        plInstance.getServer().broadcastMessage(PREF_TEXT + "has kicked " + players[i].getName() + " to make space for " + pjEvent.getPlayer().getName());
                        kickedName = players[i].getName();
                        kicked = true;
                        break;
                    }
                }

                if (kicked) {
                    System.out.println(PREF_TEXT + "kicked " + kickedName);
                } else {
                    pjEvent.getPlayer().kickPlayer("There is unfortunately no available players to kick");
                    System.out.println(PREF_TEXT + "was unable " + pjEvent.getPlayer().getName());
                }
            } else {
                pjEvent.getPlayer().kickPlayer("You do not have priority on the server!");
            }
            
        }
        
    }
    
    private boolean isFull() {
        int CurrentPlayers = Bukkit.getOnlinePlayers().size();
        
        boolean full = false;
        
        if (CurrentPlayers >= MAX_PLAYERS) {
            full = true;
        }
        
        return full;
    }
    
    private int calculateMax(Configuration cfg) {
        int buffer = cfg.getInt("buffer-size");
        int max = Bukkit.getMaxPlayers() - buffer;
        
        return max;
    }
    
    //Register Player works correctly
    private void registerPlayer(Player p, Configuration cfg) {
        if (!cfg.contains("player." + p.getUniqueId().toString())) {
            cfg.set("player." + p.getUniqueId().toString() + ".kickValue", cfg.getInt("def_kickValue"));
            cfg.set("player." + p.getUniqueId().toString() + ".canCauseKick", cfg.getInt("def_canCauseKick"));
            plInstance.saveConfig();
            System.out.println(PREF_TEXT + p.getName() + " was not found and has been added to the config.yml file!");
        } else {
            System.out.println(PREF_TEXT + p.getName() + " was found in the config.yml");
        }
    }
}

package priorityaccessplugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author user1
 */
public class playerJoin implements Listener {
    
    private static final String PREF_TEXT = "[Priority Access]: ";
    
    
    public playerJoin() {
        System.out.println(PREF_TEXT + "Player join called!");
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pjEvent) {
        int CurrentPlayerCount =  Bukkit.getOnlinePlayers().size();
        
    }
    
    private boolean inFile(PlayerJoinEvent pje) {
        boolean playerFound = false;
        try {
            String line;
            BufferedReader dataF = new BufferedReader(new FileReader("authPlayer.txt"));
            
            while ((line = dataF.readLine()) != null) {
                if (line == pje.getPlayer().getName()) {
                    playerFound = true;
                    break;
                }
            }
        } catch (FileNotFoundException FNe) {
            File auth = new File("authPlayer.txt");
            System.out.println(PREF_TEXT + "authPlayer.txt not found!");
            System.out.println(PREF_TEXT + "creating authPlayer.txt!");
            try {
                auth.createNewFile();
            } catch (IOException IOe){
                IOe.printStackTrace();
            }
        } catch (IOException rdl) {
            rdl.printStackTrace();
        }
        
        return playerFound;
    }
    
}

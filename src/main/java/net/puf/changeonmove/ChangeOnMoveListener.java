package net.puf.changeonmove;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.block.Block;

public class ChangeOnMoveListener implements Listener {

    private final ChangeOnMove plugin;


    public ChangeOnMoveListener(ChangeOnMove plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnMove(PlayerMoveEvent event) {

        if(!plugin.isChangeOnMoveEnabled()) return;

        Player player = event.getPlayer();

        if(player.getGameMode() == GameMode.SPECTATOR) return;

        Block blockBelow = player.getLocation().subtract(0,1,0).getBlock();

        if(blockBelow.getType() != Material.AIR) {
            Material blockType = plugin.getBlockTypeToPlace();
            blockBelow.setType(blockType);
        }

    }
}

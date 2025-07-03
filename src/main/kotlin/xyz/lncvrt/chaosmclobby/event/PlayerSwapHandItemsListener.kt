package xyz.lncvrt.chaosmclobby.event

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class PlayerSwapHandItemsListener : Listener {
    @EventHandler
    fun onPlayerSwapHandItemsEvent(event: PlayerSwapHandItemsEvent) {
        if (event.getPlayer().gameMode != GameMode.CREATIVE) event.isCancelled = true
    }
}
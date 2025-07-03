package xyz.lncvrt.chaosmclobby.event

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryClickListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        val entity = event.whoClicked
        if (entity is Player) {
            if (entity.gameMode != GameMode.CREATIVE) event.isCancelled = true
        }
    }
}
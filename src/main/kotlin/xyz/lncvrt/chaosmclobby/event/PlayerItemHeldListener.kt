package xyz.lncvrt.chaosmclobby.event

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack

class PlayerItemHeldListener : Listener {
    @EventHandler
    fun onPlayerItemHeldEvent(event: PlayerItemHeldEvent) {
        val player = event.getPlayer()
        if (player.gameMode == GameMode.ADVENTURE) {
            if (event.newSlot == 7) {
                player.inventory.chestplate = ItemStack(Material.ELYTRA)
            } else {
                player.inventory.chestplate = null
            }
        }
    }
}
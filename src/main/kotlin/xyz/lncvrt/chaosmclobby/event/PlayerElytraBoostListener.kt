package xyz.lncvrt.chaosmclobby.event

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.sayandev.stickynote.bukkit.plugin
import xyz.lncvrt.chaosmcapi.utils.MessageFormat
import xyz.lncvrt.chaosmclobby.ChaosMCLobbyPlugin

class PlayerElytraBoostListener(plugin: ChaosMCLobbyPlugin) : Listener {
    @EventHandler
    fun onPlayerElytraBoostEvent(event: PlayerElytraBoostEvent) {
        plugin.server.scheduler.runTaskLater(plugin, Runnable {
            val player = event.player
            val fireworkItem = ItemStack(Material.FIREWORK_ROCKET)
            val fireworkMeta = fireworkItem.itemMeta
            fireworkMeta.displayName(
                MiniMessage.miniMessage().deserialize(
                    MessageFormat.setPrimaryColor(
                        "<italic:false>Elytra ${
                            MessageFormat.setSecondaryColor("(Fly around the lobby!)")
                        }</italic>"
                    )
                )
            )
            fireworkItem.setItemMeta(fireworkMeta)
            player.inventory.setItem(7, fireworkItem)
            player.setCooldown(fireworkItem.type, 20)
        }, 1)
    }
}
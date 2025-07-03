package xyz.lncvrt.chaosmclobby.event

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.inventory.ItemStack
import xyz.lncvrt.chaosmcapi.utils.MessageFormat

class ProjectileLaunchListener() : Listener {
    @EventHandler
    fun onProjectileLaunchEvent(event: ProjectileLaunchEvent) {
        val firework = event.getEntity()
        val player = firework.shooter
        if (firework is Firework) {
            if (player is Player) {
                val fireworkItem = ItemStack(Material.FIREWORK_ROCKET)
                val fireworkMeta = fireworkItem.itemMeta
                fireworkMeta.displayName(MiniMessage.miniMessage().deserialize(MessageFormat.setPrimaryColor("<italic:false>Elytra ${MessageFormat.setSecondaryColor("(Fly around the lobby!)")}</italic>")))
                fireworkItem.setItemMeta(fireworkMeta)
                player.inventory.setItem(7, fireworkItem)
                player.setCooldown(fireworkItem.type, 20)
            }
        }
    }
}
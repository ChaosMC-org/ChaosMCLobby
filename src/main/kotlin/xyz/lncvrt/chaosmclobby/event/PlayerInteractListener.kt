package xyz.lncvrt.chaosmclobby.event

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.sayandev.stickynote.bukkit.extension.sendComponent
import xyz.lncvrt.chaosmcapi.utils.CommandHelper
import xyz.lncvrt.chaosmcapi.utils.MessageFormat
import xyz.lncvrt.chaosmclobby.ChaosMCLobbyPlugin

class PlayerInteractListener(val plugin: ChaosMCLobbyPlugin) : Listener {
    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        if (event.action !== Action.RIGHT_CLICK_AIR && event.action !== Action.RIGHT_CLICK_BLOCK) {
            return
        }

        val player = event.getPlayer()
        val item = player.inventory.itemInMainHand
        val name = PlainTextComponentSerializer.plainText().serialize(item.displayName().asComponent())

        val playerCooldowns = plugin.cooldowns.computeIfAbsent(player) { HashMap() }

        val currentTime = System.currentTimeMillis()

        if (playerCooldowns.containsKey(name) && currentTime - playerCooldowns[name]!! < 1000) return

        when (name) {
            "[Our Socials (Right Click)]" -> player.sendComponent(MessageFormat.setPrefix(MessageFormat.setPrimaryColor("Click ${MessageFormat.setSecondaryColor("<u><click:open_url:'https://chaos.lncvrt.xyz/discord'>HERE</click></u>")} to join our Discord Server, or click ${MessageFormat.setSecondaryColor("<u><click:open_url:'https://chaos.lncvrt.xyz'>HERE</click></u>")} to view our Website!")))
            "[Server Selector (Right Click)]" -> CommandHelper().executeAsConsole("dmenu open serverselector ${player.name}")
        }

        playerCooldowns.put(name, currentTime)
    }
}
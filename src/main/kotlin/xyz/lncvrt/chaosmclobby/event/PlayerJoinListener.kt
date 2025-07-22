package xyz.lncvrt.chaosmclobby.event

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.sayandev.stickynote.bukkit.extension.sendComponent
import xyz.lncvrt.chaosmcapi.utils.MessageFormat
import xyz.lncvrt.chaosmclobby.ChaosMCLobbyPlugin

class PlayerJoinListener(val plugin: ChaosMCLobbyPlugin) : Listener {
    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        val player = event.getPlayer()
        plugin.resetPlayer(player, true)
        player.gameMode = GameMode.ADVENTURE

        plugin.server.scheduler.runTaskLater(plugin, Runnable {
            player.sendComponent("<dark_gray><strikethrough>+---------------------***---------------------+")
            player.sendMessage("")
            player.sendComponent("<gray>Welcome to ${MessageFormat.name()}, ${MessageFormat.setPrimaryColor("<underlined>${player.name}</underlined>")}!</gray>")
            player.sendMessage("")
            player.sendComponent("${MessageFormat.setPrimaryColor("<bold>WEBSITE</bold><gray>:</gray>")} ${MessageFormat.setSecondaryColor("<click:open_url:'https://chaos.lncvrt.xyz'>chaos.lncvrt.xyz</click>")}")
            player.sendComponent("${MessageFormat.setPrimaryColor("<bold>DISCORD</bold><gray>:</gray>")} ${MessageFormat.setSecondaryColor("<click:open_url:'https://chaos.lncvrt.xyz/discord'>chaos.lncvrt.xyz/discord</click>")}")
            player.sendMessage("")
            player.sendComponent("<dark_gray><strikethrough>+---------------------***---------------------+")
        }, 5)
    }
}
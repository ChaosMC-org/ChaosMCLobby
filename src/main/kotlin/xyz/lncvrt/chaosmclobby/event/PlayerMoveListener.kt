package xyz.lncvrt.chaosmclobby.event

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector
import org.sayandev.stickynote.bukkit.extension.sendComponent
import xyz.lncvrt.chaosmcapi.utils.MessageFormat
import xyz.lncvrt.chaosmclobby.ChaosMCLobbyPlugin

class PlayerMoveListener(val plugin: ChaosMCLobbyPlugin) : Listener {
    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.getPlayer()
        val location = player.location

        if (location.y < 0) {
            plugin.resetPlayer(player, true)
            player.sendComponent(MessageFormat.setPrefix("<rainbow>You happy with yourself?</rainbow>"))
        } else if (player.world.maxHeight < location.y) {
            location.y = player.world.maxHeight.toDouble()
            player.teleport(location)
            player.sendComponent(MessageFormat.setPrefix(MessageFormat.setPrimaryColor("You can't go that high!")))
        }
    }

    @EventHandler
    fun onPlayerMoveLaunchPad(event: PlayerMoveEvent) {
        val player = event.getPlayer()
        val world: World? = plugin.server.getWorld("world")
        if (world == null) return
        val playerBlock = world.getBlockAt(player.location)

        val playerId = player.uniqueId
        val currentTime = System.currentTimeMillis()

        if (plugin.jumpPadCooldowns.containsKey(playerId)) {
            val lastActivation: Long = plugin.jumpPadCooldowns.get(playerId)!!
            if (currentTime - lastActivation < 1000) return
        }

        if (playerBlock.type === Material.MANGROVE_PRESSURE_PLATE) {
            plugin.jumpPadCooldowns.put(playerId, currentTime)

            if (player.gameMode == GameMode.ADVENTURE && player.inventory.heldItemSlot == 7) {
                player.velocity = Vector(0, 1, 0)
                player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2.0f, 1.0f)
                player.sendComponent(MessageFormat.setPrefix("<rainbow>Hacks!?!?!?!</rainbow>"))
                val scheduler = plugin.server.scheduler
                scheduler.runTaskLater(plugin, Runnable {
                    player.velocity = Vector(0, 2, 0)
                    player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2.0f, 1.0f)
                }, 10)
                scheduler.runTaskLater(plugin, Runnable {
                    player.velocity = Vector(0, 2, 0)
                    player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2.0f, 1.0f)
                }, 20)
                scheduler.runTaskLater(plugin, Runnable {
                    player.velocity = Vector(0, 2, 0)
                    player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2.0f, 1.0f)
                }, 30)
                scheduler.runTaskLater(plugin, Runnable {
                    player.velocity = Vector(0, 5, 0)
                    player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2.0f, 1.25f)
                }, 40)
                return
            }
            player.velocity = Vector(0.0, 0.75, 1.5)
            player.playSound(player.location, Sound.ENTITY_BAT_TAKEOFF, 1.0f, 1.0f)
        }
    }

    @EventHandler
    fun onPlayerMoveDoubleJump(event: PlayerMoveEvent) {
        val player = event.getPlayer()

        if (!player.isFlying && player.gameMode == GameMode.ADVENTURE) {
            if (player.inventory.heldItemSlot == 7) {
                player.allowFlight = false
                return
            }
            player.allowFlight = true
        }
    }
}
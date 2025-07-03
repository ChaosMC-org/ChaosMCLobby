package xyz.lncvrt.chaosmclobby

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.sayandev.stickynote.loader.bukkit.StickyNoteBukkitLoader
import xyz.lncvrt.chaosmcapi.utils.MessageFormat
import xyz.lncvrt.chaosmclobby.command.DiscordCommand
import xyz.lncvrt.chaosmclobby.command.WebsiteCommand
import xyz.lncvrt.chaosmclobby.event.*
import java.util.*

class ChaosMCLobbyPlugin : JavaPlugin() {
    val jumpPadCooldowns = HashMap<UUID, Long>()
    val cooldowns = HashMap<Player, MutableMap<String, Long>>()

    override fun onEnable() {
        StickyNoteBukkitLoader(this)
        DiscordCommand
        WebsiteCommand
        registerEvents()
    }

    private fun registerEvents() {
        val pluginManager = server.pluginManager
        pluginManager.registerEvents(InventoryClickListener(), this)
        pluginManager.registerEvents(PlayerAdvancementCriterionGrantListener(), this)
        pluginManager.registerEvents(PlayerGameModeChangeListener(), this)
        pluginManager.registerEvents(PlayerInteractListener(this), this)
        pluginManager.registerEvents(PlayerItemDamageListener(), this)
        pluginManager.registerEvents(PlayerItemHeldListener(), this)
        pluginManager.registerEvents(PlayerJoinListener(this), this)
        pluginManager.registerEvents(PlayerMoveListener(this), this)
        pluginManager.registerEvents(PlayerSwapHandItemsListener(), this)
        pluginManager.registerEvents(PlayerToggleFlightListener(), this)
        pluginManager.registerEvents(ProjectileLaunchListener(), this)
    }

    internal fun resetPlayer(player: Player, teleport: Boolean, setGamemode: Boolean) {
        val miniMessage: MiniMessage = MiniMessage.miniMessage()
        val effect = PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 1, false, false)

        player.inventory.clear()
        player.inventory.heldItemSlot = 4

        val discordItem = ItemStack(Material.DIAMOND)
        val discordMeta = discordItem.itemMeta
        discordMeta.displayName(miniMessage.deserialize(MessageFormat.setPrimaryColor("<italic:false>Our Socials ${MessageFormat.setSecondaryColor("(Right Click)")}</italic>")))
        discordItem.setItemMeta(discordMeta)
        player.inventory.setItem(1, discordItem)

        val serverSelectorItem = ItemStack(Material.NETHER_STAR)
        val serverSelectorMeta = serverSelectorItem.itemMeta
        serverSelectorMeta.displayName(miniMessage.deserialize(MessageFormat.setPrimaryColor("<italic:false>Server Selector ${MessageFormat.setSecondaryColor("(Right Click)")}</italic>")))
        serverSelectorItem.setItemMeta(serverSelectorMeta)
        player.inventory.setItem(4, serverSelectorItem)

        val fireworkItem = ItemStack(Material.FIREWORK_ROCKET)
        val fireworkMeta = fireworkItem.itemMeta
        fireworkMeta.displayName(miniMessage.deserialize(MessageFormat.setPrimaryColor("<italic:false>Elytra ${MessageFormat.setSecondaryColor("(Fly around the lobby!)")}</italic>")))
        fireworkItem.setItemMeta(fireworkMeta)
        player.inventory.setItem(7, fireworkItem)

        player.health = player.getAttribute(Attribute.MAX_HEALTH)?.value ?: 20.0
        player.foodLevel = 20
        player.saturation = 20f
        player.clearActivePotionEffects()
        player.addPotionEffect(effect)
        if (setGamemode) player.gameMode = GameMode.ADVENTURE

        if (teleport) {
            val world = server.getWorld("world")
            player.teleport(Location(world, 0.5, 65.0, 0.5, 0F, 0F))
        }
    }

    internal fun resetPlayer(player: Player, teleport: Boolean) {
        resetPlayer(player, teleport, true)
    }
}
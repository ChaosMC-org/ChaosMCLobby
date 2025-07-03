package xyz.lncvrt.chaosmclobby.event

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerAdvancementCriterionGrantListener : Listener {
    @EventHandler
    fun onPlayerAdvancementCriterionGrantEvent(event: PlayerAdvancementCriterionGrantEvent) {
        event.isCancelled = true
    }
}
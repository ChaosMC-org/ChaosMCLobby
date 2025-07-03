package xyz.lncvrt.chaosmclobby.command

import org.incendo.cloud.kotlin.MutableCommandBuilder
import org.sayandev.stickynote.bukkit.command.BukkitCommand
import org.sayandev.stickynote.bukkit.command.BukkitSender
import org.sayandev.stickynote.bukkit.extension.sendComponent
import xyz.lncvrt.chaosmcapi.utils.MessageFormat

object DiscordCommand : BukkitCommand("discord") {
    override fun rootBuilder(builder: MutableCommandBuilder<BukkitSender>) {
        builder.handler { context ->
            val player = context.sender().player() ?: return@handler
            player.sendComponent(MessageFormat.setPrefix(MessageFormat.setPrimaryColor("Click ${MessageFormat.setSecondaryColor("<u><click:open_url:'https://chaos.lncvrt.xyz/discord'>HERE</click></u>")} to join our Discord Server!")))
        }
    }
}

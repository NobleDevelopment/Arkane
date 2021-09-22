package org.nobledev.arkane.adapter.command

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.executors.CommandExecutor
import org.bukkit.command.CommandSender

class CommandBuilder(val name: String) {
    private val args = mutableListOf<Argument>()
    private val aliases = mutableListOf<String>()
    private var permission: String = ""
    private var handler: (CommandSender, Array<Any>) -> Unit = { _, _ -> }

    fun withArgument(arg: Argument) {
        args.add(arg)
    }

    fun withAliases(vararg aliases: String) {
        this.aliases.addAll(aliases)
    }

    fun withPermission(permission: String) {
        this.permission = permission
    }

    fun executes(handler: (CommandSender, Array<Any>) -> Unit) {
        this.handler = handler
    }

    fun build(): CommandAPICommand {
        return CommandAPICommand(name)
            .withAliases(*aliases.toTypedArray())
            .withArguments(*args.toTypedArray())
            .withPermission(permission)
            .executes(CommandExecutor(handler))
    }
}

fun command(name: String, builder: CommandBuilder.() -> Unit): CommandAPICommand {
    val command = CommandBuilder(name).apply(builder).build()
    command.register()
    return command
}

package br.github.superteits.pokekibble.commands;

import br.github.superteits.pokekibble.PokeKibble;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class ReloadCommand {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Command to reload PokeKibble's config."))
            .permission("pokekibble.command.reload")
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    PokeKibble.getInstance().getConfig().load(PokeKibble.getInstance().getConfigManager());
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

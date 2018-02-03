package br.github.superteits.pokekibble.commands;

import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class BaseCommand {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("PokeKibble's base command."))
            .child(new HelpCommand().getCommandSpec(), "help")
            .child(new GiveCommand().getCommandSpec(), "give")
            .child(new ReloadCommand().getCommandSpec(), "reload")
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

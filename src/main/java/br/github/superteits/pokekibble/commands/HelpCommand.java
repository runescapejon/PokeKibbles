package br.github.superteits.pokekibble.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Arrays;

public class HelpCommand {
    private PaginationList helpPagination = PaginationList.builder()
            .title(TextSerializers.formattingCode('&').deserialize("&6[&l&fPokeKibble Help&6]"))
            .header(TextSerializers.formattingCode('&').deserialize("&7Aliases - pokekibble, pk, pokek, pkibble."))
            .padding(Text.of(TextColors.GOLD, "-"))
            .contents(Arrays.asList(
                    TextSerializers.formattingCode('&').deserialize("&l&f/pk help &r&7- Displays this help."),
                    TextSerializers.formattingCode('&').deserialize("&l&f/pk give <player> <quantity> &r&7- Gives PokeKibbles to the player."),
                    TextSerializers.formattingCode('&').deserialize("&l&f/pk reload &r&7- Reloads plugin's config.")))
            .build();

    private CommandSpec commandSpec = CommandSpec.builder()
            .permission("pokekibble.command.help")
            .description(Text.of("PokeKibble's help command."))
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    helpPagination.sendTo(src);
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

package br.github.superteits.pokekibble.commands;

import br.github.superteits.pokekibble.PokeKibble;
import br.github.superteits.pokekibble.data.KibbleData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;

import java.util.ArrayList;

public class GiveCommand {

    private CommandSpec commandSpec = CommandSpec.builder()
            .permission("pokekibble.command.give")
            .description(Text.of("Command to give PokeKibbles"))
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                    GenericArguments.onlyOne(GenericArguments.integer(Text.of("quantity"))))
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    Player player = args.<Player>getOne("player").get();
                    int quantity = args.<Integer>getOne("quantity").get();
                    if(quantity > 64 || quantity <= 0) {
                        src.sendMessage(Text.of(TextColors.RED, "Invalid quantity, please use numbers from 1 until 64"));
                        return CommandResult.success();
                    }
                    ItemStack itemStack = PokeKibble.getInstance().getConfig().getItemStack();
                    itemStack.setQuantity(quantity);
                    itemStack.offer(new KibbleData(true));
                    InventoryTransactionResult itr = ((PlayerInventory) player.getInventory()).getMain().offer(itemStack);
                    src.sendMessage(Text.of(TextColors.GREEN, "Gave " + quantity + " PokkeKibble(s) to " + player.getName() + "."));
                    ArrayList<ItemStackSnapshot> rejectedItems = new ArrayList(itr.getRejectedItems());
                    if (!rejectedItems.isEmpty()) {
                        Item item = (Item) player.getWorld().createEntity(EntityTypes.ITEM, player.getLocation().getPosition());
                        item.offer(Keys.REPRESENTED_ITEM, rejectedItems.get(0));
                        player.sendTitle(Title.builder()
                                .title(Text.of(TextColors.RED, "WARNING!"))
                                .subtitle(Text.of("Inventory full, check the ground."))
                                .build());
                        player.getWorld().spawnEntity(item);
                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}

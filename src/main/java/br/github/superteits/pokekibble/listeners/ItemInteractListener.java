package br.github.superteits.pokekibble.listeners;

import br.github.superteits.pokekibble.PokeKibble;
import br.github.superteits.pokekibble.api.KibbleUseEvent;
import br.github.superteits.pokekibble.data.KibbleKeys;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemInteractListener {

    @Listener
    public void onItemInteract(InteractItemEvent.Secondary e) {
        if (e.getItemStack().get(KibbleKeys.IS_KIBBLE).isPresent() && e.getItemStack().get(KibbleKeys.IS_KIBBLE).get() == true) {
            if (e.getCause().first(Player.class).isPresent()) {
                Player player = e.getCause().first(Player.class).get();
                if (!PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP) player).isPresent()) {
                    throw new RuntimeException("Error when trying to get " + player.getName() + ", please contact plugin author");
                }
                PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP) player).get();
                KibbleUseEvent event = new KibbleUseEvent(player, storage.partyPokemon);
                Sponge.getEventManager().post(event);
                if (!event.isCancelled()) {
                    if (player.hasPermission("pokekibble.use")) {
                        storage.healAllPokemon(((EntityPlayerMP) player).getEntityWorld());
                        player.sendMessage(PokeKibble.getInstance().getConfig().getHealMessage());
                        if (event.isKibbleConsumable()) {
                            if(e.getItemStack().getQuantity() == 1) {
                                player.setItemInHand(e.getHandType(), ItemStack.of(ItemTypes.AIR, 1));
                            } else {
                                ItemStack itemStack = e.getItemStack().createStack();
                                itemStack.setQuantity(itemStack.getQuantity() - 1);
                                player.setItemInHand(e.getHandType(), itemStack);
                            }

                        }
                    } else {
                        player.sendMessage(PokeKibble.getInstance().getConfig().getPermissionErrorMessage());
                    }
                }
            }
        }
    }
}

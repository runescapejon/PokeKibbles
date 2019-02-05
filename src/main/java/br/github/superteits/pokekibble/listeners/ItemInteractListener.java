package br.github.superteits.pokekibble.listeners;

import br.github.superteits.pokekibble.PokeKibble;
import br.github.superteits.pokekibble.api.KibbleUseEvent;
import br.github.superteits.pokekibble.data.KibbleKeys;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemInteractListener {

	@Listener
	public void onItemInteract(InteractItemEvent.Secondary e) {
		if (e.getItemStack().get(KibbleKeys.IS_KIBBLE).isPresent()
				&& e.getItemStack().get(KibbleKeys.IS_KIBBLE).get() == true) {
			if (e.getCause().first(Player.class).isPresent()) {
				Player player = e.getCause().first(Player.class).get();
				Optional<PlayerPartyStorage> optstorage = Optional.ofNullable(Pixelmon.storageManager.getParty((EntityPlayerMP)player));
				if (!optstorage.isPresent()) {
					throw new RuntimeException(
							"Error when trying to get " + player.getName() + ", please contact plugin author");
				}
				PlayerPartyStorage storage = Pixelmon.storageManager.getParty((EntityPlayerMP) player);
				// PlayerStorage storage =
				// PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP)
				// player).get();
				KibbleUseEvent event = new KibbleUseEvent(player, storage);
				Sponge.getEventManager().post(event);
				if (!event.isCancelled()) {
					if (player.hasPermission("pokekibble.use")) {
					storage.heal();
						//	storage.healAllPokemon(((EntityPlayerMP) player).getEntityWorld());
						player.sendMessage(PokeKibble.getInstance().getConfig().getHealMessage());
						if (event.isKibbleConsumable()) {
							if (e.getItemStack().getQuantity() == 1) {
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

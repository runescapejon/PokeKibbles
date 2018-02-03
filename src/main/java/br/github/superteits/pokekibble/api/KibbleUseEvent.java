package br.github.superteits.pokekibble.api;

import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;

import java.util.Optional;

/**
 * This event handles kibbles uses, you can retrieve the player and the party who triggered this event.
 */

public class KibbleUseEvent extends AbstractEvent implements Cancellable {

    private Cause cause;
    private EventContext context;
    private NBTTagCompound[] party;
    private boolean cancelled;
    private boolean consumeKibble;

    public KibbleUseEvent(Entity player, NBTTagCompound[] party) {
        context = EventContext.builder().build();
        cause = Cause.builder().append(player).build(context);
        cancelled = false;
        this.party = party;
        consumeKibble = true;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    @Override
    public Object getSource() {
        return cause.root();
    }

    @Override
    public EventContext getContext() {
        return context;
    }

    /**
     * Gets the player who triggered this event.
     * @return {@link Optional} of {@link Player}
     */
    public Optional<Player> getPlayer() {
        return cause.first(Player.class);
    }

    /**
     * Gets the party of the player who triggered this.
     * @return an {@link Optional} of {@link NBTTagCompound[]}
     */
    public Optional<NBTTagCompound[]> getPokemonParty() {
        return Optional.of(party);
    }

    /**
     * Sets whether Kibble will be consumed or not.
     * @param action - boolean.
     */
    public void setKibbleConsumable(boolean action) {
        consumeKibble = action;
    }

    /**
     * Gets if Kibble will be consumed or not
     * @return true - if Kibble will be consumed.
     */
    public boolean isKibbleConsumable() {
        return consumeKibble;
    }

}

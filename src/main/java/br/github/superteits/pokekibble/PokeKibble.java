package br.github.superteits.pokekibble;

import br.github.superteits.pokekibble.commands.BaseCommand;
import br.github.superteits.pokekibble.config.Config;
import br.github.superteits.pokekibble.data.ImmutableKibbleData;
import br.github.superteits.pokekibble.data.KibbleData;
import br.github.superteits.pokekibble.data.KibbleDataBuilder;
import br.github.superteits.pokekibble.data.KibbleKeys;
import br.github.superteits.pokekibble.listeners.ItemInteractListener;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import javax.inject.Inject;
import java.nio.file.Path;

@Plugin(id = PokeKibble.ID,
        name = "PokeKibble",
        version = PokeKibble.VERSION,
        authors = "Teits",
        description = PokeKibble.DESCRIPTION,
        dependencies = @Dependency(id = "pixelmon", version = "[6.3.4]"))
public class PokeKibble {

    public static final String ID = "pokekibble";
    public static final String VERSION = "1.0.1";
    public static final String DESCRIPTION = "Plugin to create PokeKibbles and heal your Pokemons's life.";
    private static PokeKibble instance;
    @Inject
    private PluginContainer pluginContainer;
    @Inject
    private Logger logger;
    @Inject
    @DefaultConfig(sharedRoot = true)
    private Path path;
    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;
    private Config config = new Config();

    public static PokeKibble getInstance() {
        return instance;
    }

    @Listener
    public void onKeyRegistration(GameRegistryEvent.Register<Key<?>> event) {
        KibbleKeys.IS_KIBBLE = Key.builder()
                .type(new TypeToken<Value<Boolean>>() {})
                .id("is_kibble")
                .name("IsKibble")
                .query(DataQuery.of("IsKibble"))
                .build();
    }

    @Listener
    public void onDataRegistration(GameRegistryEvent.Register<DataRegistration<?, ?>> event) {
        DataRegistration.builder()
                .dataClass(KibbleData.class)
                .immutableClass(ImmutableKibbleData.class)
                .builder(new KibbleDataBuilder())
                .manipulatorId("kibble")
                .dataName("KibbleData")
                .buildAndRegister(this.pluginContainer);
    }

    @Listener
    public void onGameInit(GameInitializationEvent e) {
        instance = this;
        Sponge.getEventManager().registerListeners(this, new ItemInteractListener());
        config.install(path, configManager);
    }

    @Listener
    public void onGameStarting(GameStartingServerEvent e) {
        Sponge.getCommandManager().register(this, new BaseCommand().getCommandSpec(), "pokekibble", "pk", "pokek", "pkibble");
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getPath() {
        return path;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getConfigManager() {
        return configManager;
    }

    public Config getConfig() {
        return config;
    }
}

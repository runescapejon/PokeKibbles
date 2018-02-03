package br.github.superteits.pokekibble.config;

import br.github.superteits.pokekibble.PokeKibble;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Config {

    private Text itemName;
    private List<Text> itemDescription = new ArrayList<>();
    private ItemType itemType;
    private Text healMessage;
    private Text permissionErrorMessage;
    private int configVersion;
    private ItemStack itemStack;

    public void install(Path path, ConfigurationLoader<CommentedConfigurationNode> configManager) {
        if (Files.notExists(path)) {
            setup(path);
            load(configManager);
        } else {
            load(configManager);
        }
    }

    public void load(ConfigurationLoader<CommentedConfigurationNode> configManager) {
        try {
            ConfigurationNode configNode = configManager.load();
            itemName = TextSerializers.formattingCode('&').deserialize(configNode.getNode("Item's Name").getString());
            itemDescription.clear();
            for (String message : configNode.getNode("Item's Description").getList(TypeToken.of(String.class))) {
                itemDescription.add(TextSerializers.formattingCode('&').deserialize(message));
            }
            itemStack = configNode.getNode("Item's ID").getValue(TypeToken.of(ItemStack.class));
            healMessage = TextSerializers.formattingCode('&').deserialize(configNode.getNode("Heal Message").getString());
            permissionErrorMessage = TextSerializers.formattingCode('&').deserialize(configNode.getNode("Permission Error Message").getString());
            configVersion = configNode.getNode("Config Version").getInt();
            itemStack.offer(Keys.DISPLAY_NAME, itemName);
            itemStack.offer(Keys.ITEM_LORE, itemDescription);
            PokeKibble.getInstance().getLogger().info("Config loaded with success.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }

    public void setup(Path path) {
        try {
            Optional<Asset> optConfigFile = Sponge.getAssetManager().getAsset(PokeKibble.getInstance(), "pokekibble.conf");
            if (!optConfigFile.isPresent()) {
                throw new RuntimeException("An error has occured when plugin tried to load default configuration file, please contact plugin's author.");
            }
            Asset configFile = optConfigFile.get();
            configFile.copyToFile(path);
            PokeKibble.getInstance().getLogger().info("PokeKibble installed with success.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Text getItemName() {
        return itemName;
    }

    public List<Text> getItemDescription() {
        return itemDescription;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Text getHealMessage() {
        return healMessage;
    }

    public Text getPermissionErrorMessage() {
        return permissionErrorMessage;
    }

    public int getConfigVersion() {
        return configVersion;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}

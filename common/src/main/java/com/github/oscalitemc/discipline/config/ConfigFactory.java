package com.github.oscalitemc.discipline.config;

import com.github.oscalitemc.discipline.api.Discipline;
import com.google.inject.Inject;
import net.kyori.adventure.serializer.configurate4.ConfigurateComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@DefaultQualifier(NonNull.class)
public class ConfigFactory {

    private final Path pluginDirectory;
    private @Nullable MainConfig mainConfig = null;

    @Inject
    public ConfigFactory(
            final Discipline discipline,
            final Path pluginDirectory
            ) {
        this.pluginDirectory = pluginDirectory;
    }

    public @Nullable MainConfig reloadMainConfig() {
        try {
            this.mainConfig = this.load(MainConfig.class, "config.conf");
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
        return this.mainConfig;
    }

    public @Nullable MainConfig mainConfig() {
        if (this.mainConfig == null) {
            return this.reloadMainConfig();
        }
        return this.mainConfig;
    }

    public ConfigurationLoader<?> configurationLoader(final Path file) {
        return HoconConfigurationLoader.builder()
                .prettyPrinting(true)
                .defaultOptions(opts -> {
                    final ConfigurateComponentSerializer serializer =
                            ConfigurateComponentSerializer.configurate();

                    return opts.shouldCopyDefaults(true).serializers(serializerBuilder ->
                            serializerBuilder.registerAll(serializer.serializers())
                    );
                })
                .path(file)
                .build();
    }

    public <T> @Nullable T load(final Class<T> clazz, final String fileName) throws IOException {
        if (!Files.exists(this.pluginDirectory)) {
            Files.createDirectories(this.pluginDirectory);
        }

        final Path file = this.pluginDirectory.resolve(fileName);

        final ConfigurationLoader<?> loader = this.configurationLoader(file);

        try {
            final ConfigurationNode node = loader.load();
            final @Nullable T config = node.get(clazz);

            if (!Files.exists(file)) {
                node.set(clazz, config);
                loader.save(node);
            }

            return config;
        } catch (final ConfigurateException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}

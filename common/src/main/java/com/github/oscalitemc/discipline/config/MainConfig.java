package com.github.oscalitemc.discipline.config;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@DefaultQualifier(NonNull.class)
@ConfigSerializable
public class MainConfig {

    @Comment("")
    private DatabaseSettings databaseSettings = new DatabaseSettings();

    public DatabaseSettings databaseSettings() {
        return this.databaseSettings;
    }
}

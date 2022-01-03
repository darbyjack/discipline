package com.github.oscalitemc.discipline.config;

import org.checkerframework.framework.qual.DefaultQualifier;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@DefaultQualifier(Nullable.class)
@ConfigSerializable
public class DatabaseSettings {

    @Comment("jdbc url")
    private String url = "jdbc:mysql://localhost:3306/discipline";

    @Comment("login username")
    private String username = "user";

    @Comment("login password")
    private String password = "password";


    public String url() {
        return this.url;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }
}

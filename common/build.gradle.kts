plugins {
    id("discipline.base-conventions")
}

dependencies {
    api(projects.disciplineApi)


    // Configs
    api(libs.configurateHocon)
    api(libs.adventureSerializerConfigurate4) {
        isTransitive = false
    }
    api(libs.typesafeConfig)

    // Cloud
    api(platform(libs.cloudBom))
    api(libs.cloudCore)
    api(libs.cloudMinecraftExtras) {
        isTransitive = false
    }

    api(libs.guice)
}

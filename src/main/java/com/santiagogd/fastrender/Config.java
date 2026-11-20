package com.santiagogd.fastrender;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = FastRenderMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue maxRenderDistance = BUILDER
            .comment("Maximum render distance the mod will try to set (vanilla max is usually 32). Higher values need more RAM and a strong CPU/GPU.")
            .defineInRange("maxRenderDistance", 32, 2, 64);

    public static final ForgeConfigSpec.BooleanValue enableAggressiveChunkLoading = BUILDER
            .comment("If true, attempts to keep more chunks loaded around the player (experimental, may impact performance).")
            .define("enableAggressiveChunkLoading", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Config values are accessed via the IntValue/BooleanValue getters
    }
}

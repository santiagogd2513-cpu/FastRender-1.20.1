package com.santiagogd.fastrender;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(FastRenderMod.MODID)
public class FastRenderMod {
    public static final String MODID = "fastrender";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FastRenderMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        // Register config
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("FastRender common setup complete. Target max render distance: {}", Config.maxRenderDistance.get());
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Options options = Minecraft.getInstance().options;
                int target = Config.maxRenderDistance.get();
                int current = options.renderDistance().get();

                if (current < target) {
                    options.renderDistance().set(target);
                    LOGGER.info("FastRender: Raised render distance from {} to {}", current, target);
                } else {
                    LOGGER.info("FastRender: Current render distance already >= target ({} >= {})", current, target);
                }

                // Force save so it persists
                options.save();
            });
        }
    }
}

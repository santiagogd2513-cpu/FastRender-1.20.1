package com.santiagogd.fastrender.mixin;

import com.santiagogd.fastrender.Config;
import net.minecraft.server.level.ChunkMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin sobre ChunkMap para influir en la distancia / cantidad de chunks que se mantienen.
 *
 * Este es el punto de entrada real para la Fase 2 avanzada.
 * Por ahora inyectamos de forma segura y leemos la config.
 * Se puede expandir para modificar view distance efectiva o prioridades.
 */
@Mixin(ChunkMap.class)
public class ChunkMapMixin {

    /**
     * Ejemplo de inyección en el método que calcula la distancia de vista de un jugador.
     * En 1.20.1 el método se llama getPlayerViewDistance.
     * Ajustamos el valor según el perfil de rendimiento.
     */
    @Inject(method = "getPlayerViewDistance", at = @At("RETURN"), cancellable = true)
    private void fastrender$modifyViewDistance(CallbackInfoReturnable<Integer> cir) {
        if (!Config.enableAggressiveChunkLoading.get()) return;

        int original = cir.getReturnValue();
        int extra = Config.getEffectiveExtraBuffer();

        // Añadimos buffer extra según el perfil (high/extreme añaden más)
        int modified = original + (extra / 2);

        // Límite de seguridad
        if (modified > 128) modified = 128;

        if (modified != original) {
            cir.setReturnValue(modified);
        }
    }
}

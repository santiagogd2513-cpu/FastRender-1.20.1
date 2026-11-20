# FastRender 1.20.1 (Forge)

Mod para Minecraft Java 1.20.1 con Forge que intenta:

- Subir la distancia de renderizado máxima (configurable, por defecto 32, hasta 64).
- Preparar el terreno para una carga de chunks más agresiva (base experimental).

## Estado actual

Esqueleto funcional listo para compilar.  
- Config client-side con `maxRenderDistance` y un flag experimental de carga agresiva.  
- Al iniciar el cliente intenta forzar el valor de render distance si es más bajo que el configurado.

**Importante:** Subir mucho la render distance consume mucha RAM y CPU/GPU. Usa con precaución.

## Compilación automática

Cada push a `main` dispara GitHub Actions y genera el JAR como artefacto.

1. Ve a la pestaña **Actions** del repositorio.
2. Selecciona el workflow **Build Mod**.
3. Descarga el artefacto `FastRender-1.20.1`.

## Cómo usarlo localmente

```bash
./gradlew build
```

El JAR aparecerá en `build/libs/`.

## Próximos pasos posibles

- Mixin para aumentar el límite real de chunks cargados.
- Prefetch de chunks en dirección del movimiento del jugador.
- Integración con Distant Horizons u otros mods de LOD.
- Config GUI in-game.

Hecho con ❤️ para santiagogd2513-cpu.

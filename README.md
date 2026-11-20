# FastRender 1.20.1 (Forge 47.4.5)

Mod para Minecraft Java **1.20.1** + **Forge 47.4.5** que busca:

- Aumentar la distancia de renderizado de forma configurable.
- Acelerar y hacer más suave la carga de chunks.

## Plan de desarrollo (2 fases grandes)

### Fase 1 — Fundación y Render Distance (Actual)
- Proyecto limpio y compilable con Forge **47.4.5**
- Config client-side (`maxRenderDistance` hasta 64)
- Forzar la distancia de render al iniciar el juego
- GitHub Actions que genera el JAR automáticamente
- Estructura sólida lista para ampliar

### Fase 2 — Aceleración de carga de chunks
- Prefetch de chunks en la dirección del movimiento del jugador
- Aumento del número de chunks que se procesan por tick
- Posible uso de Mixins para optimizar el sistema de carga de vanilla
- Opciones de configuración más avanzadas (perfiles de rendimiento)
- Mejoras de estabilidad y rendimiento real

---

## Estado actual

Fase 1 en progreso / casi lista.

**Importante:** Subir mucho la render distance consume mucha RAM y CPU/GPU. Úsalo con precaución.

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

Hecho con ❤️ para santiagogd2513-cpu.

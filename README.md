# FastRender 1.20.1 (Forge 47.4.5)

Mod para Minecraft Java **1.20.1** + **Forge 47.4.5** que busca:

- Aumentar la distancia de renderizado de forma configurable (hasta **128 chunks**).
- Acelerar y hacer más suave la carga de chunks.

---

## Plan de desarrollo (2 fases grandes)

### Fase 1 — Fundación y Render Distance ✅ (en progreso)
**Objetivo:** Tener un mod estable, que compile bien y que realmente suba la distancia de render.

Incluye:
- Proyecto limpio configurado para Forge **47.4.5**
- Config client-side (`maxRenderDistance` de 2 a **128**)
- Forzar la distancia de render al iniciar el cliente
- GitHub Actions que genera el JAR automáticamente
- Logging claro y código preparado para la siguiente fase

**Nota sobre perfiles:** En la Fase 2 se añadirán perfiles de rendimiento. Por tu petición, el perfil por defecto será **Alto** (carga de chunks más agresiva).

### Fase 2 — Aceleración de carga de chunks
**Objetivo:** Que los chunks se carguen más rápido y de forma más inteligente.

Incluye:
- Prefetch de chunks en la dirección del movimiento del jugador
- Aumentar cuántos chunks se procesan por tick
- Posible uso de Mixins para optimizar el sistema de carga de vanilla
- Perfiles de rendimiento: Bajo / Medio / **Alto (por defecto)** / Extremo
- Mejoras de estabilidad y fluidez real

---

## Estado actual

Estamos trabajando en la **Fase 1**.

**Importante:** 128 chunks es muy exigente. Se recomienda tener al menos 10-12 GB de RAM asignados a Minecraft y un PC potente.

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

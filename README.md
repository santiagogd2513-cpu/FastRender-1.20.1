# FastRender 1.20.1 (Forge 47.4.5)

Mod para Minecraft Java **1.20.1** + **Forge 47.4.5** que busca:

- Aumentar la distancia de renderizado de forma configurable (hasta **128 chunks**).
- Acelerar y hacer más suave la carga de chunks.

---

## Plan de desarrollo (2 fases grandes)

### Fase 1 — Fundación y Render Distance ✅
- Proyecto limpio con Forge **47.4.5**
- Config de distancia de render (2 a **128**)
- Forzar la distancia al iniciar el cliente
- GitHub Actions para generar el JAR

### Fase 2 — Aceleración de carga de chunks 🚀 (en progreso)
**Objetivo:** Que los chunks se carguen más rápido y con menos pop-in.

Incluye ya:
- **Perfiles de rendimiento**: `low` / `medium` / `high` / `extreme`
- **Perfil por defecto: `high`** (como pediste)
- Sistema de buffer extra de chunks
- Multiplicador de chunks procesados por tick
- Handler de eventos que se ejecuta en cliente y servidor

Pendiente / próximo:
- Prefetch real en la dirección del movimiento del jugador
- Mixins para un control más profundo del sistema de carga de vanilla
- Ajustes finos de rendimiento y estabilidad

---

## Cómo usar los perfiles

En el archivo de config del cliente (`fastrender-client.toml`) puedes cambiar:

```toml
performanceProfile = "high"   # low | medium | high | extreme
enableAggressiveChunkLoading = true
```

- **low** → suave, poco impacto
- **medium** → equilibrado
- **high** → carga agresiva (por defecto)
- **extreme** → máxima agresividad (puede generar lag si el PC no da)

---

## Compilación automática

Cada push a `main` dispara GitHub Actions y genera el JAR como artefacto.

1. Ve a la pestaña **Actions**
2. Selecciona el workflow **Build Mod**
3. Descarga el artefacto `FastRender-1.20.1`

## Compilar localmente

```bash
./gradlew build
```

Hecho con ❤️ para santiagogd2513-cpu.

# FastRender 1.20.1 (Forge 47.4.5)

Mod para Minecraft **1.20.1** + **Forge 47.4.5**.

## Qué hace este mod

### Fase 1 — Render Distance
- Distancia de render configurable hasta **128 chunks**
- Se fuerza al iniciar el cliente

### Fase 2 — Carga de chunks más agresiva + Mixins
- Perfiles de rendimiento: `low` / `medium` / `high` (por defecto) / `extreme`
- Buffer extra de chunks
- Detección de dirección de movimiento
- **Mixins activos** (ChunkMapMixin) para influir realmente en el sistema de vista de chunks del servidor

---

## Configuración

Archivo: `config/fastrender-client.toml`

```toml
[Render Distance]
	maxRenderDistance = 32

[Chunk Loading - Fase 2]
	performanceProfile = "high"
	enableAggressiveChunkLoading = true
	extraChunkBuffer = 8
	chunksPerTickMultiplier = 2
```

---

## Mixins incluidos

- `ChunkMapMixin` → modifica la view distance efectiva según el perfil y el buffer configurado.

La estructura ya está preparada para añadir más Mixins fácilmente.

---

## Compilar

### Automático
1. Pestaña **Actions**
2. Workflow **Build Mod**
3. Descargar el artefacto

### Manual
```bash
./gradlew build
```

---

**Estado actual:** Fase 1 completa + Fase 2 con Mixins base  
**Autor:** santiagogd2513-cpu

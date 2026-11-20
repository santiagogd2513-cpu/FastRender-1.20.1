# FastRender 1.20.1 (Forge 47.4.5)

Mod para Minecraft **1.20.1** + **Forge 47.4.5**.

## Qué hace este mod

### Fase 1 — Render Distance
- Permite configurar la distancia de render hasta **128 chunks**.
- Fuerza el valor al iniciar el cliente.

### Fase 2 — Carga de chunks más agresiva
- Sistema de **perfiles de rendimiento**:
  - `low`
  - `medium`
  - `high` ← **por defecto**
  - `extreme`
- Buffer extra de chunks alrededor del jugador.
- Multiplicador de procesamiento de chunks.
- Detección de dirección de movimiento del jugador.

---

## Configuración

Archivo: `config/fastrender-client.toml`

```toml
[Render Distance]
	maxRenderDistance = 32   # de 2 a 128

[Chunk Loading - Fase 2]
	performanceProfile = "high"          # low | medium | high | extreme
	enableAggressiveChunkLoading = true
	extraChunkBuffer = 8
	chunksPerTickMultiplier = 2
```

---

## Cómo compilar

### Automático (recomendado)
1. Ve a la pestaña **Actions** del repositorio
2. Ejecuta el workflow **Build Mod**
3. Descarga el artefacto `FastRender-1.20.1`

### Manual
```bash
./gradlew build
```
El JAR se genera en `build/libs/`.

---

## Limitaciones actuales

Sin **Mixins** el control sobre el sistema interno de carga de chunks de vanilla es limitado.  
Esta versión implementa todo lo posible con la API pública de Forge + eventos.

Para un control mucho más profundo (prefetch real, más chunks por tick de verdad, etc.) se necesitarían Mixins. Eso se puede añadir en una versión futura si lo pides.

---

**Versión actual:** Base completa de Fase 1 + Fase 2  
**Autor:** santiagogd2513-cpu

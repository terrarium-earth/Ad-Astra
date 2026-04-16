# Ad Astra 1.21.1 + NeoForge migration (initial pass)

## Baseline selected
- Upstream branch: `1.20.x` (default and newest maintained line in upstream repo)
- Working branch: `port/1.21.1-neoforge-initial`
- Reference jar inspected: `/home/openclaw/.openclaw/media/inbound/a2d91126-d2e3-4e2e-9fce-aec678e3ee6a`
  - Jar metadata indicates it is a NeoForge/FML build targeting **1.20.4-era** dependencies, useful as parity confirmation only.

## Changes in this initial port attempt
- Bumped root `minecraftVersion` to `1.21.1`.
- Bumped NeoForge loader dependency to `21.1.226`.
- Updated NeoForge mod dependency ranges in `neoforge.mods.toml`:
  - `neoforge`: `[21.1,)`
  - `minecraft`: `[1.21.1,)`
- Removed hardcoded Minecraft version from parchment mapping artifact id:
  - `parchment-1.20.6` -> `parchment-$minecraftVersion`
- Replaced hardcoded lib artifact suffixes with `$minecraftVersion` for:
  - `resourcefullib`
  - `resourcefulconfig`
  - `botarium`
  - `athena`

## Build / verification status
- Could not run Gradle compilation in this environment: Java runtime is missing.
- Command attempted:
  - `./gradlew :neoforge:tasks`
  - `./gradlew :neoforge:compileJava -x test`
- Failure:
  - `ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.`

## Remaining blockers / checklist
- [ ] Install JDK 21 and set `JAVA_HOME`.
- [ ] Resolve dependency availability for 1.21.1 artifacts:
  - ResourcefulLib
  - ResourcefulConfig
  - Botarium
  - Athena
  - REI/JEI compileOnly versions
- [ ] Validate parchment mapping availability for `parchment-1.21.1` (or switch to Mojang-only mappings if unavailable).
- [ ] Run `:common:compileJava` and `:neoforge:compileJava` and fix API breakages from 1.20.6 -> 1.21.1.
- [ ] Run data generation (`:neoforge:runData`) and inspect generated resources changes.
- [ ] Smoke test in NeoForge dev client/server runs.
- [ ] Review/adjust mixins and access changes for 1.21.1 internals.

## Notes
This is an initial migration scaffold commit to unblock iterative compile-fix cycles once Java + dependency resolution are available.

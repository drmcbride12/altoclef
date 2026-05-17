# AltoClef

AltoClef is a Fabric client-side Minecraft bot powered by Baritone. This fork is now focused on keeping AltoClef alive on current Minecraft versions, with the active target updated to **Minecraft 26.1.2**.

The goal is simple: make AltoClef usable again, keep the autonomous task system working, and continue improving the `@gamer` beat-the-game flow on modern Minecraft.

## Current Status

- Minecraft target: **26.1.2**
- Fabric Loader: **0.19.2**
- Fabric API: **0.149.0+26.1.2**
- Java: **25**
- Build artifact: `build/libs/altoclef-0.4.0+26.1.2.jar`

This fork includes the Minecraft 26.1.2 port work, bundled Baritone runtime updates, modern Fabric setup, and runtime fixes for startup, world loading, pathing, crafting, projectile tracking, and death waypoint handling.

## What It Does

AltoClef breaks large goals into smaller Minecraft tasks. It can gather resources, craft, mine, pathfind, fight or avoid mobs, use containers, manage inventory, and run high-level objectives from chat commands.

The headline task is still:

```text
@gamer
```

That command starts the autonomous beat-the-game task.

## Install

1. Install Minecraft **26.1.2** with Fabric Loader **0.19.2**.
2. Install the matching Fabric API for **26.1.2**.
3. Build this repo or download a release jar when one is available.
4. Put `altoclef-0.4.0+26.1.2.jar` in your Minecraft `mods` folder.
5. Start the game and open a singleplayer world.

Old Baritone configs can interfere with this bot. If behavior looks strange, clear old Baritone/AltoClef config files from the Minecraft config directory and let this fork regenerate them.

## Commands

Commands are sent in chat with the AltoClef prefix:

```text
@help
@gamer
@stop
```

Use `@gamer` to begin the beat-the-game task from a survival world. Use `@stop` to stop the current task.

## Build From Source

Use JDK 25, then run:

```powershell
.\gradlew.bat build
```

The jar will be written to:

```text
build/libs/altoclef-0.4.0+26.1.2.jar
```

## Development Notes

This is now a maintained modernization fork rather than a historical mirror of the old AltoClef release flow. The old README information about 1.18, nightly builds, and unrelated forks has been removed because it no longer reflects this repo.

Recent porting work includes:

- Updated Gradle/Fabric configuration for Minecraft 26.1.2.
- Rebuilt bundled Baritone jars for this fork.
- Fixed client initialization when loading directly into a world.
- Fixed multiple renamed or removed Minecraft API usages.
- Fixed world height handling for modern dimensions.
- Fixed projectile tracking mixin recursion.
- Fixed Baritone scanner/cache issues with modern chunk data.
- Disabled the unstable modern recipe-book crafting path and restored stable manual crafting.
- Fixed task interruption so in-progress crafting can finish instead of deadlocking.

## Project Direction

This fork is taking over active maintenance for modern Minecraft support. The priority is practical reliability: keep the bot building, keep the jar runnable, fix runtime crashes as they are found, and improve the full autonomous game-completion path over time.

<div align="center">

<img src="https://cdn.discordapp.com/attachments/1232906017608695931/1541201440805232760/logoslime.png?ex=6a8cbb31&is=6a8b69b1&hm=15b35ad38d1c911d98386722acb7dd7b9a885bf142ea312fed81efa10f0d1ec8" alt="SlimeHologram Logo" width="200"/>

# SlimeHologram

**A highly advanced, strictly client-side hologram engine and API for Spigot 1.8.8.**

[![](https://jitpack.io/v/a8kj7sea/SlimeHologram.svg)](https://jitpack.io/#a8kj7sea/SlimeHologram)

</div>

## Overview

SlimeHologram is an enterprise-grade hologram plugin designed specifically for Spigot 1.8.8. By leveraging PacketEvents, the system spawns fake entities directly to the client, ensuring absolute version independence, zero NMS reflection, and zero server-side TPS drop. 

Built with modern Java 21 standards, the project utilizes SOLID principles, a multi-module Maven architecture, and advanced design patterns to provide a highly performant and extensible hologram management system.

## Features

- **Strictly Client-Side:** No real entities are spawned on the server, eliminating entity tracking lag.
- **Advanced Rendering:** Support for both static helmet items (ideal for custom skulls) and naturally rotating 3D dropped items.
- **Interactivity:** Packet interception for clickable holograms (Left, Right, and Shift clicks) and a built-in raytracing Focus (Hover) system for dynamic visual effects.
- **Cinematic Tracking:** A dynamic `LocationTracker` interface allowing holograms to follow players or move smoothly during cutscenes.
- **Session Management:** Automatic cleanup of player-bound holograms upon disconnect, death, or world change to prevent memory leaks.
- **Persistence & Integration:** YAML-based persistence with safe, built-in PlaceholderAPI support (soft-dependency).
- **Text Formatting:** Markdown support for easy formatting (e.g., `#`, `##`, `-`) and an interactive clickable chat menu for in-game editing.

## Installation

1. Download the latest `SlimeHologram.jar` from the releases page.
2. Drop the jar file into your server's `plugins` folder.
3. (Optional) Install [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) for dynamic text placeholders.
4. Restart your server. The configuration files will be automatically generated in `plugins/SlimeHologram/`.

## Commands & Permissions

Root command: `/slimehologram` (Aliases: `/sh`, `/hologram`, `/hd`)

| Command | Description | Permission |
| --- | --- | --- |
| `/sh create <name> [-p] [text]` | Create a hologram. Use `-p` for private (client-side). | `holograms.admin` |
| `/sh delete <name>` | Delete a hologram. | `holograms.admin` |
| `/sh list` | List all existing holograms. | `holograms.use` |
| `/sh addline <name> <text>` | Append a line to a hologram. | `holograms.admin` |
| `/sh insertline <name> <line> <text>` | Insert a line at a specific index. | `holograms.admin` |
| `/sh setline <name> <line> <text>` | Replace an existing line. | `holograms.admin` |
| `/sh removeline <name> <line>` | Remove a specific line. | `holograms.admin` |
| `/sh edit <name>` | Open the interactive chat edit menu. | `holograms.admin` |
| `/sh movehere <name>` | Move a hologram to your current location. | `holograms.admin` |
| `/sh teleport <name>` | Teleport to a hologram. | `holograms.admin` |
| `/sh align <x\|y\|z\|xz> <h1> <h2>` | Align `<h1>` to `<h2>` on specific axes. | `holograms.admin` |
| `/sh copy <source> <dest>` | Copy a hologram to a new name. | `holograms.admin` |
| `/sh reload` | Reload holograms from config. | `holograms.admin` |

### Special Line Formats

When using commands or editing the configuration, you can use the following prefixes for advanced rendering:

- `ICON:DIAMOND` - Displays a static item as a helmet.
- `ICON_3D:DIAMOND` - Displays a floating, rotating 3D item.
- `# Header` - Formats the line as a gold, bold header.
- `## Subheader` - Formats the line as a yellow, bold subheader.
- `- List Item` - Formats the line as a gray bullet point.

## Architecture

SlimeHologram is built using a clean, multi-module Maven structure to separate public contracts from internal implementations:

- **Command Pattern:** (`CommandDispatcher`, `SubCommand`) - Every `/sh` subcommand is isolated in its own class, ensuring easy extensibility.
- **Strategy Pattern:** (`MarkdownParser`, `MarkdownRule`) - Dynamically chain text formatting rules without modifying core logic.
- **Dependency Injection:** `HologramManager` depends on the `HologramStorage` interface, not the YAML implementation. This allows seamless migration to SQL or other backends in the future.
- **Builder Pattern:** Fluent API for constructing complex holograms (`HologramBuilder`).
- **Program to Interfaces:** The `api` module contains zero implementation details. All logic is hidden in the `base` module.

## Developer API

Looking to integrate SlimeHologram into your own plugin? Please refer to the **[API Usage Wiki](https://github.com/a8kj7sea/SlimeHologram/wiki)** for comprehensive guides, code examples, and architecture breakdowns.

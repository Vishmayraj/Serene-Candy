# 🍬 Serene Candy

A visually rich, minimalist music player for Windows — no installs, no accounts, no ads.

![Java](https://img.shields.io/badge/Java-25-orange) ![JavaFX](https://img.shields.io/badge/JavaFX-25-blue) ![Platform](https://img.shields.io/badge/platform-Windows-lightgrey) ![Status](https://img.shields.io/badge/status-active-brightgreen) ![License](https://img.shields.io/badge/license-MIT-green)

---

## 🎧 Overview

Serene Candy is a sleek desktop music player built with JavaFX. It features a warm aesthetic, smooth transitions, and dynamic album art that fills the screen with a blurred glow — all wrapped in a minimal, non-resizable frame. Drop in your music, double-click, and you're done.

---

## ⬇️ Download

Grab the latest release from the [Releases](https://github.com/Vishmayraj/Serene-Candy/releases) page.

**No Java installation required.** The JRE is fully bundled inside.

---

## 🚀 Getting Started

1. Download and extract `SereneCandy.zip`
2. Drop your `.mp3` or `.flac` files into the `Playlist/` folder
3. Double-click `SereneCandy.exe`

That's it.

> **Note:** The `Playlist/` folder lives inside `app/`. You can add, remove, or swap songs in it anytime — just relaunch the app.

---

## 📁 What's Inside the ZIP

```
SereneCandy/
├── SereneCandy.exe       ← double-click to launch
├── app/
│   ├── Playlist/         ← drop your music here
│   └── SereneCandy.jar   ← app internals (don't touch)
└── runtime/              ← bundled JRE (no Java needed)
```

---

## ✨ Features

🎵 **Automatic Playlist Detection** — reads `.mp3` and `.flac` files from the `Playlist/` folder on every launch.

🪞 **Dynamic Album Art** — extracts and displays embedded cover art directly from your music files.

🎚️ **Progress & Volume Sliders** — clean, responsive, and keyboard-friendly.

🎲 **Shuffle Playback** — randomizes your playlist order for a fresh listening experience each time.

🔁 **Autoplay** — next song starts automatically when the current one ends.

💾 **Persistent Track Memory** — remembers your last played song across sessions.

🩵 **Blurred Background** — album art doubles as an ambient soft-focus backdrop.

⚡ **Zero Dependencies** — no Java, no JavaFX SDK, no setup required on the target machine.

---

## ⌨️ Keyboard Shortcuts

| Key | Action |
|-----|--------|
| `Space` | Play / Pause |
| `Ctrl + →` | Next track |
| `Ctrl + ←` | Previous track |
| `→` | Seek +5 seconds |
| `←` | Seek −5 seconds |
| `↑` | Volume +5% |
| `↓` | Volume −5% |

---

## 🧠 Technical Design

**Pure Code Architecture** — Built entirely in Java without FXML, giving complete control over UI behavior.

**Layout Philosophy** — Uses `VBox` and `HBox` containers in a Flutter-inspired composition style.

**Visual Effects** — Background blur powered by JavaFX's `GaussianBlur` at 30px radius.

**Focus Management** — Sliders reject focus traversal to ensure seamless hotkey operation at all times.

**Media Handling** — JavaFX native `MediaPlayer` with automatic track switching via `setOnEndOfMedia()`.

**Persistence** — Java's `Preferences` API stores the last played track across sessions.

**Packaging** — Built with `jpackage` + `jlink`, bundling a trimmed JRE with only the required modules (`javafx.controls`, `javafx.fxml`, `javafx.media`, `java.logging`, `java.prefs`, `java.desktop`).

---

## 🔧 Building from Source

### Prerequisites

- Java 25 JDK
- JavaFX 25 SDK → [gluonhq.com/products/javafx](https://gluonhq.com/products/javafx/)
- JavaFX 25 jmods → same page, select **jmods** type
- Gradle

### Build

```bash
# From WSL or Linux
./gradlew build
```

### Package as Windows .exe

Run from Windows CMD after building:

```cmd
"C:\Program Files\Java\jdk-25.0.2\bin\jpackage.exe" ^
  --type app-image ^
  --name "SereneCandy" ^
  --input "path\to\build\libs" ^
  --main-jar SereneCandy-1.0.0.jar ^
  --module-path "C:\javafx-jmods-25.0.2" ^
  --add-modules javafx.controls,javafx.fxml,javafx.media,java.logging,java.prefs,java.desktop ^
  --java-options "--enable-native-access=javafx.graphics" ^
  --dest "path\to\output"
```

Then drop an empty `Playlist/` folder next to the generated `SereneCandy.exe`, zip the whole folder, and distribute.

---

## 🧩 Dependencies

| Library | Purpose | Version |
|---------|---------|---------|
| JavaFX | UI and media playback | 25.0.2 |
| Jaudiotagger | Metadata and album art extraction | 2.0.1 |
| JLayer | MP3 decoding support | 1.0.1.4 |

---

## 🪄 Roadmap

- [ ] Animated crossfade between tracks
- [ ] Custom color themes (light / dark mode)
- [ ] Lyrics display panel
- [ ] Mini player mode
- [ ] Configurable hotkeys
- [ ] Playlist management UI
- [ ] Equalizer with presets

---

## 📝 License

MIT — see [LICENSE](LICENSE) for details.

---

## 👤 Author

**Vishmay** — Coder, designer, and occasional night owl.

📀 Built on Windows, forged in WSL, powered by too much caffeine.

---

<p align="center">Made with ❤️ and ☕️</p>
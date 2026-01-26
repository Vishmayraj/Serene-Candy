# 🍬 Serene Candy

A visually rich, minimalist JavaFX music player.

## 🎧 Overview

Serene Candy is a sleek little desktop music player built with JavaFX, designed to look as good as it feels. It features a warm aesthetic, smooth transitions, and dynamic album art that fills the screen with a blurred glow — all wrapped in a minimal, non-resizable frame for pixel-perfect precision and instant focus.

![Serene Candy Demo](https://img.shields.io/badge/status-active-brightgreen) ![Java](https://img.shields.io/badge/Java-17%2B-orange) ![JavaFX](https://img.shields.io/badge/JavaFX-25-blue)

## ✨ Features

🎵 **Automatic Playlist Detection** — reads `.mp3` and `.flac` songs from a Playlist folder beside your JAR.

🪞 **Dynamic Album Art** — extracts and displays the embedded cover directly from your music files.

🎚️ **Progress & Volume Sliders** — clean, responsive, and immune to focus glitches (keyboard always works).

⌨️ **Keyboard Controls** — 
- **Space**: Play / Pause
- **Ctrl + →**: Next Track
- **Ctrl + ←**: Previous Track
- **→ / ←**: Seek ±5 seconds
- **↑ / ↓**: Volume ±5%

🔁 **Autoplay** — next song starts automatically when the current one ends.

💾 **Persistent Track Memory** — remembers your last played song using Java Preferences.

🩵 **Blurred Background Mode** — album art doubles as an ambient soft-focus backdrop.

⚡ **Lightweight & Modular** — built purely in code (no FXML!) with structured VBox/HBox layouts.

## 📁 Project Structure

```
SereneCandy/
├── build.gradle
├── settings.gradle
├── src/
│   └── main/
│       ├── java/
│       │   └── com/serenecandy/
│       │       ├── Main.java
│       │       └── models/
│       │           └── Track.java
│       └── resources/
│           └── com/serenecandy/assets/
│               ├── images/logo.jpg
│               └── css/style.css
└── Playlist/
    ├── 01 - Duvet.mp3
    ├── 02 - Pretty Scene Girl!.mp3
    └── ...
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- JavaFX 25 SDK
- Gradle (for building)

### Installation

1. **Clone or download the project**
   ```bash
   git clone https://github.com/yourusername/SereneCandy.git
   cd SereneCandy
   ```

2. **Build the JAR**
   ```bash
   ./gradlew build  # On Windows: gradlew.bat build
   ```

3. **Find the JAR**
   The compiled JAR will be available at:
   ```
   build/libs/SereneCandy-1.0.0.jar
   ```

### Running the Application

**On Windows:**
```cmd
java --module-path "C:\javafx-sdk-25.0.2\lib" ^
     --add-modules javafx.controls,javafx.fxml,javafx.media ^
     --enable-native-access=javafx.graphics ^
     -jar SereneCandy-1.0.0.jar
```

**On macOS/Linux:**
```bash
java --module-path "/path/to/javafx-sdk-25.0.2/lib" \
     --add-modules javafx.controls,javafx.fxml,javafx.media \
     --enable-native-access=javafx.graphics \
     -jar SereneCandy-1.0.0.jar
```

> **Note:** Adjust the JavaFX SDK path according to your installation.

### Adding Your Music

1. Create a folder named `Playlist` in the same directory as the JAR file.
2. Drop your `.mp3` or `.flac` files into the `Playlist` folder.
3. Launch Serene Candy — it will automatically detect and load your music!

## ⌨️ Keyboard Shortcuts

| Key / Combo | Action |
|-------------|--------|
| Space | Play / Pause |
| Ctrl + → | Next track |
| Ctrl + ← | Previous track |
| → / ← | Seek ±5 seconds |
| ↑ / ↓ | Adjust volume ±5% |

## 🧩 Dependencies

| Library | Purpose | Version |
|---------|---------|---------|
| JavaFX | UI and media playback | 25.0.2 |
| Jaudiotagger | Extract metadata and album covers | 2.0.1 |
| JLayer | Alternative MP3 decoding support | 1.0.1.4 |
| JUnit | (Optional) Testing framework | 5.10.0 |

The dependencies are managed through Gradle. See `build.gradle` for details.

## 🧠 Technical Design

**Pure Code Architecture** — Built entirely in Java without FXML, allowing for complete control over UI behavior.

**Layout Philosophy** — Uses `VBox` and `HBox` containers like a JavaFX take on Flutter's layout system, ensuring clean, responsive arrangements.

**Visual Effects** — Background blur powered by JavaFX's `GaussianBlur` effect with a 30px radius for that signature soft-focus look.

**Focus Management** — Sliders reject focus to prevent keyboard navigation issues, ensuring seamless hotkey operation.

**Media Handling** — Leverages JavaFX's native `MediaPlayer` with automatic track switching via `setOnEndOfMedia()`.

**Persistence** — Uses Java's `Preferences` API to remember playback position across sessions.

## 🔧 Development

### Building from Source

```bash
# Clone the repository
git clone https://github.com/yourusername/SereneCandy.git
cd SereneCandy

# Build with Gradle
./gradlew build

# Run tests
./gradlew test

# Create a distribution
./gradlew assembleDist
```

### IDE Setup

1. **IntelliJ IDEA**:
   - Import as Gradle project
   - Ensure JavaFX plugin is installed
   - Set SDK to Java 17+

2. **VS Code**:
   - Install "Extension Pack for Java" and "Gradle for Java"
   - Open the project folder
   - Set up JavaFX module path in `.vscode/settings.json`

## 🪄 Roadmap & Future Plans

- [ ] **Shuffle & repeat support**
- [ ] **Animated crossfade between tracks**
- [ ] **Custom color themes (light / dark mode)**
- [ ] **Lyrics display panel**
- [ ] **Mini player mode**
- [ ] **Configurable hotkeys**
- [ ] **Playlist management**
- [ ] **Equalizer with presets**
- [ ] **Cross-platform installers**

## ❤️ Inspiration & Philosophy

> "Good music doesn't just fill the room — it fills the frame."

Serene Candy started as a weekend project to combine code simplicity with visual warmth. Every pixel, shadow, and keypress is tuned for that soft "handmade" feeling — because sometimes, good UI *is* the music.

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Vishmay** — Coder, designer, and occasional night owl.

📀 Built on Windows, forged in WSL, and powered by too much caffeine.

## 🙏 Acknowledgments

- JavaFX team for the excellent media framework
- Jaudiotagger library for seamless metadata extraction
- The open-source community for inspiration and support

---

<p align="center">
  Made with ❤️ and ☕️
</p>

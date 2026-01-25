# 🍬 Serene Candy

*A visually rich, minimalist JavaFX music player.*

---

### 🎧 Overview

**Serene Candy** is a sleek little desktop music player built with **JavaFX**, designed to *look* as good as it *feels*.
It features a warm aesthetic, smooth transitions, and dynamic album art that fills the screen with a blurred background — all wrapped around a simple, non-resizable 640×480 frame for pixel-perfect precision.

---

### ✨ Features

* 🎵 **Automatic Playlist Detection** — reads songs from a `Playlist` folder placed next to your JAR.
* 🪞 **Dynamic Album Art** — extracts and displays the embedded cover from your music files.
* 🔊 **Real-Time Audio Controls** — play, pause, skip, and go back instantly.
* 🎚️ **Progress & Volume Sliders** — responsive and cleanly integrated.
* 💾 **Persistent Track Memory** — remembers where you left off using Java Preferences API.
* 🩵 **Blurred Background Mode** — album art doubles as a soft ambient background.
* ⚡ **Minimal, Modular Design** — structured with clean VBox/HBox layouting (no FXML!).

---

### 📂 Folder Structure

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

---

### 🚀 Running the App

1. **Build the JAR**

   ```
   gradlew build
   ```

   The final JAR will appear in:

   ```
   build/libs/SereneCandy-1.0.0.jar
   ```

2. **Run with JavaFX (Windows example)**

   ```bash
   java --module-path "C:\javafx-sdk-25.0.2\lib" ^
        --add-modules javafx.controls,javafx.fxml,javafx.media ^
        --enable-native-access=javafx.graphics ^
        -jar SereneCandy-1.0.0.jar
   ```

3. **Add your songs**
   Place `.mp3` or `.flac` files inside the `Playlist/` folder beside your JAR.

---

### 🧩 Dependencies

| Library                | Purpose                              |
| ---------------------- | ------------------------------------ |
| **JavaFX 25**          | UI and media playback                |
| **Jaudiotagger 2.0.1** | Extracting metadata and album covers |
| **JLayer 1.0.1.4**     | Alternative MP3 decoding support     |
| **JUnit 5**            | (Optional) testing framework         |

---

### 🧠 Design Notes

* The UI is built entirely in **code**, inspired by Flutter’s Column/Row paradigm.
* `VBox` and `HBox` are used for layout modularity.
* The window size is **fixed** intentionally for consistent visuals and performance.
* The background blur effect uses `GaussianBlur` from JavaFX’s built-in effects library.

---

### 🪄 Future Plans

* 🔁 Shuffle & repeat support
* 🎨 Animated transitions between songs
* 💿 Custom themes (light / dark)
* 🪶 Lyrics display & mini mode

---

### ❤️ Inspiration

> “Good music doesn’t just fill the room — it fills the frame.”

This project started as a personal experiment to merge **code elegance** and **visual calm**,
and it grew into a full-fledged music experience that *feels handmade*.

If you’ve made it this far, you probably get it —
Serene Candy isn’t just a player, it’s a *vibe*.

---

### 👤 Author

**Vishmay** — Coder, designer, and occasional night owl.
📀 Built on Windows, born in WSL, and raised with love.

---


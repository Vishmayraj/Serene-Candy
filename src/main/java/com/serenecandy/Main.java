package com.serenecandy;

import com.serenecandy.models.Track;

import java.io.File;
import java.io.InputStream;
import java.util.prefs.Preferences;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.effect.GaussianBlur;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // ───────────────────────────────
        //  🧠 INITIALIZATION & SETUP
        // ───────────────────────────────
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        final int[] currentIndex = { prefs.getInt("currentTrackIndex", 0) };

        LogManager.getLogManager().reset();
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);

        // Get project root
        String codePath = Main.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();

        File codeLocation = new File(codePath);
        File projectRoot = codeLocation;

        if (projectRoot.getPath().contains("build")) {
            projectRoot = projectRoot.getParentFile().getParentFile().getParentFile();
        } else {
            projectRoot = projectRoot.getParentFile();
        }

        String projectPath = projectRoot.getAbsolutePath();
        if (projectPath.startsWith("C:\\Ubuntu-22.04")) {
            projectPath = projectPath.replace("C:\\Ubuntu-22.04", "\\\\wsl.localhost\\Ubuntu-22.04");
            projectRoot = new File(projectPath);
        }

        // Playlist folder
        File playlistDir = new File(projectRoot, "Playlist");
        if (!playlistDir.exists()) playlistDir.mkdir();

        File[] songs = playlistDir.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".flac")
        );

        if (songs == null || songs.length == 0) {
            System.out.println("No songs found in Playlist!");
            return;
        }

        // ───────────────────────────────
        //  🎵 CURRENT TRACK & PLAYER
        // ───────────────────────────────
        Track currentTrack = Track.fromFile(songs[currentIndex[0]]);
        MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        Media media = new Media(songs[currentIndex[0]].toURI().toString());
        mediaPlayer[0] = new MediaPlayer(media);

        // ───────────────────────────────
        //  🎨 UI ELEMENTS
        // ───────────────────────────────
        Label titleLabel = new Label(currentTrack.getTitle());
        Label artistLabel = new Label(currentTrack.getArtist());
        Label albumLabel = new Label(currentTrack.getAlbum());

        Image albumImage = currentTrack.getAlbumCover();
        ImageView albumView = new ImageView(albumImage);
        albumView.setFitHeight(360);
        albumView.setFitWidth(640);
        albumView.setPreserveRatio(true);

        ImageView blurredBackground = new ImageView(albumImage);
        blurredBackground.setFitWidth(640);
        blurredBackground.setFitHeight(600);
        blurredBackground.setEffect(new GaussianBlur(30));

        // ───────────────────────────────
        //  💅 STYLING
        // ───────────────────────────────
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white; "
                + "-fx-effect: dropshadow(two-pass-box, black, 3, 0.8, 0, 0);");
        artistLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: semi-bold; -fx-text-fill: white; "
                + "-fx-effect: dropshadow(two-pass-box, black, 3, 0.8, 0, 0);");
        albumLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; "
                + "-fx-effect: dropshadow(two-pass-box, black, 3, 0.8, 0, 0);");

        String buttonStyle = "-fx-font-size: 28px; -fx-background-color: transparent; "
                + "-fx-text-fill: white; -fx-effect: dropshadow(two-pass-box, black, 3, 0.8, 0, 0);";

        // ───────────────────────────────
        //  🎚️ SLIDERS SECTION
        // ───────────────────────────────
        Slider progressSlider = new Slider(0, 100, 0);
        progressSlider.setPrefWidth(300);
        progressSlider.setMaxWidth(300);
        VBox.setVgrow(progressSlider, Priority.NEVER);

        Slider volumeSlider = new Slider(0, 1, 0.6);
        volumeSlider.setPrefWidth(150);
        volumeSlider.setMaxWidth(150);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                mediaPlayer[0].setVolume(newVal.doubleValue())
        );

        setupPlayerListeners(mediaPlayer[0], progressSlider);

        //Setting focus mode on
        progressSlider.setFocusTraversable(false);
        volumeSlider.setFocusTraversable(false);

        // prevent arrow key hijack
        volumeSlider.setOnKeyPressed(e -> e.consume());

        // prevent focus sticking after mouse click
        volumeSlider.setOnMousePressed(e -> {
            ((StackPane) volumeSlider.getScene().getRoot()).requestFocus();
        });
        volumeSlider.setOnMouseDragged(e -> {
            ((StackPane) volumeSlider.getScene().getRoot()).requestFocus();
        });

        // Seek support
        progressSlider.setOnMousePressed(e -> {
            ((StackPane) progressSlider.getScene().getRoot()).requestFocus();
            mediaPlayer[0].seek(mediaPlayer[0].getTotalDuration().multiply(progressSlider.getValue() / 100.0));
            e.consume();
        });
        progressSlider.setOnMouseDragged(e -> {
            ((StackPane) progressSlider.getScene().getRoot()).requestFocus();
            mediaPlayer[0].seek(mediaPlayer[0].getTotalDuration().multiply(progressSlider.getValue() / 100.0));
            e.consume();
        });

        VBox slidersBox = new VBox(8, progressSlider, volumeSlider);
        slidersBox.setAlignment(Pos.CENTER);
        slidersBox.setPadding(new Insets(5, 0, 5, 0));

        // ───────────────────────────────
        //  ⏯ CONTROLS SECTION
        // ───────────────────────────────
        Button playPauseBtn = new Button("▶");
        Button nextBtn = new Button("⏭");
        Button lastBtn = new Button("⏮");
        
        playPauseBtn.setFocusTraversable(false);
        nextBtn.setFocusTraversable(false);
        lastBtn.setFocusTraversable(false);

        playPauseBtn.setStyle(buttonStyle);
        nextBtn.setStyle(buttonStyle);
        lastBtn.setStyle(buttonStyle);

        HBox controlsRow = new HBox(20, lastBtn, playPauseBtn, nextBtn);
        controlsRow.setAlignment(Pos.CENTER);
        VBox.setMargin(controlsRow, new Insets(10, 0, 20, 0));

        // ───────────────────────────────
        //  🧾 INFO SECTION
        // ───────────────────────────────
        VBox infoBox = new VBox(5, titleLabel, artistLabel, albumLabel);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setPadding(new Insets(10, 0, 0, 0));

        // ───────────────────────────────
        //  🎛 EVENT HANDLERS
        // ───────────────────────────────
        nextBtn.setOnAction(e -> {
            currentIndex[0] = (currentIndex[0] + 1) % songs.length;
            prefs.putInt("currentTrackIndex", currentIndex[0]);

            mediaPlayer[0].stop();
            mediaPlayer[0].dispose();
            mediaPlayer[0] = new MediaPlayer(new Media(songs[currentIndex[0]].toURI().toString()));
            setupPlayerListeners(mediaPlayer[0], progressSlider);
            progressSlider.setValue(0);
            mediaPlayer[0].setOnEndOfMedia(() -> nextBtn.fire());

            Track nextTrack = Track.fromFile(songs[currentIndex[0]]);
            titleLabel.setText(nextTrack.getTitle());
            artistLabel.setText(nextTrack.getArtist());
            albumLabel.setText(nextTrack.getAlbum());
            albumView.setImage(nextTrack.getAlbumCover());
            blurredBackground.setImage(nextTrack.getAlbumCover());

            playPauseBtn.setText("⏸");
            mediaPlayer[0].play();
        });

        lastBtn.setOnAction(e -> {
            currentIndex[0] = (currentIndex[0] - 1 + songs.length) % songs.length;
            prefs.putInt("currentTrackIndex", currentIndex[0]);

            mediaPlayer[0].stop();
            mediaPlayer[0].dispose();
            mediaPlayer[0] = new MediaPlayer(new Media(songs[currentIndex[0]].toURI().toString()));
            setupPlayerListeners(mediaPlayer[0], progressSlider);
            mediaPlayer[0].setOnEndOfMedia(() -> nextBtn.fire());
            progressSlider.setValue(0);

            Track prevTrack = Track.fromFile(songs[currentIndex[0]]);
            titleLabel.setText(prevTrack.getTitle());
            artistLabel.setText(prevTrack.getArtist());
            albumLabel.setText(prevTrack.getAlbum());
            albumView.setImage(prevTrack.getAlbumCover());
            blurredBackground.setImage(prevTrack.getAlbumCover());

            playPauseBtn.setText("⏸");
            mediaPlayer[0].play();
        });

        playPauseBtn.setOnAction(e -> {
            MediaPlayer player = mediaPlayer[0];
            if (playPauseBtn.getText().equals("▶")) {
                playPauseBtn.setText("⏸");
                player.play();
            } else {
                playPauseBtn.setText("▶");
                player.pause();
            }
        });

        // ───────────────────────────────
        //  🧩 MAIN COLUMN
        // ───────────────────────────────
        VBox mainColumn = new VBox(12, albumView, infoBox, slidersBox, controlsRow);
        mainColumn.setAlignment(Pos.CENTER);
        mainColumn.setMaxHeight(580);
        mainColumn.setSpacing(6); // smaller global spacing
        VBox.setMargin(controlsRow, new Insets(5, 0, 10, 0)); // lighter padding


        StackPane root = new StackPane(blurredBackground, mainColumn);

        // ───────────────────────────────
        //  🎬 SCENE & STAGE
        // ───────────────────────────────
        Scene scene = new Scene(root, 640, 600, Color.TRANSPARENT);
        InputStream iconStream = Main.class.getResourceAsStream("/com/serenecandy/assets/images/logo.jpg");
        Image icon = new Image(iconStream);
        primaryStage.getIcons().add(icon);

        //
        // KEYBOARD HANDLER
        //

        scene.setOnKeyPressed(e -> {
            MediaPlayer player = mediaPlayer[0];
            javafx.util.Duration total = player.getTotalDuration();

            switch (e.getCode()) {
                case SPACE -> {
                    if (playPauseBtn.getText().equals("▶")) {
                        playPauseBtn.setText("⏸");
                        player.play();
                    } else {
                        playPauseBtn.setText("▶");
                        player.pause();
                    }
                }

                case RIGHT -> {
                    if (e.isControlDown()) {
                        // CTRL + → → Next track
                        nextBtn.fire();
                    } else {
                        // → alone → Seek +5s forward
                        if (total != null && !total.isUnknown()) {
                            javafx.util.Duration newTime = player.getCurrentTime().add(javafx.util.Duration.seconds(5));
                            if (newTime.greaterThan(total)) newTime = total;

                            player.seek(newTime); // ⬅ this moves the song's actual time

                            // now also move the slider visually
                            double progress = (newTime.toMillis() / total.toMillis()) * 100.0;
                            progressSlider.setValue(progress);
                        }
                    }
                }

                case LEFT -> {
                    if (e.isControlDown()) {
                        // CTRL + ← → Previous track
                        lastBtn.fire();
                    } else {
                        // ← alone → Seek −5s backward
                        javafx.util.Duration newTime = player.getCurrentTime().subtract(javafx.util.Duration.seconds(5));
                        if (newTime.lessThan(javafx.util.Duration.ZERO)) newTime = javafx.util.Duration.ZERO;

                        player.seek(newTime); // ⬅ move the song’s current time
                        if (total != null && !total.isUnknown() && total.greaterThan(javafx.util.Duration.ZERO)) {
                            double progress = (newTime.toMillis() / total.toMillis()) * 100.0;
                            progressSlider.setValue(progress);
                        }
                    }
                }

                case UP -> {
                    double newVol = Math.min(volumeSlider.getValue() + 0.05, 1.0);
                    volumeSlider.setValue(newVol);
                    player.setVolume(newVol);
                }

                case DOWN -> {
                    double newVol = Math.max(volumeSlider.getValue() - 0.05, 0.0);
                    volumeSlider.setValue(newVol);
                    player.setVolume(newVol);
                }

                default -> {}
            }

            e.consume();
        });

        root.setFocusTraversable(true);
        scene.setOnMouseClicked(e -> root.requestFocus());
        root.requestFocus();


        primaryStage.setTitle("Serene Candy");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setupPlayerListeners(MediaPlayer player, Slider progressSlider) {
        // clear any previous listeners (if any)
        player.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            double total = player.getTotalDuration().toMillis();
            if (total > 0) {
                double progress = (newTime.toMillis() / total) * 100;
                progressSlider.setValue(progress);
            }
        });

        // When song finishes, go to next
        player.setOnEndOfMedia(() -> {
            // simulate nextBtn click
            player.stop(); // stop current
            progressSlider.setValue(0);
            // nextBtn.fire();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

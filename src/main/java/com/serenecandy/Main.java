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
import javafx.scene.layout.Region;
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
        blurredBackground.setFitHeight(480);
        blurredBackground.setEffect(new GaussianBlur(30));

        // ───────────────────────────────
        //  💅 STYLING & SPACING
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
        //  ⏯ CONTROLS & PROGRESS BAR
        // ───────────────────────────────
        Button playPauseBtn = new Button("▶");
        Button nextBtn = new Button("⏭");
        Button lastBtn = new Button("⏮");

        playPauseBtn.setStyle(buttonStyle);
        nextBtn.setStyle(buttonStyle);
        lastBtn.setStyle(buttonStyle);

        Slider progressBar = new Slider(0, 100, 0);
        progressBar.setMaxWidth(300);
        VBox.setVgrow(progressBar, Priority.NEVER);
        mediaPlayer[0].currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            double progress = (newTime.toMillis() / mediaPlayer[0].getTotalDuration().toMillis()) * 100;
            progressBar.setValue(progress);
        });
        progressBar.setOnMousePressed(e ->
                mediaPlayer[0].seek(mediaPlayer[0].getTotalDuration().multiply(progressBar.getValue() / 100.0))
        );
        progressBar.setOnMouseDragged(e ->
                mediaPlayer[0].seek(mediaPlayer[0].getTotalDuration().multiply(progressBar.getValue() / 100.0))
        );

        // ───────────────────────────────
        //  🎛 EVENT HANDLERS
        // ───────────────────────────────
        nextBtn.setOnAction(e -> {
            currentIndex[0] = (currentIndex[0] + 1) % songs.length;
            prefs.putInt("currentTrackIndex", currentIndex[0]);

            mediaPlayer[0].stop();
            mediaPlayer[0].dispose();
            mediaPlayer[0] = new MediaPlayer(new Media(songs[currentIndex[0]].toURI().toString()));

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
        //  🧱 LAYOUT CONSTRUCTION
        // ───────────────────────────────
        HBox controlsRow = new HBox(20, lastBtn, playPauseBtn, nextBtn);
        controlsRow.setAlignment(Pos.CENTER);
        // VBox.setMargin(controlsRow, new Insets(10, 0, 20, 0));

        VBox infoSection = new VBox(4, titleLabel, artistLabel, albumLabel, progressBar, controlsRow);
        infoSection.setAlignment(Pos.CENTER);

        VBox mainColumn = new VBox(5, albumView, infoSection);
        mainColumn.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(blurredBackground, mainColumn);

        // ───────────────────────────────
        //  🎬 SCENE & STAGE
        // ───────────────────────────────
        Scene scene = new Scene(root, 640, 480, Color.web("#7aadff"));
        InputStream iconStream = Main.class.getResourceAsStream("/com/serenecandy/assets/images/logo.jpg");
        Image icon = new Image(iconStream);
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Serene Candy");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

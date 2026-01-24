@echo off
title SereneCandy
echo ===========================================
echo      🎵 Running SereneCandy Player
echo ===========================================

REM --- JavaFX SDK path ---
set "JAVAFX_PATH=C:\javafx-sdk-25.0.2\lib"

REM --- Check if Java executable exists ---
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Java not found! Please install JDK 17 or higher.
    pause
    exit /b
)

REM --- Check if JavaFX path exists ---
if not exist "%JAVAFX_PATH%" (
    echo ❌ JavaFX SDK not found at %JAVAFX_PATH%
    pause
    exit /b
)

REM --- Run the app ---
echo ✅ Launching SereneCandy...
java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml,javafx.media -jar "%~dp0build\libs\SereneCandy-1.0.0.jar"

echo ===========================================
echo 🎉 Application closed.
pause

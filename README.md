# Cow Chase Game 🐮

A fun Android game where you control a cow trying to collect coins while avoiding mischievous cowgirls!

## Features

- 🎮 Two control modes:
  - Button controls (tap left/right)
  - Tilt controls using device sensors
- 💰 Coin collection system for scoring
- 🤠 Dodge cowgirls to stay alive
- ❤️ Lives system with visual heart indicators
- 🏆 High score system with location tracking
- 🗺️ Google Maps integration to view score locations
- 🔊 Sound effects for coin collection and collisions
- 💫 Rich game feedback:
  - Vibration on collisions
  - Toast notifications for game events
- 🎚️ Two difficulty levels:
  - Easy mode: Slower gameplay
  - Hard mode: Faster gameplay with increased challenge

## Game Controls

### Button Mode
- Tap left button to move cow left
- Tap right button to move cow right

### Tilt Mode
- Tilt device left to move cow left
- Tilt device right to move cow right
- Tilt forward/backward to adjust game speed

## Gameplay Mechanics

- The cow starts with 3 lives (displayed as hearts)
- Collect coins to increase your score
- Avoid cowgirls - collision results in life loss
- Game ends when all lives are lost

### High Score System

The game features a sophisticated high score tracking system that combines a traditional scoreboard with location-based features:

#### Score List View
- Displays top 10 highest scores
- Shows score value, date, and location for each entry
- Scores are automatically sorted from highest to lowest
- Click on location to see it on the map

#### Interactive Map View
- Google Maps integration showing score locations
- Markers for each high score position
- Click markers to see score details
- Automatic zoom to selected score location
- Smooth camera animations when navigating

#### Location Features
- Each score is saved with precise GPS coordinates
- Locations are stored using latitude and longitude
- Uses Google Play Services for accurate positioning
- Interactive integration between list and map views

## Architecture

### Activities Overview

1. Entry Screen (EntryScreenActivity):
- Control method selection (buttons/sensors)
- Difficulty selection (Easy/Hard)
- Start Game and View Records buttons

2. Main Game (MainActivity):
- 8x5 game board
- Lives display (3 hearts)
- Score counter
- Cow movement and collision handling

3. Game Over (GameOverActivity):
- Game over message
- Auto-transitions to high scores after 2 seconds

4. High Score (HighScoreActivity):
- High score list (top section)
- Interactive map (bottom section)
- Return to main menu option

### Application Structure
The game follows a modular architecture with:
- Activity-based navigation
- Fragment-based views:
  - HighScoreFragment: Manages the score list display
  - MapFragment: Handles the Google Maps integration
  - Efficient communication between fragments using callbacks
- Manager classes for game logic
- Utility classes:
  - DataManager: Handles score persistence and retrieval
  - HighScoreAdapter: Custom RecyclerView adapter for score display
  - TiltDetector: Manages sensor input
- Interface-based collision system
- Builder pattern for high score construction

## Technical Features

### Core Components
- Application Class (App):
  - Initializes core systems
  - Manages global state
  - Sets up SharedPreferences
  - Configures signal management
- SignalManager (Singleton):
  - Manages device interactions
  - Handles vibration feedback:
    - Supports modern and legacy Android versions
    - Configurable vibration patterns
  - Controls toast notifications for game events
  - Thread-safe implementation
- Built with Kotlin
- Uses Android Sensor Manager for tilt controls
- Implements Google Maps API for location visualization
- Utilizes SharedPreferences for score persistence
- Features fragment-based architecture
- Implements sound management system
- Uses Material Design components
- Location-based high score tracking

## Requirements

- Android SDK 21 or higher
- Google Play Services (for Maps)
- Location permissions for high score tracking
- Sensor hardware for tilt controls

## Installation

1. Clone the repository
2. Open the project in Android Studio
3. Configure your Google Maps API key in the manifest
4. Build and run the application

## Permissions

The app requires the following permissions:
- `ACCESS_FINE_LOCATION`: For high score location tracking
- `VIBRATE`: For collision feedback
- Sensor access for tilt controls
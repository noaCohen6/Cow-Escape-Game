# 🐄 Cow Escape Game

## 📝 Description
A mobile game developed in Android Studio using Kotlin, where the player controls a cow that needs to escape from cowgirls. The game combines elements of agility and timing as the player moves the cow left and right to avoid colliding with cowgirls descending from above.

## 🎮 Game Features
- **Life System**: Player has three lives represented by hearts
- **Controls**: Left and right buttons for cow movement
- **Obstacles**: Cowgirls descending from the top of the screen
- **Vibration**: Device vibrates on collision
- **Notifications**: Toast messages appear during important game events

## 🛠️ Technical Architecture
The game consists of several main components:

### MainActivity
- Main game screen
- Manages cow and cowgirl movement
- Handles collisions
- Tracks game state

### GameManager
- Manages game logic
- Handles life system
- Triggers notifications and vibration alerts

### GameOverActivity
- Displays game over screen
- Shows appropriate end game message

### SignalManager
- Manages device interactions
- Handles vibration and toast messages
- Implemented using Singleton pattern

## 📱 System Requirements
- Android Studio Arctic Fox or higher
- Kotlin 1.5.0 or higher
- Android API Level 21 or higher

## 🚀 Installation and Running
1. Clone the repository
2. Open the project in Android Studio
3. Wait for Gradle sync to complete
4. Run the app on an emulator or physical device

## 🎯 How to Play
1. Use the left and right buttons to move the cow
2. Dodge the descending cowgirls
3. Try to survive as long as possible

## 📈 Scoring System
- Start with 3 lives
- Lose a life on each collision with a cowgirl
- Game ends when all lives are lost
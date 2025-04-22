# Clock App

A modern Android clock application with multiple features including world clock, stopwatch, and timer functionality. The app features a clean, iOS-inspired design with a dark theme.

## Features

### World Clock
- Display multiple time zones simultaneously
- Add and remove time zones
- Automatic time zone conversion
- Clean, easy-to-read interface

### Stopwatch
- Start, stop, and reset functionality
- Lap recording and display
- Millisecond precision
- Lap history with detailed timing
- iOS-style interface

### Timer
- Set custom countdown duration
- Start, pause, and reset controls
- Visual countdown display
- Clean, intuitive interface

## Technologies Used

- **Kotlin**: Primary programming language
- **Android Jetpack Components**:
  - ViewModel
  - LiveData
  - Navigation Component
  - RecyclerView
- **Material Design**: For UI components
- **Coroutines**: For asynchronous operations
- **Gradle**: Build system
- **Android Studio**: Development environment

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/clock/
│   │   │   ├── MainActivity.kt
│   │   │   ├── WorldClockFragment.kt
│   │   │   ├── StopwatchFragment.kt
│   │   │   ├── TimerFragment.kt
│   │   │   └── TimeZoneAdapter.kt
│   │   └── res/
│   │       ├── layout/
│   │       │   ├── activity_main.xml
│   │       │   ├── fragment_world_clock.xml
│   │       │   ├── fragment_stopwatch.xml
│   │       │   ├── fragment_timer.xml
│   │       │   └── item_lap.xml
│   │       └── values/
│   │           ├── colors.xml
│   │           └── themes.xml
```

## Getting Started

### Prerequisites
- Android Studio (latest version)
- Android SDK
- Kotlin plugin

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/Clock.git
   ```
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments
- Material Design for Android
- Android Jetpack Components
- Kotlin programming language 
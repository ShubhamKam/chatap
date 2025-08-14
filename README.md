# AI Chat Android App

An Android application that provides a chat interface for connecting to multiple cloud LLM models (Claude, Grok, OpenAI) and local models through Ollama. The app supports code generation, execution, error resolution, and file management within the chat interface.

## Features

- **Chat Interface**: User-friendly interface for interacting with AI models
- **LLM Integration**: Connect to cloud models (Claude, Grok, OpenAI) and local models via Ollama
- **Code Generation and Execution**: Generate and run Python code directly in the chat
- **Error Resolution**: Automatically detect and suggest fixes for code errors
- **Code File Management**: Save and retrieve Python scripts within the app
- **Jupyter-like UI Rendering**: Display code output and visualizations similar to Jupyter notebooks
- **APK Generation**: Automated build process to generate APK using GitHub Actions

## Setup Instructions

### Prerequisites

- Android Studio
- Android SDK (API level 21 or higher)
- Java Development Kit (JDK) 11
- API keys for cloud LLM services (OpenAI, Claude, Grok)

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd android-app
   ```

2. Open the project in Android Studio

3. Add your API keys in `MainActivity.kt`:
   ```kotlin
   val apiKey = "Bearer YOUR_ACTUAL_API_KEY_HERE"
   ```

4. Build and run the project

### Setting up Ollama (for local models)

1. Install Ollama on your system
2. Start the Ollama server
3. The app will connect to `http://10.0.2.2:11434/` (for Android emulator)

## Usage

### Chat with AI Models

1. Type your message in the chat input field
2. Press the Send button
3. The app will connect to the configured LLM and display the response

### Code Execution

1. Type your Python code in the chat with proper markdown formatting:
   ```
   ```python
   print("Hello, World!")
   ```
   ```
2. The app will execute the code and display the output
3. If there are errors, the app will suggest fixes

### File Management

- Code files are automatically saved in the app's internal storage
- Use the file management features to organize your Python scripts

## Building the APK

### Local Build

```bash
./gradlew assembleRelease
```

The APK will be generated in `app/build/outputs/apk/release/`

### Automated Build via GitHub Actions

1. Push your code to the main branch
2. GitHub Actions will automatically build the APK
3. Download the APK from the Actions tab in your repository

## Project Structure

```
app/src/main/java/com/ai/chat/
├── api/           # API services for cloud LLMs
├── ollama/        # Ollama integration for local models
├── code/          # Code execution services
├── storage/       # File management services
├── error/         # Error resolution services
├── ui/            # Jupyter-like rendering
└── MainActivity.kt
```

## Dependencies

- Retrofit: For API calls to cloud LLMs
- OkHttp: HTTP client
- AndroidX: Core Android libraries
- Kotlin: Programming language

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 
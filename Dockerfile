FROM ubuntu:20.04

# Set environment variables
ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH=${PATH}:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

# Install necessary packages
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk \
    wget \
    unzip \
    git \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Install Android SDK
RUN mkdir -p ${ANDROID_SDK_ROOT}
RUN wget -O sdk-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-6858069_latest.zip
RUN unzip sdk-tools.zip -d ${ANDROID_SDK_ROOT}
RUN mv ${ANDROID_SDK_ROOT}/cmdline-tools ${ANDROID_SDK_ROOT}/latest
RUN mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools
RUN mv ${ANDROID_SDK_ROOT}/latest ${ANDROID_SDK_ROOT}/cmdline-tools/latest
RUN yes | sdkmanager --licenses
RUN sdkmanager "platform-tools" "platforms;android-30" "build-tools;30.0.3"

# Set up working directory
WORKDIR /app

# Copy project files
COPY . .

# Command to run when container starts
CMD ["bash"] 
#!/bin/bash
# Android SDK configuration script for ÉTSMobile on Travis-CI
# Running on Ubuntu 16.04 (Xenial Xerus)
# Club App|ETS

# SDK Tools Revision 26.1.1
# For Updates check https://developer.android.com/studio#downloads
ANDROID_SDK_TOOLS=sdk-tools-linux-4333796.zip

# Compile and install OpenSSL
lsb_release -a
wget https://www.openssl.org/source/openssl-1.1.0g.tar.gz > /dev/null
tar xzvf openssl-1.1.0g.tar.gz > /dev/null
cd openssl-1.1.0g
./config
make > /dev/null
sudo make install > /dev/null
export LD_LIBRARY_PATH=/usr/local/lib:/usr/local/lib64
sudo ldconfig
pwd

# Install OpenJDK 8 for compiling the application
if ! dpkg -s openjdk-8-jdk >/dev/null 2>&1; then sudo apt-get update; sudo apt-get install openjdk-8-jdk; fi

# Download und unzip Android SDK Tools
wget https://dl.google.com/android/repository/$ANDROID_SDK_TOOLS
unzip -q $ANDROID_SDK_TOOLS -d $HOME/sdk
# Create empty cfg file to prevent sdkmanager warning message
mkdir -p $HOME/.android && touch $HOME/.android/repositories.cfg

# Install Android SDK components
yes | sdkmanager "platform-tools" > /dev/null
yes | sdkmanager "build-tools;29.0.0" > /dev/null
yes | sdkmanager "platforms;android-29" > /dev/null
yes | sdkmanager "extras;google;m2repository" > /dev/null
yes | sdkmanager "extras;android;m2repository" > /dev/null
yes | sdkmanager "system-images;android-21;default;x86_64" > /dev/null
yes | sdkmanager --channel=3 "emulator" > /dev/null

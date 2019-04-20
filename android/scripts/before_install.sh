#!/bin/bash

ANDROID_API_LEVEL=28
ANDROID_BUILD_TOOLS_VERSION=28.0.3
# SDK Tools Revision 26.1.1
ANDROID_SDK_TOOLS=sdk-tools-linux-4333796.zip
# For Updates check https://developer.android.com/studio#downloads

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
cd ../android
openssl version -v
openssl aes-256-cbc -k "$ENCRYPTED_ANDROID_GOOGLE_SERVICES_PW" -in ./encrypted/google-services.json.encrypted -out ./app/google-services.json -d
openssl aes-256-cbc -k "$ENCRYPTED_ANDROID_SECRETS_XML_PW" -in ./encrypted/secrets.xml.encrypted -out ./app/src/main/res/values/secrets.xml -d
openssl aes-256-cbc -k "$ENCRYPTED_ETSMOBILE_KEYSTORE_PW" -in ./encrypted/etsm_upload_ks.jks.encrypted -out ./etsm_upload_ks.jks -d
openssl aes-256-cbc -k "$ENCRYPTED_KEYSTORE_PROPERTIES_PW" -in ./encrypted/keystore.properties.encrypted -out ./keystore.properties -d
openssl aes-256-cbc -k "$ENCRYPTED_SERVICE_ACCOUNT_CREDENTIALS_BETA_PW" -in ./encrypted/service_account_credentials_beta.json.encrypted -out ./service_account_credentials_beta.json -d

if ! dpkg -s openjdk-8-jdk >/dev/null 2>&1; then sudo apt-get update; sudo apt-get install openjdk-8-jdk; fi

# download und unzip Android SDK Tools
wget https://dl.google.com/android/repository/$ANDROID_SDK_TOOLS
unzip -q $ANDROID_SDK_TOOLS -d $HOME/sdk
# create empty cfg file to prevent sdkmanager warning message
mkdir -p $HOME/.android && touch $HOME/.android/repositories.cfg

yes | sdkmanager "platform-tools" > /dev/null
yes | sdkmanager "build-tools;28.0.2" > /dev/null
yes | sdkmanager "platforms;android-28" > /dev/null
yes | sdkmanager "extras;google;m2repository" > /dev/null
yes | sdkmanager "extras;android;m2repository" > /dev/null
yes | sdkmanager "system-images;android-21;default;x86_64" > /dev/null
yes | sdkmanager --channel=3 "emulator" > /dev/null

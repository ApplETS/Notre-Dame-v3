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
openssl aes-256-cbc -k "$ENCRYPTED_ANDROID_BETA_GOOGLE_SERVICES_PW" -in ./encrypted/google-services.json.beta.encrypted -out ./app/google-sersvices.json -d
openssl aes-256-cbc -k "$ENCRYPTED_ANDROID_SECRETS_XML_PW" -in ./encrypted/secrets.xml.encrypted -out ./app/src/main/res/values/secrets.xml -d
openssl aes-256-cbc -k "$ENCRYPTED_ETSMOBILE_KEYSTORE_PW" -in ./encrypted/etsm_upload_ks.jks.encrypted -out ./etsm_upload_ks.jks -d
openssl aes-256-cbc -k "$ENCRYPTED_KEYSTORE_PROPERTIES_PW" -in ./encrypted/keystore.properties.encrypted -out ./keystore.properties -d
openssl aes-256-cbc -k "$ENCRYPTED_SERVICE_ACCOUNT_CREDENTIALS_BETA_PW" -in ./encrypted/service_account_credentials_beta.json.encrypted -out ./service_account_credentials_beta.json -d
yes | sdkmanager "platforms;android-28" > /dev/null
yes | sdkmanager "platforms;android-27" > /dev/null
echo no | android create avd --force -n test -t android-21 --abi armeabi-v7a
emulator -avd test -no-audio -no-window &
android-wait-for-emulator
adb shell input keyevent 82 &
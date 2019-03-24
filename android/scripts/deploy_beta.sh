cd android
pwd
openssl version -v
openssl aes-256-cbc -k "$ENCRYPTED_ANDROID_BETA_GOOGLE_SERVICES_PW" -in ./encrypted/google-services.json.beta.encrypted -out ./app/google-sersvices.json -d
openssl aes-256-cbc -k "$ENCRYPTED_ANDROID_SECRETS_XML_PW" -in ./encrypted/secrets.xml.encrypted -out ./app/src/main/res/values/secrets.xml -d
openssl aes-256-cbc -k "$ENCRYPTED_ETSMOBILE_KEYSTORE_PW" -in ./encrypted/etsm_upload_ks.jks.encrypted -out ./etsm_upload_ks.jks -d
openssl aes-256-cbc -k "$ENCRYPTED_KEYSTORE_PROPERTIES_PW" -in ./encrypted/keystore.properties.encrypted -out ./keystore.properties -d
openssl aes-256-cbc -k "$ENCRYPTED_SERVICE_ACCOUNT_CREDENTIALS_BETA_PW" -in ./encrypted/service_account_credentials_beta.json.encrypted -out ./service_account_credentials_beta.json -d
echo "Publishing Beta APK file"
./gradlew publishBetaReleaseApk
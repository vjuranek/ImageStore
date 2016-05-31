#!/bin/bash

echo "Generating server certificate"
keytool -genkeypair -keystore keystore_server.jks -alias img_server -dname "CN=localhost,O=ImgServer" -keyalg RSA -keysize 2048 -storepass secret
echo "Exporting server certificate"
keytool -exportcert -keystore keystore_server.jks -alias img_server -storepass secret > img_server.cert
echo "Importing server certificate into client truststore"
keytool -import -noprompt -alias img_server -file img_server.cert -keystore truststore_client.jks -storepass secret

echo "Generating client certificate"
keytool -genkeypair -keystore keystore_client.jks -alias img_client -dname "CN=localhost,O=ImgClient" -keyalg RSA -keysize 2048 -storepass secret
echo "Exporting client certificate"
keytool -exportcert -keystore keystore_client.jks -alias img_client -storepass secret > img_client.cert
echo "Importing client certificate into server truststore"
keytool -import -noprompt -alias img_client -file img_client.cert -keystore truststore_server.jks -storepass secret 

rm img_server.cert
rm img_client.cert

clear
echo "Content of generated stores:"
echo " =================="
echo "| Server keystore: |"
echo " =================="
keytool -list -keystore keystore_server.jks -storepass secret
echo "-------------------------------------------------------------------------------------------"
echo " ===================="
echo "| Server truststore: |"
echo " ===================="
keytool -list -keystore truststore_server.jks -storepass secret
echo "-------------------------------------------------------------------------------------------"
echo " =================="
echo "| Client keystore: |"
echo " =================="
keytool -list -keystore keystore_client.jks -storepass secret
echo "-------------------------------------------------------------------------------------------"
echo " ===================="
echo "| Client truststore: |"
echo " ===================="
keytool -list -keystore truststore_client.jks -storepass secret
echo "-------------------------------------------------------------------------------------------"

printf "\n\n\n"
echo "Moving stores into certs dir ..."
mkdir -p certs
mv keystore_server.jks certs/
mv truststore_server.jks certs/
mv keystore_client.jks certs/
mv truststore_client.jks certs/

echo "DONE!"

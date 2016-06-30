#!/bin/bash
keytool -exportcert -alias img_server -storepass secret -keystore ./certs/keystore_server.jks -rfc -file ./certs/server.pem

#include <QCoreApplication>
#include <iostream>

#include "include/imgserver/restclient.h"

int main(int argc, char *argv[])
{
    QCoreApplication::setOrganizationName(RestClient::ORG_NAME);
//    QCoreApplication::setOrganizationDomain("vjuranek.github.io");
    QCoreApplication::setApplicationName(RestClient::APP_NAME);
    QCoreApplication app(argc, argv);

    QSettings settings;
    settings.setValue(RestClient::KEY_REST_URL, "https://imgserver-vjuranek.rhcloud.com/imgserver/rest");
    //settings.setValue(RestClient::KEY_REST_URL, "http://localhost:8080/imgserver/rest");
    settings.setValue(RestClient::KEY_SERVER_CERT_PATH, "/tmp/rhcloud.pem");
    settings.setValue(RestClient::KEY_CLIENT_LOGIN, "testclient");
    settings.setValue(RestClient::KEY_CLIENT_PASSWORD, "testpassword");
    settings.setValue(RestClient::KEY_CLIENT_REALM, "rest");
    settings.setValue(RestClient::KEY_CLIENT_VERIFY_PEER, true);

    RestClient *client = new RestClient();
    QMap<QString, QString> images;
    images["test.png"] = "/tmp/test.png";
    images["ispn.png"] = "/tmp/ispn.png";
    client->uploadImages(images);

    return app.exec();
}

#include <QCoreApplication>
#include <iostream>

#include "include/imgserver/restclient.h"

int main(int argc, char *argv[])
{
//    QCoreApplication::setOrganizationName("vjuranek");
//    QCoreApplication::setOrganizationDomain("vjuranek.github.io");
//    QCoreApplication::setApplicationName("Img server");
    QCoreApplication app(argc, argv);

    std::cout << "start" << std::endl;

    QSettings settings;
    settings.setValue(RestClient::KEY_REST_URL, "https://imgserver-vjuranek.rhcloud.com/imgserver/rest");
    settings.setValue(RestClient::KEY_SERVER_CERT_PATH, "/tmp/rhcloud.pem");
    settings.setValue(RestClient::KEY_CLIENT_LOGIN, "testclient");
    settings.setValue(RestClient::KEY_CLIENT_PASSWORD, "testpassword");
    settings.setValue(RestClient::KEY_CLIENT_REALM, "rest");
    settings.setValue(RestClient::KEY_CLIENT_VERIFY_PEER, true);

    RestClient *client = new RestClient();
    client->uploadImage("test.png", "/tmp/test.png");

    std::cout << "end" << std::endl;

    return app.exec();
}

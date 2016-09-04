#ifndef RESTCLIENT_H
#define RESTCLIENT_H

#include <QtCore>
#include <QString>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include <QAuthenticator>
#include <QSettings>

class RestClient : public QObject
{
    Q_OBJECT

public:
    static const QString ORG_NAME;
    static const QString APP_NAME;

    static const QString KEY_REST_URL;
    static const QString KEY_SERVER_CERT_PATH;
    static const QString KEY_CLIENT_LOGIN;
    static const QString KEY_CLIENT_PASSWORD;
    static const QString KEY_CLIENT_REALM;
    static const QString KEY_CLIENT_VERIFY_PEER;

    RestClient();
    ~RestClient();

    /**
     * @brief RestClient::createClient Creates new client recoed on remote server (in the databse).
     * @param versionMajor Major version of the client.
     * @param versionMinor Minor version of the client.
     */
    void createClient(int versionMajor, int versionMinor);

    /**
     * @brief RestClient::uploadImage Uploads local image to the remote server
     * @param imageName Name of the image on the remote server
     * @param imagePath Local path to the image
     */
    void uploadImage(QString imageName, QString imagePath);

private:
    static const QString DEFAULT_REST_URL;

    QNetworkAccessManager *manager;
    QSettings *settings;
    QByteArray imageContent;
    QSslConfiguration sslConfig;

    /**
     * @brief RestClient::prepareImageUpload Sends initial POST resquest to the remote server, asking for image upload. Request conatins image name and it's SHA256 hash.
     * @param imageName Name of the image on the remote server
     * @param imagePath Local path to the image
     */
    void prepareImageUpload(QString imageName, QString imagePath);

    /**
     * @brief RestClient::parseLinkHeader Parses "Link" header
     * @param headers HTTP headers from the reply.
     * @return Value of the "Link" header.
     */
    QString parseLinkHeader(QList<QPair<QByteArray, QByteArray>> headers);

    /**
     * @brief RestClient::prepareSslConfig Configures parameters for SSL/TLS connection.
     * @return QSslConfiguration instance with appropriate setup.
     */
    QSslConfiguration prepareSslConfig();

public slots:
    /**
     * @brief RestClient::readReply Debug method to dump network reply to std out. Prints reply headers and reply body.
     * @param reply Reply to be dumped on std out.
     */
    void readReply(QNetworkReply *reply);

    /**
     * @brief RestClient::doImageUpload Callback method which does actual image upload via another POST request, containg image itself.
     * Before doing that, parses previous reply heads to find out upload link.
     * @param reply Reply from previous request. It has to contain "Link" header with URL where image should be uploaded.
     */
    void doImageUpload(QNetworkReply *reply);

    /**
     * @brief RestClient::setCredentials Sets client credential to authenticate on remote sever.
     * @param reply Reply request for authentication.
     * @param auth QAuthentication instance with set up credentials.
     */
    void setCredentials(QNetworkReply *reply, QAuthenticator *auth);

};

#endif // RESTCLIENT_H

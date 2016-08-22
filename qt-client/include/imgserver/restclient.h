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

    void createClient(int versionMajor, int versionMinor);
    void uploadImage(QString imageName, QString imagePath);

private:
    static const QString DEFAULT_REST_URL;

    QNetworkAccessManager *manager;
    QSettings *settings;
    QByteArray imageContent;
    QSslConfiguration sslConfig;

    void prepareImageUpload(QString imageName, QString imagePath);
    QString parseLinkHeader(QList<QPair<QByteArray, QByteArray>> headers);
    QSslConfiguration prepareSslConfig();

public slots:
    void readReply(QNetworkReply *reply);
    void doImageUpload(QNetworkReply *reply);
    void setCredentials(QNetworkReply *reply, QAuthenticator *auth);

};

#endif // RESTCLIENT_H

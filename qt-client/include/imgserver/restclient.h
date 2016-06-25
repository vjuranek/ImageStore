#ifndef RESTCLIENT_H
#define RESTCLIENT_H

#include <QtCore>
#include <QString>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include <QAuthenticator>


class RestClient : public QObject
{
    Q_OBJECT

public:
    RestClient();
    ~RestClient();

    // general REST methods
    void get(const QString &url);

    // methods specific to img server
    void createClient(int versionMajor, int versionMinor);
    void uploadImage(QString imageName, QString imagePath);

private:
    QNetworkAccessManager *manager;
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

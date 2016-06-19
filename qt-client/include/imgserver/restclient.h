#ifndef RESTCLIENT_H
#define RESTCLIENT_H

#include <QtCore>
#include <QString>
#include <QNetworkAccessManager>
#include <QNetworkReply>


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

    void prepareImageUpload(QString imageName, QString imagePath);
    QString parseLinkHeader(QList<QPair<QByteArray, QByteArray>> headers);


public slots:
    void readReply(QNetworkReply *reply);
    void doImageUpload(QNetworkReply *reply);

};

#endif // RESTCLIENT_H

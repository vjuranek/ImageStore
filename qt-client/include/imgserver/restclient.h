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
    /**
     *  Creates new client with specified version
     */
    void createClient(int versionMajor, int versionMinor);

private:
    QNetworkAccessManager *manager;
    QByteArray responseData;

public slots:
    void readReply(QNetworkReply *reply);

};

#endif // RESTCLIENT_H

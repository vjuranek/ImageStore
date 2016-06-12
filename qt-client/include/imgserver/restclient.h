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
    void get(const QString &url);

private:
    QNetworkAccessManager *manager;
    QByteArray responseData;

public slots:
    void readReply(QNetworkReply *reply);

};

#endif // RESTCLIENT_H

#include "include/imgserver/restclient.h"
#include <iostream>

RestClient::RestClient()
{
    this->manager = new QNetworkAccessManager(this);
    connect(this->manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(readReply(QNetworkReply*)));
}

RestClient::~RestClient()
{
    delete this->manager;
}

void RestClient::get(const QString &url)
{
     manager->get(QNetworkRequest(QUrl(url)));
}

void RestClient::readReply(QNetworkReply *reply)
{
    std::cout << "GOT REPLY" << std::endl;
    std::cout << reply->readAll().data() << std::endl;
    delete reply;
}

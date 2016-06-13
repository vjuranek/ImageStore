#include "include/imgserver/restclient.h"
#include <iostream>
#include <QByteArray>

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

    std::cout << "REPLY headers:" << std::endl;
    QList<QPair<QByteArray, QByteArray>> headers = reply->rawHeaderPairs();
    for (int i = 0; i < headers.size(); i++) {
        std::cout << headers.value(i).first.data() << "\t" << headers.value(i).second.data() << std::endl;
    }

    std::cout << "REPLY body:" << std::endl;
    std::cout << reply->readAll().data() << std::endl;

    delete reply;
}

void RestClient::createClient(int versionMajor, int versionMinor)
{
    QByteArray xmlReq;
    QXmlStreamWriter stream(&xmlReq);
    stream.writeStartDocument();
    stream.writeStartElement("clientVersion");
    stream.writeAttribute("major", std::to_string(versionMajor).c_str());
    stream.writeAttribute("minor", std::to_string(versionMinor).c_str());
    stream.writeEndElement();
    stream.writeEndDocument();

    QNetworkRequest req = QNetworkRequest(QUrl("http://localhost:8080/imgserver/rest/client"));
    req.setHeader(QNetworkRequest::KnownHeaders::ContentTypeHeader, "application/xml");
    manager->post(req, xmlReq);
}

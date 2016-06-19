#include "include/imgserver/restclient.h"
#include <iostream>
#include <QByteArray>
#include <QFile>

RestClient::RestClient()
{
    this->manager = new QNetworkAccessManager(this);
    //connect(this->manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(readReply(QNetworkReply*)));
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
    for (int i = 0; i < headers.size(); i++)
    {
        std::cout << headers.at(i).first.data() << "\t" << headers.at(i).second.data() << std::endl;
    }

    std::cout << "REPLY body:" << std::endl;
    std::cout << reply->readAll().data() << std::endl;
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

void RestClient::uploadImage(QString imageName, QString imagePath)
{
    this->prepareImageUpload(imageName, imagePath);
}

void RestClient::prepareImageUpload(QString imageName, QString imagePath)
{
    QFile file(imagePath);
    if (!file.open(QIODevice::ReadOnly)) return; //TODO throw error
    this->imageContent = file.readAll();
    QByteArray imageSha256 = QCryptographicHash::hash(this->imageContent, QCryptographicHash::Sha256);
    qDebug() << "SHA256: " << imageSha256.toHex();

    QByteArray xmlReq;
    QXmlStreamWriter stream(&xmlReq);
    stream.writeStartDocument();
    stream.writeStartElement("image");
    stream.writeAttribute("name", imageName);
    stream.writeAttribute("sha256", imageSha256.toHex());
    stream.writeEndElement();
    stream.writeEndDocument();

    connect(this->manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(doImageUpload(QNetworkReply*)));
    QNetworkRequest req = QNetworkRequest(QUrl("http://localhost:8080/imgserver/rest/image"));
    req.setHeader(QNetworkRequest::KnownHeaders::ContentTypeHeader, "application/xml");
    manager->post(req, xmlReq);
}


void RestClient::doImageUpload(QNetworkReply *reply)
{
    QString uploadLink = this->parseLinkHeader(reply->rawHeaderPairs());
    qDebug() << "upload link: " << uploadLink;

    QNetworkRequest req = QNetworkRequest(QUrl(uploadLink));
    req.setHeader(QNetworkRequest::KnownHeaders::ContentTypeHeader, "image/*;charset=UTF-8");
    manager->disconnect();
    manager->post(req, this->imageContent);
}

QString RestClient::parseLinkHeader(QList<QPair<QByteArray, QByteArray>> headers)
{
    QString uploadLink = nullptr;
    for (QList<QPair<QByteArray, QByteArray>>::iterator iter = headers.begin(); iter != headers.end(); ++iter)
    {
        if (QString::compare("Link", iter->first.data(), Qt::CaseInsensitive) == 0) {
            QString headerLink = iter->second.data();
            int linkBegin = headerLink.indexOf("<");
            int linkEnd = headerLink.indexOf(">");
            if (linkBegin >= 0 && linkEnd > 0)
            {
                uploadLink = headerLink.mid(linkBegin + 1, linkEnd - linkBegin - 1);
            }
            break;
        }
    }
    //TODO if uploadLink == null throw error
    return uploadLink;
}

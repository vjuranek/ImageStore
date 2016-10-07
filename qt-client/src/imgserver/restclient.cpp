#include "include/imgserver/restclient.h"
#include <iostream>
#include <QByteArray>
#include <QFile>

const QString RestClient::ORG_NAME = "Image Code";
const QString RestClient::APP_NAME = "RestClient";

const QString RestClient::KEY_REST_URL = "rest/url";
const QString RestClient::KEY_SERVER_CERT_PATH = "server/certificate";
const QString RestClient::KEY_CLIENT_LOGIN = "client/login";
const QString RestClient::KEY_CLIENT_PASSWORD = "client/password";
const QString RestClient::KEY_CLIENT_REALM = "client/realm";
const QString RestClient::KEY_CLIENT_VERIFY_PEER = "client/ssl/verify_peer";
const QString RestClient::DEFAULT_REST_URL = "http://localhost:8080/imgserver/rest";


RestClient::RestClient()
{
    manager = new QNetworkAccessManager(this);
    settings = new QSettings(RestClient::ORG_NAME, RestClient::APP_NAME);
    sslConfig = prepareSslConfig();
    connect(manager, SIGNAL(authenticationRequired(QNetworkReply*, QAuthenticator*)), this, SLOT(setCredentials(QNetworkReply*, QAuthenticator*)));
}

RestClient::~RestClient()
{
    delete manager;
    delete settings;
}

void RestClient::readReply(QNetworkReply *reply)
{
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

    QNetworkRequest req = QNetworkRequest(QUrl(settings->value(RestClient::KEY_REST_URL, RestClient::DEFAULT_REST_URL).toString() + "/client"));
    req.setHeader(QNetworkRequest::KnownHeaders::ContentTypeHeader, "application/xml");
    manager->post(req, xmlReq);
}

void RestClient::uploadImage(QString imageName, QString imagePath)
{
    prepareImageUpload(imageName, imagePath);
}

void RestClient::prepareImageUpload(QString imageName, QString imagePath)
{
    QFile file(imagePath);
    if (!file.open(QIODevice::ReadOnly)) return; //TODO throw error
    imageContent = file.readAll();
    QByteArray imageSha256 = QCryptographicHash::hash(imageContent, QCryptographicHash::Sha256);
    qDebug() << "SHA256: " << imageSha256.toHex();

    QByteArray xmlReq;
    QXmlStreamWriter stream(&xmlReq);
    stream.writeDefaultNamespace("urn:imgserver:server:image:0.1");
    stream.writeStartDocument();
    stream.writeStartElement("image");
    stream.writeTextElement("name", imageName);
    stream.writeTextElement("sha256", imageSha256.toHex());
    stream.writeEndElement();
    stream.writeEndDocument();

    connect(manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(doImageUpload(QNetworkReply*)));
    QNetworkRequest req = QNetworkRequest(QUrl(settings->value(RestClient::KEY_REST_URL, RestClient::DEFAULT_REST_URL).toString() + "/image"));
    req.setHeader(QNetworkRequest::KnownHeaders::ContentTypeHeader, "application/xml");
    req.setSslConfiguration(sslConfig);
    manager->post(req, xmlReq);
}

void RestClient::doImageUpload(QNetworkReply *reply)
{
    //qDebug() << "ERROR: " << reply->errorString();
    QString uploadLink = parseLinkHeader(reply->rawHeaderPairs());
    qDebug() << "upload link: " << uploadLink;

    QNetworkRequest req = QNetworkRequest(QUrl(uploadLink));
    req.setHeader(QNetworkRequest::KnownHeaders::ContentTypeHeader, "image/*;charset=UTF-8");
    manager->disconnect(); // disconnect form signal to avoid infinite loop - this method is slot for finished reply signal
    manager->post(req, imageContent);
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

QSslConfiguration RestClient::prepareSslConfig()
{
    QSslConfiguration sslConfig(QSslConfiguration::defaultConfiguration());
    sslConfig.setProtocol(QSsl::TlsV1_2OrLater);

    QFile certFile(settings->value(RestClient::KEY_SERVER_CERT_PATH).toString());
    certFile.open(QIODevice::ReadOnly);
    QList<QSslCertificate> caList = sslConfig.caCertificates();
    QSslCertificate serverCert(&certFile, QSsl::Pem);
    caList.append(serverCert);
    sslConfig.setCaCertificates(caList);

    sslConfig.setPeerVerifyMode(settings->value(RestClient::KEY_CLIENT_VERIFY_PEER).toBool() ? QSslSocket::VerifyPeer : QSslSocket::VerifyNone);
    return sslConfig;
}

void RestClient::setCredentials(QNetworkReply *reply, QAuthenticator *auth)
{
    auth->setUser(settings->value(RestClient::KEY_CLIENT_LOGIN).toString());
    auth->setPassword(settings->value(RestClient::KEY_CLIENT_PASSWORD).toString());
    auth->setRealm(settings->value(RestClient::KEY_CLIENT_REALM).toString());
}

package cz.jurankovi.imgserver.model.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "client")
@XmlAccessorType(XmlAccessType.FIELD)
public class Client {

    @XmlAttribute
    private ClientVersion clientVersion;

    public ClientVersion getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(ClientVersion clientVersion) {
        this.clientVersion = clientVersion;
    }

}

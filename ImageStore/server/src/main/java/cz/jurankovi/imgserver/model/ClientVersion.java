package cz.jurankovi.imgserver.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clientVersion")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientVersion {

    @XmlAttribute
    private short major;
    
    @XmlAttribute
    private short minor;

    public short getMajor() {
        return major;
    }

    public void setMajor(short major) {
        this.major = major;
    }

    public short getMinor() {
        return minor;
    }

    public void setMinor(short minor) {
        this.minor = minor;
    }
    
    @Override
    public String toString() {
        return String.format("%d.%d", major, minor);
    }

}

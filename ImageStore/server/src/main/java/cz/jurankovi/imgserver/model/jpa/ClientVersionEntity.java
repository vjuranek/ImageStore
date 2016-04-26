package cz.jurankovi.imgserver.model.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client_version")
public class ClientVersionEntity {

    @Id
    @GeneratedValue
    private int id;
    private short major;
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

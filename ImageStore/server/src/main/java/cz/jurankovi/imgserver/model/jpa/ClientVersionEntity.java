package cz.jurankovi.imgserver.model.jpa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "client_version")
@NamedQueries({
    @NamedQuery(name = ClientVersionEntity.GET_CLIENT_VERTION_QUERY, 
            query = "SELECT version FROM ClientVersionEntity AS version WHERE :major IS NOT NULL AND :minor IS NOT NULL AND version.major = :major AND version.minor = :minor")
})
public class ClientVersionEntity {

    public static final String GET_CLIENT_VERTION_QUERY = "getClientversionQuery";
    
    @Id
    @SequenceGenerator(name = "client_version_seq", sequenceName = "client_version_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_version_seq")
    private int id;
    private short major;
    private short minor;
    private Date released;

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

    public Date getReleased() {
        return released;
    }

    public void setReleased(Date released) {
        this.released = released;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + major;
        result = prime * result + minor;
        result = prime * result + ((released == null) ? 0 : released.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientVersionEntity other = (ClientVersionEntity) obj;
        if (major != other.getMajor())
            return false;
        if (minor != other.getMinor())
            return false;
        if (released == null) {
            if (other.getReleased() != null)
                return false;
        } else if (!released.equals(other.getReleased()))
            return false;
        return true;
    }
    
}

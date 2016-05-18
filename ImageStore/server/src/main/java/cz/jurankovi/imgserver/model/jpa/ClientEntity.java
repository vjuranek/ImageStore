package cz.jurankovi.imgserver.model.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class ClientEntity {

    @Id
    @SequenceGenerator(name = "client_seq", sequenceName = "client_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    private long id;

    @OneToOne
    @JoinColumn(name = "version_id")
    private ClientVersionEntity clientVersion;
    
    public ClientEntity() {
        
    }
    
    public ClientEntity(ClientVersionEntity clientVersion) {
        this.clientVersion = clientVersion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClientVersionEntity getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(ClientVersionEntity clientVersion) {
        this.clientVersion = clientVersion;
    }

}

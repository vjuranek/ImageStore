package cz.jurankovi.imgserver.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class ImageEntity {

    @Id
    @SequenceGenerator(name = "image_seq", sequenceName = "image_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq")
    private long id;
    @Column(name = "client_id")
    private int clientId;
    private String path;
    private String name;
    private String sha256;
    private Date created;
    private Date uploaded;
    @Column(name = "upload_finished")
    private boolean uploadFinished;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    public Date getUploaded() {
        return uploaded;
    }

    public void setUploaded(Date uploaded) {
        this.uploaded = uploaded;
    }

    public boolean isUploadFinished() {
        return uploadFinished;
    }

    public void setUploadFinished(boolean uploadFinished) {
        this.uploadFinished = uploadFinished;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + clientId;
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((uploaded == null) ? 0 : uploaded.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((sha256 == null) ? 0 : sha256.hashCode());
        result = prime * result + (uploadFinished ? 1231 : 1237);
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
        ImageEntity other = (ImageEntity) obj;
        if (clientId != other.getClientId())
            return false;
        if (created == null) {
            if (other.getCreated() != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (uploaded == null) {
            if (other.getUploaded() != null)
                return false;
        } else if (!uploaded.equals(other.uploaded))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (sha256 == null) {
            if (other.sha256 != null)
                return false;
        } else if (!sha256.equals(other.sha256))
            return false;
        if (uploadFinished != other.uploadFinished)
            return false;
        return true;
    }

}

package cz.jurankovi.imgserver.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {
    
    @Produces
    @PersistenceContext(unitName = "imgserver")
    private EntityManager em;
}
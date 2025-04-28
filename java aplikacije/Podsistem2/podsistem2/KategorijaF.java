package podsistem2;

import entiteti.Kategorija;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Tijana
 */
public class KategorijaF {
    
    public String kreiraj(EntityManager em, String naziv){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            Kategorija k = new Kategorija();
            k.setNaziv(naziv);
            
            em.persist(k);
            em.flush();
            res = "Uspesno kreirana kategorija";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String sveKategorije(EntityManager em){
        String res = "";
        try{
            em.getTransaction().begin();
            
            List<Kategorija> lista = em.createQuery("select k from Kategorija k", Kategorija.class).getResultList();
            
            for(Kategorija k: lista){
                res += "naziv: "+ k.getNaziv()+"\n";
            }
            
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
}

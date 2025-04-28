package podsistem2;

import entiteti.Korisnik;
import javax.persistence.EntityManager;

/**
 *
 * @author Tijana
 */
public class KorisnikF {
    public String kreiraj(EntityManager em, String ime, String email, int godiste, Character pol){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            Korisnik k = new Korisnik();
            k.setIme(ime);
            k.setEmail(email);
            k.setGodiste(godiste);
            k.setPol(pol);
            
            em.persist(k);
            em.flush();
            res = "Kreiran korisnik";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
}

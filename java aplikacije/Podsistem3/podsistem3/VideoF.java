package podsistem3;

import entiteti.Korisnik;
import entiteti.Video;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Tijana
 */
public class VideoF {
    public String kreiraj(EntityManager em, String naziv, int trajanje, String kor){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Korisnik> listK = em.createQuery("select k from Korisnik k where k.ime=:naziv", Korisnik.class).setParameter("naziv", kor).getResultList();
            if(listK==null || listK.isEmpty()) return "Ne postoji korisnik";
            Korisnik k = listK.get(0);
            
            Video v = new Video();
            v.setNaziv(naziv);
            v.setTrajanje(trajanje);
            v.setVlasnik(k);
            v.setDatumVreme(new Date());
            
            em.persist(v);
            em.flush();
            
            res = "Uspesno kreiran video";
            em.getTransaction().commit();
        }       
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String promeniNaziv(EntityManager em, String naziv, String novNaziv){
        String res = "Grrska";
        try{
            em.getTransaction().begin();
            
            Video v = em.createNamedQuery("Video.findByNaziv", Video.class).setParameter("naziv", naziv).getSingleResult();
            if(v==null) return "Ne postoji video";
            
            v.setNaziv(novNaziv);
            
            em.persist(v);
            em.flush();
            res = "Uspesno promenjen naziv";
            em.getTransaction().commit();
        }    
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String brisi(EntityManager em, String korisnik, String video){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            Korisnik k = em.createNamedQuery("Korisnik.findByIme", Korisnik.class).setParameter("ime", korisnik).getSingleResult();
            if(k==null) return "Ne postoji korisnik";
            
            int br = em.createQuery("delete from Video v where v.naziv=:videoN and v.vlasnik.idKor=:idK").setParameter("videoN", video).setParameter("idK", k.getIdKor()).executeUpdate();
            if(br==1) res = "Uspesno izbrisan video";
            else res = "Nije izbrisan video";
            em.getTransaction().commit();
        }    
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
}

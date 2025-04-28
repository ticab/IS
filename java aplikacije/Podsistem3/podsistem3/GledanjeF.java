package podsistem3;

import entiteti.Korisnik;
import entiteti.Gleda;
import entiteti.Video;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Tijana
 */
public class GledanjeF {
    public String kreiraj(EntityManager em, String kor, String video, int pocetni, int trajanje){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Korisnik> listK = em.createQuery("select k from Korisnik k where k.ime=:naziv", Korisnik.class).setParameter("naziv", kor).getResultList();
            if(listK==null || listK.isEmpty()) return "Ne postoji korisnik";
            Korisnik k = listK.get(0);
            
            List<Video> listV = em.createNamedQuery("Video.findByNaziv", Video.class).setParameter("naziv", video).getResultList();
            if(listV==null || listV.isEmpty()) return "Ne postoji video";
            Video v = listV.get(0);
            
            Gleda g = new Gleda();
            g.setDatumVreme(new Date());
            g.setIdVid(v);
            g.setKorisnik(k);
            g.setPocetniSekund(pocetni);
            g.setTrajanje(trajanje);
            
            em.persist(g);
            em.flush();
            res = "Uspesno kreirano gledanje";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String sva(EntityManager em, String video){
         String res = "";
        try{
            em.getTransaction().begin();
            
            List<Video> listV = em.createNamedQuery("Video.findByNaziv", Video.class).setParameter("naziv", video).getResultList();
            if(listV==null || listV.isEmpty()) return "Ne postoji video";
            Video v = listV.get(0);
            
            List<Gleda> lista = em.createQuery("select k from Gleda k where k.idVid.idVid=:id", Gleda.class).setParameter("id", v.getIdVid()).getResultList();
            
            for(Gleda k: lista){
                res += "korisnik: " + k.getKorisnik().getIme()+
                        ", video: " + k.getIdVid().getNaziv()+
                        ", datum: " + k.getDatumVreme().toString()+
                        ", pocetni sekund: " + k.getPocetniSekund()+
                        ", trajanje: " + k.getTrajanje()+
                        "\n";
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

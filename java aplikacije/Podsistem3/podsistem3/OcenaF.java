package podsistem3;

import entiteti.Gleda;
import entiteti.Korisnik;
import entiteti.Ocena;
import entiteti.OcenaPK;
import entiteti.Video;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Tijana
 */
public class OcenaF {
    public String kreiraj(EntityManager em, String kor, String video, int ocena){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Korisnik> listK = em.createQuery("select k from Korisnik k where k.ime=:naziv", Korisnik.class).setParameter("naziv", kor).getResultList();
            if(listK==null || listK.isEmpty()) return "Ne postoji korisnik";
            Korisnik k = listK.get(0);
            
            List<Video> listV = em.createNamedQuery("Video.findByNaziv", Video.class).setParameter("naziv", video).getResultList();
            if(listV==null || listV.isEmpty()) return "Ne postoji video";
            Video v = listV.get(0);
            
            OcenaPK pk = new OcenaPK(k.getIdKor(), v.getIdVid());
            
            Ocena o = new Ocena(pk);
            o.setDatumVreme(new Date());
            o.setOcena(ocena);
            
            
            em.persist(o);
            em.flush();
            res = "Uspesno napravljena ocena";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String promeni(EntityManager em, String kor, String video, int ocena){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Ocena> oList = em.createQuery("select o from Ocena o join Korisnik k on o.ocenaPK.korisnik=k.idKor join Video v on o.ocenaPK.video=v.idVid where k.ime=:kor and v.naziv=:vid", Ocena.class).setParameter("kor", kor).setParameter("vid", video).getResultList();
            if(oList==null || oList.isEmpty()) return "Ne postoji ocena";
            Ocena o = oList.get(0);
            
            o.setOcena(ocena);
            em.persist(o);
            em.flush();
            res = "Uspesno promenjeno";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String brisi(EntityManager em, String kor, String video){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Korisnik> korLista = em.createQuery("select k from Korisnik k where k.ime=:ime", Korisnik.class).setParameter("ime", kor).getResultList();
            if(kor==null || kor.isEmpty()) return "Ne postoji korisnik";
            Korisnik k = korLista.get(0);
            
            List<Video> vidLista = em.createQuery("select v from Video v where v.naziv=:ime", Video.class).setParameter("ime", video).getResultList();
            if(vidLista==null || vidLista.isEmpty()) return "Ne postoji korisnik";
            Video v = vidLista.get(0);
            
            
            em.createQuery("delete from Ocena o where o.ocenaPK.korisnik=:kor and o.ocenaPK.video=:vid").setParameter("kor", k.getIdKor()).setParameter("vid", v.getIdVid()).executeUpdate();
            
            res = "Uspesno obrisana ocena";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String sve(EntityManager em, String video){
        String res = "";
        try{
            em.getTransaction().begin();
            
            List<Ocena> lista = em.createQuery("select k from Ocena k join Video v on k.ocenaPK.video=v.idVid where k.ocenaPK.video=v.idVid and v.naziv=:naziv", Ocena.class).setParameter("naziv", video).getResultList();
            
            for(Ocena k: lista){
                String kor = em.createQuery("select k.ime from Korisnik k where k.idKor=:id", String.class).setParameter("id", k.getOcenaPK().getKorisnik()).getSingleResult();
                res += "ocena: " + k.getOcena()+
                        ", korisnik: "+kor+
                        ", datum: " + k.getDatumVreme().toString()+
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

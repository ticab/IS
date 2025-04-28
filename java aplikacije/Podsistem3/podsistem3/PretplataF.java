package podsistem3;

import entiteti.Korisnik;
import entiteti.Paket;
import entiteti.Pretplata;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Tijana
 */
public class PretplataF {
    public String kreiraj(EntityManager em, String korisnik, String paket){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Paket> listP = em.createQuery("select p from Paket p where p.naziv=:naziv", Paket.class).setParameter("naziv", paket).getResultList();
            if(listP==null || listP.isEmpty()) return "Ne postoji paket";
            Paket p = listP.get(0);
            
            List<Korisnik> listK = em.createQuery("select k from Korisnik k where k.ime=:naziv", Korisnik.class).setParameter("naziv", korisnik).getResultList();
            if(listK==null || listK.isEmpty()) return "Ne postoji korisnik";
            Korisnik k = listK.get(0);
            
            Pretplata pretplata = new Pretplata();
            pretplata.setDatumVreme(new Date());
            pretplata.setIdKor(k);
            pretplata.setIdPak(p);
            pretplata.setPlacenaCena(p.getMesecnaCena());
            
            em.persist(pretplata);
            em.flush();
            res = "Uspesno kreirana pretplata";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String sve(EntityManager em, String kor){
        String res = "";
        try{
            em.getTransaction().begin();
            
            List<Pretplata> lista = em.createQuery("select k from Pretplata k where k.idKor.ime=:ime", Pretplata.class).setParameter("ime", kor).getResultList();
            
            for(Pretplata k: lista){
                res += "korisnik: " + k.getIdKor().getIme()+
                        ", paket: " + k.getIdPak().getNaziv()+
                        ", datum: " + k.getDatumVreme().toString()+
                        ", placena cena: " + k.getPlacenaCena()+
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

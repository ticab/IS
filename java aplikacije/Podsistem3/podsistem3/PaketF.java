package podsistem3;

import entiteti.Korisnik;
import entiteti.Paket;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Tijana
 */
public class PaketF {
    public String kreiraj(EntityManager em, double cena, String naziv){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            Paket p = new Paket();
            p.setNaziv(naziv);
            p.setMesecnaCena(new BigDecimal(cena));
            
            em.persist(p);
            em.flush();
            res = "Uspesno kreiran paket";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String promenaCene(EntityManager em, double cena, String naziv){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Paket> listP = em.createQuery("select p from Paket p where p.naziv=:naziv", Paket.class).setParameter("naziv", naziv).getResultList();
            if(listP==null || listP.isEmpty()) return "Ne postoji paket";
            Paket p = listP.get(0);
            
            p.setMesecnaCena(new BigDecimal(cena));
            
            em.persist(p);
            em.flush();
            res = "Uspesno promenjena cena";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
    public String svi(EntityManager em){
         String res = "";
        try{
            em.getTransaction().begin();
            
            List<Paket> lista = em.createQuery("select k from Paket k", Paket.class).getResultList();
            
            for(Paket k: lista){
                res += "naziv: "+k.getNaziv()+
                        ", cena: "+k.getMesecnaCena()+
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

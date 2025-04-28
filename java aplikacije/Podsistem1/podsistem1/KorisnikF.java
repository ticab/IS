package podsistem1;

import entiteti.Korisnik;
import entiteti.Mesto;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Tijana
 */
public class KorisnikF {
    
    public String kreiraj(EntityManager em, String ime, String email, int godiste, Character pol, String mesto){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Mesto> listaM = em.createQuery("select m from Mesto m where m.naziv=:naziv", Mesto.class).setParameter("naziv", mesto).getResultList();
            if(listaM==null || listaM.isEmpty()) return "Ne postoji mesto";
            Mesto m = listaM.get(0);
            
            Korisnik k = new Korisnik();
            k.setIme(ime);
            k.setEmail(email);
            k.setGodiste(godiste);
            k.setPol(pol);
            k.setIdMes(m);
            
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
    
    public String promenaEmaila(EntityManager em, String ime, String email){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            List<Korisnik> listaK = em.createQuery("select k from Korisnik k where k.ime=:ime", Korisnik.class).setParameter("ime", ime).getResultList();
            if(listaK==null || listaK.isEmpty()) return "Ne postoji korisnik";
            
            Korisnik k = listaK.get(0);
            k.setEmail(email);
            
            em.persist(k);
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
    
    public String promenaMesta(EntityManager em, String imeK, String nazivM){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            List<Korisnik> lista1 = em.createNamedQuery("Korisnik.findByIme").setParameter("ime", imeK).getResultList();
            if(lista1 == null || lista1.isEmpty())
                return "Ne postoji korisnik";
            
            List<Mesto> lista2 = em.createQuery("select m from Mesto m where m.naziv=:naziv", Mesto.class).setParameter("naziv", nazivM).getResultList();
            if(lista2 == null || lista2.isEmpty())
                return "Ne postoji mesto";

            Korisnik k = lista1.get(0);
            Mesto m = lista2.get(0);
            k.setIdMes(m);
            
            em.persist(k);
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
    
    public String sviKorisnici(EntityManager em){
        String res = "";
        try{
            em.getTransaction().begin();
            
            List<Korisnik> lista = em.createQuery("select k from Korisnik k", Korisnik.class).getResultList();
            
            for(Korisnik k: lista){
                res += "ime: "+ k.getIme()+", email: "+k.getEmail()+
                        "godiste: "+k.getGodiste()+", pol: "+ k.getPol() 
                        + ", mesto:"+k.getIdMes().getNaziv()+"\n";
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
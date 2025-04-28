package podsistem1;

import entiteti.Mesto;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 
@author Tijana*/
public class MestoF {

    public String kreiraj(EntityManager em, String naziv){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            System.out.println("1");
            Mesto m = new Mesto();
            m.setNaziv(naziv);
            System.out.println("2");
            em.persist(m);
            System.out.println("3");
            em.flush();
            System.out.println("4");
            res = "Kreirano mesto";
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        System.out.println(res);
        return res;
    }

    public String svaMesta(EntityManager em){
        String res = "";
        try{
            em.getTransaction().begin();

            List<Mesto> lista = em.createQuery("select m from Mesto m", Mesto.class).getResultList();

            for(Mesto m: lista){
                res += "naziv: " + m.getNaziv()+"\n";
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
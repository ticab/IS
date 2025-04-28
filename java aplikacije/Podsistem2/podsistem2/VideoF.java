/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package podsistem2;

import entiteti.Kategorija;
import entiteti.Korisnik;
import entiteti.Video;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Tijana
 */
public class VideoF {
    public String kreiraj(EntityManager em, String naziv, int trajanje,String kor, String kat){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Korisnik> listK = em.createNamedQuery("Korisnik.findByIme", Korisnik.class).setParameter("ime", kor).getResultList();
            if(listK==null || listK.isEmpty()) return "Ne postoji korisnik";
            
            List<Kategorija> listKa = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", kat).getResultList();
            if(listKa==null || listKa.isEmpty()) return "Ne postoji kategorija";
            
            Korisnik k = listK.get(0);
            
            Video v = new Video();
            v.setNaziv(naziv);
            v.setTrajanje(trajanje);
            v.setVlasnik(k);
            v.setDatumVreme(new Date());
            v.setKategorijaList(listKa);
            
            Kategorija ka = listKa.get(0);
            List<Video> lV = ka.getVideoList();
            lV.add(v);
            ka.setVideoList(lV);
            
            em.persist(v);
            em.persist(k);
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
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Video> listV = em.createNamedQuery("Video.findByNaziv", Video.class).setParameter("naziv", naziv).getResultList();
            if(listV==null || listV.isEmpty()) return "Ne postoji video";
            Video v = listV.get(0);
            
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
    public String dodajKat(EntityManager em, String video, String kat){
        String res = "Greska";
        try{
            em.getTransaction().begin();
            
            List<Video> listV = em.createNamedQuery("Video.findByNaziv", Video.class).setParameter("naziv", video).getResultList();
            if(listV==null || listV.isEmpty()) return "Ne postoji video";
            Video v = listV.get(0);
            
            List<Kategorija> listK = em.createQuery("select k from Kategorija k where k.naziv=:naziv", Kategorija.class).setParameter("naziv", kat).getResultList();
            if(listK==null || listK.isEmpty()) return "Ne postoji kategorija";
            Kategorija k = listK.get(0);
            
            List<Kategorija> lista = v.getKategorijaList();
            if(!lista.contains(k)) lista.add(k);
            else return "Video vec pripada toj kategoriji";
            
            List<Video> lista2 = k.getVideoList();
            lista2.add(v);
            
            em.persist(v);
            em.persist(k);
            em.flush();
            res = "Uspesno dodata kategorija";
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
            
            List<Korisnik> listK = em.createNamedQuery("Korisnik.findByIme", Korisnik.class).setParameter("ime", korisnik).getResultList();
            if(listK==null || listK.isEmpty()) return "Ne postoji korisnik";
            Korisnik k = listK.get(0);
            
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
    public String sviVidei(EntityManager em){
        String res = "";
        try{
            em.getTransaction().begin();
            
            List<Video> lista = em.createQuery("select v from Video v", Video.class).getResultList();
            
            for(Video v: lista){
                res += "naziv: "+ v.getNaziv()+
                        ", trajanje: " + v.getTrajanje()+
                        ", vlasnik: " + v.getVlasnik().getIme()+
                        ", vreme: " + v.getDatumVreme().toString()+
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
    
    public String sveKat(EntityManager em, String video){
        String res = "";
        try{
            em.getTransaction().begin();
            
            List<Video> listV = em.createNamedQuery("Video.findByNaziv", Video.class).setParameter("naziv", video).getResultList();
            if(listV==null || listV.isEmpty()) return "Ne postoji video";
            Video v = listV.get(0);
            
            for(Kategorija k: v.getKategorijaList()){
                res += k.getNaziv() + ", ";
            }
            if(res.length()>2) res = res.substring(0, res.length()-2);
            res += "\n";
            
            em.getTransaction().commit();
        }
        finally{
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
        return res;
    }
}

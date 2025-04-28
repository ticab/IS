package podsistem3;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Tijana
 */
public class Main {

    @Resource(lookup = "myConnFactory")
    private static ConnectionFactory cf;
    
    @Resource(lookup = "queue3") //podsistem3
    private static Queue queue3;
    
    @Resource(lookup = "myQueue") //server
    private static Queue queue;
    
    public static void main(String[] args) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue3);
        JMSProducer producer = context.createProducer();
        System.out.println("Pokrenut podsistem3");
        
        while(true){
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
                EntityManager em = emf.createEntityManager();
                
                Message msg = consumer.receive();
                TextMessage txt = (TextMessage) msg;
                String zahtev;
                zahtev = txt.getText();
                
                switch (zahtev) {
                    case "Kreiranje paketa":{ //9
                        double cena = txt.getDoubleProperty("cena");
                        String naziv = txt.getStringProperty("naziv");
                        String res = (new PaketF()).kreiraj(em, cena, naziv);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Promena cene":{ //10
                        double cena = txt.getDoubleProperty("cena");
                        String naziv = txt.getStringProperty("naziv");
                        String res = (new PaketF()).promenaCene(em, cena, naziv);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        break;
                    }
                    case "Kreiranje pretplate":{ //11
                        String korisnik = txt.getStringProperty("kor");
                        String paket = txt.getStringProperty("paket");
                        
                        String res = (new PretplataF()).kreiraj(em, korisnik, paket);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Kreiranje gledanja":{ //12
                        String korisnik = txt.getStringProperty("kor");
                        String video = txt.getStringProperty("video");
                        int pocetni = txt.getIntProperty("pocetni");
                        int trajanje = txt.getIntProperty("trajanje");
                        
                        String res = (new GledanjeF()).kreiraj(em, korisnik, video, pocetni, trajanje);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Kreiranje ocene":{ //13
                        String korisnik = txt.getStringProperty("kor");
                        String video = txt.getStringProperty("video");
                        int ocena = txt.getIntProperty("ocena");
                        
                        String res = (new OcenaF()).kreiraj(em, korisnik, video, ocena);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Promena ocene":{ //14
                        String korisnik = txt.getStringProperty("kor");
                        String video = txt.getStringProperty("video");
                        int ocena = txt.getIntProperty("ocena");
                        
                        String res = (new OcenaF()).promeni(em, korisnik, video, ocena);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Brisanje ocene":{ //15
                        String korisnik = txt.getStringProperty("kor");
                        String video = txt.getStringProperty("video");
                        
                        String res = (new OcenaF()).brisi(em, korisnik, video);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dohv paketa":{ //22
                        String res = (new PaketF()).svi(em);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dohv pretplata":{ //23
                        String kor = txt.getStringProperty("kor");
                        String res = (new PretplataF()).sve(em, kor);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dohv gledanja":{ //24
                        String video = txt.getStringProperty("video");
                        String res = (new GledanjeF()).sva(em, video);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dohv ocene":{ //25
                        String video = txt.getStringProperty("video");
                        String res = (new OcenaF()).sve(em, video);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Kreiranje korisnika":{ //2
                        String ime = txt.getStringProperty("ime");
                        String email = txt.getStringProperty("email");
                        int godiste = txt.getIntProperty("godiste");
                        Character pol = txt.getStringProperty("pol").charAt(0);
                        String res = (new KorisnikF()).kreiraj(em, ime, email, godiste, pol);
                        System.out.println(res);
                        break;
                    }
                    case "Kreiranje videa":{ //6
                        String naziv = txt.getStringProperty("naziv");
                        int trajanje = txt.getIntProperty("trajanje");
                        String korisnik = txt.getStringProperty("kor");
                        String res = (new VideoF()).kreiraj(em, naziv, trajanje, korisnik);
                        System.out.println(res);
                        break;
                    }
                    case "Promena videa":{ //7
                        String naziv = txt.getStringProperty("naziv");
                        String novNaziv = txt.getStringProperty("novNaziv");
                        String res = (new VideoF()).promeniNaziv(em, naziv, novNaziv);
                        System.out.println(res);
                        break;
                    }
                    case "Brisanje videa":{ //16
                        String kor = txt.getStringProperty("kor");
                        String video = txt.getStringProperty("video");
                        String res = (new VideoF()).brisi(em, kor, video);
                        System.out.println(res);
                        break;
                    }
                }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
}

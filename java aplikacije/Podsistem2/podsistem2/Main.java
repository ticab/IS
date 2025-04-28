package podsistem2;

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
    
    @Resource(lookup = "queue2") //podsistem2
    private static Queue queue2; 
    
    @Resource(lookup = "myQueue") //server
    private static Queue queue;
    
    public static void main(String[] args) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue2);
        JMSProducer producer = context.createProducer();
        
        System.out.println("Pokrenut podsistem2");
        
        while(true){
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                EntityManager em = emf.createEntityManager();
                
                Message msg = consumer.receive();
                TextMessage txt = (TextMessage) msg;
                String zahtev;
                zahtev = txt.getText();
                
                switch (zahtev) {
                    case "Kreiranje kategorije":{ //5
                        String naziv = txt.getStringProperty("naziv");
                        String res = (new KategorijaF()).kreiraj(em, naziv);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Kreiranje videa":{ //6
                        String naziv = txt.getStringProperty("naziv");
                        int trajanje = txt.getIntProperty("trajanje");
                        String korisnik = txt.getStringProperty("kor");
                        String kategorija = txt.getStringProperty("kat");
                        String res = (new VideoF()).kreiraj(em, naziv, trajanje, korisnik, kategorija);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Promena videa":{ //7
                        String naziv = txt.getStringProperty("naziv");
                        String novNaziv = txt.getStringProperty("novNaziv");
                        String res = (new VideoF()).promeniNaziv(em, naziv, novNaziv);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dodavanje kategorije":{ //8
                        String video = txt.getStringProperty("video");
                        String kat = txt.getStringProperty("kat");
                        String res = (new VideoF()).dodajKat(em, video, kat);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Brisanje videa":{ //16
                        String kor = txt.getStringProperty("kor");
                        String video = txt.getStringProperty("video");
                        String res = (new VideoF()).brisi(em, kor, video);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dohv kat":{ //19
                        String res = (new KategorijaF()).sveKategorije(em);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dohv videa":{ //20
                        String res = (new VideoF()).sviVidei(em);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dohv videoKat":{ //21
                        String video = txt.getStringProperty("video");
                        String res = (new VideoF()).sveKat(em, video);
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
                }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
}

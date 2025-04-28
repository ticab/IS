package podsistem1;

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

public class Main {
    
    @Resource(lookup = "myConnFactory")
    private static ConnectionFactory cf;
    
    @Resource(lookup = "queue1") //podsistem1
    private static Queue queue1; 
    
    @Resource(lookup = "myQueue") //server
    private static Queue queue;
    
    public static void main(String[] args) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue1);
        JMSProducer producer = context.createProducer();
        
        System.out.println("Pokrenut podsistem1");
        
        while(true){
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                EntityManager em = emf.createEntityManager();
                
                Message msg = consumer.receive();
                TextMessage txt = (TextMessage) msg;
                String zahtev;
                zahtev = txt.getText();
                
                switch (zahtev) {
                    case "Kreiranje grada":{ //1
                        String naziv = txt.getStringProperty("naziv");
                        String res = (new MestoF()).kreiraj(em, naziv);
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
                        String mesto = txt.getStringProperty("mesto");
                        String res = (new KorisnikF()).kreiraj(em, ime, email, godiste, pol, mesto);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Promena emaila":{ //3
                        String ime = txt.getStringProperty("ime");
                        String email = txt.getStringProperty("email");
                        String res = (new KorisnikF()).promenaEmaila(em, ime, email);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Promena mesta":{ //4
                        String imeK = txt.getStringProperty("korisnik");
                        String nazivM = txt.getStringProperty("mesto");
                        System.out.println(imeK +" "+nazivM);
                        String res = (new KorisnikF()).promenaMesta(em, imeK, nazivM);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dohv mesta":{ //17
                        System.out.println("Dohvatanje svih mesta");
                        String res = (new MestoF()).svaMesta(em);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
                        System.out.println(res);
                        break;
                    }
                    case "Dohv kor":{ //18
                        String res = (new KorisnikF()).sviKorisnici(em);
                        TextMessage resp = context.createTextMessage(res);
                        producer.send(queue, resp);
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
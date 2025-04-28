package com.mycompany.server.resources;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Tijana
 */
@Path("call")
public class Server {
    
    @Resource(lookup = "myConnFactory")
    ConnectionFactory cf;
    
    @Resource(lookup = "myQueue") //server
    Queue queue;
    
    @Resource(lookup = "queue1") //ps1
    Queue queue1;
    
    @Resource(lookup = "queue2") //ps2
    Queue queue2;
    
    @Resource(lookup = "queue3") //ps3
    Queue queue3;
    
    @POST
    @Path("kreiranjeGrada/{naziv}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response kreiranjeGrada(@PathParam("naziv") String naziv){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Kreiranje grada");
        try {
            msg.setStringProperty("naziv", naziv);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue1, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("kreiranjeKorisnika/{ime}/{email}/{godiste}/{pol}/{mesto}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response kreiranjeKorisnika(@PathParam("ime") String ime, 
            @PathParam("email") String email, @PathParam("godiste") int godiste,
            @PathParam("pol") String pol, @PathParam("mesto") String mesto){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Kreiranje korisnika");
        try {
            msg.setStringProperty("ime", ime);
            msg.setStringProperty("email", email);
            msg.setStringProperty("mesto", mesto);
            msg.setStringProperty("pol", pol);
            msg.setIntProperty("godiste", godiste);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue1, msg);
        producer.send(queue2, msg);
        producer.send(queue3, msg);

        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("promenaEmaila/{ime}/{email}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response promenaEmaila(@PathParam("ime") String ime, @PathParam("email") String email){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Promena emaila");
        try {
            msg.setStringProperty("ime", ime);
            msg.setStringProperty("email", email);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue1, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("promenaMesta/{korisnik}/{mesto}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response promenaMesta(@PathParam("korisnik") String korisnik, @PathParam("mesto") String mesto){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Promena mesta");
        try {
            msg.setStringProperty("korisnik", korisnik);
            msg.setStringProperty("mesto", mesto);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue1, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @GET
    @Path("dohvMesta")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dohvMesta(){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dohv mesta");
        
        producer.send(queue1, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @GET
    @Path("dohvKor")
    public Response dohvKor(){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dohv kor");
        
        producer.send(queue1, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("kreiranjeKategorije/{naziv}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response kreiranjeKategorije(@PathParam("naziv") String naziv){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Kreiranje kategorije");
        try {
            msg.setStringProperty("naziv", naziv);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue2, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("kreiranjeVidea/{naziv}/{trajanje}/{kor}/{kat}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response kreiranjeVidea(@PathParam("naziv") String naziv,
            @PathParam("trajanje") int trajanje, @PathParam("kor") String kor,
            @PathParam("kat") String kat){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Kreiranje videa");
        try {
            msg.setStringProperty("naziv", naziv);
            msg.setStringProperty("kor", kor);
            msg.setIntProperty("trajanje", trajanje);
            msg.setStringProperty("kat", kat);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue2, msg);
        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);
        
        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("promenaVidea/{naziv}/{novNaziv}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response promenaVidea(@PathParam("naziv") String naziv, @PathParam("novNaziv") String novNaziv){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Promena videa");
        try {
            msg.setStringProperty("naziv", naziv);
            msg.setStringProperty("novNaziv", novNaziv);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue2, msg);
        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("dodavanjeKategorije/{video}/{kat}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dodavanjeKategorije(@PathParam("video") String video, @PathParam("kat") String kat){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dodavanje kategorije");
        try {
            msg.setStringProperty("video", video);
            msg.setStringProperty("kat", kat);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue2, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @DELETE
    @Path("brisanjeVidea/{kor}/{video}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response brisanjeVidea(@PathParam("video") String video, @PathParam("kor") String kor){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Brisanje videa");
        try {
            msg.setStringProperty("video", video);
            msg.setStringProperty("kor", kor);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue2, msg);
        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);
        
        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @GET
    @Path("dohvKat")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dohvKat(){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dohv kat");
        
        producer.send(queue2, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @GET
    @Path("dohvVidea")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dohvVidea(){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dohv videa");
        
        producer.send(queue2, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @GET
    @Path("dohvVideoKat/{video}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dohvVideoKat(@PathParam("video") String video){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dohv videoKat");
        try {
            msg.setStringProperty("video", video);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        producer.send(queue2, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("kreiranjePaketa/{cena}/{naziv}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response kreiranjePaketa(@PathParam("naziv") String naziv, @PathParam("cena") double cena){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Kreiranje paketa");
        try {
            msg.setStringProperty("naziv", naziv);
            msg.setDoubleProperty("cena", cena);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("promenaCene/{cena}/{naziv}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response promenaCene(@PathParam("naziv") String naziv, @PathParam("cena") double cena){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Promena cene");
        try {
            msg.setStringProperty("naziv", naziv);
            msg.setDoubleProperty("cena", cena);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("kreiranjePretplate/{kor}/{paket}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response kreiranjePretplate(@PathParam("kor") String kor, @PathParam("paket") String paket){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Kreiranje pretplate");
        try {
            msg.setStringProperty("kor", kor);
            msg.setStringProperty("paket", paket);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("kreiranjeGledanja/{kor}/{video}/{pocetni}/{trajanje}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response kreiranjeGledanja(@PathParam("kor") String kor, @PathParam("video") String video,
            @PathParam("pocetni") int pocetni, @PathParam("trajanje") int trajanje){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Kreiranje gledanja");
        try {
            msg.setStringProperty("kor", kor);
            msg.setStringProperty("video", video);
            msg.setIntProperty("pocetni", pocetni);
            msg.setIntProperty("trajanje", trajanje);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("kreiranjeOcene/{kor}/{video}/{ocena}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response kreiranjeOcene(@PathParam("kor") String kor, @PathParam("video") String video,
            @PathParam("ocena") int ocena){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Kreiranje ocene");
        try {
            msg.setStringProperty("kor", kor);
            msg.setStringProperty("video", video);
            msg.setIntProperty("ocena", ocena);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @POST
    @Path("promenaOcene/{kor}/{video}/{ocena}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response promenaOcene(@PathParam("kor") String kor, @PathParam("video") String video,
            @PathParam("ocena") int ocena){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Promena ocene");
        try {
            msg.setStringProperty("kor", kor);
            msg.setStringProperty("video", video);
            msg.setIntProperty("ocena", ocena);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @DELETE
    @Path("brisanjeOcene/{kor}/{video}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response brisanjeOcene(@PathParam("kor") String kor, @PathParam("video") String video){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Brisanje ocene");
        try {
            msg.setStringProperty("kor", kor);
            msg.setStringProperty("video", video);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @GET
    @Path("dohvPaketa")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dohvPaketa(){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dohv paketa");
        
        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @GET
    @Path("dohvPretplata/{kor}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dohvPretplata(@PathParam("kor") String kor){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dohv pretplata");
        try {
            msg.setStringProperty("kor", kor);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @GET
    @Path("dohvGledanja/{video}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dohvGledanja(@PathParam("video") String video){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dohv gledanja");
        try {
            msg.setStringProperty("video", video);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
    
    @GET
    @Path("dohvOcene/{video}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dohvOcene(@PathParam("video") String video){
        
        String res = "";
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSProducer producer = context.createProducer();

        while(consumer.receiveNoWait()!=null) {}

        TextMessage msg = context.createTextMessage("Dohv ocene");
        try {
            msg.setStringProperty("video", video);
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        producer.send(queue3, msg);
        
        TextMessage msgResponse = (TextMessage) consumer.receive(5000);

        try {
            res = msgResponse.getText();
        } catch (JMSException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Response.Status.ACCEPTED).entity(res).build();
    }
}
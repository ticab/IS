package com.mycompany.klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author Tijana
 */
public class Klijent {

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://localhost:8080/Server/resources/call/").build();
        
        while(true){
            System.out.println("__________________");
            String[][] opcije = {
                {"1: Kreiraj grad", "6: Kreiranje videa", "11: Kreiraj pretplatu", "16: Brisi video", "21: Dohvati kateg za video"},
                {"2: Kreiraj korisnika", "7: Promeni naziva videa", "12: Kreiraj gledanje", "17: Dohvati sva mesta", "22: Dohvati sve pakete"},
                {"3: Promeni email-a", "8: Dodaj kategoriju videu", "13: Kreiraj ocenu", "18: Dohvati sve korisnike", "23: Dohvati sve pretplate"},
                {"4: Promeni mesta kor", "9: Kreiraj paket", "14: Promeni ocenu", "19: Dohvati sve kategorije", "24: Dohvati sva gledanja"},
                {"5: Kreiraj kategoriju", "10: Promena mesecne cene", "15: Brisi ocenu", "20: Dohvati sve videe", "25: Dohvati sve ocene"}
            };
            String headerFormat = "%-30s %-30s %-30s %-30s %-30s%n";
            for(String[] red: opcije){
                System.out.format(headerFormat, red[0], red[1], red[2], red[3], red[4]);
            }
            System.out.println("__________________");
            System.out.print("Izaberi opciju: ");
            try {
                int opcija = Integer.parseInt(in.readLine());
                switch (opcija) {
                    case 1: {
                        System.out.print("Ime mesta: ");
                        String ime = in.readLine();
                        String res = retrofit.create(Pozivi.class).kreiranjeGrada(ime).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 2: {
                        System.out.print("Ime korisnika: ");
                        String ime = in.readLine();
                        System.out.print("Email: ");
                        String email = in.readLine();
                        System.out.print("Pol: ");
                        String pol = in.readLine();
                        System.out.print("Mesto: ");
                        String mesto = in.readLine();
                        System.out.print("Godiste: ");
                        int godiste = Integer.parseInt(in.readLine());
                        String res = retrofit.create(Pozivi.class).kreiranjeKorisnika(ime, email, godiste, pol, mesto).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 3: {
                        System.out.print("Ime korisnika: ");
                        String ime = in.readLine();
                        System.out.print("Novi email: ");
                        String email = in.readLine();
                        String res = retrofit.create(Pozivi.class).promenaEmaila(ime, email).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 4: {
                        System.out.print("Ime korisnika: ");
                        String korisnik = in.readLine();
                        System.out.print("Ime mesta: ");
                        String mesto = in.readLine();
                        String res = retrofit.create(Pozivi.class).promenaMesta(korisnik, mesto).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 5: {
                        System.out.print("Naziv kategorije: ");
                        String naziv = in.readLine();
                        String res = retrofit.create(Pozivi.class).kreiranjeKategorije(naziv).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 6: {
                        System.out.print("Naziv videa: ");
                        String naziv = in.readLine();
                        System.out.print("Trajanje: ");
                        int trajanje = Integer.parseInt(in.readLine());
                        System.out.print("Korisnik: ");
                        String kor = in.readLine();
                        System.out.print("Kategorija: ");
                        String kat = in.readLine();
                        String res = retrofit.create(Pozivi.class).kreiranjeVidea(naziv, trajanje, kor, kat).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 7: {
                        System.out.print("Trenutni naziv videa: ");
                        String naziv = in.readLine();
                        System.out.print("Nov naziv: ");
                        String nov = in.readLine();
                        String res = retrofit.create(Pozivi.class).promenaVidea(naziv, nov).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 8: {
                        System.out.print("Video: ");
                        String video = in.readLine();
                        System.out.print("Kategorija: ");
                        String kat = in.readLine();
                        String res = retrofit.create(Pozivi.class).dodavanjeKategorije(video, kat).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 9: {
                        System.out.print("Naziv paketa: ");
                        String naziv = in.readLine();
                        System.out.print("Cena: ");
                        double cena = Double.parseDouble(in.readLine());
                        String res = retrofit.create(Pozivi.class).kreiranjePaketa(naziv, cena).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 10: {
                        System.out.print("Paket: ");
                        String naziv = in.readLine();
                        System.out.print("Cena: ");
                        double cena = Double.parseDouble(in.readLine());
                        String res = retrofit.create(Pozivi.class).promenaCene(naziv, cena).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 11: {
                        System.out.print("Korisnik: ");
                        String kor = in.readLine();
                        System.out.print("Paket: ");
                        String paket = in.readLine();
                        String res = retrofit.create(Pozivi.class).kreiranjePretplate(kor, paket).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 12: {
                        System.out.print("Korisnik: ");
                        String kor = in.readLine();
                        System.out.print("Video: ");
                        String video = in.readLine();
                        System.out.print("Sekund video snimka od kojeg je započeto gledanje: ");
                        int pocetni = Integer.parseInt(in.readLine());
                        System.out.print("Koliko sekundi video snimka je odgledano: ");
                        int trajanje = Integer.parseInt(in.readLine());
                        String res = retrofit.create(Pozivi.class).kreiranjeGledanja(kor, video, pocetni, trajanje).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 13: {
                        System.out.print("Korisnik: ");
                        String kor = in.readLine();
                        System.out.print("Video: ");
                        String video = in.readLine();
                        System.out.print("Ocena: ");
                        int ocena = Integer.parseInt(in.readLine());
                        String res = retrofit.create(Pozivi.class).kreiranjeOcene(kor, video, ocena).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 14: {
                        System.out.print("Korisnik: ");
                        String kor = in.readLine();
                        System.out.print("Video: ");
                        String video = in.readLine();
                        System.out.print("Nova ocena: ");
                        int ocena = Integer.parseInt(in.readLine());
                        String res = retrofit.create(Pozivi.class).promenaOcene(kor, video, ocena).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 15: {
                        System.out.print("Korisnik: ");
                        String kor = in.readLine();
                        System.out.print("Video: ");
                        String video = in.readLine();
                        String res = retrofit.create(Pozivi.class).brisanjeOcene(kor, video).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 16: {
                        System.out.print("Video: ");
                        String video = in.readLine(); 
                        System.out.print("Korisnik: ");
                        String kor = in.readLine();
                        String res = retrofit.create(Pozivi.class).brisanjeVidea(video, kor).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 17: {
                        String res = retrofit.create(Pozivi.class).dohvMesta().execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 18: {
                        String res = retrofit.create(Pozivi.class).dohvKor().execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 19: {
                        String res = retrofit.create(Pozivi.class).dohvKat().execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 20: {
                        String res = retrofit.create(Pozivi.class).dohvVidea().execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 21: {
                        System.out.print("Video: ");
                        String video = in.readLine();
                        String res = retrofit.create(Pozivi.class).dohvVideoKat(video).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 22: {
                        String res = retrofit.create(Pozivi.class).dohvPaketa().execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 23: {
                        System.out.print("Korisnik: ");
                        String kor = in.readLine();
                        String res = retrofit.create(Pozivi.class).dohvPretplata(kor).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 24: {
                        System.out.print("Video: ");
                        String video = in.readLine();
                        String res = retrofit.create(Pozivi.class).dohvGledanja(video).execute().body();
                        System.out.println(res);
                        break;
                    }
                    case 25: {
                        System.out.print("Video: ");
                        String video = in.readLine();
                        String res = retrofit.create(Pozivi.class).dohvOcene(video).execute().body();
                        System.out.println(res);
                        break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
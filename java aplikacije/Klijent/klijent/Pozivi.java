package com.mycompany.klijent;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *
 * @author Tijana
 */
public interface Pozivi {

    @POST("kreiranjeGrada/{naziv}") Call<String> kreiranjeGrada(@Path("naziv") String naziv);
    @POST("kreiranjeKorisnika/{ime}/{email}/{godiste}/{pol}/{mesto}") Call<String> kreiranjeKorisnika(
            @Path("ime") String ime, @Path("email") String email, @Path("godiste") int godiste,
            @Path("pol") String pol, @Path("mesto") String mesto);
    @POST("promenaEmaila/{ime}/{email}") Call<String> promenaEmaila(@Path("ime") String ime, @Path("email") String email);
    @POST("promenaMesta/{korisnik}/{mesto}") Call<String> promenaMesta(@Path("korisnik") String korisnik, @Path("mesto") String mesto);
    
    @POST("kreiranjeKategorije/{naziv}") Call<String> kreiranjeKategorije(@Path("naziv") String naziv);
    @POST("kreiranjeVidea/{naziv}/{trajanje}/{kor}/{kat}") Call<String> kreiranjeVidea(@Path("naziv") String naziv,
            @Path("trajanje") int trajanje, @Path("kor") String kor,
            @Path("kat") String kat);
    @POST("promenaVidea/{naziv}/{novNaziv}") Call<String> promenaVidea(@Path("naziv") String naziv, @Path("novNaziv") String novNaziv);
    @POST("dodavanjeKategorije/{video}/{kat}") Call<String> dodavanjeKategorije(@Path("video") String video, @Path("kat") String kat);
    @POST("kreiranjePaketa/{cena}/{naziv}") Call<String> kreiranjePaketa(@Path("naziv") String naziv, @Path("cena") double cena);
    @POST("promenaCene/{cena}/{naziv}") Call<String> promenaCene(@Path("naziv") String naziv, @Path("cena") double cena);
    
    @POST("kreiranjePretplate/{kor}/{paket}") Call<String> kreiranjePretplate(@Path("kor") String kor, @Path("paket") String paket);
    @POST("kreiranjeGledanja/{kor}/{video}/{pocetni}/{trajanje}") Call<String> kreiranjeGledanja(@Path("kor") String kor, @Path("video") String video,
            @Path("pocetni") int pocetni, @Path("trajanje") int trajanje);
    @POST("kreiranjeOcene/{kor}/{video}/{ocena}") Call<String> kreiranjeOcene(@Path("kor") String kor, @Path("video") String video,
            @Path("ocena") int ocena);
    @POST("promenaOcene/{kor}/{video}/{ocena}") Call<String> promenaOcene(@Path("kor") String kor, @Path("video") String video,
            @Path("ocena") int ocena);
    @DELETE("brisanjeOcene/{kor}/{video}") Call<String> brisanjeOcene(@Path("kor") String kor, @Path("video") String video);
    
    @DELETE("brisanjeVidea/{kor}/{video}") Call<String> brisanjeVidea(@Path("video") String video, @Path("kor") String kor);
    @GET("dohvMesta") Call<String> dohvMesta();
    @GET("dohvKor") Call<String> dohvKor();
    @GET("dohvKat") Call<String> dohvKat();
    @GET("dohvVidea") Call<String> dohvVidea();
    
    @GET("dohvVideoKat/{video}") Call<String> dohvVideoKat(@Path("video") String video);
    @GET("dohvPaketa") Call<String> dohvPaketa();
    @GET("dohvPretplata/{kor}") Call<String> dohvPretplata(@Path("kor") String kor);
    @GET("dohvGledanja/{video}") Call<String> dohvGledanja(@Path("video") String video);
    @GET("dohvOcene/{video}") Call<String> dohvOcene(@Path("video") String video);
}
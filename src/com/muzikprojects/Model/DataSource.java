package com.muzikprojects.Model;

import com.muzikprojects.Sarkici;

import java.sql.*;
import java.util.ArrayList;

public class DataSource {
    public static final String DB_NAME ="muzik.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:"+DB_NAME;

    //ALBUM TABLOSU
    public static final String TABLO_ALBUM = "album";
    public static final String SUTUN_ALBUM_ID = "albumID";
    public static final String SUTUN_ALBUM_ADI = "albumAdi";
    public static final String SUTUN_ALBUM_SARKICI_ID = "sarkiciID";

    //ŞARKICI TABLOSU
    public static final String TABLO_SARKICI = "sarkici";
    public static final String SUTUN_SARKICI_ID = "sarkiciID";
    public static final String SUTUN_SARKICI_ADI ="sarkiciAdi";

    // ŞARKI TABLOSU
    public static final String TABLO_SARKI = "sarki";
    public static final String SUTUN_SARKI_ID = "sarkiID";
    public static final String SUTUN_SARKI_ADI = "sarkiAdi";
    public static final String SUTUN_SARKI_ALBUMID = "albumID";

    //SIRALAMASI
    public static final int ARTAN_SIRADA = 1;
    public static final int AZALAN_SIRADA = 2;



    // DB
    private Connection baglanti;

    // DB BAĞLANTI AÇ
    public boolean veriTabaniBaglantiAc() {
        try {
            baglanti = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Bağlantı Gerçekleşti....\n");
            return true;

        } catch (SQLException e) {
            System.out.println("DB AÇILMADA Hata : "+e);
            return false;
        }
    }

    // DB BAĞLANTI OFF
    public void veriTabaniBaglantisiniKapat() {

        try {
            if (baglanti != null) {
                baglanti.close();
                System.out.println("\n\nBağlantı başarıyla kapatıldı...");
            }

        } catch (SQLException e) {
            System.out.println("DB kapanmada hata : " + e);
        }


    }


    // TÜM ŞARKICILARI GÖSTER
    public ArrayList<Sarkici> tumSarkicilariGoster(int siralama) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLO_SARKICI);

        if(siralama == ARTAN_SIRADA){
            sb.append(" ORDER BY ");
            sb.append(SUTUN_SARKICI_ADI);
            sb.append(" ASC");

        } else {
            sb.append(" ORDER BY ");
            sb.append(SUTUN_SARKICI_ADI);
            sb.append(" DESC");
        }


        try(
            Statement statement = baglanti.createStatement();
            ResultSet sonuc = statement.executeQuery(sb.toString()); ){

            ArrayList<Sarkici> tumSarkicilar = new ArrayList<>();

            while(sonuc.next()){
                Sarkici sarkici = new Sarkici();
                sarkici.setSarkiciId(sonuc.getInt(SUTUN_SARKICI_ID));
                sarkici.setSarkiciAdi(sonuc.getString(SUTUN_SARKICI_ADI));
                tumSarkicilar.add(sarkici);

            }

            return tumSarkicilar;

        }  catch (SQLException e) {
            System.out.println("Hata : "+e);
            return null;
        }
    }







}

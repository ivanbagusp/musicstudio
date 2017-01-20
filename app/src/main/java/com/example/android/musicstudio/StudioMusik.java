package com.example.android.musicstudio;

/**
 * Created by ASUS on 19/06/2016.
 */
public class StudioMusik {

    private long id;
    private String nama;
    private String alamat;
    private String harga;
    private String gambar;
    private String jam,call,alatmusik,lastupdate,ratingalat,ratingrecording,ratingtempat,latitude,longitude;
    private String fav;
   // private Double latitude,longitude;

    public StudioMusik() {
    }

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getNama(){return nama;}
    public void setNama(String nama){this.nama = nama;}

    public String getAlamat(){return alamat;}
    public void setAlamat(String alamat){this.alamat = alamat;}

    public String getHarga(){return harga;}
    public void setHarga(String harga){this.harga = harga;}

    public String getGambar(){return gambar;}
    public void setGambar(String gambar){this.gambar = gambar;}

    //
    public String getCall(){return call;}
    public void setCall(String call){this.call = call;}

    public String getJam(){return jam;}
    public void setJam(String jam){this.jam = jam;}

    public String getAlatmusik(){return alatmusik;}
    public void setAlatmusik(String alatmusik){this.alatmusik = alatmusik;}

    public String getLastupdate(){return lastupdate;}
    public void setLastupdate(String lastupdate){this.lastupdate = lastupdate;}

    public String getRatingalat(){return ratingalat;}
    public void setRatingalat(String ratingalat){this.ratingalat = ratingalat;}

    public String getRatingrecording(){return ratingrecording;}
    public void setRatingrecording(String ratingrecording){this.ratingrecording = ratingrecording;}

    public String getRatingtempat(){return ratingtempat;}
    public void setRatingtempat(String ratingtempat){this.ratingtempat = ratingtempat;}

    public String getLatitude(){return latitude;}
    public void setLatitude(String latitude){this.latitude = latitude;}

    public String getLongitude(){return longitude;}
    public void setLongitude(String longitude){this.longitude = longitude;}

    public String getFav(){return fav;}
    public void setFav(String fav){this.fav = fav;}

//    @Override
//    public String toString()
//    {
//        return "Alat Musik "+ nama +" "+ alamat + " "+ harga + " " + gambar+ " " + fav;
//    }
}

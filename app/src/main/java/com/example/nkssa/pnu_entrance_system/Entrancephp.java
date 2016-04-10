package com.example.nkssa.pnu_entrance_system;

/**
 * Created by nkssa on 3/27/2016.
 */
public class Entrancephp extends visitorchanges {
    String URL = "http://192.168.1.108/ES/entrancelog.php";
    String url = "";
    String response = "";

    public String tampilBiodata() {
        try {
            url = URL + "?operation=view";
            System.out.println("URL Tampil visitorphp: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String updateBiodata(String Id, String reason, String plate) {
        try {

            reason = reason.replace(" ", "%20");
            plate = plate.replace(" ", "%20");

            url = URL + "?operation=report&Id=" + Id + "&reason=" + reason + "&plate=" + plate;
            System.out.println("URL Insert Entrancephp : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBiodataById(int id) {
        try {
            url = URL + "?operation=get_biodata_by_id&id=" + id;
            System.out.println("URL Insert Entrancephp : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }


}

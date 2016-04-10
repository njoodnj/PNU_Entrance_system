package com.example.nkssa.pnu_entrance_system;

/**
 * Created by bashayer93 on 3/27/2016.
 */
public class visitorphp extends visitorchanges {
    String URL = "http://192.168.100.4/ES/visitor.php";
    String url = "";
    String response = "";

    public String tampilBiodata(String rid) {
        try {
            url = URL + "?operation=view&rid=" + rid;
            System.out.println("URL Tampil visitorphp: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String inserBiodata(String name, String refnum , String number) {
        try {

            name = name.replace(" ", "%20");
            refnum = refnum.replace(" ", "%20");
            number = number.replace(" ", "%20");

            url = URL + "?operation=insert&name =" + name + "&Refnum=" + refnum + "&Number=" + number;
            System.out.println("URL Insert visitorphp : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBiodataById(int rid) {
        try {
            url = URL + "?operation=get_biodata_by_id&rid=" + rid;
            System.out.println("URL Insert visitorphp : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String updateBiodata(String rid, String name, String refnum, String number) {
        try {

            name = name.replace(" ", "%20");
            refnum = refnum.replace(" ", "%20");
            number = number.replace(" ", "%20");

            url = URL + "?operation=update&rid=" + rid + "&name=" + name + "&Refnum=" + refnum + "&Number=" + number;
            System.out.println("URL Insert visitorphp : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String deleteBiodata(int refnum) {
        try {
            url = URL + "?operation=delete&Refnum=" + refnum ;
            System.out.println("URL Insert visitorphp : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }
}
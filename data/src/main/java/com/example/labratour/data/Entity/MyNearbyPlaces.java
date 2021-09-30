package com.example.labratour.data.Entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyNearbyPlaces implements Serializable {



    public MyNearbyPlaces(NearbyPlaceResult[] results) {
        this.results = results;
    }

    public NearbyPlaceResult[] getResults() {
        return results;
    }

    //    private String next_page_token;
    @SerializedName("results")
    public NearbyPlaceResult[] results;
}

//
//    private String[] html_attributions;
//
//    private String status;
//
//    public String getNext_page_token ()
//    {
//        return next_page_token;
//    }
//
//    public void setNext_page_token (String next_page_token)
//    {
//        this.next_page_token = next_page_token;
//    }
//
//    public NearbyPlaceResult[] getResults ()
//    {
//        return results;
//    }
//
//    public void setResults (NearbyPlaceResult[] results)
//    {
//        this.results = results;
//    }
//
//    public String[] getHtml_attributions ()
//    {
//        return html_attributions;
//    }
//
//    public void setHtml_attributions (String[] html_attributions)
//    {
//        this.html_attributions = html_attributions;
//    }
//
//    public String getStatus ()
//    {
//        return status;
//    }
//
//    public void setStatus (String status)
//    {
//        this.status = status;
//    }
//
//    @Override
//    public String toString()
//    {
//        return "ClassPojo [next_page_token = "+next_page_token+", results = "+"results"+", html_attributions = "+html_attributions+", status = "+status+"]";
//    }
//}

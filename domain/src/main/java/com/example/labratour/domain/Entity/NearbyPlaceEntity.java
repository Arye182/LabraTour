package com.example.labratour.domain.Entity;

import com.example.labratour.domain.Results;
import com.google.gson.annotations.SerializedName;

public class NearbyPlaceEntity {
  private String[] html_attributions; //assumed data type to be string
  private String next_page_token;
  private Results[] results;
  private String status;

  @SerializedName("html_attributions")
  public String[] getHtmlAttributions() {
    return html_attributions;
  }

  @SerializedName("html_attributions")
  public void setHtmlAttributions(String[] html_attributions) {
    this.html_attributions = html_attributions;
  }

  @SerializedName("next_page_token")
  public String getNextPageToken() {
    return next_page_token;
  }

  @SerializedName("next_page_token")
  public void setNextPageToken(String next_page_token) {
    this.next_page_token = next_page_token;
  }

  @SerializedName("results")
  public Results[] getResults() {
    return results;
  }

  @SerializedName("results")
  public void setResults(Results[] results) {
    this.results = results;
  }

  @SerializedName("status")
  public String getStatus() {
    return status;
  }

  @SerializedName("status")
  public void setStatus(String status) {
    this.status = status;
  }
}

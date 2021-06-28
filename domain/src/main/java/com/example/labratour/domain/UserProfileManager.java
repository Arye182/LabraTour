package com.example.labratour.domain;

import com.example.labratour.domain.Entity.User;

import java.util.List;

public class UserProfileManager {
  // TODO: 26/06/2021 calculate new atributes(old, ratesCounter, List<AtributedPoi>
    private User user;



    public UserProfileManager(User user) {
        this.user = user;
    }

    public void updateByOneRate(AtributedPoi atributedPoi){

    }

  public User updateByListOfPois(List<AtributedPoi> pois) {return null;}
}

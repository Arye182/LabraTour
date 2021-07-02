package com.example.labratour.domain;

import com.example.labratour.domain.Entity.UserDomain;

import java.util.List;

public class UserProfileManager {
  // TODO: 26/06/2021 calculate new atributes(old, ratesCounter, List<AtributedPoi>
    private UserDomain userDomain;



    public UserProfileManager(UserDomain userDomain) {
        this.userDomain = userDomain;
    }

    public void updateByOneRate(AtributedPoi atributedPoi){

    }

  public UserDomain updateByListOfPois(List<AtributedPoi> pois) {return null;}
}

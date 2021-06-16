package com.example.labratour.domain.di;

import com.example.labratour.presentation.LabratourApplication;

public class DomainContainer {

    private FirebaseContainer domainFirebaseContainer;
    public static LabratourApplication appContext;

    public DomainContainer(LabratourApplication appContext) {
        this.appContext = appContext;
        this.domainFirebaseContainer= new FirebaseContainer();
    }

}

package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.NearbyPlacesRepository;
import com.fernandocejas.arrow.checks.Preconditions;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class NearbyPlacesUseCase {
    NearbyPlacesRepository repository;

    CompositeDisposable myDisposables;
    ExecutionThread executionThread;
    PostExecutionThread postExecutionThread;

    public NearbyPlacesUseCase(NearbyPlacesRepository nearbyPlacesRepository,  ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        this.myDisposables = new CompositeDisposable();
        this.executionThread = executionThread;
        this.postExecutionThread = postExecutionThread;
        repository = nearbyPlacesRepository;
    }


    protected void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(myDisposables);
        myDisposables.add(disposable);
    }}

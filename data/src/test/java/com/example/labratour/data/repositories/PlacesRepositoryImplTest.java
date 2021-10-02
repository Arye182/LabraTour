package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.OpeningHours1;
import com.example.labratour.data.Entity.PlaceOpeningHoursPeriod;
import com.example.labratour.data.Entity.PlaceOpeningHoursPeriodDetail;
import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.data.net.RestApi;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.repositories.PlacesRepository;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Single;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlacesRepositoryImplTest extends TestCase {
    private PlacesRepository placesRepository;
    @Mock
    RestApi mMocRestApi ;
    @Before
    public void SetUp(){
        placesRepository = new PlacesRepositoryImpl(mMocRestApi);
        }

    @Test
    public void testGetPoiById() throws MalformedURLException {
        final int[] result = {0};
        when(mMocRestApi.getPlaceById("3")).thenReturn(Single.fromCallable(new Callable<PoiDetailsEntity>() {
            @Override
            public PoiDetailsEntity call() throws Exception {
                return GenerateAtributesPoiDetails1.newInstance();
            }
        }));
        Assert.assertEquals(placesRepository.getPoiById("3").blockingGet().getClass(), Atributes.class);

}}
       class GenerateAtributesPoiDetails1  {
         public static PoiDetailsEntity newInstance(){
            ArrayList <PlaceOpeningHoursPeriod> list= new  ArrayList<PlaceOpeningHoursPeriod>();
            for (int i=0; i<6;i++){
                list.add(new PlaceOpeningHoursPeriod(new PlaceOpeningHoursPeriodDetail(i, "0000")));}
            String[] types =new String[]{"spa"};

            return new PoiDetailsEntity(new OpeningHours1(list), "3", 4.0, types, 3);

        }
    }


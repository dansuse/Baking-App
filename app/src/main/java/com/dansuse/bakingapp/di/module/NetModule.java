package com.dansuse.bakingapp.di.module;

import com.dansuse.bakingapp.BuildConfig;
import com.dansuse.bakingapp.data.source.remote.BakingApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LENOVO on 20/08/2017.
 */

@Module
public class NetModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    BakingApi provideBakingApi(Retrofit retrofit){
        return retrofit.create(BakingApi.class);
    }
}

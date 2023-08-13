package com.example.valenciag;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    GoogleMap mp = null ;
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inicializar view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Inicializar fragmento de mapa
        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);

        LatLng capillaSantoCaliz = new LatLng(39.475249492212164, -0.37504434197085745); //Cap
        LatLng mTeodorLlorente = new LatLng(39.46597149470814, -0.371273283915616); //Monumento al Poeta Teodor L.
        LatLng mNCASGM = new LatLng(39.47263873234082, -0.3746498726619957); // Museo Nacional de Cerámica y Artes Suntuarias González Martí
        LatLng maestroValls = new LatLng(39.4649666169424, -0.3420014296878959); //Falla maestro Valls
        LatLng iEunate = new LatLng(42.67227405793624, -1.761460377954586); //Igreja de Santa Maria de Eunate

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);



                        //_______12/05 - 9:38
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        //



                        //_______12/05 - 9:38
                        googleMap.addMarker(markerOptions.position(capillaSantoCaliz).title("Capilla del Santo Cáliz"));
                        //
                    }
                });
            }
        });

        return view;
    }*/
    /*
    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mp) {
                mp.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if (Home.getSelecao().equals("Capilla de Santo Caliz")) {
                            mp.addMarker(new MarkerOptions().position(capillaSantoCaliz).title("Marker in Sydney"));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mp.animateCamera(CameraUpdateFactory.newLatLngZoom(capillaSantoCaliz, 16));
                                }
                            }, 1000);
                        } else {

                        }
                    }
            });

            }
        });
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inicializar view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Inicializar fragmento de mapa
        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);

        LatLng capillaSantoCaliz = new LatLng(39.475249492212164, -0.37504434197085745); //Cap
        LatLng mTeodorLlorente = new LatLng(39.46597149470814, -0.371273283915616); //Monumento al Poeta Teodor L.
        LatLng mNCASGM = new LatLng(39.47263873234082, -0.3746498726619957); // Museo Nacional de Cerámica y Artes Suntuarias González Martí
        LatLng maestroValls = new LatLng(39.4649666169424, -0.3420014296878959); //Falla maestro Valls
        LatLng iEunate = new LatLng(42.67227405793624, -1.761460377954586); //Igreja de Santa Maria de Eunate

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mp) {
                mp.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        if(Home.getSelecao().equals("Capilla de Santo Caliz")) {
                            mp.clear();
                            mp.addMarker(new MarkerOptions().position(capillaSantoCaliz).title("Capilla de Santo Caliz"));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mp.animateCamera(CameraUpdateFactory.newLatLngZoom(capillaSantoCaliz, 16));
                                }
                            }, 1000);
                        } else {
                            if(Home.getSelecao().equals("Monumento al Poeta Teodor L.")) {
                                mp.clear();
                                mp.addMarker(new MarkerOptions().position(mTeodorLlorente).title("Monumento al Poeta Teodor Llorente"));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mp.animateCamera(CameraUpdateFactory.newLatLngZoom(mTeodorLlorente, 16));
                                    }
                                }, 1000);
                            } else {
                                if(Home.getSelecao().equals("Museo Nacional de Cerámica y Artes Suntuarias González Martí")) {
                                    mp.clear();
                                    mp.addMarker(new MarkerOptions().position(mNCASGM).title("Museo Nacional de Cerámica y Artes Suntuarias González Martí"));
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mp.animateCamera(CameraUpdateFactory.newLatLngZoom(mNCASGM, 16));
                                        }
                                    }, 1000);
                                }else{
                                    if(Home.getSelecao().equals("Falla maestro Valls")) {
                                        mp.clear();
                                        mp.addMarker(new MarkerOptions().position(maestroValls).title("Falla maestro Valls"));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mp.animateCamera(CameraUpdateFactory.newLatLngZoom(maestroValls, 16));
                                            }
                                        }, 1000);
                                    }else{
                                        if(Home.getSelecao().equals("Igreja de Santa Maria de Eunate")) {
                                            mp.clear();
                                            mp.addMarker(new MarkerOptions().position(iEunate).title("Igreja de Santa Maria de Eunate"));
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mp.animateCamera(CameraUpdateFactory.newLatLngZoom(iEunate, 16));
                                                }
                                            }, 1000);
                                        }
                                     }
                             }
                            }
                        }
                    }
                    });

            }
        });
        return view;
    }

}
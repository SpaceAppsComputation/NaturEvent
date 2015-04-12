package com.example.asabater.pruebasmaps;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private final int CREATE_MARKER = 1;
    private MapsActivity act = this;
    private Address address = null;

    private String[] mCategories;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private ArrayList<Marker> points;

    private PointsDbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();


        points = new ArrayList<Marker>();
        mDbHelper = new PointsDbAdapter(this);
        mDbHelper.open();

        //mDbHelper.deleteAll();

        // CREA EL DRAWER LAYOUT LATERAL
        mCategories = getResources().getStringArray(R.array.categories_array_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mCategories));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(1);
        }



        LocationManager locManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        List<String> listaProviders = locManager.getAllProviders();


        //Obtenemos la última posición conocida
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng currentLoc = new LatLng(loc.getLatitude(), loc.getLongitude());

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(loc.getLatitude(), loc.getLongitude()), 10));



        /*/try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> list = geocoder.getFromLocation(
                    loc.getLatitude(), loc.getLongitude(), 1);
            if (!list.isEmpty()) {
                address = list.get(0);
                Log.e("MI POSICION", "Mi direccion es: " + address.getCountryName() + address.getLocality());
                //messageTextView2.setText("Mi direcci—n es: \n"+ address.getAddressLine(0));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/


        /*Marker current = mMap.addMarker(new MarkerOptions()
                .title(address.getLocality())
                .snippet(address.getCountryName())
                .position(currentLoc)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));*/

        // Setting a custom info window adapter for the google map
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(final Marker arg0) {

                // Getting view from the layout file info_window_layout
               // View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);

                // Getting the position from the marker
                final LatLng latLng = arg0.getPosition();

               /* // Getting reference to the TextView to set latitude
                final TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);

                // Getting reference to the TextView to set longitude
                TextView tvPlace = (TextView) v.findViewById(R.id.tvPlace);

                // Setting the latitude
                tvTitle.setText(arg0.getTitle());*/

                //Toast.makeText(act, arg0.getTitle(), Toast.LENGTH_SHORT).show();

                /*Geocoder geocoder = new Geocoder(act, Locale.getDefault());
                List<Address> list = null;
                try {
                    list = geocoder.getFromLocation(
                            latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    Toast.makeText(act,"IOException", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                if (!list.isEmpty()) {
                    address = list.get(0);
                    //Log.e("MI POSICION", "Mi direccion es: " + address.getCountryName() + address.getLocality());
                    //messageTextView2.setText("Mi direcci—n es: \n"+ address.getAddressLine(0));
                }
                // Setting the longitude
               // tvPlace.setText(address.getCountryName() + ", " +  address.getLocality());

                Log.e("SHOW INFO", "SHOW INFO");

                /*Button btMore = (Button) v.findViewById(R.id.btProfile);

                if (btMore == null) {
                    Log.e("BTMORE", "BTMORE == null");
                }*/


                //Toast.makeText(act, arg0.getTitle(), Toast.LENGTH_SHORT).show();

                /*Bundle b = new Bundle();
                b.putString("title", arg0.getTitle());
                b.putDouble("latitude", latLng.latitude);
                b.putDouble("longitude", latLng.longitude);
                b.putString("location", address.getCountryName() + ", " +  address.getLocality());
                b.putString("category", "caca");
                b.putString("description", "defecacion");*/


                Intent i = new Intent(act, ProfileActivity.class);
                i.putExtra("title", arg0.getTitle());
                i.putExtra("latitude", latLng.latitude);
                i.putExtra("longitude", latLng.longitude);
                /*if(address != null) {
                    i.putExtra("location", address.getCountryName() + ", " + address.getLocality());
                }
                else {
                    i.putExtra("location", "Desconocido");
                }
                i.putExtra("category", "caca");
                i.putExtra("description", "defecacion");

                address = null;*/

                startActivity(i);



               /* btMore.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        // LLAMAR A LA VISTA DEL PERFIL PASANDOLE EL ID DEL MARKER

                        Log.e("BTMORE- onClick", "BTMORE onCLick");

                        Bundle b = new Bundle();
                        b.putString("title", arg0.getTitle());
                        b.putDouble("latitude", latLng.latitude);
                        b.putDouble("longitude", latLng.longitude);
                        b.putString("location", address.getCountryName() + ", " +  address.getLocality());
                        b.putString("category", "caca");
                        b.putString("description", "defecacion");


                        Intent i = new Intent(act, ProfileActivity.class);
                        i.putExtra("latLng", latLng);

                        startActivity(i);

                    }
                });*/

                // Returning the view containing InfoWindow contents
                return null;

            }
        });

        fetchPoints();

    }


    public void fetchPoints() {

        Cursor c = mDbHelper.fetchAllPoints();
        startManagingCursor(c);
        points = new ArrayList<>();

        Log.w("COSAS", c.getColumnName(3) + " " +  c.getColumnName(4));
        while(c.moveToNext()){
            Log.w("MOVE NEXT", c.getDouble(3) + " " + c.getDouble(4));

            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        c.getDouble(3), c.getDouble(4), 1);
                if (!list.isEmpty()) {
                    address = list.get(0);
                    //messageTextView2.setText("Mi direcci—n es: \n"+ address.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(address != null) {
                if (address.getLocality()!= null) {
                    points.add(mMap.addMarker(new MarkerOptions()
                                    .title(c.getString(1))
                                    .snippet(address.getLocality() + ", " + address.getCountryName())
                                    .position(new LatLng(c.getDouble(3), c.getDouble(4)))
                    ));
                }
                else {
                    points.add(mMap.addMarker(new MarkerOptions()
                                    .title(c.getString(1))
                                    .snippet("---" + ", " + address.getCountryName())
                                    .position(new LatLng(c.getDouble(3), c.getDouble(4)))
                    ));
                }

            }
            else {
                points.add(mMap.addMarker(new MarkerOptions()
                                .title(c.getString(1))
                                .snippet("---" + ", " + "---")
                                .position(new LatLng(c.getDouble(3), c.getDouble(4)))
                ));
            }

            address = null;
        }

    }



    @Override
    public void onMapLongClick(LatLng point) {
        //Toast.makeText(this,"Nuevo onLongClick", Toast.LENGTH_SHORT).show();

        createMarker(null, point);

    }


    public void createMarker(View view, LatLng latLng) {
        Intent i = new Intent(this, CreateMarker.class);
        i.putExtra("latLng", latLng);

        startActivityForResult(i, CREATE_MARKER);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID );
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

            mMap.getUiSettings().setRotateGesturesEnabled(true);
            mMap.getUiSettings().setScrollGesturesEnabled(true);
            mMap.getUiSettings().setTiltGesturesEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);

            mMap.setOnMapLongClickListener(this);

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    public void setLocation(Location loc) {
        //Obtener la direcci—n de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address address = list.get(0);
                    //messageTextView2.setText("Mi direcci—n es: \n"+ address.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, intent);

        Bundle extras = intent.getExtras();
        switch (requestCode) {
            case CREATE_MARKER:
               // Perfil p = (Perfil) extras.getSerializable("Profile");

                String name = extras.getString("name");
                double lat= extras.getDouble("lat");
                double lon = extras.getDouble("long");
                String descr = extras.getString("descr");
                String category = extras.getString("category");

                Geocoder geocoder = new Geocoder(act, Locale.getDefault());
                List<Address> list = null;
                try {
                    list = geocoder.getFromLocation(
                            lat, lon, 1);
                } catch (IOException e) {
                    Toast.makeText(act,"IOException", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                if (!list.isEmpty()) {
                    address = list.get(0);
                    //Log.e("MI POSICION", "Mi direccion es: " + address.getCountryName() + address.getLocality());
                    //messageTextView2.setText("Mi direcci—n es: \n"+ address.getAddressLine(0));
                }

                if (address!= null) {
                    if (address.getLocality()!= null) {
                        Marker newMarker = mMap.addMarker(new MarkerOptions()
                                .title(name)
                                .position(new LatLng(lat, lon))
                                .snippet( address.getLocality() + ", " + address.getCountryName()));
                    }
                    else {
                        Marker newMarker = mMap.addMarker(new MarkerOptions()
                                .title(name)
                                .position(new LatLng(lat, lon))
                                .snippet("---" + ", " + address.getCountryName()));
                    }
                }
                else {
                    Marker newMarker = mMap.addMarker(new MarkerOptions()
                                    .title(name)
                                    .position(new LatLng(lat, lon))
                                    .snippet("---" + ", " + "---")
                    );
                }

                Log.w("LAT LON", lat + " " + lon);
                mDbHelper.createPoint(name, descr, category, lat, lon);

                fetchPoints();
                //newMarker.setTitle(newMarker.getId());

                // ***************************
                // ENVIAR AL SERVIDOR
                // ***************************

                break;
        }
    }




    private void selectItem(int position) {
        // update the main content by replacing fragments
		/*
		 * Fragment fragment = new PlanetFragment(); Bundle args = new Bundle();
		 * args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
		 * fragment.setArguments(args);
		 *
		 * FragmentManager fragmentManager = getFragmentManager();
		 * fragmentManager.beginTransaction().replace(R.id.content_frame,
		 * fragment).commit();
		 */

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        // setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

        Cursor c = null;

        switch(position) {
            case 0:

                break;
            case 1:
                c = mDbHelper.fetchAllPoints();
                populate(c);
                break;
            case 2:
                c = mDbHelper.fetchPointByCat("Volcano");
                populate(c);
                break;
            case 3:
                c = mDbHelper.fetchPointByCat("Storm");
                populate(c);

                break;
            case 4:
                c = mDbHelper.fetchPointByCat("Eartquake");
                populate(c);

                break;
            case 5:
                c = mDbHelper.fetchPointByCat("Flood");
                populate(c);

                break;
            case 6:
                c = mDbHelper.fetchPointByCat("Tornado");
                populate(c);

                break;
            case 7:
                c = mDbHelper.fetchPointByCat("Hurricane");
                populate(c);

                break;
            case 8:
                c = mDbHelper.fetchPointByCat("Wildfire");
                populate(c);

                break;
            case 9:
                c = mDbHelper.fetchPointByCat("Avalanche");

                populate(c);
                break;
            case 10:
                c = mDbHelper.fetchPointByCat("Plague");
                populate(c);

                break;
            case 11:
                c = mDbHelper.fetchPointByCat("Cats");
                populate(c);

                break;

        }

    }


    public void populate(Cursor c) {
        startManagingCursor(c);

        for (Marker m: points) {
            m.remove();
            mMap.clear();
        }

        points = null;
        points = new ArrayList<>();

        Log.w("COSASDASDSADASD", ""+c.getCount());
        while(c.moveToNext()){
            Log.w("MOVE NEXT", c.getDouble(3) + " " + c.getDouble(4));

            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        c.getDouble(3), c.getDouble(4), 1);
                if (!list.isEmpty()) {
                    address = list.get(0);
                    list = null;
                    //messageTextView2.setText("Mi direcci—n es: \n"+ address.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if(address != null) {
                if (address.getLocality()!= null) {
                    points.add(mMap.addMarker(new MarkerOptions()
                                    .title(c.getString(1))
                                    .snippet(address.getLocality() + ", " + address.getCountryName())
                                    .position(new LatLng(c.getDouble(3), c.getDouble(4)))
                    ));
                }
                else {
                    points.add(mMap.addMarker(new MarkerOptions()
                                    .title(c.getString(1))
                                    .snippet("---" + ", " + address.getCountryName())
                                    .position(new LatLng(c.getDouble(3), c.getDouble(4)))
                    ));
                }

            }
            else {
                points.add(mMap.addMarker(new MarkerOptions()
                                .title(c.getString(1))
                                .snippet("---" + ", " + "---")
                                .position(new LatLng(c.getDouble(3), c.getDouble(4)))
                ));
            }

            address = null;
        }
    }


    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }



}

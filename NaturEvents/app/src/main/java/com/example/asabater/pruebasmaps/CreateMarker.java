package com.example.asabater.pruebasmaps;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alberto on 11/04/2015.
 */
public class CreateMarker extends Activity {

    private EditText nameGet;
    private Spinner spinnerCat;
    private EditText descriptionGet;
    private Button btCreate;

    private Bundle bundle;
    private Activity act;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialognewmarker);

        bundle = getIntent().getExtras();
        final LatLng latLng = (LatLng) bundle.get("latLng");

        nameGet = (EditText) findViewById(R.id.nameGet);
        spinnerCat = (Spinner) findViewById(R.id.spinnerCat);
        Resources res = getResources();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        spinnerCat.setAdapter(adapter);

        descriptionGet = (EditText) findViewById(R.id.descriptionGet);

        btCreate = (Button) findViewById(R.id.btCreate);
        btCreate.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {

                            //Perfil p = new Perfil(nameGet.getText().toString(),
                             //       latLng, descriptionGet.getText().toString(), "", 0 );

                            Bundle b = new Bundle();
                           // b.putSerializable("Profile", p);
                            b.putString("name", nameGet.getText().toString());
                            b.putDouble("lat", latLng.latitude);
                            b.putDouble("long", latLng.longitude);
                            b.putString("descr", descriptionGet.getText().toString());
                            b.putString("category", spinnerCat.getSelectedItem().toString());

                            Log.e("SPINNER", spinnerCat.getSelectedItem().toString());


                            Intent mIntent = new Intent();
                            mIntent.putExtras(b);
                            setResult(RESULT_OK, mIntent);

                            finish();
                        }
                    });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

        }
        return false;
    }
}

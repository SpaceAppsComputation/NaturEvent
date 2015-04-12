package com.example.asabater.pruebasmaps;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alberto on 12/04/2015.
 */
public class ProfileActivity extends Activity{

    private TextView tvTitle;
    private TextView tvLocation;
    private TextView tvCityLocation;
    private TextView tvCategory;
    private TextView tvDescription;
    private ImageView ivDesc;
    private ImageButton btShare;

    private ImageButton img1;
    private ImageButton img2;
    private TextView rat1;
    private TextView rat2;
    private TextView tvRating;
    private Button btNext;
    private Button btPrev;
    private ScrollView scroll;

    private double like = 0;
    private double dislike = 0;
    private boolean votado = false;
    private Address address;
    private int dia;

    private Bundle bundle;
    private Cursor c;
    private PointsDbAdapter bd;
    private Activity act;

    private ImageView downloadedImg;
    private String url;
    //private String downloadUrl = "http://www.9ori.com/store/media/images/8ab579a656.jpg";

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.markerprofile);
        act = this;

        bundle = getIntent().getExtras();

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvCityLocation = (TextView) findViewById(R.id.tvCityLocation);
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        ivDesc = (ImageView) findViewById(R.id.ivDesc);

        bd = new PointsDbAdapter(this);
        bd.open();
        double latitude = bundle.getDouble("latitude");
        double longitude = bundle.getDouble("longitude");

        Log.w("LATIDUESDSDASDSAD", latitude + ", " + longitude);
        c = bd.fetchPoint(latitude, longitude);



        tvTitle.setText(c.getString(1));

        double lat = Math.floor(latitude * 100) / 100;
        double lon = Math.floor(longitude * 100) / 100;
      //  int lat = (int)(latitude * 100);
       // double lat2 = lat/100;
      //  int lon = (int)(longitude * 100);
      //  double lon2 = lon/100;

        tvLocation.setText(lat + ", " + lon);

        address = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Toast.makeText(this,"IOException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        if (!list.isEmpty()) {
            address = list.get(0);
            //Log.e("MI POSICION", "Mi direccion es: " + address.getCountryName() + address.getLocality());
            //messageTextView2.setText("Mi direcci—n es: \n"+ address.getAddressLine(0));
        }



        if (address!= null) {
            if (address.getLocality()!= null) {
                tvCityLocation.setText(address.getLocality() + ", " + address.getCountryName());
            }
            else {
                tvCityLocation.setText("---" + ", " + address.getCountryName());
            }
        }
        else {
            tvCityLocation.setText("---" + ", " + "---");
        }

        //tvCityLocation.setText(address.getLocality() + ", " + address.getCountryName());
        tvCategory.setText(c.getString(7));
        tvDescription.setText(c.getString(2));


        dia = 15;
        url = Earth.getMiniatura(bundle.getDouble("latitude"), bundle.getDouble("longitude"), 4, "2015-03-" + dia);
        Log.e("IMG", url);


        //thumburl = temp.getString(temp.getColumnIndex("thumburl"));
        //Drawable drawable = LoadImageFromWebOperations(url);
        //ivDesc.setImageDrawable(drawable);
        new ImageDownloader().execute(url);

        btNext = (Button) findViewById((R.id.btNext));
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia ++;
                url = Earth.getMiniatura(bundle.getDouble("latitude"), bundle.getDouble("longitude"), 4, "2015-03-" + dia);
                new ImageDownloader().execute(url);
            }
        });

        btPrev = (Button) findViewById((R.id.btPrev));
        btPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia --;
                url = Earth.getMiniatura(bundle.getDouble("latitude"), bundle.getDouble("longitude"), 5, "2015-03-" + dia);
                new ImageDownloader().execute(url);
            }
        });



        //ivDesc.setImageDrawable(loadImage(url));
        /*URL imageUrl = null;
        HttpURLConnection conn = null;

        try {

            imageUrl = new URL("https://mycodeandlife.files.wordpress.com/2013/01/384088_2317070728022_2086719259_n.jpg");
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2

            Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream(), new Rect(0, 0, 0, 0), options);
            ivDesc.setImageBitmap(imagen);

        } catch (IOException e) {

            e.printStackTrace();

        }*/

        img1 = (ImageButton) findViewById((R.id.img1));
        img2 = (ImageButton) findViewById(R.id.img2);

        img1.setImageResource(R.drawable.manitarriba);
        img2.setImageResource(R.drawable.manitabajo);

        rat1 = (TextView) findViewById(R.id.rat1);
        rat2 = (TextView) findViewById(R.id.rat2);

        rat1.setText("" + (int) c.getInt(5));
        rat2.setText("" + (int)c.getInt(6));

        tvRating = (TextView) findViewById(R.id.tvRating);

        like = c.getInt(5);
        dislike = c.getInt(6);

        if (like + dislike != 0) {
            double d = Math.floor((like / (like + dislike))*100*100) / 100;
            tvRating.setText(d + " %");
        }
        else {
            tvRating.setText("N/A");
        }


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!votado) {
                    bd.addRating(c.getInt(0), 1);
                    like++;
                    double d = Math.floor((like / (like + dislike)) * 100 * 100) / 100;
                    tvRating.setText(d + " %");
                    rat1.setText((int) like + "");
                    votado = true;
                }
                else {
                    Toast.makeText(act, "Ya has votado madafaka", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!votado) {
                    bd.addRating(c.getInt(0), -1);
                    dislike++;
                    double d = Math.floor((like / (like + dislike)) * 100 * 100) / 100;
                    tvRating.setText(d + " %");
                    rat2.setText((int) dislike + "");
                    votado = true;
                }
                else {
                    Toast.makeText(act, "Ya has votado madafaka", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btShare = (ImageButton) findViewById(R.id.btShare);
        btShare.setImageResource(R.drawable.tweet);
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent using ACTION_VIEW and a normal Twitter url:

                String tweetUrl = "";

                if (address!= null) {
                    if (address.getLocality()!= null) {
                        tweetUrl =
                                String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                                        urlEncode("Look! I have found a " + c.getString(7) + " at " + address.getLocality() + " (" + address.getCountryName() + ") with #NaturEvents app"), urlEncode(""));
                    }
                    else {
                        tweetUrl =
                                String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                                        urlEncode("Look! I have found a " + c.getString(7) + " at " + address.getCountryName() + " with #NaturEvents app"), urlEncode(""));                    }
                }
                else {
                    tweetUrl =
                            String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                                    urlEncode("Look! I have found a " + c.getString(7)), urlEncode("") + " with #NaturEvents app");
                }



                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

                // Narrow down to official Twitter app, if available:
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                        intent.setPackage(info.activityInfo.packageName);
                    }
                }

                startActivity(intent);
            }
        });




    }



    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            Log.wtf("TWITTER", "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }


    private Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }
    }

    public static Drawable loadImage(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            return  null;
        }
    }



    protected Bitmap toBitmap(String... args) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private InputStream fetch(String urlString) throws MalformedURLException, IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return false;
    }


    private class ImageDownloader extends AsyncTask<String,String,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap((String) params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            Log.i("Async-Example", "onPostExecute Called");
            ivDesc.setImageBitmap(result);
        }

        private Bitmap downloadBitmap(String url) {
            // initilize the default HTTP client object
            final DefaultHttpClient client = new DefaultHttpClient();

            //forming a HttoGet request
            final HttpGet getRequest = new HttpGet(url);
            try {

                HttpResponse response = client.execute(getRequest);

                //check 200 OK for success
                final int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    Log.w("ImageDownloader", "Error " + statusCode +
                            " while retrieving bitmap from " + url);
                    return null;

                }

                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    try {
                        // getting contents from the stream
                        inputStream = entity.getContent();

                        // decoding stream data back into image Bitmap that android understands
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        return bitmap;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entity.consumeContent();
                    }
                }
            } catch (Exception e) {
                // You Could provide a more explicit error message for IOException
                getRequest.abort();
                Log.e("ImageDownloader", "Something went wrong while" +
                        " retrieving bitmap from " + url + e.toString());
            }

            return null;
        }
    }


}

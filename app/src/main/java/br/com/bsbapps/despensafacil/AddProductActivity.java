package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddProductActivity extends AppCompatActivity {
    Long currentList;

    private EditText barcodeEditText;
    private EditText productEditText;
    private EditText quantityEditText;
    private EditText dueDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        currentList = sharedPref.getLong("br.com.bsbapps.despensafacil.CURRENT_LIST_ID", 1);

        barcodeEditText = (EditText) findViewById(R.id.addBarcodeText);
        productEditText = (EditText) findViewById(R.id.productNameText);
        quantityEditText = (EditText) findViewById(R.id.quantityText);
        dueDateEditText= (EditText) findViewById(R.id.dueDateText);

        Bundle extras = getIntent().getExtras();

        if (extras!=null){
            barcodeEditText.setText(extras.getString("barcode"));
            productEditText.setText(extras.getString("product"));
            quantityEditText.setText(extras.getString("quantity"));
            dueDateEditText.setText(extras.getString("duedate"));
        }
    }

    public void scanBarcode(View view) {
        new IntentIntegrator(this).initiateScan();
    }

    public void scanBarcodeInverted(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.addExtra(Intents.Scan.INVERTED_SCAN, true);
        integrator.initiateScan();
    }

    public void scanBarcodeCustomLayout(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan something");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    public void scanBarcodeFrontCamera(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        integrator.initiateScan();
    }

    public void scanWithTimeout(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setTimeout(8000);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                TextView barcodeText = (TextView) findViewById(R.id.addBarcodeText);
                barcodeText.setText(result.getContents());
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    String urlText = "http://becklas.com/bsbapps/despensafacil/dfsearch.php?source=12579de41dd291e38ec0f9acd4a6c720";
                    urlText = urlText.concat("&q=");
                    urlText = urlText.concat(barcodeText.getText().toString());
                    new SearchProduct().execute(urlText);
                } else {

                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class SearchProduct extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urlText) {
            String resultText="";
            // params comes from the execute() call: params[0] is the url.
            try {
                resultText=urlText[0].toString();
                URL searchURL = new URL(resultText);
                BufferedReader in = new BufferedReader(new InputStreamReader(searchURL.openStream()));
                String inputLine;
                resultText="";
                while ((inputLine = in.readLine()) != null)
                    resultText = resultText.concat(inputLine);
                in.close();
                return resultText;
            } catch (MalformedURLException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            TextView productText = (TextView) findViewById(R.id.productNameText);
            productText.setText(result);
        }
    }

    private void saveProductOnList() {
        if (barcodeEditText.getText().length() != 0) {
            AsyncTask<Object, Object, Object> saveProductTask =
                    new AsyncTask<Object, Object, Object>() {
                        @Override
                        protected Object doInBackground(Object... params) {
                            saveProduct();
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object result) {
                            finish();
                        }
                    };
        }
    }

    private void saveProduct(){
        DatabaseConnector dbConnector = new DatabaseConnector(this);
        Cursor query;
        if (getIntent().getExtras()==null){
            dbConnector.open();
            query = dbConnector.getProduct(barcodeEditText.getText().toString());
            if(query == null){
                dbConnector.insertProduct(barcodeEditText.getText().toString(), productEditText.getText().toString());
            }

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.
            Date dateObject = new Date();
            try{
                String dob_var=(dueDateEditText.getText().toString());
                dateObject = formatter.parse(dob_var);
            }
            catch (java.text.ParseException e)
            {
                e.printStackTrace();
            }
            dbConnector.insertProductOnList(currentList, barcodeEditText.getText().toString(),
                    Integer.parseInt(quantityEditText.getText().toString()),dateObject);

        }
    }
}

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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.Date;

import br.com.bsbapps.util.DateHandler;
import br.com.bsbapps.util.SecurityToken;

/**
 * Created by André Becklas on 28/11/2016.
 *
 * Classe da activity de edição de produto
 */

public class AddProductActivity extends AppCompatActivity {
    // Armazena a lista atualmente selecionada pelo usuário
    Long currentList;

    // Objetos utilizados no layout
    private EditText barcodeEditText;
    private EditText productEditText;
    private EditText quantityEditText;
    private EditText dueDateEditText;

    DatabaseConnector dbConnector = new DatabaseConnector(this);
    Cursor query;

    // Método OnCreate
    // Chamado na criação da activity, antes da exibição na tela
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Captura código da lista selecionada atualmente
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        currentList = sharedPref.getLong("br.com.bsbapps.despensafacil.CURRENT_LIST_ID", 1);

        // Instancia os objetos do layout com os quais o código irá interagir
        barcodeEditText = (EditText) findViewById(R.id.addBarcodeText);
        productEditText = (EditText) findViewById(R.id.productNameText);
        quantityEditText = (EditText) findViewById(R.id.quantityText);
        dueDateEditText= (EditText) findViewById(R.id.dueDateText);

        // Verifica e captura se há valores passados para esta activity através do Bundle
        // Serve para passar os valores de um produto selecionado e permitir a edição dele
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            barcodeEditText.setText(extras.getString("barcode"));
            productEditText.setText(extras.getString("product"));
            quantityEditText.setText(extras.getString("quantity"));
            dueDateEditText.setText(extras.getString("duedate"));
        }
    }

    // Método scanBarcode
    // Inicia a intent da câmera - integração com a lib zxing
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

    // Metodo onActivityResult
    // Recebe um resultado da intent de câmera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Armazena o resultado da intent de câmera
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        // Variáveis locais
        int product_status=0;
        String product_name="Produto em atualização";

        // Se o resultado da intent da câmera não for nulo
        if(result != null) {
            // Se o conteúdo for nulo, houve um cancelamento da digitalização
            if(result.getContents() == null) {
                // Exibe mensagem de cancelamento ao usuário num Toast e grava em log
                Log.d("AddProductActivity", "Leitura do código de barras cancelada");
                Toast.makeText(this, "Leitura do código de barras cancelada",
                        Toast.LENGTH_LONG).show();
            } else {
                // Grava mensagem de sucesso em log
                Log.d("AddProductActivity", "Leitura do código de barras com sucesso");

                // Exibe código de barras capturado no campo
                barcodeEditText.setText(result.getContents());

                // Verifica se o produto existe na base local
                dbConnector.open();
                query = dbConnector.getProduct(barcodeEditText.getText().toString());
                if(query != null){
                    if (query.getCount()!=0) {
                        // Se o produto existir, captura o nome e status
                        query.moveToFirst();
                        product_status = query.getInt(query.getColumnIndex("product_status"));
                        product_name = query.getString(query.getColumnIndex("product_name"));
                    }
                }

                // Se o status = 0 (produto não validado), se o usuário tiver internet habilitada
                // chama o serviço de pesquisa de produto
                if (product_status==0) {
                    // Instancia o serviço de comunicação
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    // Se houver internet habilitada
                    if (networkInfo != null && networkInfo.isConnected()) {
                        // Monta token de validação para chamada do serviço
                        String key = new SecurityToken().getKey();
                        // Monta url de chamada do serviço passando os parâmetros
                        String urlText = "http://becklas.com/bsbapps/despensafacil/dfsearch.php";
                        urlText = urlText.concat("?source=");
                        urlText = urlText.concat(key);
                        urlText = urlText.concat("&q=");
                        urlText = urlText.concat(barcodeEditText.getText().toString());
                        // Chama método assíncrono de pesquisa de produto
                        new SearchProduct().execute(urlText);
                        return;
                    }
                }
                // Se o produto existir na base local e estiver valido (status=1), seu nome local
                // será exibido.
                // Se o produto existir mas não estiver validado (status=0) e não houver internet
                // habilitada, seu nome local será exibido
                productEditText.setText(product_name);
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    // Metódo assincrono de pesquisa de produto
    // Lê conteudo de uma url
    private class SearchProduct extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urlText) {
            String resultText="";
            // Tenta abrir a URL e lê seu conteúdo.
            try {
                // urlText recebe um array de Strings com apenas um resultado
                resultText=urlText[0];
                URL searchURL = new URL(resultText);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(searchURL.openStream()));
                String inputLine;
                resultText="";
                while ((inputLine = in.readLine()) != null)
                    resultText = resultText.concat(inputLine);
                in.close();
                return resultText;
            } catch (MalformedURLException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            } catch (IOException e) {
                e.printStackTrace();;
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // Captura o resultado da AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            // Mostra o nome do produto capturado
            TextView productText = (TextView) findViewById(R.id.productNameText);
            productText.setText(result);
        }
    }

    public void saveProductOnList(View view) {
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
            saveProductTask.execute();
        }
    }

    private void saveProduct(){
        if (getIntent().getExtras()==null){
            dbConnector.open();
            query = dbConnector.getProduct(barcodeEditText.getText().toString());
            if(query == null || query.getCount()==0){
                dbConnector.insertProduct(barcodeEditText.getText().toString(),
                        productEditText.getText().toString());
            } else {
                dbConnector.updateProduct(barcodeEditText.getText().toString(),
                        productEditText.getText().toString());
            }
            query.close();
            Date dateObject = new DateHandler().getDate("dd/MM/yyyy",
                    dueDateEditText.getText().toString());

            dbConnector.insertProductOnList(currentList, barcodeEditText.getText().toString(),
                    Integer.parseInt(quantityEditText.getText().toString()),dateObject);

        }
    }
}

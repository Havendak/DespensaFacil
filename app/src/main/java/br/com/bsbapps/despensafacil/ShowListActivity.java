package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class ShowListActivity extends AppCompatActivity {
    //implements SearchView.OnQueryTextListener
    //private SearchView busca;
    private ListView productListView;
    private SimpleCursorAdapter productListAdapter;
    private int currentList;
    AdapterView.AdapterContextMenuInfo menuinfo = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        // Captura código da lista selecionada atualmente
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        currentList = sharedPref.getInt("br.com.bsbapps.despensafacil.CURRENT_PANTRY_LIST_ID", 1);


        //instancia objetos
        //busca = (SearchView) findViewById(R.id.searchViewProdutos); //instancia caixa de busca
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addBtn);
        productListView = (ListView) findViewById(R.id.productListView);

        //preenche lista
        //ListViewAdapter adapter=new ListViewAdapter(this, list);
        //lista.setAdapter(adapter);
        String[] from = new String[]{DatabaseOpenHelper.COLUMN_BARCODE,
                DatabaseOpenHelper.COLUMN_PRODUCT_NAME,
                DatabaseOpenHelper.COLUMN_QUANTITY,
                DatabaseOpenHelper.COLUMN_DUE_DATE};
        int[] to = new int[]{R.id.showListBarcodeTextView, R.id.showListProductNameTextView,
                R.id.showListQuantityTextView, R.id.showListDueDateTextView};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            productListAdapter = new SimpleCursorAdapter(
                    ShowListActivity.this, R.layout.product_list_item, null, from, to, 0);
        }
        productListView.setAdapter(productListAdapter);

        //evento ao clicar no botão add
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
            }
        });

        //cria menu de contexto da list
        registerForContextMenu(productListView);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_main_action_dashboard) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (item.getItemId() == R.id.menu_main_action_lists) {
            startActivity(new Intent(getApplicationContext(), ShowListActivity.class));
        }
        if (item.getItemId() == R.id.menu_main_action_add_product) {
            startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetProductTask().execute((Object[]) null);
    }

    @Override
    protected void onStop() {
        productListAdapter.changeCursor(null);
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ShowList Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    private class GetProductTask extends AsyncTask<Object, Object, Cursor> {
        DatabaseConnector dbConnector = new DatabaseConnector(ShowListActivity.this);

        @Override
        protected Cursor doInBackground(Object... params) {
            dbConnector.open();
            return dbConnector.getAllListProducts(currentList);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            productListAdapter.changeCursor(result);
            dbConnector.close();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_product_list, menu);
        menuinfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.clearHeader();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        menuinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String barcode = ((TextView) info.targetView.findViewById(R.id.showListBarcodeTextView)).getText().toString();
        switch (item.getTitle().toString()) {
            case "Excluir":
                DatabaseConnector dbConnector = new DatabaseConnector(ShowListActivity.this);
                dbConnector.open();
                dbConnector.deleteProductFromList(currentList, barcode);
                dbConnector.close();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

/* A PARTIR DAQUI, TESTES PARA SEARCHVIEW

        //liga searchview ao listview
        lista.setTextFilterEnabled(true);
        setupSearchView();

        //evento ao clicar num item
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                //int pos=position+1;
                //Toast.makeText(ListaDespensa.this, Integer.toString(pos) + " Clicado", Toast.LENGTH_SHORT).show();
            }

        });



    }

    private void setupSearchView()
    {
        busca.setIconifiedByDefault(false);
        busca.setOnQueryTextListener(this);
        busca.setSubmitButtonEnabled(false);
        busca.setQueryHint("Busque aqui");
    }

    @Override
    public boolean onQueryTextSubmit(String newText)
    {
        if (TextUtils.isEmpty(newText)) {
            lista.clearTextFilter();
        } else {
            lista.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    /*
        //dados fictícios para primeiro teste da lista
        int quantidade = 100;
        ArrayList<String> listArray = new ArrayList<>();
        for (int i=0;i<=quantidade;i++) {
            listArray.add(i, "Item " + i);
        }
        //teste de lista
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listArray); //inicializa adapter
        lista.setAdapter(arrAdapter); //seta adapter para a listView
    */
}

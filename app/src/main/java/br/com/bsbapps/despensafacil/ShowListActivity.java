package br.com.bsbapps.despensafacil;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

public class ShowListActivity extends AppCompatActivity  {
    //implements SearchView.OnQueryTextListener
    //private SearchView busca;
    private ListView productListView;
    private SimpleCursorAdapter productListAdapter;
    private int currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        // Captura código da lista selecionada atualmente
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        currentList = sharedPref.getInt("br.com.bsbapps.despensafacil.CURRENT_LIST_ID", 1);


        //instancia objetos
        //busca = (SearchView) findViewById(R.id.searchViewProdutos); //instancia caixa de busca
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addBtn);
        productListView = (ListView) findViewById(R.id.productListView);

        //preenche lista
        //ListViewAdapter adapter=new ListViewAdapter(this, list);
        //lista.setAdapter(adapter);
        String[] from = new String[]{"product_name", "quantity", "due_date"};
        int[] to = new int[]{R.id.showListProductNameTextView, R.id.showListQuantityTextView, R.id.showListDueDateTextView};
        productListAdapter = new SimpleCursorAdapter(
                ShowListActivity.this, R.layout.product_list_item, null, from, to, 0);
        productListView.setAdapter(productListAdapter);

        //evento ao clicar no botão add
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.menu_main_action_dashboard) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (item.getItemId()==R.id.menu_main_action_lists) {
            startActivity(new Intent(getApplicationContext(), ShowListActivity.class));
        }
        if (item.getItemId()==R.id.menu_main_action_add_product) {
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
        Cursor cursor = productListAdapter.getCursor();
        productListAdapter.changeCursor(null);
        super.onStop();
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

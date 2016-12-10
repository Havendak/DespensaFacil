package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import br.com.bsbapps.despensafacil.domain.PantryItem;

public class ShowAlertListActivity extends AppCompatActivity  {
    private ListView alertListView;
    private SimpleCursorAdapter alertListAdapter;
    private int currentList;
    int alertDays = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_alert_list);

        // Captura código da lista selecionada atualmente
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        currentList = sharedPref.getInt("br.com.bsbapps.despensafacil.CURRENT_LIST_ID", 1);

        //cria objetos
        alertListView = (ListView) findViewById(R.id.alertListView);
        TextView listTitle = (TextView) findViewById(R.id.textViewTitle);
        listTitle.setText("A vencer em até " + alertDays + "dias:");

        //preenche lista
        String[] from = new String[]{"product_name"};
        int[] to = new int[]{R.id.alertTextView};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            alertListAdapter = new SimpleCursorAdapter(
                    ShowAlertListActivity.this, R.layout.alert_list_item, null, from, to, 0);
        }
        alertListView.setAdapter(alertListAdapter);
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
            startActivity(new Intent(getApplicationContext(), ShowAlertListActivity.class));
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
        alertListAdapter.changeCursor(null);
        super.onStop();
    }

    private class GetProductTask extends AsyncTask<Object, Object, Cursor> {
        PantryItem pantryItem = new PantryItem(getApplicationContext());

        @Override
        protected Cursor doInBackground(Object... params) {
            return pantryItem.getAlertProducts(alertDays);
        }

        @Override
        protected void onPostExecute(Cursor result) {
            alertListAdapter.changeCursor(result);
        }
    }
}

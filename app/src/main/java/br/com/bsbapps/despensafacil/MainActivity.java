package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new createFirstList().execute(new Long[] {null});

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
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (item.getItemId()==R.id.menu_main_action_add_product) {
            startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
        }
        return true;
    }

    private class createFirstList extends AsyncTask<Long, Object, Cursor>{
        DatabaseConnector dbConnector = new DatabaseConnector(MainActivity.this);

        @Override
        protected Cursor doInBackground(Long... params){
            dbConnector.open();
            return dbConnector.getDefaultList();
        }

        @Override
        protected void onPostExecute(Cursor result){
            super.onPostExecute(result);
            Long id;
            if(result.getCount() == 0) {
                id = dbConnector.insertList("Primeira Lista", 1);
            } else {
                result.moveToFirst();
                id = result.getLong(result.getColumnIndex("user_list_id"));
            }
            result.close();
            dbConnector.close();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("br.com.bsbapps.despensafacil.CURRENT_LIST_ID", id); // value to store
            editor.apply();
        }
    }

    private void testaTabela(SQLiteDatabase db) {

        Cursor cursor = db.rawQuery("select NOME_PRODUTO from PRODUTO",null);

        cursor.moveToFirst();
        List<String> produto = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            produto.add(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));//add the item
            cursor.moveToNext();
        }
        cursor.close();
        if(produto.size()>1) {
            Toast.makeText(this, produto.get(1), Toast.LENGTH_LONG).show();
        }
    }
}
package br.com.bsbapps.despensafacil;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.bsbapps.despensafacil.domain.PantryList;

public class MainActivity extends AppCompatActivity {
    PantryList pantryList = new PantryList(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new createFirstList().execute(new Long[]{null});
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

    private class createFirstList extends AsyncTask<Long, Object, Cursor> {

        @Override
        protected Cursor doInBackground(Long... params){
            return pantryList.getDefaultList();
        }

        @Override
        protected void onPostExecute(Cursor result){
            super.onPostExecute(result);
            Long id;
            if(result.getCount() == 0) {
                pantryList.setListName("Despensa Padrão");
                pantryList.setStatus(PantryList.PANTRY_LIST_STATUS_DEFAULT);
                id = pantryList.insert();
            } else {
                result.moveToFirst();
                id = result.getLong(result.getColumnIndex(DatabaseOpenHelper.COLUMN_LIST_ID));
            }
            result.close();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("br.com.bsbapps.despensafacil.CURRENT_PANTRY_LIST_ID", id); // value to store
            editor.apply();
        }
    }
}
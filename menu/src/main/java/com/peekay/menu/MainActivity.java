package com.peekay.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.choose:
                Toast.makeText(MainActivity.this,"1",Toast.LENGTH_LONG).show();
                break;
            case R.id.choose1:
                Toast.makeText(MainActivity.this,"2",Toast.LENGTH_LONG).show();
                break;
            case R.id.item1:
                Toast.makeText(MainActivity.this,"3",Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                Toast.makeText(MainActivity.this,"4",Toast.LENGTH_LONG).show();
                break;
            case R.id.item3:
                Toast.makeText(MainActivity.this,"5",Toast.LENGTH_LONG).show();
                break;
            case R.id.item4:
                Toast.makeText(MainActivity.this,"6",Toast.LENGTH_LONG).show();
                break;
            case R.id.item5:
                Toast.makeText(MainActivity.this,"7",Toast.LENGTH_LONG).show();
                break;
            case R.id.item6:
                Toast.makeText(MainActivity.this,"8",Toast.LENGTH_LONG).show();
                break;
            case R.id.item7:
                Toast.makeText(MainActivity.this,"9",Toast.LENGTH_LONG).show();
                break;
            case R.id.item8:
                Toast.makeText(MainActivity.this,"10",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

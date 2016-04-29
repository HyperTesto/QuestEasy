package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import me.hypertesto.questeasy.R;

public class EditDecActivity extends AppCompatActivity {

    private FloatingActionButton gotoSelectCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dec);

        gotoSelectCategory = (FloatingActionButton)findViewById(R.id.categoryGuestGo);
        gotoSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditDecActivity.this,NewCardActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.edit_dec_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

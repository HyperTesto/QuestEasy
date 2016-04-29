package me.hypertesto.questeasy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class CreateCard extends AppCompatActivity {

    private FloatingActionButton gotoSelectCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        gotoSelectCategory = (FloatingActionButton)findViewById(R.id.categoryGuestGo);
        gotoSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateCard.this,CardCategoryGuest.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.creation_card_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

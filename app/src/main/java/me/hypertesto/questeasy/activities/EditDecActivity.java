package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.adapters.CardListAdapter;
import me.hypertesto.questeasy.model.listitems.CardListItem;

public class EditDecActivity extends AppCompatActivity {

    private FloatingActionButton gotoSelectCategory;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dec);

        ArrayList<CardListItem> items = new ArrayList<>();
        items.add(new CardListItem("singolo","Pippo","Fuffa",true,null,null));
        items.add(new CardListItem("famiglia","Famiglia Rossi", "3 persone", false, null, null));
        items.add(new CardListItem("gruppo", "Scolaresca", "25 persone", true, null, null));
        items.add(new CardListItem("singolo","Pippo","Fuffa",true,null,null));
        items.add(new CardListItem("famiglia","Famiglia Rossi","3 persone",false,null,null));
        items.add(new CardListItem("gruppo","Scolaresca","25 persone",true,null,null));
        items.add(new CardListItem("singolo","Pippo","Fuffa",true,null,null));
        items.add(new CardListItem("famiglia","Famiglia Rossi","3 persone",false,null,null));
        items.add(new CardListItem("gruppo","Scolaresca","25 persone",true,null,null));
        items.add(new CardListItem("singolo","Pippo","Fuffa",true,null,null));
        items.add(new CardListItem("famiglia","Famiglia Rossi","3 persone",false,null,null));
        items.add(new CardListItem("gruppo","Scolaresca","25 persone",true,null,null));


        CardListAdapter adapter = new CardListAdapter(this,R.layout.card_list_item,items);
        listView = (ListView)findViewById(R.id.cardlistView);
        listView.setAdapter(adapter);

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

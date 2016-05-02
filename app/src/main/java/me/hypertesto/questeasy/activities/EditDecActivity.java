package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.adapters.CardListAdapter;
import me.hypertesto.questeasy.model.listitems.CardListItem;

public class EditDecActivity extends AppCompatActivity {

    private FloatingActionsMenu gotoSelectCategory;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dec);

        ArrayList<CardListItem> items = new ArrayList<>();
        items.add(new CardListItem("singolo", "Pippo", "Fuffa", true, null, null));
        items.add(new CardListItem("famiglia", "Famiglia Rossi", "3 persone", false, null, null));
        items.add(new CardListItem("gruppo", "Scolaresca", "25 persone", true, null, null));
        items.add(new CardListItem("singolo","Pippo","Fuffa",true,null,null));
        items.add(new CardListItem("famiglia","Famiglia Rossi","3 persone",false,null,null));
        items.add(new CardListItem("gruppo","Scolaresca","25 persone",true,null,null));
        items.add(new CardListItem("singolo","Pippo","Fuffa",true,null,null));
        items.add(new CardListItem("famiglia","Famiglia Rossi","3 persone",false,null,null));
        items.add(new CardListItem("gruppo","Scolaresca","25 persone",true,null,null));
        items.add(new CardListItem("singolo","Pippo","Fuffa",true,null,null));
        items.add(new CardListItem("famiglia","Famiglia Rossi","3 persone",false,null,null));
        items.add(new CardListItem("gruppo", "Scolaresca", "25 persone", true, null, null));


        CardListAdapter adapter = new CardListAdapter(this,R.layout.card_list_item,items);
        listView = (ListView)findViewById(R.id.cardlistView);
        listView.setAdapter(adapter);

        /*gotoSelectCategory = (FloatingActionsMenu)findViewById(R.id.categoryGuestGo);
        gotoSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(EditDecActivity.this,NewCardActivity.class));
            }
        });
        */
        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameButtonCategory);

        try {
            frameLayout.getBackground().setAlpha(0);
            final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.categoryGuestGo);
            fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
                @Override
                public void onMenuExpanded() {
                    frameLayout.getBackground().setAlpha(170);
                    frameLayout.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            fabMenu.collapse();
                            return true;
                        }
                    });
                }

                @Override
                public void onMenuCollapsed() {
                    frameLayout.getBackground().setAlpha(0);
                    frameLayout.setOnTouchListener(null);
                }
            });
        }catch (NullPointerException e){
            Log.e("ERROR","error null field");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.edit_dec_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

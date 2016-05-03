package me.hypertesto.questeasy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Date;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.stubsubs.Card;
import me.hypertesto.questeasy.model.stubsubs.FamilyCard;
import me.hypertesto.questeasy.model.stubsubs.FamilyMemberGuest;
import me.hypertesto.questeasy.model.stubsubs.GroupCard;
import me.hypertesto.questeasy.model.stubsubs.GroupMemberGuest;
import me.hypertesto.questeasy.model.stubsubs.SingleGuestCard;
import me.hypertesto.questeasy.model.stubsubs.adapters.CardListAdapter;

public class EditDecActivity extends AppCompatActivity {

    private FloatingActionsMenu gotoSelectCategory;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dec);

      ArrayList<Card> items = new ArrayList<>();
			items.add(new SingleGuestCard(null, new Date(), 5, true));
			items.add(new FamilyCard(null, new ArrayList<FamilyMemberGuest>(), new Date(), 12, true));
			items.add(new GroupCard(null, new ArrayList<GroupMemberGuest>(), new Date(), 7, true));
			items.add(new SingleGuestCard(null, new Date(), 5, false));
			items.add(new FamilyCard(null, new ArrayList<FamilyMemberGuest>(), new Date(), 12, false));
			items.add(new GroupCard(null, new ArrayList<GroupMemberGuest>(), new Date(), 7, false));
			items.add(new SingleGuestCard(null, new Date(), 5, true));
			items.add(new FamilyCard(null, new ArrayList<FamilyMemberGuest>(), new Date(), 12, true));
			items.add(new GroupCard(null, new ArrayList<GroupMemberGuest>(), new Date(), 7, true));

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

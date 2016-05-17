package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.SingleGuestCard;
import me.hypertesto.questeasy.utils.StaticGlobals;

public class EditCardActivity extends AppCompatActivity {
	private Card card;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_card);

		Intent intent = getIntent();

		Serializable tmp = intent.getSerializableExtra(StaticGlobals.intentExtras.CARD);

		Intent intentToForm = new Intent(EditCardActivity.this, FormGuestActivity.class);

		if (tmp instanceof SingleGuestCard){
			card = (SingleGuestCard) tmp;
			SingleGuestCard sgCard = (SingleGuestCard) card;

			if (sgCard.getGuest() == null){
				//START FORM
				sgCard.setPermanenza(4);

				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, "SINGLE_GUEST");
				startActivityForResult(intentToForm, StaticGlobals.requestCodes.NEW_SINGLE_GUEST);
			}


		} else if (tmp instanceof FamilyCard){
			card = (FamilyCard) tmp;
		} else if (tmp instanceof GroupCard){
			card = (GroupCard) tmp;
		} else {
			throw new RuntimeException("cacca");
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == StaticGlobals.requestCodes.NEW_SINGLE_GUEST){
			if (resultCode == StaticGlobals.resultCodes.NEW_GUEST_SUCCESS){
				Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

				if (s instanceof SingleGuest){
					SingleGuest sg = (SingleGuest) s;
					((SingleGuestCard) card).setGuest(sg);

					System.out.println(((SingleGuestCard) card).getGuest().getName());
				}
			}
		}
	}
}

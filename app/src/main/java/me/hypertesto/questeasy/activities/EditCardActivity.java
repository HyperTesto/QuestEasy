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

public class EditCardActivity extends AppCompatActivity {
	private Card card;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_card);

		Intent intent = getIntent();

		Serializable tmp = intent.getSerializableExtra("me.hypertesto.questeasy.activities.CARD");

		Intent intentToForm = new Intent(EditCardActivity.this, FormGuestActivity.class);

		if (tmp instanceof SingleGuestCard){
			card = (SingleGuestCard) tmp;
			SingleGuestCard sgCard = (SingleGuestCard) card;

			if (sgCard.getGuest() == null){
				//START FORM
				sgCard.setPermanenza(4);

				intentToForm.putExtra("guestType", "SINGLE_GUEST");
				startActivityForResult(intentToForm, 1);
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

		if (requestCode == 1){
			if (resultCode == 1){
				Serializable s = data.getSerializableExtra("CREATED_GUEST");

				if (s instanceof SingleGuest){
					SingleGuest sg = (SingleGuest) s;
					((SingleGuestCard) card).setGuest(sg);

					System.out.println(((SingleGuestCard) card).getGuest().getName());
				}
			}
		}
	}
}

package me.hypertesto.questeasy.model.testcrap;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.SingleGuestCard;
import me.hypertesto.questeasy.model.dao.fs.FSDeclarationDao;

/**
 * Created by rigel on 05/05/16.
 */
public class ModelTestMethods {
	public static void testWriteReadSomeCrap(Context context){
		ArrayList<Declaration> items = new ArrayList<>();
		items.add(new Declaration(new Date(), false));
		items.add(new Declaration(new Date(), false));
		items.add(new Declaration(new Date(), false));

		FSDeclarationDao fsd = new FSDeclarationDao(context);

		fsd.clear();

		fsd.populate();

		fsd.open();

		for (Declaration dec : items){
			//fsd.insertDeclaration(dec);
		}

		ArrayList<Declaration> decs = fsd.getAllDeclarations();

		for (Declaration d : decs){
			System.out.println(d.getDate());
			for (Card c : d){
				System.out.println("> " + c.getTitle());
				if (c instanceof SingleGuestCard){
					SingleGuestCard sgc = (SingleGuestCard) c;
					System.out.println("  > " + sgc.getGuest().getName());
				} else if (c instanceof FamilyCard) {
					FamilyCard fc = (FamilyCard) c;
					System.out.println("  > " + fc.getCapoFamiglia().getName());
				} else if (c instanceof GroupCard){
					GroupCard gc = (GroupCard) c;
					System.out.println("  > " + gc.getCapoGruppo().getName());
				}
			}
			System.out.println("================================================");
		}

		fsd.close();
	}
}

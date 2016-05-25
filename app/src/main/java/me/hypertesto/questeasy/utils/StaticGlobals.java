package me.hypertesto.questeasy.utils;

/**
 * Created by rigel on 17/05/16.
 */
public class StaticGlobals {

	public static final class requestCodes {
		public static final int NEW_SINGLE_GUEST = 1;
		public static final int NEW_FAMILY_HEAD = 2;
		public static final int NEW_FAMILY_MEMBER = 3;
		public static final int NEW_GROUP_HEAD = 4;
		public static final int NEW_GROUP_MEMBER = 5;
		public static final int EDIT_SINGLE_GUEST = 6;
		public static final int EDIT_FAMILY_HEAD = 7;
		public static final int EDIT_FAMILY_MEMBER = 8;
		public static final int EDIT_GROUP_HEAD = 9;
		public static final int EDIT_GROUP_MEMBER = 10;

		public static final int NEW_CARD = 11;
		public static final int EDIT_CARD = 12;
	}

	public static final	class resultCodes {
		public static final int GUEST_FORM_SUCCESS = 1;
		public static final int EDIT_CARD_SUCCESS = 2;
	}

	public static final class intentExtras {
		public static final	String GUEST_TYPE = "me.hypertesto.questeasy.activities.GUEST_TYPE";
		public static final String DECLARATION = "me.hypertesto.questeasy.activities.DECLARATION";
		public static final String DECLARATION_DATE = "me.hypertesto.questeasy.activities.DECLARATION_DATE";
		public static final String DECLARATION_OWNER = "me.hypertesto.questeasy.activities.DECLARATION_OWNER";
		public static final String CARD = "me.hypertesto.questeasy.activities.CARD";
		public static final String CREATED_GUEST = "me.hypertesto.questeasy.activities.CREATED_GUEST";
		public static final String PERMANENZA = "me.hypertesto.questeasy.activities.PERMANENZA";
		public static final String GUEST_TO_EDIT = "me.hypertesto.questeasy.activities.GUEST_TO_EDIT";
	}

	public static final class saveDialogOptions {
		public static final String SAVE_DISK = "Memoria interna";
		public static final String SHARE = "Condividi";
	}

	public static final class filterDialogOptions {
		public static final String FILTER_SINGLE  = "Ospite singolo";
		public static final String FILTER_FAMILY = "Famiglia";
		public static final String FILTER_GROUP = "Gruppo";
	}
}

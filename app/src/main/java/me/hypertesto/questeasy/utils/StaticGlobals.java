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
	}

	public static final	class resultCodes {
		public static final int NEW_GUEST_SUCCESS = 1;
	}

	public static final class intentExtras {
		public static final	String GUEST_TYPE = "me.hypertesto.questeasy.activities.GUEST_TYPE";
		public static final String DECLARATION = "me.hypertesto.questeasy.activities.DECLARATION";
		public static final String CARD = "me.hypertesto.questeasy.activities.CARD";
		public static final String CREATED_GUEST = "me.hypertesto.questeasy.activities.CREATED_GUEST";
	}

}

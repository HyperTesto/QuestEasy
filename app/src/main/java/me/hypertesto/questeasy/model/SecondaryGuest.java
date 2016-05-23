package me.hypertesto.questeasy.model;

/**
 * Created by rigel on 03/05/16.
 */
public abstract class SecondaryGuest extends Guest {

	@Override
	public boolean equals(Object o){
		if (o != null){
			if (o instanceof SecondaryGuest){
				SecondaryGuest sgo = (SecondaryGuest) o;

				if (this.name.equals(sgo.getName())){
					if (this.surname.equals(sgo.getSurname())){
						if (this.birthDate.equals(sgo.getBirthDate())){
							if (this.sex.equals(sgo.getSex())){
								if (this.placeOfBirth.equals(sgo.getPlaceOfBirth())){
									if (this.cittadinanza.equals(sgo.getCittadinanza())){
										return this.getClass().equals(sgo.getClass());
									}
								}
							}
						}
					}
				}
			}
		}

		return false;
	}
}

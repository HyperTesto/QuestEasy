package me.hypertesto.questeasy.model;

/**
 * Classe che specializza l'ospite completo, usata per l'ospite singolo, il capo famiglia e
 * il capo gruppo.
 * Created by rigel on 02/05/16.
 */
public abstract class MainGuest extends Guest {
	protected Documento documento;

	@Override
	public boolean isComplete() {
		if (!super.isComplete()){
			return false;
		} else if (documento == null || !documento.isComplete()){
				return false;
		}

		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null){
			if (o instanceof MainGuest){
				MainGuest mgo = (MainGuest) o;
				return (this.documento.equals(mgo.getDocumento()) &&
								this.getClass().equals(mgo.getClass()));
			}
		}

		return false;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
}

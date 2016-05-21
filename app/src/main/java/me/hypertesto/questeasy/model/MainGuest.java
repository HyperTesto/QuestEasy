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
		} else if (documento == null){
				return false;
		}

		return true;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
}

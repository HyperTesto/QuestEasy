package me.hypertesto.questeasy.model.other;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by rigel on 05/05/16.
 */
public class AppendingObjectOutputStream extends ObjectOutputStream {
	protected AppendingObjectOutputStream() throws IOException {}

	public AppendingObjectOutputStream(OutputStream output) throws IOException {
		super(output);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		reset();
	}
}

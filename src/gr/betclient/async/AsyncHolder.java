package gr.betclient.async;

import java.io.Serializable;
import java.util.List;

public interface AsyncHolder {
	
	void onAsyncFinished(List<? extends Serializable> objectList);

}

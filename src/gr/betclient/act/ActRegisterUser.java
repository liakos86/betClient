package gr.betclient.act;

import gr.betclient.R;
import gr.betclient.async.AsyncCreateUser;
import gr.betclient.model.user.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ActRegisterUser
extends Activity {
	
	EditText editTextUsername;
	
	Button buttonSaveUser;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupViews();
    }
    
	void setupViews() {
		editTextUsername = (EditText) findViewById(R.id.editTextUsername);
		buttonSaveUser = (Button) findViewById(R.id.buttonSaveUser);
		buttonSaveUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				User user = new User();
				user.setUsername(editTextUsername.getText().toString());
				new AsyncCreateUser(user,((BetClientApplication) getApplication())).execute();
			}
		});
	}

}

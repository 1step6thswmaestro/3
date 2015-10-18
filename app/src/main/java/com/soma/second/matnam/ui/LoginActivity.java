package com.soma.second.matnam.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.faradaj.blurbehind.BlurBehind;
import com.soma.second.matnam.R;

public class LoginActivity extends Activity implements View.OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		blurBehindBackAcitivity();

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
	}

	public void blurBehindBackAcitivity() {
		BlurBehind.getInstance()
				.withAlpha(150)
				.withFilterColor(Color.parseColor("#696969"))
				.setBackground(this);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.loginButton :
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				break;
		}
	}
}

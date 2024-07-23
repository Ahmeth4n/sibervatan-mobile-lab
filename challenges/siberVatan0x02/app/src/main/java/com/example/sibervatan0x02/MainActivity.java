package com.example.sibervatan0x02;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvResult = findViewById(R.id.tvResult);

        btnLogin.setOnClickListener(view -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            // Kullanıcı adı ve şifre kontrolü
            if (username.equals("sibervatan") && checkPassword(password)) {
                etUsername.setText("");
                etPassword.setText("");
                tvResult.setText("Giriş başarılı!");
                Toast.makeText(MainActivity.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
            } else {
                tvResult.setText("Kullanıcı adı veya şifre yanlış.");
                Toast.makeText(MainActivity.this, "Kullanıcı adı veya şifre yanlış.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkPassword(String password) {
        if (password.length() != 11) {
            return false;
        }
        char[] pass = password.toCharArray();


        return pass[9] == 'n' && pass[5] == 'v' && pass[8] == '4' &&
                pass[3] == '3' && pass[7] == 't' && pass[0] == 's' &&
                pass[6] == '4' && pass[2] == 'b' && pass[10] == '!' &&
                pass[4] == 'r' && pass[1] == '1';
    }
}

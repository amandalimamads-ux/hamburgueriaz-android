package com.example.hamburgueriaz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int quantidade = 0;

    private TextView txtQuantidade;
    private TextView resumoPedido;
    private EditText editNome;
    private CheckBox checkBacon, checkQueijo, checkOnion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtQuantidade = findViewById(R.id.txtQuantidade);
        resumoPedido = findViewById(R.id.resumoPedido);
        editNome = findViewById(R.id.editNome);
        checkBacon = findViewById(R.id.checkBacon);
        checkQueijo = findViewById(R.id.checkQueijo);
        checkOnion = findViewById(R.id.checkOnion);

        Button btnMais = findViewById(R.id.btnMais);
        Button btnMenos = findViewById(R.id.btnMenos);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        btnMais.setOnClickListener(v -> {
            quantidade++;
            txtQuantidade.setText(String.valueOf(quantidade));
        });

        btnMenos.setOnClickListener(v -> {
            if (quantidade > 0) {
                quantidade--;
                txtQuantidade.setText(String.valueOf(quantidade));
            }
        });

        btnEnviar.setOnClickListener(v -> enviarPedido());
    }

    private void enviarPedido() {

        String nome = editNome.getText().toString();

        boolean temBacon = checkBacon.isChecked();
        boolean temQueijo = checkQueijo.isChecked();
        boolean temOnion = checkOnion.isChecked();

        double precoBase = 20;
        double precoFinal = precoBase;

        if (temBacon) precoFinal += 2;
        if (temQueijo) precoFinal += 2;
        if (temOnion) precoFinal += 3;

        precoFinal = precoFinal * quantidade;

        String resumo =
                "Nome do cliente: " + nome + "\n" +
                        "Tem Bacon? " + (temBacon ? "Sim" : "Não") + "\n" +
                        "Tem Queijo? " + (temQueijo ? "Sim" : "Não") + "\n" +
                        "Tem Onion Rings? " + (temOnion ? "Sim" : "Não") + "\n" +
                        "Quantidade: " + quantidade + "\n" +
                        "Preço final: R$ " + precoFinal;

        resumoPedido.setText(resumo);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nome);
        intent.putExtra(Intent.EXTRA_TEXT, resumo);

        startActivity(Intent.createChooser(intent, "Escolha o aplicativo de email"));
        }
    }
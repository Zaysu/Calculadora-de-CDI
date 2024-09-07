package com.jc.calculadoracdi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class MainActivity extends Activity {

    private EditText vencimento;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        EditText valorAplicado = findViewById(R.id.valorAplicado);
        vencimento = findViewById(R.id.vencimento);
        EditText percent = findViewById(R.id.percent);

        // Configurando o DatePicker para o campo vencimento
        vencimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Button button = findViewById(R.id.btn_secondscreen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Captura os valores digitados
                String valorAplicadoStr = valorAplicado.getText().toString();
                String vencimentoStr = vencimento.getText().toString();
                String percentStr = percent.getText().toString();

                // Cria uma Intent para passar os dados para a segunda tela
                Intent intent = new Intent(MainActivity.this, ActivityResult.class);
                intent.putExtra("VALOR_APLICADO", valorAplicadoStr);
                intent.putExtra("VENCIMENTO", vencimentoStr);
                intent.putExtra("PERCENT", percentStr);

                // Inicia a segunda atividade
                startActivity(intent);
            }
        });
    }

    // Método para exibir o DatePickerDialog
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Define a data selecionada no EditText
                        vencimento.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}

package com.jc.calculadoracdi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActivityResult extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        // Recupera os dados enviados da MainActivity
        String valorAplicadoStr = getIntent().getStringExtra("VALOR_APLICADO");
        String vencimentoStr = getIntent().getStringExtra("VENCIMENTO");
        String percentStr = getIntent().getStringExtra("PERCENT");

        // Conversões e cálculos necessários
        double valorAplicado = Double.parseDouble(valorAplicadoStr);
        double percentualCDI = Double.parseDouble(percentStr);

        // Formatação de números e datas
        DecimalFormat df = new DecimalFormat("#,##0.00");

        // Simulação dos cálculos
        double rendimento = valorAplicado * 0.088; // cálculo de rendimento
        double valorBruto = valorAplicado + rendimento;
        double ir = rendimento * 0.15; // cálculo de IR (15%)
        double valorLiquido = valorBruto - ir;

        // Data atual e de resgate (vencimento)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();

        Date vencimentoDate = null;
        try {
            vencimentoDate = sdf.parse(vencimentoStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long diasCorridos = 0;
        if (vencimentoDate != null) {
            long diffInMillis = vencimentoDate.getTime() - currentDate.getTime();
            diasCorridos = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        }

        // Rendimento mensal e rentabilidades
        double rendimentoMensal = rendimento / diasCorridos * 30;
        double rentabilidadeAnual = percentualCDI * 0.12;
        double rentabilidadePeriodo = rendimento / valorAplicado * 100;

        // Atualizando os TextViews com as informações calculadas
        TextView resultadoTextView = findViewById(R.id.resultadoTextView);
        TextView rendimentoTextView = findViewById(R.id.rendimentoTextView);
        TextView valorAplicadoTextView = findViewById(R.id.valorAplicadoTextView);
        TextView valorBrutoTextView = findViewById(R.id.valorBrutoTextView);
        TextView irTextView = findViewById(R.id.irTextView);
        TextView valorLiquidoTextView = findViewById(R.id.valorLiquidoTextView);
        TextView dataResgateTextView = findViewById(R.id.dataResgateTextView);
        TextView diasCorridosTextView = findViewById(R.id.diasCorridosTextView);
        TextView rendimentoMensalTextView = findViewById(R.id.rendimentoMensalTextView);
        TextView percentualCDITextView = findViewById(R.id.percentualCDITextView);
        TextView rentabilidadeAnualTextView = findViewById(R.id.rentabilidadeAnualTextView);
        TextView rentabilidadePeriodoTextView = findViewById(R.id.rentabilidadePeriodoTextView);

        // Exibindo os dados
        resultadoTextView.setText("Resultado da simulação do CDI:" + df.format(valorBruto));
        rendimentoTextView.setText("Rendimento total: R$ " + df.format(rendimento));
        valorAplicadoTextView.setText("Valor aplicado inicialmente: R$ " + df.format(valorAplicado));
        valorBrutoTextView.setText("Valor bruto sobre o investimento: R$ " + df.format(valorBruto));
        irTextView.setText("IR sobre o investimento: R$ " + df.format(ir));
        valorLiquidoTextView.setText("Valor líquido do investimento: R$ " + df.format(valorLiquido));
        dataResgateTextView.setText("Data de resgate: " + vencimentoStr);
        diasCorridosTextView.setText("Dias corridos: " + diasCorridos);
        rendimentoMensalTextView.setText("Rendimento mensal: R$ " + df.format(rendimentoMensal));
        percentualCDITextView.setText("Percentual do CDI do investimento: " + df.format(percentualCDI) + "%");
        rentabilidadeAnualTextView.setText("Rentabilidade anual: " + df.format(rentabilidadeAnual) + "%");
        rentabilidadePeriodoTextView.setText("Rentabilidade no período: " + df.format(rentabilidadePeriodo) + "%");

        //botão para voltar e calcular novamente
        Button btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Voltar para a MainActivity
                Intent intent = new Intent(ActivityResult.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Limpa a pilha de atividades
                startActivity(intent);
                finish();
            }
        });
    }
}

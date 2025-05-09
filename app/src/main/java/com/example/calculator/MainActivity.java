package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Zmienna dla TextView i MaterialButton
    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Przypisanie zmiennych
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        // Przypisanie przyciskówa
        assignId(buttonC, R.id.button_c1);
        assignId(buttonBrackOpen, R.id.button_nawias1_1);
        assignId(buttonBrackClose, R.id.button_nawias2_1);
        assignId(buttonDivide, R.id.button_divide1);
        assignId(buttonMultiply, R.id.button_multi);
        assignId(buttonPlus, R.id.button_add);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonEquals, R.id.result);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonAC, R.id.button_ac);
        assignId(buttonDot, R.id.button_dot);
    }

    // metoda przypisująca przycisk do TextView i nasłuchująca na kliknięcia
    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        // "AC" (resetuje ekran)
        if (buttonText.equals("Ac")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        // "=" (wykonuje obliczenia)
        if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }

        // "C" (usuwanie ostatniego znaku)
        if (buttonText.equals("C")) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
        } else {

            if (dataToCalculate.equals("0")) {
                dataToCalculate = buttonText;
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        }


        solutionTv.setText(dataToCalculate);


        String finalResult = getResult(dataToCalculate);


        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }


    String getResult(String data) {
        try {

            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();


            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();


            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {

            return "Err";
        } finally {
            Context.exit();
        }
    }
}

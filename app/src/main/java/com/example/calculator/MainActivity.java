package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    //display to hold operands and type of calculations
    private Double operand1 = null;
    private String pendingOperation = "=";

    //tim's bug solution
    private static String STATE_PENDING_OPERATION = "pending";
    private static String STATE_OPERAND = "operand";
    //end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_look);

        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonNeg = findViewById(R.id.buttonNeg);
        Button buttonC = findViewById(R.id.buttonC);
        Button buttonPercentage=findViewById(R.id.buttonPercentage);
        Button buttonDelete=findViewById(R.id.buttonDelete);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };

        View.OnClickListener listenerForNeg = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = newNumber.getText().toString();
                if (temp.length() == 0) {
                    newNumber.setText("-");
                } else {
                    try {
                        Double value = Double.valueOf(temp);
                        value *= -1;
                        newNumber.setText(value.toString());
                    } catch (NumberFormatException e) {
                        newNumber.setText("");
                    }
                }

            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);


        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();

                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newNumber.getText().toString().length()!=0){
                    String text=newNumber.getText().toString();
                    newNumber.setText(text.substring(0,text.length()-1));
                }

            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operand1 = null;
                newNumber.setText("");
                result.setText("");
                displayOperation.setText("");
            }
        });

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonPercentage.setOnClickListener(opListener);
        buttonNeg.setOnClickListener(listenerForNeg);
    }


    private void performOperation(Double value, String operation) {
        if (null == operand1) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "%":
                    operand1=(operand1*value)/100;


            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /* mysolution
        displayOperation.setText(savedInstanceState.getString(temp));
        performOperation(Double.valueOf(result.getText().toString()),displayOperation.getText().toString());
        pendingOperation = displayOperation.getText().toString();
        //end */

        //tim's bug solution
        operand1 = savedInstanceState.getDouble(STATE_OPERAND);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        displayOperation.setText(pendingOperation);
        //end
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        /*mysolution
        outState.putString(temp,displayOperation.getText().toString());
        end */

        //tim's solution
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND, operand1);
        }
        //end
        super.onSaveInstanceState(outState);
    }

}

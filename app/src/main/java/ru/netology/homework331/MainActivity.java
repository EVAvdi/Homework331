package ru.netology.homework331;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    TextView result;
    TextView operation;
    EditText number;
    Double operand = null;
    String lastOperation = "=";
    ConstraintLayout gan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView)findViewById(R.id.resultField);
        operation = (TextView)findViewById(R.id.operationField);
        number = (EditText)findViewById(R.id.numberField);
        gan = (ConstraintLayout) findViewById(R.id.generatoin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.menu_basice_calc :
                gan.setVisibility(View.GONE);
                break;
            case R.id.menu_engan_calc :
                gan.setVisibility(View.VISIBLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null){
            outState.putDouble("OPERAND", operand);
        }
        outState.putString("RESULT", result.getText().toString());
        outState.putString("ALLOPERATION", operation.getText().toString());
        outState.putString("NUMBER", number.getText().toString());
        if(gan.getVisibility() == View.VISIBLE){
        outState.putBoolean("EngiCalc", true);
        } else {outState.putBoolean("EngiCalc", false);
        }

        super.onSaveInstanceState(outState);
    }
    protected  void onRestoreInstanceState(Bundle savedInstenceState){
        super.onRestoreInstanceState(savedInstenceState);
        lastOperation = savedInstenceState.getString("OPERATION");
        operand = savedInstenceState.getDouble("OPERAND");
        result.setText(operand.toString());
        operation.setText(lastOperation);
        if (savedInstenceState.getBoolean("EngiCalc")){
        gan.setVisibility(View.VISIBLE);
        }else{gan.setVisibility(View.GONE);
    }}
    public void onNumberClick(View view){
        Button button = (Button) view;
        number.append(button.getText());
        if(lastOperation.equals("=")&&(operand!=null)){
            operand=null;
        }
    }
    public void onOperationClick (View view){
        Button button = (Button)view;
        String op = button.getText().toString();
        String numb = number.getText().toString();
        if(numb.length()>0){
            numb = numb.replace(',','.');
            try {
                performOperaton(Double.valueOf(numb), op);
            }
            catch (NumberFormatException ex){
                number.setText("");
            }
        }
        lastOperation = op;
        operation.setText(lastOperation);
    }
    private void performOperaton(Double numb, String operation){
        if(operand==null){
            operand = numb;
        }
        else {
            if(lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=":
                    operand = numb;
                    break;
                case "-":
                    operand -= numb;
                    break;
                case "+":
                    operand += numb;
                    break;
                case "*":
                    operand *= numb;
                    break;
                case "/":
                    if (numb == 0) {
                        operand = 0.0;
                    } else {
                        operand /= numb;
                    }
                    break;
                case "%":
                    operand = operand/100*numb;
                    break;
                case "+/-":
                    operand = -operand;
                    break;
            }
        }

        result.setText(operand.toString().replace(',','.'));
        number.setText("");
    }
}

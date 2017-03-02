// MainActivity.java
// Calculates a bill total based on a tip percentage
package com.deitel.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;

// MainActivity class for the Tip Calculator app
public class MainActivity extends AppCompatActivity {

   // currency and percent formatter objects
   private static final NumberFormat currencyFormat =
           NumberFormat.getCurrencyInstance();
   private static final NumberFormat percentFormat =
           NumberFormat.getPercentInstance();

   //private double billAmount = 0.0; // bill amount entered by the user
   //private double percent = 0.15; // initial tip percentage


   private String billAmount = "0.0"; // bill amount entered by the user
   private double percent = 0.15; // initial tip percentage

   private TextView amountTextView; // shows formatted bill amount
   private TextView percentTextView; // shows tip percentage
   private TextView tipTextView; // shows calculated tip amount
   private TextView totalTextView; // shows calculated total bill amount

   private BigDecimal bigBillAmount = new BigDecimal(billAmount);
   private BigDecimal bigPerenct = new BigDecimal("0.15");
   private BigDecimal bigTip = new BigDecimal("0.0");
   private BigDecimal bigTotal = new BigDecimal("0.0");
   private BigDecimal bigOneHundred = new BigDecimal("100.0");

   // called when the activity is first created
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState); // call superclass onCreate
      setContentView(R.layout.activity_main); // inflate the GUI

      // get references to programmatically manipulated TextViews
      amountTextView = (TextView) findViewById(R.id.amountTextView);
      percentTextView = (TextView) findViewById(R.id.percentTextView);

      tipTextView = (TextView) findViewById(R.id.tipTextView);
      totalTextView = (TextView) findViewById(R.id.totalTextView);
      tipTextView.setText(currencyFormat.format(0));
      totalTextView.setText(currencyFormat.format(0));

      // set amountEditText's TextWatcher
      EditText amountEditText =
              (EditText) findViewById(R.id.amountEditText);
      amountEditText.addTextChangedListener(amountEditTextWatcher);

      // set percentSeekBar's OnSeekBarChangeListener
      SeekBar percentSeekBar =
              (SeekBar) findViewById(R.id.percentSeekBar);
      percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
   }

   // calculate and display tip and total amounts
   private void calculate() {
      // format percent and display in percentTextView
//this percentFormat should be change to bigDecimal to avoid the use of double!!!!------------
      //bigBillAmount = bigDs.divide(bigOneHundred, 2,BigDecimal.ROUND_CEILING);
      percentTextView.setText(percentFormat.format(percent));
      //percentTextView.setText(String.valueOf(bigPerenct));
      // calculate the tip and total
      //double tip = billAmount * percent;
      //double total = billAmount + tip;
      bigTip = bigBillAmount.multiply(bigPerenct);

      bigTotal = bigBillAmount.add(bigTip);
      //BigDecimal bigTip = new BigDecimal(Double.toString(tip));
      //BigDecimal bigTotal = new BigDecimal(Double.toString(total));

      // display tip and total formatted as currency
      //tipTextView.setText(currencyFormat.format(tip));
      //totalTextView.setText(currencyFormat.format(total));

      tipTextView.setText(currencyFormat.format(bigTip));
      totalTextView.setText(currencyFormat.format(bigTotal));
   }

   // listener object for the SeekBar's progress changed events
   private final OnSeekBarChangeListener seekBarListener =
           new OnSeekBarChangeListener() {
              // update percent, then call calculate
              @Override
              public void onProgressChanged(SeekBar seekBar, int progress,
                                            boolean fromUser) {
                 //------------should use BigDecimal calculation
                 BigDecimal bigProgress = new BigDecimal(progress);

                 bigPerenct = bigProgress.divide(bigOneHundred, 2 , BigDecimal.ROUND_CEILING);
                 percent = progress / 100.0; // set percent based on progress
                 calculate(); // calculate and display tip and total
              }

              @Override
              public void onStartTrackingTouch(SeekBar seekBar) { }

              @Override
              public void onStopTrackingTouch(SeekBar seekBar) { }
           };

   // listener object for the EditText's text-changed events
   private final TextWatcher amountEditTextWatcher = new TextWatcher() {
      // called when the user modifies the bill amount
      @Override
      public void onTextChanged(CharSequence s, int start,
                                int before, int count) {

         try { // get bill amount and display currency formatted value
            //billAmount = Double.parseDouble(s.toString()) / 100.0;
            BigDecimal bigDs = new BigDecimal(s.toString());

            bigBillAmount = bigDs.divide(bigOneHundred, 2,BigDecimal.ROUND_CEILING);

            //billAmount = String.valueOf(s.toString() / 100.0);
            //currencyFormat.setParseBigDecimal(true);
            amountTextView.setText(currencyFormat.format(bigBillAmount));
         }
         catch (NumberFormatException e) { // if s is empty or non-numeric
            amountTextView.setText("");
            billAmount = "0.0";
            bigBillAmount = new BigDecimal("0.0");
         }

         calculate(); // update the tip and total TextViews
      }

      @Override
      public void afterTextChanged(Editable s) { }

      @Override
      public void beforeTextChanged(
              CharSequence s, int start, int count, int after) { }
   };
}

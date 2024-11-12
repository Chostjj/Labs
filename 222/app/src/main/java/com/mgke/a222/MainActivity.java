package com.mgke.a222;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Switch temperatureSwitch;
    private ImageView temperatureImage;
    private CheckBox milkCheckbox, creamCheckbox, sugarCheckbox, syrupCheckbox;
    private ImageButton submitButton;
    private RadioGroup deliveryGroup;
    private RadioButton pickupRadio, deliveryRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        temperatureSwitch = findViewById(R.id.temperature_switch);
        temperatureImage = findViewById(R.id.temperature_image);

        milkCheckbox = findViewById(R.id.milk_checkbox);
        creamCheckbox = findViewById(R.id.cream_checkbox);
        sugarCheckbox = findViewById(R.id.sugar_checkbox);
        syrupCheckbox = findViewById(R.id.syrup_checkbox);

        deliveryGroup = findViewById(R.id.delivery_group);
        pickupRadio = findViewById(R.id.pickup_radio);
        deliveryRadio = findViewById(R.id.delivery_radio);

        submitButton = findViewById(R.id.submit_button);

        // Обработка переключения состояния Switch (горячий/холодный)
        temperatureImage.setImageResource(R.drawable.blue_circle); // Установите синий кружок для холодного
        temperatureSwitch.setText("Холодный");

        // Обработка изменения состояния Switch (горячий/холодный)
        temperatureSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                temperatureImage.setImageResource(R.drawable.red_circle); // Горячий кофе
                temperatureSwitch.setText("Горячий");
            } else {
                temperatureImage.setImageResource(R.drawable.blue_circle); // Холодный кофе
                temperatureSwitch.setText("Холодный");
            }
        });

        // Обработка нажатия на кнопку
        submitButton.setOnClickListener(v -> {
            StringBuilder orderSummary = new StringBuilder();

            // Температура кофе
            if (temperatureSwitch.isChecked()) {
                orderSummary.append("Горячий кофе");
            } else {
                orderSummary.append("Холодный кофе");
            }

            // Добавки
            orderSummary.append("\nДобавки: ");
            if (milkCheckbox.isChecked()) orderSummary.append("Молоко ");
            if (creamCheckbox.isChecked()) orderSummary.append("Сливки ");
            if (sugarCheckbox.isChecked()) orderSummary.append("Сахар ");
            if (syrupCheckbox.isChecked()) orderSummary.append("Сироп ");

            orderSummary.append("\nСпособ доставки: ");
            if (pickupRadio.isChecked()) {
                orderSummary.append("Самовывоз");
            } else if (deliveryRadio.isChecked()) {
                orderSummary.append("Доставка");
            }

            // Переход на новую активность с заказом
            Intent intent = new Intent(MainActivity.this, OrderSummaryActivity.class);
            intent.putExtra("orderSummary", orderSummary.toString());
            startActivity(intent);

            // Показ Toast уведомления
            Toast.makeText(MainActivity.this, "Заказ выполнил: Мартьянов Ярослав", Toast.LENGTH_SHORT).show();
        });
    }
}
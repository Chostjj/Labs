package com.mgke.a26;

import java.util.Locale;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class StopwatchFragment extends Fragment implements View.OnClickListener {
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    private Handler handler = new Handler();
    private long startTime; // Время, когда таймер был запущен

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateTimer();
            handler.postDelayed(this, 100); // Проверяем каждые 100 мс
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        Button startButton = layout.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        Button stopButton = layout.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        Button resetButton = layout.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        return layout;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_button) {
            onClickStart();
        } else if (v.getId() == R.id.stop_button) {
            onClickStop();
        } else if (v.getId() == R.id.reset_button) {
            onClickReset();
        } else {
            throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
        handler.removeCallbacks(runnable); // Останавливаем таймер
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
            startTime = System.currentTimeMillis(); // Сохраняем текущее время
            handler.post(runnable); // Запускаем таймер
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    private void onClickStart() {
        if (!running) { // Предотвращаем повторный запуск
            running = true;
            startTime = System.currentTimeMillis(); // Обновляем время старта
            handler.post(runnable); // Запускаем таймер
        }
    }

    private void onClickStop() {
        running = false;
    }

    private void onClickReset() {
        running = false;
        seconds = 0;
    }

    private void updateTimer() {
        if (running) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime; // Вычисляем прошедшее время

            if (elapsedTime >= 1000) { // Если прошло 1 секунда
                seconds++; // Увеличиваем секунды
                startTime = currentTime; // Обновляем время старта
            }
        }

        TextView timeView = getView().findViewById(R.id.time_view); // Получаем ссылку на timeView
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        timeView.setText(time);
    }
}

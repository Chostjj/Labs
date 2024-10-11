package com.mgke.a24;


public class Drink {
    private String name;
    private String description;
    private int imageResourceId;

    // Массив статических объектов Drink
    public static final Drink[] drinks = {
            new Drink("Латте", "Кофейный напиток, на основе молока, представляющий собой трёхслойную смесь из пены, молока и кофе эспрессо", R.drawable.latte),
            new Drink("Капучино", "Кофейный напиток итальянской кухни на основе эспрессо с добавлением в него подогретого вспененного молока", R.drawable.capuccino),
            new Drink("Raf", "Кофейный напиток, готовится путём добавления нагретых паром сливок с небольшим количеством пены в одиночную порцию эспрессо", R.drawable.raf)
    };

    // Конструктор класса Drink
    private Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    // Геттер для description
    public String getDescription() {
        return description;
    }

    // Геттер для name
    public String getName() {
        return name;
    }

    // Геттер для imageResourceId
    public int getImageResourceId() {
        return imageResourceId;
    }

    // Переопределение метода toString для возвращения имени напитка
    @Override
    public String toString() {
        return this.name;
    }
}
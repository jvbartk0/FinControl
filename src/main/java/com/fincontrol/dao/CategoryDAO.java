package com.fincontrol.dao;

import com.fincontrol.model.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static final String FILE_PATH = "src/main/resources/data/categories.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Category> loadCategories() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                createDefaultCategories();
            }

            String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            Type listType = new TypeToken<ArrayList<Category>>(){}.getType();
            List<Category> categories = gson.fromJson(json, listType);
            return categories != null ? categories : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar categorias: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveCategories(List<Category> categories) {
        try {
            Files.createDirectories(Paths.get("src/main/resources/data"));
            String json = gson.toJson(categories);
            Files.write(Paths.get(FILE_PATH), json.getBytes());
        } catch (IOException e) {
            System.err.println("Erro ao salvar categorias: " + e.getMessage());
        }
    }

    private void createDefaultCategories() {
        List<Category> defaultCategories = new ArrayList<>();
        defaultCategories.add(new Category("Alimentação"));
        defaultCategories.add(new Category("Transporte"));
        defaultCategories.add(new Category("Moradia"));
        defaultCategories.add(new Category("Saúde"));
        defaultCategories.add(new Category("Educação"));
        defaultCategories.add(new Category("Lazer"));
        defaultCategories.add(new Category("Salário"));
        defaultCategories.add(new Category("Investimentos"));

        saveCategories(defaultCategories);
    }
}
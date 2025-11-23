package com.fincontrol.dao;

import com.fincontrol.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private static final String FILE_PATH = "src/main/resources/data/transactions.json";
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class,
                    new com.google.gson.TypeAdapter<LocalDate>() {
                        @Override
                        public void write(com.google.gson.stream.JsonWriter out, LocalDate value)
                                throws IOException {
                            out.value(value.toString());
                        }
                        @Override
                        public LocalDate read(com.google.gson.stream.JsonReader in)
                                throws IOException {
                            return LocalDate.parse(in.nextString());
                        }
                    })
            .create();

    public List<Transaction> loadTransactions() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            Type listType = new TypeToken<ArrayList<Transaction>>(){}.getType();
            List<Transaction> transactions = gson.fromJson(json, listType);
            return transactions != null ? transactions : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar transações: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveTransactions(List<Transaction> transactions) {
        try {
            Files.createDirectories(Paths.get("src/main/resources/data"));
            String json = gson.toJson(transactions);
            Files.write(Paths.get(FILE_PATH), json.getBytes());
        } catch (IOException e) {
            System.err.println("Erro ao salvar transações: " + e.getMessage());
        }
    }
}
package com.fincontrol;

import com.fincontrol.model.Transaction;
import com.fincontrol.model.TransactionType;
import com.fincontrol.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private Category foodCategory;
    private Category salaryCategory;

    @BeforeEach
    void setUp() {
        foodCategory = new Category("Alimentação");
        salaryCategory = new Category("Salário");
    }

    @Test
    void testTransactionCreation() {
        Transaction transaction = new Transaction(
                LocalDate.of(2024, 1, 15),
                "Supermercado",
                TransactionType.DESPESA,
                foodCategory,
                150.75
        );

        assertNotNull(transaction.getId());
        assertEquals(LocalDate.of(2024, 1, 15), transaction.getDate());
        assertEquals("Supermercado", transaction.getDescription());
        assertEquals(TransactionType.DESPESA, transaction.getType());
        assertEquals(foodCategory, transaction.getCategory());
        assertEquals(150.75, transaction.getAmount());
    }

    @Test
    void testTransactionEquality() {
        Transaction transaction1 = new Transaction(
                LocalDate.now(),
                "Test",
                TransactionType.RECEITA,
                salaryCategory,
                100.0
        );

        Transaction transaction2 = new Transaction(
                LocalDate.now(),
                "Test",
                TransactionType.RECEITA,
                salaryCategory,
                100.0
        );

        // Transações com IDs diferentes não devem ser iguais
        assertNotEquals(transaction1, transaction2);
    }

    @Test
    void testTransactionTypeDescriptions() {
        assertEquals("Receita", TransactionType.RECEITA.getDescricao());
        assertEquals("Despesa", TransactionType.DESPESA.getDescricao());
    }

    @Test
    void testTransactionAmountValidation() {
        Transaction transaction = new Transaction();

        // Teste de valor positivo
        transaction.setAmount(100.0);
        assertEquals(100.0, transaction.getAmount());

        // Teste de valor zero (deve ser permitido para o modelo)
        transaction.setAmount(0.0);
        assertEquals(0.0, transaction.getAmount());
    }
}
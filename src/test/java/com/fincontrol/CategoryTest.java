package com.fincontrol;

import com.fincontrol.model.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    void testCategoryCreation() {
        Category category = new Category("Transporte");

        assertNotNull(category.getId());
        assertEquals("Transporte", category.getName());
    }

    @Test
    void testCategoryEquality() {
        Category category1 = new Category("Saúde");
        Category category2 = new Category("Educação");

        // Categorias com IDs diferentes não devem ser iguais
        assertNotEquals(category1, category2);

        // Uma categoria deve ser igual a si mesma
        assertEquals(category1, category1);
    }

    @Test
    void testCategoryNameUpdate() {
        Category category = new Category("Original");
        assertEquals("Original", category.getName());

        category.setName("Updated");
        assertEquals("Updated", category.getName());
    }

    @Test
    void testCategoryToString() {
        Category category = new Category("Lazer");
        assertEquals("Lazer", category.toString());
    }

    @Test
    void testCategoryHashCode() {
        Category category = new Category("Test");
        assertNotNull(category.hashCode());

        // Hashcode deve ser consistente
        int initialHashCode = category.hashCode();
        assertEquals(initialHashCode, category.hashCode());
    }
}
# 💰 FinControl: Sistema de Controle Financeiro Pessoal

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-blue.svg" alt="Java Version">
  <img src="https://img.shields.io/badge/JavaFX-17-green.svg" alt="JavaFX Version">
  <img src="https://img.shields.io/badge/Build-Maven-red.svg" alt="Build Tool">
  <img src="https://img.shields.io/badge/Persistence-JSON%20(Gson)-lightgrey.svg" alt="Persistence">
  <img src="https://img.shields.io/badge/Tests-JUnit%205-yellow.svg" alt="Testing">
</p>

## 🎯 Visão Geral do Projeto

O **FinControl** é uma aplicação desktop robusta desenvolvida em **JavaFX** e **Java 17** para auxiliar no gerenciamento financeiro pessoal. O projeto demonstra uma arquitetura limpa e modular, utilizando o padrão **MVC (Model-View-Controller)**, e oferece uma interface de usuário moderna com suporte a **Tema Escuro (Dark Theme)**.

A persistência dos dados é realizada de forma simples e eficiente através de arquivos **JSON**, utilizando a biblioteca **Gson**.

## ✨ Funcionalidades Principais

| Funcionalidade | Descrição |
| :--- | :--- |
| **Dashboard Interativo** | Visão geral do saldo atual, receitas e despesas. Inclui gráficos de pizza (gastos por categoria) e de barras (gastos por mês) para análise visual. |
| **Gerenciamento de Transações** | CRUD (Criação, Leitura, Atualização e Exclusão) completo de transações, com campos para data, descrição, tipo (Receita/Despesa), categoria e valor. |
| **Controle de Categorias** | Gerenciamento de categorias personalizáveis para classificar as transações. |
| **Persistência em JSON** | Todos os dados (transações e categorias) são salvos em arquivos JSON, garantindo portabilidade e facilidade de inspeção. |
| **Interface Moderna** | Design limpo e intuitivo, com um tema escuro agradável para longas sessões de uso. |
| **Testes Unitários** | Cobertura de testes com **JUnit 5** para as classes de Model e DAO, garantindo a integridade da lógica de negócio. |

## 🛠️ Tecnologias Utilizadas

O projeto foi construído com as seguintes tecnologias e bibliotecas:

*   **Linguagem:** Java 17+
*   **Interface Gráfica:** JavaFX 17 (com módulos `javafx-controls`, `javafx-fxml`, `javafx-web`)
*   **Gerenciamento de Dependências:** Apache Maven
*   **Serialização JSON:** Google Gson (v2.10.1)
*   **Segurança (Potencial):** jBCrypt (v0.4) - Presente no `pom.xml`, indicando a possibilidade de implementação de hash de senhas.
*   **Testes:** JUnit 5 (v5.9.2)

## 📂 Estrutura do Projeto

O projeto segue o padrão de estrutura do Maven, com uma organização clara de pacotes:

```
FinControl/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com.fincontrol/
    │   │       ├── controller/  (Lógica de controle das telas FXML)
    │   │       ├── dao/         (Camada de acesso a dados - JSON)
    │   │       ├── model/       (Classes de modelo de dados)
    │   │       └── util/        (Classes utilitárias, como navegação)
    │   └── resources/
    │       └── com.fincontrol/
    │           ├── *.fxml       (Arquivos de layout da interface)
    │           ├── style.css    (Estilização da aplicação - Dark Theme)
    │           └── data/        (Arquivos JSON de persistência)
    └── test/
        └── java/
            └── com.fincontrol/  (Testes unitários com JUnit 5)
```

## 🚀 Como Executar o Projeto

### Pré-requisitos

*   **Java Development Kit (JDK) 17** ou superior.
*   **Apache Maven 3.6** ou superior.
*   Uma IDE compatível com Maven e JavaFX (ex: IntelliJ IDEA, VS Code com extensões Java).

### Execução via Linha de Comando

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/jvbartk0/FinControl.git
    cd FinControl
    ```

2.  **Execute a aplicação:**
    O plugin `javafx-maven-plugin` permite a execução direta:
    ```bash
    mvn clean javafx:run
    ```

3.  **Execute os testes unitários:**
    ```bash
    mvn test
    ```

### Configuração em IDEs (Exemplo: IntelliJ IDEA)

1.  **Importar:** Abra o IntelliJ e selecione **File** -> **Open** e escolha a pasta `FinControl`. O IntelliJ deve reconhecer automaticamente o `pom.xml` como um projeto Maven.
2.  **Configurar JDK:** Certifique-se de que o **Project SDK** esteja configurado para o **Java 17**.
3.  **Executar:** A classe principal é `com.fincontrol.Main`. Você pode executá-la diretamente.

> **Nota:** Se houver problemas com a execução do JavaFX, pode ser necessário configurar as VM Options, apontando para o caminho do seu JavaFX SDK, conforme a documentação oficial do OpenJFX.

## 👤 Autor

Este projeto foi desenvolvido por **jvbartk0** para fins de demonstração de uma aplicação JavaFX completa e bem estruturada.

## 📄 Licença

Este projeto é fornecido para fins educacionais e de demonstração. Por favor, consulte a licença específica do projeto, se houver.

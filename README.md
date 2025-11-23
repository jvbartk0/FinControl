FinControl - Sistema de Controle Financeiro Pessoal
Descrição
O FinControl é uma aplicação JavaFX para controle financeiro pessoal que permite gerenciar transações, categorias e visualizar relatórios através de gráficos interativos.

Funcionalidades
Login Simples - Tela de entrada sem validação real

Dashboard - Visão geral com saldo, receitas e despesas

Gráficos - Pizza (gastos por categoria) e Barras (gastos por mês)

Transações - CRUD completo com data, descrição, tipo, categoria e valor

Categorias - Gerenciamento de categorias personalizáveis

Persistência - Dados salvos em JSON

Tema Dark - Interface moderna com tema escuro

Testes Unitários - Cobertura com JUnit 5

Tecnologias Utilizadas
Java 17 - Linguagem de programação

JavaFX 17 - Framework para interface gráfica

Maven - Gerenciamento de dependências

Gson - Serialização JSON

JUnit 5 - Testes unitários

Estrutura do Projeto
A estrutura do projeto segue o padrão Maven com separação clara de pacotes:

src/main/java/com/fincontrol/ - Código fonte da aplicação

src/main/resources/com/fincontrol/ - Arquivos FXML e CSS

src/test/java/com/fincontrol/ - Testes unitários

Como Executar
Pré-requisitos
Java 17 ou superior

Maven 3.6 ou superior

IntelliJ IDEA (recomendado) ou outra IDE

Configuração no IntelliJ IDEA
Importar o projeto:

File → Open → Selecione a pasta do projeto

Import como Maven project

Configurar SDK:

File → Project Structure → Project Settings → Project

Definir Project SDK: Java 17

Project language level: 17

Configurar JavaFX:

Adicione as VM Options para o módulo principal:
--module-path "caminho/para/javafx-sdk-17.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.web

Substitua "caminho/para/javafx-sdk-17.0.2" pelo caminho real do JavaFX SDK

Executar a aplicação:

Clique com botão direito em Main.java

Select Run 'Main.main()'

Execução via Terminal
Compilar e executar:
mvn clean javafx:run

Executar testes:
mvn test

Instalação do JavaFX
Baixe o JavaFX SDK 17 do site oficial

Extraia em uma pasta local

Configure as VM Options conforme mostrado acima

Uso da Aplicação
Login: Use qualquer usuário/senha (validação simulada)

Dashboard: Visualize saldo, gráficos e resumo financeiro

Transações: Adicione, edite ou exclua transações

Categorias: Gerencie categorias personalizadas

Persistência: Dados são automaticamente salvos em JSON

Dados de Exemplo
Ao executar pela primeira vez, o sistema cria categorias padrão:

Alimentação, Transporte, Moradia, Saúde, Educação, Lazer, Salário, Investimentos

Autor
Projeto desenvolvido para demonstração de aplicação JavaFX completa.

Licença
Este projeto é para fins educacionais.
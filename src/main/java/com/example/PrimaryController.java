package com.example;

import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController {
    // textField e TextArea são objetos/classes
    @FXML
    ListView<Credencial>listSenha;
    @FXML
    TextField txtChave;
    @FXML
    TextField txtSite;
    @FXML
    TextField txtSenha;
    @FXML
    Button proximo;
    @FXML
    Button salvar;
    

    // collections em java - ArrayList
    // <String> - generics
    // inner class x class
    // (classe com metodos de uso interno usaria ele aqui dentro)
    //private ArrayList<Credencial> cre = new ArrayList<>();

    public void adicionarCredencial() {
        var credencial = new Credencial(txtSite.getText(), (txtSenha.getText()) );

        //conectar com o banco
        
        final String USER = "rm99004";
        final String PASS = "270805";
        
        final String URL = "jdbc:oracle:thin:@oracle.com.br:1521:orcl";

        try {
            var con = DriverManager.getConnection(URL, USER, PASS);
            //statement
            // var instrucao = con.createStatement();
            // var sql = "INSERT INTO alunos (nome, turma, rm) VALUES ('"+ aluno.nome() + "', '"+ aluno.turma() +"', "+ aluno.rm() +" )";
            // instrucao.execute(sql);

            var sql = "INSERT INTO Credencial ( Site, Senha) VALUES ( ?, ? )";
            var instrucao = con.prepareStatement(sql);
            instrucao.setString(1, credencial.Site());
            instrucao.setString(2, credencial.Senha());
            instrucao.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION, "Salvo com sucesso");
            alert.show();

            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        //executar o insert

        //desconectar


       
        // txtAlunos.appendText(txtNome.getText() + "\n");
        txtSite.clear();

        adicionarCredencial();
    }

    public void mostrarCredencial() {
        //conectar com o banco
        final String USER = "rm99004";
        final String PASS = "270805";
        
        final String URL = "jdbc:oracle:thin:@oracle.com.br:1521:orcl";

        try {
            var con = DriverManager.getConnection(URL, USER, PASS);

            var sql = "SELECT * FROM credenciais ORDER BY site";
            var instrucao = con.prepareStatement(sql);
            var dados = instrucao.executeQuery();

            while(dados.next()){
                var credencial = new Credencial(
                    dados.getString("Chave"),
                    dados.getString("Site")
                );
                listSenha.getItems().add(credencial);
            }

            con.close();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void verificarCredencial() {
        System.out.println(txtChave.getText().toString().equals("270805"));

       if(txtChave.getText().toString().equals("270805")){
        System.out.println("Ta certo miseravi");
        salvar.setDisable(true);
       }
       else{
        System.out.println("errado");
        salvar.setDisable(false);
       }
    
    }
}

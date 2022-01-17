package com.example.anket;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;


class Globals {
    public static int LoginUserCode = 0;
    ///
}

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        //****************************************burada textimizde kayıtlı boot edilecek kullanıcıları tanımlıyoruz******************************************************
        User[] users = new User[100];

        File file = new File("C:\\Users\\Yusuf\\UserList.txt");
        file.createNewFile();

        for (int i = 0; i < 100; i++) {
            users[i] = new User(i, "", "");
        }

        Label userUsername = new Label(users[Globals.LoginUserCode].name);

        //****************************************burada textimizde kayıtlı kullanıcıları boot ediyoruz******************************************************
        try {
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String remember;
            int counter = -1;
            while (true) {
                remember = fr.readLine();
                counter++;

                if (remember == null) {
                    for (int i = counter; i<100; i++){PrintWriter writer = new PrintWriter(users[i].file);writer.print("");writer.close();PrintWriter writer1 = new PrintWriter(users[i].FriendsFile);writer.print("");writer.close();}
                    break;
                }
                if (users[counter] == null) {
                    break;
                }

                String[] arrOfStr = remember.split(" ", 10);

                users[counter].name = arrOfStr[0];
                users[counter].password = arrOfStr[1];

            }
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //****************************************Paneleri tanımlıyoruz******************************************************

        BorderPane border = new BorderPane();
        Pane MainPane = new Pane();
        ScrollPane PostScrollPane = new ScrollPane();
        border.setPadding(new Insets(0, 0, 0, 0));

        Scene scene = new Scene(border, 1400, 700);

        //****************************************Login ve Register pane'ini oluşturuyoruz******************************************************
        Pane LoginPane = new Pane();

        int offsetX = 575;
        int offsetY = 250;

        Label Username = new Label("Username:");
        LoginPane.getChildren().add(Username);
        Username.setLayoutX(offsetX);
        Username.setLayoutY(offsetY);
        Username.setWrapText(true);
        Username.setPrefWidth(100);

        TextField UsernameB = new TextField();
        LoginPane.getChildren().add(UsernameB);
        UsernameB.setLayoutX(offsetX + 100);
        UsernameB.setLayoutY(offsetY);

        Label Password = new Label("Password:");
        LoginPane.getChildren().add(Password);
        Password.setLayoutX(offsetX);
        Password.setLayoutY(offsetY + 45);
        Password.setWrapText(true);
        Password.setPrefWidth(100);

        TextField PasswordB = new TextField();
        LoginPane.getChildren().add(PasswordB);
        PasswordB.setLayoutX(offsetX + 100);
        PasswordB.setLayoutY(offsetY + 45);



        //Login Butonu

        Button Login = new Button("Login");
        LoginPane.getChildren().add(Login);
        Login.setLayoutX(offsetX + 100);
        Login.setLayoutY(offsetY + 90);

        Login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    BufferedReader fr = new BufferedReader(new FileReader(file));
                    String remember;
                    int counter = -1;
                    while (true) {
                        counter++;
                        String log = (UsernameB.getText() + " " + PasswordB.getText());
                        remember = fr.readLine();

                        if (log.equals(remember)) {
                            System.out.println("Basarili giris");
                            Globals.LoginUserCode = counter;
                            userUsername.setText(users[Globals.LoginUserCode].name);
                            border.setCenter(PostScrollPane);
                            break;
                        }
                        if (remember == null) {
                            System.out.println("Yanlis Girdiniz");
                            showAlertWithoutHeaderText("Yanlis Girdiniz");
                            break;
                        }
                    }
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        //Register Butonu
        Button Register = new Button("Register");
        LoginPane.getChildren().add(Register);
        Register.setLayoutX(offsetX + 180);
        Register.setLayoutY(offsetY + 90);

        Register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {

                    BufferedReader fr = new BufferedReader(new FileReader(file));
                    String user = UsernameB.getText() + " " + PasswordB.getText();
                    String remember;
                    boolean itsSame = false;
                    int counter = -1;
                    while (true) {
                        remember = fr.readLine();
                        counter++;
                        if (remember == null) {
                            break;
                        }

                        if (user.equals(remember)) {
                            itsSame = true;
                            System.out.println("Maalesef Girmis Oldugunuz Isım ya da Sıfre Alinmistir.");
                            showAlertWithoutHeaderText("Maalesef Girmis Oldugunuz Isım ya da Sıfre Alinmistir.");
                            break;
                        }

                    }
                    if (!(itsSame) && !(UsernameB.getText() == null) && !(PasswordB.getText() == null) && !(Objects.equals(UsernameB.getText(), "")) && !(Objects.equals(PasswordB.getText(), ""))) {
                        BufferedWriter fw = new BufferedWriter(new FileWriter(file, true));
                        fw.append(user);// && !(users[counter]==null)
                        String[] arrOfStr = user.split(" ", 10);

                        users[counter].name = arrOfStr[0];
                        users[counter].password = arrOfStr[1];
                        users[counter].code = counter;

                        System.out.println("Hesabınız oluşturulmuştur.");
                        showAlertWithoutHeaderText("Hesabınız oluşturulmuştur.");
                        fw.newLine();
                        fw.close();
                    }
                    else {System.out.println("Hesabınız Maalesef Oluşturulamamıştır.");
                    showAlertWithoutHeaderText("Hesabınız Maalesef Oluşturulamamıştır.");}
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //*******************************Kullanıcı girmiş bulunmakta ve kullanıcının ara yüzü olan pane'i oluşturuyoruz******************************************************

        int offsetMainX = 50;
        int offsetMainY = 50;

        Label labelUsername = new Label("Username:");
        MainPane.getChildren().add(labelUsername);
        labelUsername.setLayoutX(offsetMainX);
        labelUsername.setLayoutY(offsetMainY);
        labelUsername.setWrapText(true);
        labelUsername.setPrefWidth(100);
        labelUsername.setStyle("-fx-background-color: rgba(255, 255, 255, 0.25);");

        //Yukarıda tanımladık Label userUsername = new Label(users[Globals.LoginUserCode].name);
        MainPane.getChildren().add(userUsername);
        userUsername.setLayoutX(offsetMainX + 115);
        userUsername.setLayoutY(offsetMainY);
        userUsername.setWrapText(true);
        userUsername.setPrefWidth(100);
        userUsername.setStyle("-fx-background-color: rgba(255, 255, 255, 0.25);");

        TextField AddFriendT = new TextField();
        MainPane.getChildren().add(AddFriendT);
        AddFriendT.setLayoutX(offsetMainX);
        AddFriendT.setLayoutY(offsetMainY + 40);

        //Add Friend Butonu
        Button AddFriend = new Button("Add Friend");
        MainPane.getChildren().add(AddFriend);
        AddFriend.setLayoutX(offsetMainX + 155);
        AddFriend.setLayoutY(offsetMainY + 40);

        AddFriend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                users[Globals.LoginUserCode].AddFriend(AddFriendT.getText(), users, file);
            }

        });

        Label Posts = new Label("");
        MainPane.getChildren().add(Posts);
        Posts.setLayoutX(offsetMainX + 700);
        Posts.setLayoutY(offsetMainY + 50);
        Posts.setWrapText(true);
        Posts.setPrefWidth(600);
        Posts.setStyle("-fx-border-color: rgba(128, 128, 128, 0.5); -fx-background-color: rgba(255, 255, 255, 0.5);");

        //Show Friend Butonu
        Button ListFriendsPosts = new Button("Show Friends Posts");
        MainPane.getChildren().add(ListFriendsPosts);
        ListFriendsPosts.setLayoutX(offsetMainX + 950);
        ListFriendsPosts.setLayoutY(offsetMainY);

        ListFriendsPosts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Posts.setText(ShowPosts(users[Globals.LoginUserCode], users));
            }

        });

        Label YourFriends = new Label("");
        MainPane.getChildren().add(YourFriends);
        YourFriends.setLayoutX(offsetMainX);
        YourFriends.setLayoutY(offsetMainY + 900);
        YourFriends.setWrapText(true);
        YourFriends.setPrefWidth(600);
        YourFriends.setStyle("-fx-border-color: rgba(128, 64, 64, 0.5);");

        //Add Post Butonu
        Button ListYourFriends = new Button("List Your Friends");
        MainPane.getChildren().add(ListYourFriends);
        ListYourFriends.setLayoutX(offsetMainX + 250);
        ListYourFriends.setLayoutY(offsetMainY + 850);

        ListYourFriends.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String returnValue = "";
                String remember;
                for (int thisCode:
                        users[Globals.LoginUserCode].Friends) {

                    if (thisCode == -1) continue;

                    returnValue = returnValue+"--"+users[thisCode].name+"--\n";
                }
                YourFriends.setText(returnValue);
            }

        });

        Label Users = new Label("");
        MainPane.getChildren().add(Users);
        Users.setLayoutX(offsetMainX);
        Users.setLayoutY(offsetMainY + 1500);
        Users.setWrapText(true);
        Users.setPrefWidth(600);
        Users.setStyle("-fx-border-color: rgba(128, 64, 64, 0.5);");

        //Add Post Butonu
        Button ListUsers = new Button("List Users");
        MainPane.getChildren().add(ListUsers);
        ListUsers.setLayoutX(offsetMainX + 250);
        ListUsers.setLayoutY(offsetMainY + 1450);

        ListUsers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String returnValue = "";
                String remember;
                for (User user:
                        users) {

                    if (user.name == "") continue;

                    returnValue = returnValue+"--"+user.name+"--\n";
                }
                Users.setText(returnValue);
            }

        });

        TextArea AddNewPostA = new TextArea();
        MainPane.getChildren().add(AddNewPostA);
        AddNewPostA.setPrefWidth(600);
        AddNewPostA.setPrefHeight(500);
        AddNewPostA.setLayoutX(offsetMainX);
        AddNewPostA.setLayoutY(offsetMainY + 200);
        AddNewPostA.setStyle("-fx-background-color: rgba(53,89,119,0.2);");

        //Add Post Butonu
        Button AddNewPost = new Button("Add New Post");
        MainPane.getChildren().add(AddNewPost);
        AddNewPost.setLayoutX(offsetMainX + 250);
        AddNewPost.setLayoutY(offsetMainY + 150);

        AddNewPost.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                users[Globals.LoginUserCode].NewPost(AddNewPostA.getText());
            }

        });

        //Add Friend Butonu
        Button LogOut = new Button("Log Out");
        MainPane.getChildren().add(LogOut);
        LogOut.setLayoutX(offsetMainX);
        LogOut.setLayoutY(offsetMainY + 100);

        LogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                border.setCenter(LoginPane);
            }

        });



        //***********************************************************************
        Label Space = new Label();
        Space.setLayoutY(4000);
        BackgroundImage myBI= new BackgroundImage(new Image("C:\\Users\\Yusuf\\Desktop\\Yusuf\\Arka Plan\\2.jpg",1500,700,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        //then you set to your node
        MainPane.setPrefWidth(1401);
        MainPane.setBackground(new Background(myBI));
        LoginPane.setBackground(new Background(myBI));
        MainPane.getChildren().add(Space);
        PostScrollPane.setContent(MainPane);
        border.setCenter(LoginPane);
        primaryStage.setTitle("Sosyal Medya Ardakaş Takip Desktop App");
        primaryStage.setScene(scene);
        primaryStage.show();
        //***********************************************************************
    }

    public String ShowPosts(User user, User[] users1){
        String returnValue = "";

        //BufferedReader fr = new BufferedReader(new FileReader(file));
        String remember;
        int counter = -1;
        int[] FriendsCodes = user.Friends;

        for (int thisCode:
             FriendsCodes) {
                try {
                    if (thisCode == -1){continue;}
                    BufferedReader fr = new BufferedReader(new FileReader(users1[thisCode].file));
                    int counter1 = -1;
                    while (true){
                        counter1++;
                        remember = fr.readLine();
                        if (remember == null){break;}
                        if (remember.equals("")||remember.equals(" ")){counter1--; continue;}
                        returnValue = returnValue+"Author: --" +users1[thisCode].name+ "-- " + String.valueOf(counter1) + ". Post" + "\n" +remember+"\n"+"\n"+"\n";

                    }

                fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        //En sona kendi postlarimizi görüntülüyoruz
        try {
        BufferedReader fr1 = new BufferedReader(new FileReader(user.file));
        int counter2 = -1;
        while (true){
            counter2++;
            remember = fr1.readLine();
            if (remember == null){break;}
            if (remember.equals("")||remember.equals(" ")){counter2--; continue;}
            returnValue = returnValue+"Author: --" +user.name+ "-- " + String.valueOf(counter2) + ". Post" + "\n" +remember+"\n"+"\n"+"\n";

        }
        fr1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public void showAlertWithoutHeaderText(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    //Hangi Username'in Hangi Code'a Sahip Olduğunu bulan fonksiyon
    public int WhichCode(String name, File file){
        int returnValue = -1;
        try {
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String remember;
            int counter = -1;
            while (true) {
                counter++;
                remember = fr.readLine();

                if (name.equals(remember)) {
                    returnValue = counter;
                    break;
                }
                if (remember==null) break;

            }
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public static void main(String[] args) {
        launch();
    }
}
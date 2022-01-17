package com.example.anket;

import javafx.scene.control.Alert;

import java.io.*;
import java.util.Arrays;

public class User {
    public String name = "None";
    public String password = "None";
    public int code = -1;
    File file;
    File FriendsFile;

    int[] Friends = new int[100];

    public void ChangeName(){

    }

    public void NewPost(String post){

        String replacedPost = post.replaceAll("\n"," ");

        try {

            BufferedWriter fw = new BufferedWriter(new FileWriter(file,true));
            fw.append(replacedPost);
            if (replacedPost.equals("")){showAlertWithoutHeaderText("Lütfen Karakter giriniz");}
            else {showAlertWithoutHeaderText("Yeni Post Paylaştınız");}
            fw.newLine();

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddFriend(String FriendName, User[] user, File file){
        try {
            BufferedReader fr = new BufferedReader(new FileReader(file));
            String remember;
            int counter = -1;
            String log = FriendName;
            boolean isItSame = false;
            
            while (true) {
                counter++;
                remember = fr.readLine();
                String[] arrOfStr = remember.split(" ", 10);
                
                if (log.equals(arrOfStr[0])) {
                    System.out.println("Kullanici Bulundu");
                    for (int codes:
                         Friends) {
                        BufferedReader fr1 = new BufferedReader(new FileReader(this.FriendsFile));
                        String buf = "-1";
                        while (true){
                            buf = fr1.readLine();
                            if (buf == null)break;
                            if (Integer.parseInt(buf)==counter&&!(isItSame)){isItSame = true;System.out.println("Kullanici Zaten Ekli");showAlertWithoutHeaderText("Kullanici Zaten Ekli");}
                        };

                        if (this.code==counter){isItSame = true;System.out.println("Maalesef Kendinizi Arkadaş Ekleyemezsiniz");
                            showAlertWithoutHeaderText("Maalesef Kendinizi Arkadaş Ekleyemezsiniz");}
                        fr1.close();
                    }
                    if (!(isItSame)){
                        this.Friends = Arrays.copyOf(this.Friends, this.Friends.length + 1);
                        this.Friends[this.Friends.length - 1] = counter;
                        BufferedWriter fw = new BufferedWriter(new FileWriter(this.FriendsFile, true));
                        fw.append(String.valueOf(counter));
                        fw.newLine();
                        fw.close();
                        System.out.println("Kullanici Arkadas Listenize Eklendi");
                        showAlertWithoutHeaderText("Kullanici Arkadas Listenize Eklendi");
                    }

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


    public void RemoveFriend(User user){

    }
    User(int code, String name, String password)
    {
        this.code = code;
        this.name = name;
        this.password  = password;
        this.file = new File(("C:\\Users\\Yusuf\\"+String.valueOf(this.code)+".txt"));
        this.FriendsFile = new File(("C:\\Users\\Yusuf\\"+"usersFriends"+String.valueOf(this.code)+".txt"));
        try {
            this.file.createNewFile();
            this.FriendsFile.createNewFile();

            for(int i = 0;i<this.Friends.length;i++){
                this.Friends[i] = -1;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader fr = new BufferedReader(new FileReader(FriendsFile));
            for(int i = 0;i<this.Friends.length;i++){

                String rem = fr.readLine();
                if (rem == null){break;}
                rem.replaceAll("\\s","");
                this.Friends[Integer.parseInt(rem.trim())] = Integer.parseInt(rem.trim());

            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void showAlertWithoutHeaderText(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

}

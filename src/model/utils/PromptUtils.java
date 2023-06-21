package model.utils;

import model.PromptException;

import java.io.IOException;

public class PromptUtils {
    public static void clearConsole(){
        try{
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();

        }catch (IOException | InterruptedException e){
            throw new PromptException(e.getMessage());
        }
    }
}

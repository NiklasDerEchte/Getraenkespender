package test;

import bin.RecipeReader;

import java.util.Vector;

public class RecipeReaderTest {
    public static void main(String[] args) {
     RecipeReader recipeReader = new RecipeReader();
        Vector<String[]> data = recipeReader.getRecipes();
     if(data != null) {
         for(String[] ar : data) {
             for(String st : ar) {
                 System.out.println(st);
             }
         }
     }
    }
}

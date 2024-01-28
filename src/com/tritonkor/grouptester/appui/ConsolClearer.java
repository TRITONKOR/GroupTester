package com.tritonkor.grouptester.appui;

public class ConsolClearer {
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}

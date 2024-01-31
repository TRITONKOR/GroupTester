package com.tritonkor.grouptester.appui;

public class ConsolItems {
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("   ____                         _____         _            \n"
                + "  / ___|_ __ ___  _   _ _ __   |_   _|__  ___| |_ ___ _ __ \n"
                + " | |  _| '__/ _ \\| | | | '_ \\    | |/ _ \\/ __| __/ _ \\ '__|\n"
                + " | |_| | | | (_) | |_| | |_) |   | |  __/\\__ \\ ||  __/ |   \n"
                + "  \\____|_|  \\___/ \\__,_| .__/    |_|\\___||___/\\__\\___|_|   \n"
                + "                       |_|");
    }
}

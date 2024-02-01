package com.tritonkor.grouptester.appui;

/**
 * The ConsolItems class provides utility methods related to console manipulation.
 */
public class ConsolItems {

    /**
     * Clears the console screen by printing a special ANSI escape sequence. Note: This method may
     * not work properly in all environments.
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J"); // ANSI escape sequence for clearing the console
        System.out.flush();

        // Print a welcome message after clearing the console
        System.out.println("   ____                         _____         _            \n"
                + "  / ___|_ __ ___  _   _ _ __   |_   _|__  ___| |_ ___ _ __ \n"
                + " | |  _| '__/ _ \\| | | | '_ \\    | |/ _ \\/ __| __/ _ \\ '__|\n"
                + " | |_| | | | (_) | |_| | |_) |   | |  __/\\__ \\ ||  __/ |   \n"
                + "  \\____|_|  \\___/ \\__,_| .__/    |_|\\___||___/\\__\\___|_|   \n"
                + "                       |_|");
    }
}

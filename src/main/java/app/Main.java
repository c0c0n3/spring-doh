package app;

import app.run.AppRunner;

/**
 * Program's entry point; delegates all the work to {@link AppRunner}.
 */
public class Main {

    public static void main(String[] args) {
        AppRunner runner = new AppRunner();
        runner.launch(args);
    }

}

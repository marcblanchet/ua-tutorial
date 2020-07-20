package org.icann.ua;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class TestRunner {

    protected static final int EXPECTED_ERROR_VALUE = 42;

    @Option(name="-e", aliases="--email", required=true)
    protected String email;

    @Option(name="-n", aliases="--no-validation")
    protected boolean noValidation;

    protected void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            // parse the arguments.
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            // Problem with CLI args
            System.err.println(e.getMessage());
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
            System.exit(-1);
        }
        try {
            run();
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(-1);
        }
    }

    protected void run() throws Exception {
        throw new UnsupportedOperationException();
    }
}

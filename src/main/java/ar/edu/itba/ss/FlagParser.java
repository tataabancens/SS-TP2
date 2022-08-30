package ar.edu.itba.ss;

import org.apache.commons.cli.*;

public class FlagParser {
    protected static RuleSet ruleSet = RuleSet.DEFAULT_RULE;
    protected static Integer timeInterval = 100;
    protected static String staticFile;
    protected static String dynamicFile;

    private static final String PARAM_T = "t";
    private static final String PARAM_R = "r";
    private static final String PARAM_DF = "df";


    private static Options GenerateOptions() {
        Options options = new Options();
        options.addOption(PARAM_T, "time_iteration", true, "Amount of iterations (or time) to run the Game Of Life for.");
        options.addOption(PARAM_R, "rule_set", true, "Id of the rule set to apply to the Game Of Life");
        options.addOption(PARAM_DF, "dynamic_file", true, "Path to the file with the dynamic values.");
        return options;
    }

    public static void ParseOptions(String[] args) {
        // Generating the options
        Options options = GenerateOptions();

        // Creating the parser
        CommandLineParser parser = new DefaultParser();

        try {
            // Parsing the options
            CommandLine cmd = parser.parse(options,args);


            // Parsing the rule set id
            if (cmd.hasOption(PARAM_R)){
                ruleSet = RuleSet.fromId(Integer.parseInt(cmd.getOptionValue(PARAM_R)));
                if (ruleSet == null) {
                    System.out.format("Non existing rule set. Choose a number between %d and %d\n", RuleSet.minRule(), RuleSet.maxRule());
                    System.exit(1);
                }
            }

            // Checking if the time amount is present
            if (!cmd.hasOption(PARAM_T)){
                System.out.println("A time frame amount must be specified");
                System.exit(1);
            }
            // Retrieving the amount of "time" to iterate with
            timeInterval = Integer.parseInt(cmd.getOptionValue(PARAM_T));

            // Checking if the files were present
            if (!cmd.hasOption(PARAM_DF)){
                System.out.println("The dynamic file path is needed");
                System.exit(1);
            }

            // Parsing the file paths
            dynamicFile = cmd.getOptionValue(PARAM_DF);

        } catch (ParseException e) {
            System.out.println("Unknown command used");
        }
    }


}

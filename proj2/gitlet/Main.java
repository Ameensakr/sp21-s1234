package gitlet;


import java.io.IOException;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author TODO
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {


        if (args.length == 0) {
            System.out.println("no comand");
            System.exit(0);
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                Repository.add(args[1]);
                break;
            case "rm":
                Repository.rm(args[1]);
                break;

            case "commit":
                new Commit(args[1], false);
                break;
            case "log":
                Repository.log();
                break;
            case "global-log":
                Repository.global_log();
                break;
            case "find":
                Repository.find(args[1]);
                break;
            case "checkout":
                switch (args.length) {
                    case 3:
                        if(!args[1].equals("--")) {
                            System.out.println("Incorrect operands.");
                            System.exit(0);
                        }
                        Repository.checkout(args[2]);
                        break;

                    case 4:
                        if(!args[2].equals("--")) {
                            System.out.println("Incorrect operands.");
                            System.exit(0);
                        }
                        Repository.checkout(args[3], args[1]);
                        break;
                    case 2:
                        Repository.checkout_branch(args[1]);
                        break;
                }
                break;
            case "branch":
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                Repository.rm_branch(args[1]);
                break;
            case "reset":
                Repository.reset(args[1]);
                break;
            case "status":
                Repository.status();
                break;
            case "merge":
                Repository.merge(args[1]);
                break;
        }


    }
}
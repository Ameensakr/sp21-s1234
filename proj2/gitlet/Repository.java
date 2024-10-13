package gitlet;

import java.io.IOException;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import static gitlet.Utils.*;
import static java.lang.String.copyValueOf;
import static java.lang.Thread.sleep;
import static java.sql.Types.NULL;


public class Repository {


    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(System.getProperty("user.dir"), ".gitlet");

    public static File stage = join(GITLET_DIR, "staging area");

    public static File blobs = join(GITLET_DIR, "blobs");

    public static File commit = join(GITLET_DIR, "commits");
    public static File addition = join(stage, "addition");
    public static File removal = join(stage, "removal");
    public static File HEAD = join(GITLET_DIR, "HEAD");
    public static File branches_file = join(GITLET_DIR, "branches");
    public static HashMap<String, String> branches = new HashMap<>();
    public static String cur_branch = new String("master");
    public static File current_branch = join(GITLET_DIR, "current_branch");

    public static boolean check_exist(String cwd, String last_com, String check) {
        File loc = join(cwd, last_com);
        Commit temp = readObject(loc, Commit.class);
        if (temp.blobs.containsKey(check))
            return true;
        return false;

    }


    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        try {


            GITLET_DIR.mkdir();
            stage.mkdir();
            commit.mkdir();
            addition.mkdir();
            removal.mkdir();
            HEAD.createNewFile();
            blobs.mkdir();
            branches_file.createNewFile();
            current_branch.createNewFile();

            new Commit("initial commit", true);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void add(String name) {

        try {
            sleep(500);

            if(!join(CWD, name).exists())
            {
                System.out.println("File does not exist.");
                System.exit(0);
            }

            String sha1;

            byte[] arr = readContents(join(CWD, name));

            sha1 = sha1(arr);
            sha1 = sha1(sha1 + name);


            for (File it : addition.listFiles()) {
                if (it.getName().substring(40).equals(name)) {
                    it.delete();
                    break;
                }
            }
            for (File it : removal.listFiles()) {
                if (it.getName().substring(40).equals(name)) {
                    it.delete();
                    break;
                }
            }

            HashMap <String , String> blobs = readObject(join(commit, Commit.get_head()), Commit.class).blobs;
            if(blobs.containsKey(sha1))
            {
                return;
            }

            File add = join(addition, sha1 + name);
            File rem = join(removal, sha1 + name);



            writeContents(add, arr);
            add.createNewFile();


            if (rem.exists()) {
                rem.delete();
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rm(String name) {
        try {
            String sha1;
            byte[] arr = readContents(join(CWD, name));
            sha1 = sha1(arr);
            sha1 = sha1(sha1 + name);

            File add = join(addition, sha1 + name);
            File rem = join(removal, sha1 + name);
//        writeContents(add,arr);

            HashMap<String , String>blobs = readObject(join(commit, Commit.get_head()), Commit.class).blobs;
            if (add.exists()) {
                add.delete();
            } else if (blobs.containsValue(name)) {
                File temp = join(CWD, name);
                writeContents(rem, arr);
                rem.createNewFile();
                temp.delete();
            }
            else {
                System.out.println("No reason to remove the file.");
                System.exit(0);
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void log() {
        try{
        sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String cur = copyValueOf(Commit.get_head().toCharArray());
        Commit cur_com = readObject(join(commit, cur), Commit.class);
//

        while (true) {
            System.out.println("===");
            System.out.println("commit " + cur);
            System.out.println("Date: " + cur_com.get_date());
            System.out.println(cur_com.get_commit_message());
            System.out.println();
            if (cur_com.get_parent().isEmpty())
                break;
            cur = cur_com.get_parent();
            cur_com = readObject(join(commit, cur), Commit.class);

        }
    }

    public static void global_log() {
        File logs = new File(String.valueOf((Repository.commit)));
        File[] allLogs = logs.listFiles();
        assert allLogs != null;
        for (File it : allLogs) {
            Commit cur_com = readObject(it, Commit.class);
            System.out.println("===");
            System.out.println("commit " + it.getName());
            System.out.println("Date: " + cur_com.get_date());
            System.out.println(cur_com.get_commit_message());
            System.out.println();
        }
    }

    public static void find(String target) {
        File logs = new File(String.valueOf((Repository.commit)));
        File[] allLogs = logs.listFiles();
        boolean done = false;
        assert allLogs != null;
        for (File it : allLogs) {
            Commit cur_com = readObject(it, Commit.class);
            if (cur_com.get_commit_message().equals(target)) {
                done = true;
                System.out.println(it.getName());
            }
        }
        if (!done) {
            System.out.println("Found no commit with that message.");
        }
    }

    public static void checkout(String name) {


        String cur_head = Commit.get_head();
        checkout(name, cur_head);


    }

    public static void checkout(String name, String cur_head) {

        try {
            // i want to check if there is a commit with the given id
            File f = join(commit, cur_head);
            if(!f.exists())
            {
                System.out.println("No commit with that id exists.");
                System.exit(0);
            }
            // i want to check if there is a file with that name in the commit


            Commit cur = readObject(f, Commit.class);
            HashMap<String, String> check = cur.blobs;
            boolean ch = false;
            for (Map.Entry<String, String> check1 : check.entrySet()) {
                if (check1.getValue().equals(name)) {
                    ch = true;
                    join(CWD, name).delete();
                    File w = join(CWD, check1.getValue());
                    File cp = join(blobs, check1.getKey() + check1.getValue());
                    w.createNewFile();
                    FileChannel src = new FileInputStream(cp).getChannel();
                    FileChannel dest = new FileOutputStream(w).getChannel();
                    dest.transferFrom(src, 0, src.size());
                }
            }
            if(!ch)
            {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void branch(String name) {
        Commit.readMap();
        if (branches.containsKey(name)) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        Commit.split_point.put(new Pair(cur_branch, name), Commit.get_head());
        Commit.split_point.put(new Pair(name, cur_branch), Commit.get_head());

        branches.put(name, Commit.get_head());
        Commit.save_branch();
    }

    public static void checkout_branch(String name) {
        try {
            Commit.readMap();
            if (!branches.containsKey(name)) {
                System.out.println("No such branch exists.");
                System.exit(0);
            }
            if (name.equals(cur_branch)) {
                System.out.println("No need to checkout the current branch.");
                System.exit(0);
            }

            cur_branch = name;
            Commit.HEAD = (String) branches.get(name);
            writeContents(join(GITLET_DIR, "HEAD"), Commit.HEAD);
            List<String> st = plainFilenamesIn(CWD);
            for (String it : st) {
                File x = join(CWD, it);
                x.delete();
            }

            HashMap<String, String> blobs = readObject(join(commit, Commit.get_head()), Commit.class).blobs;
            for (Map.Entry<String, String> it : blobs.entrySet()) {
                File w = join(CWD, it.getValue());
                File cp = join(String.valueOf(blobs), it.getKey() + it.getValue());
                w.createNewFile();
                FileChannel src = new FileInputStream(cp).getChannel();
                FileChannel dest = new FileOutputStream(w).getChannel();
                dest.transferFrom(src, 0, src.size());
            }


            Commit.save_branch();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rm_branch(String name) {
        Commit.readMap();
        if (!branches.containsKey(name)) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        if (name.equals(cur_branch)) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        }
        Pair temp = new Pair("temp" , "temp");
        String splet = new String("");
        for (Map.Entry<Pair, String> it : Commit.split_point.entrySet()) {
            if (it.getKey().getSecond().equals(name)) {
                temp = new Pair(it.getKey().getFirst(), it.getKey().getSecond());
                splet = it.getValue();
            } else if (it.getKey().getFirst().equals(name)) {
                temp = new Pair(it.getKey().getFirst(), it.getKey().getSecond());
                splet = it.getValue();
            }
        }
//        String cur = branches.get(name);
//        while(cur != splet) {
//            Commit temp_com = readObject(join(commit, cur), Commit.class);
//            for (Map.Entry<String, String> it : temp_com.blobs.entrySet()) {
//                File f = join(blobs, it.getKey() + it.getValue());
//                f.delete();
//            }
//            cur = temp_com.parent;
//        }

        Commit.split_point.remove(temp);


        branches.remove(name);
        Commit.save_branch();
    }

    public static void reset(String sh1) {
        try {
            File f = join(commit, sh1);
            if (!f.exists()) {
                System.out.println("No commit with that id exists.");
                System.exit(0);
            }


            Commit target = readObject(f, Commit.class);

            Commit cur_head = readObject(join(commit, Commit.get_head()), Commit.class);


            // I want to check if the there is untracked files in the working directory in the current commit
            List<String> files = plainFilenamesIn(CWD);
            for (String it : files) {
                if (it.equals(".gitlet"))
                    continue;
                if (!cur_head.blobs.containsValue(it)) {
                    System.out.println("There is an untracked file in the way; delete it or add it first.");
                    System.exit(0);
                }
            }


            // delete all the files in the working directory
            for (String it : files) {
                if (it.equals(".gitlet"))
                    continue;

                File x = new File(CWD, it);
                x.delete();
            }

            // I want to add all files in the working directory from the commit

            for (Map.Entry<String, String> it : target.blobs.entrySet()) {
                File w = join(CWD, it.getValue());
                File cp = join(blobs, it.getKey() + it.getValue());
                w.createNewFile();
                FileChannel src = new FileInputStream(cp).getChannel();
                FileChannel dest = new FileOutputStream(w).getChannel();
                dest.transferFrom(src, 0, src.size());
            }

            // change the head to the new commit and save the branch
            Commit.readMap();
            Commit.HEAD = sh1;

            writeContents(join(GITLET_DIR, "HEAD"), Commit.HEAD);

            branches.put(cur_branch, sh1);
            Commit.save_branch();
        }catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }

    }


    public static void status() {
        Commit.readMap();
        System.out.println("=== Branches ===");
        for (Map.Entry<String, String> it : branches.entrySet()) {
            if (it.getKey().equals(cur_branch))
                System.out.println("*" + it.getKey());
            else
                System.out.println(it.getKey());
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        File[] add_blobs = addition.listFiles();
        assert add_blobs != null;
        for (File it : add_blobs) {
            System.out.println(it.getName().substring(40));
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        File[] rem_blobs = removal.listFiles();
        assert rem_blobs != null;
        for (File it : rem_blobs) {
            System.out.println(it.getName().substring(40));
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }


    public static void merge(String name) {
        try {
            Commit.readMap();
            if (!branches.containsKey(name)) {
                System.out.println("A branch with that name does not exist.");
                System.exit(0);
            }
            if (name.equals(cur_branch)) {
                System.out.println("Cannot merge a branch with itself.");
                System.exit(0);
            }

            String split_commit = Commit.split_point.get(new Pair(cur_branch, name));
            if (split_commit.equals(Commit.HEAD)) {
                System.out.println("Given branch is an ancestor of the current branch.");
                System.exit(0);
            }
            if (split_commit.equals(branches.get(name))) {
                System.out.println("Current branch fast-forwarded.");
                checkout(name);
                System.exit(0);
            }
            Commit cur = readObject(join(commit, Commit.HEAD), Commit.class);
            Commit given = readObject(join(commit, branches.get(name)), Commit.class);
            Commit split_point = readObject(join(commit, split_commit), Commit.class);
            HashMap<String, String> cur_blobs = cur.blobs;
            HashMap<String, String> given_blobs = given.blobs;
            HashMap<String, String> split_blobs = split_point.blobs;
            List<String> allFiles = plainFilenamesIn(CWD);
            boolean is_conflict = false;
            assert allFiles != null;
            for (String it : allFiles) {
                if (it.equals(".gitlet"))
                    continue;
                if (!cur_blobs.containsValue(it) && given_blobs.containsValue(it)) {
                    System.out.println("There is an untracked file in the way; delete it or add it first.");
                    System.exit(0);
                }
            }
            for (Map.Entry<String, String> it : split_blobs.entrySet()) {
                if (cur_blobs.containsValue(it.getValue()) && given_blobs.containsValue(it.getValue())) {
                    if (cur_blobs.containsKey(it.getKey()) && given_blobs.containsKey(it.getKey())) {
                        continue;
                    } else if (cur_blobs.containsKey(it.getKey()) && !given_blobs.containsKey(it.getKey())) {
                        continue;
                    } else if (!cur_blobs.containsKey(it.getKey()) && given_blobs.containsKey(it.getKey())) {
                        checkout(it.getValue(), branches.get(name));
                        add(it.getValue());
                    } else {
                        String sha1 = "", sha2 = "";
                        for (Map.Entry<String, String> it2 : cur_blobs.entrySet()) {
                            if (it2.getValue().equals(it.getValue())) {
                                sha1 = it2.getKey();
                                break;
                            }
                        }

                        for (Map.Entry<String, String> it2 : given_blobs.entrySet()) {
                            if (it2.getValue().equals(it.getValue())) {
                                sha2 = it2.getKey();
                                break;
                            }
                        }

                        if (!sha1.equals(sha2)) {
                            is_conflict = check_for_conflict(sha1, sha2, it);
                        }
                    }
                } else if (cur_blobs.containsValue(it.getValue()) && !given_blobs.containsValue(it.getValue())) {
                    if (cur_blobs.containsKey(it.getKey())) {
                        rm(it.getValue());
                    } else {
                        is_conflict = true;
                    }

                } else if (!cur_blobs.containsValue(it.getValue()) && given_blobs.containsValue(it.getValue())) {
                    if (given_blobs.containsKey(it.getKey())) {
                        continue;
                    } else {
                        checkout(it.getValue(), branches.get(name));
                        add(it.getValue());
                        is_conflict = true;
                    }
                }
            }

            Commit new_commit = new Commit("Merged " + cur_branch + " with " + name + ".", false);
            new_commit.parent = Commit.HEAD;
            new_commit.second_parent = branches.get(name);
            new_commit.save_branch();

            if (is_conflict) {
                System.out.println("Encountered a merge conflict.");
            }
        }catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean check_for_conflict(String cur , String given, Map.Entry<String, String> it) throws IOException {


        // conflict
        File cur_file = join(blobs, cur + it.getValue());
        File given_file = join(blobs, given + it.getValue());
        File w = join(CWD, it.getValue());
        // i want to overwrite the w if it  exists
        w.createNewFile();
        writeContents(w, "<<<<<<< HEAD\n");
        writeContents(w, readContentsAsString(cur_file));
        writeContents(w, "=======\n");
        writeContents(w, readContentsAsString(given_file));
        writeContents(w, ">>>>>>>\n");
        add(it.getValue());
        return true;

    }


}
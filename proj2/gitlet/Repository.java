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
import static java.sql.Types.NULL;


// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */

public class Repository {


    /**
     * TODO: add instance variables here.
     *
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */


    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(System.getProperty("user.dir"), ".gitlet");

    /* TODO: fill in the rest of this class. */
    public static File stage = join(GITLET_DIR,"staging area");

    public static File blobs = join(GITLET_DIR,"blobs");

    public static File commit = join(GITLET_DIR,"commits");
    public static File addition = join(stage,"addition");
    public static File removal = join(stage,"removal");
    public static File HEAD = join(GITLET_DIR,"HEAD");
    public static boolean  check_exist(String cwd,String last_com,String check)
    {
        File loc=join(cwd,last_com);
        Commit temp = readObject(loc,Commit.class);
        if(temp.blobs.containsKey(check))
            return true;
        return false;

    }




    public static void init()  {
        if(GITLET_DIR.exists())
        {
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
            new Commit("initial commit", true);
        }catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void add(String name)  {
        try {


            String sha1;

            byte[] arr = readContents(Utils.join(CWD, name));

            sha1 = Utils.sha1(arr);
            File add = join(addition, sha1 + name);
            File rem = join(removal, sha1 + name);
            if (!add.exists()) {
                writeContents(add, arr);
                add.createNewFile();
            }
            if (rem.exists()) {
                rem.delete();
            }
        }
        catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void rm(String name)
    {
        try {

            //System.out.println(name);
            String sha1;
            byte[] arr = readContents(Utils.join(CWD, name));
            sha1 = Utils.sha1(arr);
            //System.out.println(sha1);
            File add = join(addition, sha1 + name);
            File rem = join(removal, sha1 + name);
//        writeContents(add,arr);
            if (add.exists()) {
                add.delete();
            } else if (check_exist(String.valueOf(join(GITLET_DIR, "commits")), Commit.HEAD, sha1 + name)) {
                File temp = join(CWD, name);
                writeContents(rem, arr);
                rem.createNewFile();
                temp.delete();
            } else System.out.println("No reason to remove the file.");
        }
        catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void log()
    {
        String cur = copyValueOf(Commit.get_head().toCharArray());
        Commit cur_com = readObject(join(commit,cur),Commit.class);
//

        while (true) {
            System.out.println("===");
            System.out.println("commit " + cur);
            System.out.println("Date: " + cur_com.get_date());
            System.out.println(cur_com.get_commit_message());
            System.out.println();
            if(cur_com.get_parent().isEmpty())
                break;
            cur = cur_com.get_parent();
            cur_com = readObject(join(commit,cur),Commit.class);

        }
    }

    public static void global_log()
    {
        File logs = new File(String.valueOf((Repository.commit)));
        File[] allLogs=logs.listFiles();
        assert allLogs!=null;
        for(File it : allLogs)
        {
            Commit cur_com = readObject(it , Commit.class);
            System.out.println("===");
            System.out.println("commit " + it.getName());
            System.out.println("Date: " + cur_com.get_date());
            System.out.println(cur_com.get_commit_message());
            System.out.println();
        }
    }

    public static void find(String target)
    {
        File logs = new File(String.valueOf((Repository.commit)));
        File[] allLogs=logs.listFiles();
        boolean done = false;
        assert allLogs!=null;
        for(File it : allLogs)
        {
            Commit cur_com = readObject(it , Commit.class);
            if(cur_com.get_commit_message().equals(target))
            {
                done = true;
                System.out.println(it.getName());
            }
        }
        if(!done)
        {
            System.out.println("Found no commit with that message.");
        }
    }
    public static void checkout(String name) {


        String cur_head = Commit.get_head();
        checkout(name, cur_head);


    }
    public static void checkout(String name , String cur_head) {
//        System.out.println(name + " " + cur_head);
//        System.exit(0);
        try {


            File f = join(commit, cur_head);
            Commit cur = readObject(f, Commit.class);
            HashMap<String, String> check = cur.blobs;
            for (Map.Entry<String, String> check1 : check.entrySet()) {
                //System.out.println(check1.getKey() + ": " + check1.getValue());
                if (check1.getValue().equals(name)) {
                    join(CWD, name).delete();
                    File w = join(CWD, check1.getValue());
                    File cp = join(blobs, check1.getKey() + check1.getValue());
                    w.createNewFile();
                    FileChannel src = new FileInputStream(cp).getChannel();
                    FileChannel dest = new FileOutputStream(w).getChannel();
                    dest.transferFrom(src, 0, src.size());
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
package gitlet;

import jdk.jshell.execution.Util;

import java.io.*;
import java.util.Date;

import gitlet.Utils.*;

import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Utils.*;
import static java.lang.Thread.sleep;

public class Commit implements Serializable {

    public static String HEAD = (readContentsAsString(join(Repository.GITLET_DIR, "HEAD")));
    private String message;
    public HashMap<String, String> blobs; // sha1 of the file and the name of the file
    private String date;
    public static HashMap<Pair , String> split_point = new HashMap<>();
    public String parent = null;
    private String sh1;
    private boolean is_init;
    public String second_parent = null;

    void save() {
        File com = join(Repository.commit, sh1);
        writeObject(com, this);
        writeContents(Repository.HEAD, sh1);
    }

    static void save_branch() {
        File branch = join(Repository.GITLET_DIR, "branches_file");
        writeObject(branch, Repository.branches);
        File branch2 = join(Repository.GITLET_DIR, "current_branch");
        writeObject(branch2, Repository.cur_branch);
        File branch3 = join(Repository.GITLET_DIR, "split_point");
        writeObject(branch3, split_point);
    }

    public static String find_split_point(String branch1, String branch2) {
        return split_point.get(new Pair(branch1, branch2));
    }

    public boolean get_is_init() {
        return is_init;
    }


    public String get_date() {
        return date;
    }

    public String get_commit_message() {
        return message;
    }

    public String get_parent() {
        if (parent == null)
            return "";
        return parent;
    }

    public static String get_head() {
        HEAD = (readContentsAsString(join(Repository.GITLET_DIR, "HEAD")));
        return HEAD;
    }

    @SuppressWarnings("unchecked")
    public static void readMap() {

        File branch = join(Repository.GITLET_DIR, "branches_file");
        Repository.branches = (HashMap<String, String>) readObject(branch, HashMap.class);
        File branch2 = join(Repository.GITLET_DIR, "current_branch");
        Repository.cur_branch = readObject(branch2, String.class);
        File branch3 = join(Repository.GITLET_DIR, "split_point");
        split_point = readObject(branch3, HashMap.class);
    }

    public Commit(String message, boolean is_init) {

        // hard code the initial time
        this.is_init = is_init;
        if(!is_init && message.length() == 0)
        {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        blobs = new HashMap<>();
        try {
            sleep(500);
            if (is_init) {
                date = "Thu Jan 01 02:00:00 1970 +0200";
            } else {
                Date time = new Date();
                Formatter ff = new Formatter().format("%1$ta %1$tb %1$td %1$tT %1$tY %1$tz", time);
                //SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
                date = ff.toString();
                HEAD = (readContentsAsString(join(Repository.GITLET_DIR, "HEAD")));
                File comm = join(Repository.commit, HEAD);

                File directory_add = new File(String.valueOf((Repository.addition)));
                File[] add_blobs = directory_add.listFiles();
                assert add_blobs != null;
                A:
                for (Map.Entry<String, String> temp : readObject(comm, Commit.class).blobs.entrySet()) {
                    for (File it : add_blobs) {
                        if (it.getName().substring(40).equals(temp.getValue())) {
                            continue A;
                        }
                    }
                    blobs.put(temp.getKey(), temp.getValue());
                }


                for (File it : add_blobs) {
                    blobs.put(it.getName().substring(0, 40), it.getName().substring(40));
                    File f = new File(Repository.blobs, it.getName());
                    f.createNewFile();
                    FileChannel src = new FileInputStream(it).getChannel();
                    FileChannel dest = new FileOutputStream(f).getChannel();
                    dest.transferFrom(src, 0, src.size());
                    it.delete();
                }
                File directory_rem = new File(String.valueOf((Repository.removal)));
                File[] rem_blobs = directory_rem.listFiles();
                if(rem_blobs.length == 0 && add_blobs.length == 0){
                    System.out.println("No changes added to the commit.");
                    System.exit(0);
                }


                assert rem_blobs != null;
                for (File it : rem_blobs) {
                    blobs.remove(it.getName().substring(0, 40), it.getName().substring(40));
                    it.delete();
                }
            }


            this.message = (message.substring(0));
            if (HEAD.equals(sha1(this.toString() + date).substring(0))) {
                return;
            }
            this.sh1 = (sha1(this.toString() + date).substring(0));

            if (!is_init) {
                parent = new String(HEAD);
            }


            HEAD = (sh1.substring(0));


            if (is_init) {
                split_point.put(new Pair("3n3n3n","teeeeeeet"),"3n3n3n3n3n");
                Repository.branches.put("master", sh1);
                Repository.cur_branch = "master";
            } else {
                readMap();
                Repository.branches.put(Repository.cur_branch, HEAD);
            }

            save_branch();


        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        save();
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



    }


}
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

// TODO: any imports you need here

import static gitlet.Utils.*;


/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    public static String HEAD = (readContentsAsString(join(Repository.GITLET_DIR, "HEAD")));
    private String message;
    public HashMap<String,String> blobs; // sha1 of the file and the name of the file
    private String date;
    private String parent = null;
    private String sh1;
    private boolean is_init;

    void save() {
        File com = join(Repository.commit, sh1);
        writeObject(com, this);
        writeContents(Repository.HEAD, sh1);
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
        if(parent ==null)
            return "";
        return parent;
    }

    public static String get_head() {
        HEAD = (readContentsAsString(join(Repository.GITLET_DIR, "HEAD")));
        return HEAD;
    }


    public Commit(String message , boolean is_init)  {
        // hard code the initial time
        this.is_init = is_init;
        blobs=new HashMap<>();
        try {


            if (is_init)
            {
                date = "Thu Jan 01 02:00:00 1970 +0200";
            }
            else {
                Date time = new Date();
                Formatter ff = new Formatter().format("%1$ta %1$tb %1$td %1$tT %1$tY %1$tz", time);
                //SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
                date = ff.toString();
                HEAD = (readContentsAsString(join(Repository.GITLET_DIR, "HEAD")));
                File comm = join(Repository.commit, HEAD);
                blobs.putAll(readObject(comm, Commit.class).blobs);
                File directory_add = new File(String.valueOf((Repository.addition)));
                File[] add_blobs = directory_add.listFiles();
                assert add_blobs != null;
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
                assert rem_blobs != null;
                for (File it : rem_blobs) {
                    blobs.remove(it.getName().substring(0, 40), it.getName().substring(40));
                    it.delete();
                }
            }



            this.message = (message.substring(0));
            if(HEAD.equals(sha1(this.toString() + date).substring(0)))
            {
                return;
            }
            this.sh1 = (sha1(this.toString() + date).substring(0));

            if(!is_init) {
                parent = new String(HEAD);
            }
            HEAD = (sh1.substring(0));
//        System.out.println(HEAD +" " +parent);
        }
        catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        save();

    }


    /* TODO: fill in the rest of this class. */
}
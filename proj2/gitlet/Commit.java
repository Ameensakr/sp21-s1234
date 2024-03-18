package gitlet;
import jdk.jshell.execution.Util;

import java.io.*;

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
import static java.lang.String.copyValueOf;



public class Commit implements Serializable{

    public static String HEAD;
    private String message;
    public HashMap<String,String> blobs;
    private String date;
    private String parent ;
    private String sh1;
    private boolean is_init;
    void save()
    {
        File com = join(Repository.commit , sh1);
        writeObject(com,this);
        writeContents(Repository.HEAD,sh1);
    }

    public boolean get_is_init()
    {
        return is_init;
    }


    public String get_date()
    {
        return date;
    }

    public String get_commit_message()
    {
        return message;
    }
    public String get_parent()
    {
        return parent;
    }
    public static String get_head()
    {
        HEAD = (readContentsAsString(join(Repository.GITLET_DIR,"HEAD")));
        return HEAD;
    }

    public Commit(String message , boolean is_init) throws IOException {
        // hard code the initial time
        this.is_init = is_init;
        blobs=new HashMap<>();
        if (is_init)
        {
            date = "00:00:00 UTC, Thursday, 1 January 1970";
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
            date = sdf.format(new Date());
            HEAD = (readContentsAsString(join(Repository.GITLET_DIR,"HEAD")));
            File comm=join(Repository.commit,HEAD);
            blobs.putAll(readObject(comm, Commit.class).blobs);
            File directory_add = new File(String.valueOf((Repository.addition)));
            File[] add_blobs=directory_add.listFiles();
            assert add_blobs != null;
            for(File it:add_blobs) {
                blobs.put(it.getName().substring(0,40),it.getName().substring(40));
                File f=new File(Repository.blobs,it.getName());
                f.createNewFile();
                FileChannel src = new FileInputStream(it).getChannel();
                FileChannel dest = new FileOutputStream(f).getChannel();
                dest.transferFrom(src, 0, src.size());
                it.delete();
            }
            File directory_rem = new File(String.valueOf((Repository.removal)));
            File[] rem_blobs=directory_rem.listFiles();
            assert rem_blobs != null;
            for(File it:rem_blobs) {
                blobs.remove(it.getName().substring(0,40),it.getName().substring(40));
                it.delete();
            }
        }
        this.message = (message.substring(0));
        this.sh1 = (sha1(blobs.toString()).substring(0));
        if(!is_init) {
            parent = new String(HEAD);
        }
        HEAD = (sh1.substring(0));
//        System.out.println(HEAD +" " +parent);
        save();

    }

}
package com.cyr1en.mcutils.utils;

import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Checksum;
import java.util.zip.ZipFile;

public class FileUtil {

    public static boolean isJarFile(File file) throws IOException {
        if (!isZipFile(file)) {
            return false;
        }
        ZipFile zip = new ZipFile(file);
        boolean manifest = zip.getEntry("META-INF/MANIFEST.MF") != null;
        zip.close();
        return manifest;
    }

    public static boolean isZipFile(File file) throws IOException {
        if (file.isDirectory()) {
            return false;
        }
        if (!file.canRead()) {
            throw new IOException("Cannot read file " + file.getAbsolutePath());
        }
        if (file.length() < 4) {
            return false;
        }
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        int test = in.readInt();
        in.close();
        return test == 0x504b0304;
    }

    public static InputStream getResourceAsStream(String path) {
        InputStream inputStream = null;
        try {
            inputStream = FileUtil.class.getResource(path).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static PluginDescriptionFile getPluginDescriptionFile() {
        InputStream is = getResourceAsStream("plugin.ymk");
        PluginDescriptionFile pdl = null;
        try {
            pdl = new PluginDescriptionFile(is);
        } catch (InvalidDescriptionException e) {
            e.printStackTrace();
        }
        return pdl;
    }

    public static String checkSum(String algorithm) {
        File currentJavaJarFile = new File(Checksum.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String filepath = currentJavaJarFile.getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            FileInputStream fis = new FileInputStream(filepath);
            byte[] dataBytes = new byte[1024];
            int nread = 0;

            while ((nread = fis.read(dataBytes)) != -1)
                md.update(dataBytes, 0, nread);

            byte[] mdbytes = md.digest();

            for (byte mdbyte : mdbytes) sb.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

package com.example.starkey.androidtoolproject.untils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtil {
    public static String computeSize(long cacheSize) {
        String result = "";
        if (cacheSize == 0) {
            result = "";
        } else if (cacheSize > 0 && cacheSize < 1024) {
            result = cacheSize + "B";
        } else if (cacheSize >= 1024 && cacheSize < (1024 * 1024)) {
            result = cacheSize / 1024 + "K";
        } else if (cacheSize >= 1024 * 1024) {
            result = cacheSize / (1024 * 1024) + "M";
        }
        return result;
    }

    /**
     * 统计目录文件大小
     *
     * @param file
     * @return
     */
    public static long countFile(File file) {

        long size = 0L;
        if (file == null || !file.exists()) {
            return size;
        }

        if (file.isFile()) {
            return file.length();
        }

        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list == null) {
                return size;
            }
            for (File childFile : list) {
                size += countFile(childFile);
            }
        }

        return size;
    }

    /**
     * 删除目录下的文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file == null || !file.exists() || !file.canWrite()) {
            return false;
        }

        if (file.isFile()) {
            return file.delete();
        }

        boolean success = true;
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list == null) {
                return true;
            }
            for (File childFile : list) {
                success &= deleteFile(childFile);
            }
        }

        return success;
    }

    public static String readFile(String path) {
        String result = null;
        InputStreamReader in = null;
        try {
            in = new FileReader(path);
            StringBuffer sb = new StringBuffer();
            char[] buffer = new char[1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                sb.append(buffer, 0, length);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 字符数组写入文件
     *
     * @param file 被写入的文件
     * @return 字符数组
     * @parambytes 被写入的字符数组
     */
    public static boolean writeByteFile(byte[] bytes, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                } catch (Exception e) {
                }
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
        return true;
    }

    public static String loadAssetsFile(Context context, String file) {

        StringBuilder buf = new StringBuilder();
        BufferedReader in = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(file);
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return buf.toString();
    }

    // support folder and file
    public static void copyAssetsToFiles(Context context, String path) {
        AssetManager assetManager = context.getAssets();
        try {
            String[] assets = assetManager.list(path);
            if (assets.length == 0) {
                copyAssetsToFile(context, path);
            } else {
                for (int i = 0; i < assets.length; ++i) {
                    copyAssetsToFiles(context, path + "/" + assets[i]);
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private static void copyAssetsToFile(Context context, String filename) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String filePath = context.getFilesDir().getAbsolutePath();
            String[] split = filename.split("/");
            for (int i = 0; i < split.length - 1; i++) {
                filePath += "/" + split[i];
                File dir = new File(filePath);
                if (!dir.exists())
                    dir.mkdir();
            }
            filePath += "/" + split[split.length - 1];
            String newFileName = filePath;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}

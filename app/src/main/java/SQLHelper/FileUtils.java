package SQLHelper;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    private static final String TAG = "test123";

    public static boolean copyFileFromAsset(Context context, String fileName, File targetPathFile) {
        try {
            InputStream myInput = context.getAssets().open(fileName);
            OutputStream myOutput =  new FileOutputStream(targetPathFile);
            return copyFile(myInput, myOutput);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("test123", "ko copy dc db:"+e.getMessage());
            StackTraceElement[] st = e.getStackTrace();
            for (int i = 0; i < st.length; i++) {
                StackTraceElement el = st[i];
                Log.d(TAG, "copyFileFromAsset: "+el.toString());
            }
            return false;
        }

    }

    public static boolean copyFile(String pathFile, String targetPath) {
        try {
            InputStream myInput = new FileInputStream(pathFile);
            OutputStream myOutput = new FileOutputStream(targetPath);

            File f = new File(targetPath);
            if (!f.exists())
                f.mkdir();

            return copyFile(myInput, myOutput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyFile(File pathFile, File targetPath) {
        try {
            InputStream myInput = new FileInputStream(pathFile);
            OutputStream myOutput = new FileOutputStream(targetPath);

            File f = new File(targetPath.getParent());
            if (!f.exists())
                f.mkdir();

            return copyFile(myInput, myOutput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyFile(InputStream in, OutputStream out) {
        try {
            InputStream myInput = in;
            OutputStream myOutput = out;
            byte[] buffer = new byte[1024];
            int length;
            int count = 0;
            while ((length = myInput.read(buffer)) > 0) {
                count+=length;
                myOutput.write(buffer, 0, length);
            }

            Log.d(TAG, "copyFile: number of bytes copyed:"+count);

            myOutput.flush();
            myOutput.close();
            myInput.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("PVH", "ko copy dc");
            return false;
        }

    }

    /*
        public static ArrayList<String> readFileText(File filePath) {
            ArrayList localArrayList = new ArrayList();
            File localFile = filePath;
            try {
                BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
                while (question_true) {
                    String str = localBufferedReader.readLine();
                    if (str == null)
                        break;
                    localArrayList.add(str);
                }
                localBufferedReader.close();
                return localArrayList;
            } catch (IOException localIOException) {
            }
            return localArrayList;
        }

        public static void writeDataFileText(String filePath, String bodyText) {
            try {
                File file = new File(filePath);
                FileWriter writer = new FileWriter(file);
                writer.write(bodyText);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static boolean unzipFile(String pathFile, String targetPath) {
            InputStream is;
            ZipInputStream zis;
            try {
                String filename;
                is = new FileInputStream(pathFile);
                zis = new ZipInputStream(new BufferedInputStream(is));
                ZipEntry ze;
                byte[] buffer = new byte[1024];
                int count;

                while ((ze = zis.getNextEntry()) != null) {
                    filename = ze.getName();

                    if (ze.isDirectory()) {
                        File fmd = new File(targetPath + filename);
                        fmd.mkdirs();
                        continue;
                    }

                    FileOutputStream fout = new FileOutputStream(targetPath + filename);

                    while ((count = zis.read(buffer)) != -1) {
                        fout.write(buffer, 0, count);
                    }

                    fout.close();
                    zis.closeEntry();
                }

                zis.close();
            } catch (IOException e) {
                e.printStackTrace();
                return question_false;
            }

            return question_true;
        }
        */
    public static boolean deleteFile(File file) {
        try {
            return file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String[] listFile(String path) {
        File file = new File(path);
        return file.list();
    }

    public static boolean checkExistsFile(File pathFile) {
        if (pathFile.exists() && !pathFile.isDirectory()) {
            return true;
        }
        return false;
    }

    public static boolean checkExistsDir(File pathDir) {
        if (pathDir.exists() && pathDir.isDirectory()) {
            return true;
        }
        return false;
    }
}

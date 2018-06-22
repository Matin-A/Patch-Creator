import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Matin Afkhami on 6/16/2018.
 * Java 10 is recommended
 */

class PatchCreator {

    private File oldDirectory, newDirectory, saveTo;
    private boolean hashCodeMethod;
    private ArrayList<String> deletedFilesList = new ArrayList<>();

    PatchCreator(File oldDirectory, File newDirectory, File saveTo, boolean hashCodeMethod) throws Exception {
        if (oldDirectory.exists() && newDirectory.exists() && saveTo.exists()){
            if (oldDirectory.isDirectory() && newDirectory.isDirectory() && saveTo.isDirectory()){
                this.hashCodeMethod = hashCodeMethod;
                this.oldDirectory = oldDirectory;
                this.newDirectory = newDirectory;
                this.saveTo = saveTo;
            }else{
                throw new Exception("Not Directory Exception");
            }
        }else{
            throw new IOException();
        }
    }

    void createPatch() throws Exception {

        copyNewFiles(newDirectory);
        findDeletedFiles(oldDirectory);

        File batchFile = new File(saveTo+"/"+"delete_Removed_Files.bat");
        FileWriter fileWriter = new FileWriter(batchFile,true);
        fileWriter.write("@echo off"+System.lineSeparator()+"title \"Please Wait...\""+System.lineSeparator());
        for (String filePath : deletedFilesList){
            fileWriter.write("del \""+filePath.substring(1)+"\""+System.lineSeparator());
        }
        fileWriter.close();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void copyNewFiles(File directory) throws Exception {
        for (File eachFile : Objects.requireNonNull(directory.listFiles())){
            if (eachFile.isDirectory()){
                copyNewFiles(eachFile);
            }else{
                String pathToFile = eachFile.getParent().substring(newDirectory.getPath().length());
                File oldFile = new File(oldDirectory.getPath()+pathToFile+"/"+eachFile.getName());
                if (!(oldFile.exists() && fileEquals(eachFile,oldFile))){
                    File folders = new File(saveTo.getPath()+pathToFile);
                    folders.mkdirs();
                    copyFile(eachFile,new File(folders.getPath()+"/"+eachFile.getName()));
                }
            }
        }
    }

    private void findDeletedFiles(File directory) {
        for (File eachFile : Objects.requireNonNull(directory.listFiles())){
            if (eachFile.isDirectory()){
                findDeletedFiles(eachFile);
            }else{
                String pathToFile = eachFile.getPath().substring(oldDirectory.getPath().length());
                if (!new File(newDirectory.getPath()+pathToFile).exists()){
                    deletedFilesList.add(pathToFile);
                }
            }
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void copyFile(File source, File dest) throws IOException {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean fileEquals(File file1, File file2) throws IOException {
        if (file1.length()!=file2.length())
            return false;
        if (hashCodeMethod){
            MessageDigest sha = null;
            try {
                sha = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException ignored) {}
            return getFileChecksum(sha, file1)
                    .contentEquals(getFileChecksum(sha, file2));
        }
        return true;
    }

    private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[1024];
        int bytesCount;
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }
        fis.close();
        byte[] bytes = digest.digest();
        StringBuilder hashStringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            hashStringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return hashStringBuilder.toString();
    }

}

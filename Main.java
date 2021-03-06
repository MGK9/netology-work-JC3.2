package StreamAPIprod.Instalation.Saving;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final String PATH = "E:/Games/Portal/savegames";

    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(100, 2, 3, 1.9);
        GameProgress game2 = new GameProgress(90, 4, 5, 4.4);
        GameProgress game3 = new GameProgress(60, 8, 14, 7.5);

        saveGame(PATH + "/Save1.dat", game1);
        saveGame(PATH + "/Save2.dat", game2);
        saveGame(PATH + "/Save3.dat", game3);

        zipFiles(PATH + "/savegames.zip", Arrays.asList(PATH + "/Save1.dat", PATH + "/Save2.dat", PATH + "/Save3.dat"));

        removeNonZip(PATH);
    }

    private static void saveGame(String path, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFiles(String path, List<String> filePaths) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String filePath : filePaths) {
                File file = new File(filePath);
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void removeNonZip(String path) {
        Arrays.stream(new File(path).listFiles())
                .filter(item -> !item.getName().endsWith("zip"))
                .forEach(File::delete);
    }
}

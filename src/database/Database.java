package database;

import entities.Product;
import entities.Ticket;
import vista.View;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private List<Product> trees;
    private List<Product> flowers;
    private List<Product> decorations;
    private List<Ticket> tickets;
    private static File currentDirectory = configDirectory();
    private static final Path[] pathNames = {Path.of(currentDirectory + File.separator + "trees_db.txt"),
            Path.of(currentDirectory + File.separator + "flowers_db.txt"),
            Path.of(currentDirectory + File.separator + "decor_db.txt"),
            Path.of(currentDirectory + File.separator + "tickets_db.txt")};

    public Database() {

        trees = new ArrayList<>();
        flowers = new ArrayList<>();
        decorations = new ArrayList<>();
        tickets = new ArrayList<>();

    }

    public List<Product> getTrees() { return trees; }
    public List<Product> getFlowers(){ return flowers; }
    public List<Product> getDecorations(){ return decorations; }
    public List<Ticket> getTickets() { return tickets;}

    // Métodos de escritura y lectura de archivos.

    public void writeDataToFiles() {
        /*
        Con el método writeDataToFiles() conseguimos transformar un ArrayList de productos a un archivo txt con el código de bytes
        del objeto de una manera que Java podrá comprender a posteriori. Para ello creamos un objeto ObjectOutputStream que recibe un
        objeto FileOutputStream que creará el archivo en la ruta especificada usando pathnames.
         */
        ObjectOutputStream writeFile = null;
        try {
            writeFile = new ObjectOutputStream(new FileOutputStream(String.valueOf(pathNames[0])));
            writeFile.writeObject(this.trees);
            writeFile = new ObjectOutputStream(new FileOutputStream(String.valueOf(pathNames[1])));
            writeFile.writeObject(this.flowers);
            writeFile = new ObjectOutputStream(new FileOutputStream(String.valueOf(pathNames[2])));
            writeFile.writeObject(this.decorations);
            writeFile = new ObjectOutputStream(new FileOutputStream(String.valueOf(pathNames[3])));
            writeFile.writeObject(this.tickets);
            writeFile.close();
        } catch (IOException e) {
            View.fileNotFound();
            e.printStackTrace();
        }

    }

    public void readDataFromFiles() {
        /*
        Creamos método de lectura de los archivos 'txt' que creamos en el método anterior.
        Usamos un objeto tipo ObjectInputStream al que le pasamos otro objeto FileInputStream que apunta al archivo txt deseado.
        Lo que hacemos es coger el fichero txt con la codificación de bytes del objeto que hemos creado en el método anterior y
        lo transformamos a un objeto ArrayList<Product> que Java puede leer.
         */

        ObjectInputStream readFile = null;
        try {
            readFile = new ObjectInputStream(new FileInputStream(String.valueOf(pathNames[0])));
            trees = ((ArrayList<Product>) readFile.readObject()); // Hacemos cast porque readFile devuelve un Object genérico y debemos pasar a <Product>.
            readFile = new ObjectInputStream(new FileInputStream(String.valueOf(pathNames[1])));
            flowers = (ArrayList<Product>) readFile.readObject();
            readFile = new ObjectInputStream(new FileInputStream(String.valueOf(pathNames[2])));
            decorations = (ArrayList<Product>) readFile.readObject();
            readFile = new ObjectInputStream(new FileInputStream(String.valueOf(pathNames[3])));
            tickets = (ArrayList<Ticket>) readFile.readObject();
            readFile.close();
        } catch (ClassNotFoundException | IOException e) {
            View.fileNotFound();
            e.printStackTrace();
        }

    }

    public static void configDatabase(Database database) {
        /*
        Método de configuración previa para crear los archivos en caso de que no existan en el directorio del usuario usando una condicional.
        Inicialmente el método comprueba que los archivos existan. Si alguno o todos no existen, los crea vacíos. Hacemos esto
        porque después de testear en Windows vimos que el método writeDataToFiles() no funcionaba si el archivo no existía de antemano.
        Después de esta condición inicial, lee los archivos con normalidad para recuperar los datos guardados si hubieran.
         */
        File file;
        try {
            if(!Files.exists(pathNames[0]) && !Files.exists(pathNames[1])
                    && !Files.exists(pathNames[2]) && !Files.exists(pathNames[3])) {
                for (int i = 0; i < pathNames.length; i++) {
                    file = new File(String.valueOf(pathNames[i]));
                    if(!file.exists()) {
                        file.createNewFile();
                    }
                }
                database.writeDataToFiles();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        database.readDataFromFiles();

    }

    public static File configDirectory(){
        /*
        Método para crear el directorio de destino del programa. Hacemos una condicional según el sistema operativo destino del usuario.
        Finalmente guardamos la ruta del directorio final en la variable "currentDirectory" que usaremos en el resto de métodos de la clase.
         */

        String osName = System.getProperty("os.name").toLowerCase();

        if(osName.contains("mac") || osName.contains("nix") || osName.contains("nux") || osName.contains("aix")){
            currentDirectory = new File(System.getProperty("user.dir") + File.separator + "database");
        } else {
            currentDirectory = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "database");
        }

        return currentDirectory;

    }

}

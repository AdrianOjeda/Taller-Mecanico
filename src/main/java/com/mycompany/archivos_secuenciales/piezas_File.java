package com.mycompany.archivos_secuenciales;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author brand
 */

public class piezas_File {
    int testing = 1;
    private DataOutputStream write;
    private DataInputStream read;
    private String path = "C:\\Proyecto\\piezas.txt";

    Object[][] datos = new Object[3][100];
    Object[][] eliminar = new Object[3][100];

    public void Guardar(piezas pi) throws FileNotFoundException {
        try {
            write = new DataOutputStream(new FileOutputStream(path, true));
            write.writeInt(pi.getPiz());
            write.writeUTF(pi.getDescrp());
            write.writeInt(pi.getStock());
            write.close();
        } catch (IOException ex) {

        }
    }

    public piezas BuscarPiezas(piezas pi) throws FileNotFoundException {
        piezas aux = null;
        int id = 0, stock = 0;
        String descrp = "";
        try {
            read = new DataInputStream(new FileInputStream(path));
            while (true) {
                id = read.readInt();
                descrp = read.readUTF();
                stock = read.readInt();

                if (pi.getPiz() == id) {
                    aux = new piezas();
                    aux.SetPiz(id);
                    aux.SetDescrp(descrp);
                    aux.SetStock(stock);
                }
            }
        } catch (IOException ex) {
        }
        try {
            read.close();
        } catch (IOException ex) {

        }
        return aux;
    }

    public void Editar(piezas pi) throws IOException {
        int i = 0;
        int z = 0;
        try {
            read = new DataInputStream(new FileInputStream(path));

            while (true) {
                datos[0][i] = read.readInt();
                datos[1][i] = read.readUTF();
                datos[2][i] = read.readInt();
                i++;
            }
        } catch (IOException ex) {
        }
        try {
            read.close();

        } catch (IOException ex) {

        }

        while (z < i) {
            if (pi.getPiz() == (int) datos[0][z]) {
                System.out.println(datos[0][z]);
                System.out.println(datos[1][z]);
                System.out.println(datos[2][z]);

                datos[0][z] = pi.getPiz();
                datos[1][z] = pi.getDescrp();
                datos[2][z] = pi.getStock();
            }
            z++;
        }

        i = 0;

        try {
            write = new DataOutputStream(new FileOutputStream(path));
            while (i < z) {
                write.writeInt((int) datos[0][i]);
                write.writeUTF(datos[1][i].toString());
                write.writeInt((int) datos[2][i]);
                i++;
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {
            if (write != null) {
                try {
                    write.close();
                } catch (IOException ex) {

                }
            }
        }
    }

    public int getMax() {
        int maxId = -1; 
        int id = 0, stock = 0;
        String descrp = "";
        try {
            read = new DataInputStream(new FileInputStream(path));
            while (true) {
                id = read.readInt();
                descrp = read.readUTF();
                stock = read.readInt();

                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (EOFException e) {
            // Fin del archivo, no se hace nada aquí
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de piezas");
        } finally {
            try {
                if (read != null) {
                    read.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo de piezas");
            }
        }

        // Verificar si hay IDs sin asignar desde 0 al máximo encontrado
        for (int i = 0; i <= maxId; i++) {
            boolean idEncontrado = false;

            try {
                read = new DataInputStream(new FileInputStream(path));
                while (true) {
                    id = read.readInt();
                    descrp = read.readUTF();
                    stock = read.readInt();

                    if (id == i) {
                        idEncontrado = true;
                        break;
                    }
                }
            } catch (EOFException e) {
                // Fin del archivo, no se hace nada aquí
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de piezas");
            } finally {
                try {
                    if (read != null) {
                        read.close();
                    }
                } catch (IOException e) {
                    System.out.println("Error al cerrar el archivo de piezas");
                }
            }
            if (!idEncontrado) {
                return i;
            }
        }
        return maxId + 1;
    }

    public void Eliminar_Piezas(piezas pi) throws IOException {
        int i = 0;
        int z = 0;
        int j;
        eliminar[0][0] = "";
        eliminar[1][0] = "";
        eliminar[2][0] = "";

        try {
            read = new DataInputStream(new FileInputStream(path));

            while (true) {
                eliminar[0][i] = read.readInt();
                eliminar[1][i] = read.readUTF();
                eliminar[2][i] = read.readInt();

                System.out.println("ELIMINAR");
                System.out.println(eliminar[0][i]);
                System.out.println(eliminar[1][i]);
                System.out.println(eliminar[2][i]);

                i++;
            }
        } catch (IOException ex) {
        }
        try {
            read.close();

        } catch (IOException ex) {

        }
        j = 0;
        while (z < i) {
            if ((int) eliminar[0][z] != pi.getPiz()) {
                datos[0][j] = eliminar[0][z];
                datos[1][j] = eliminar[1][z];
                datos[2][j] = eliminar[2][z];
                j++;
            }
            z++;
        }
        
        i = 0;

        try {
            write = new DataOutputStream(new FileOutputStream(path));
            while (i < j) {
                write.writeInt((int) datos[0][i]);
                write.writeUTF(datos[1][i].toString());
                write.writeInt((int) datos[2][i]);
                i++;
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {
            if (write != null) {
                try {
                    write.close();
                } catch (IOException ex) {

                }
            }
        }
    }
}
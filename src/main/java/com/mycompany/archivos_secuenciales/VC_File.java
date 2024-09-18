
package com.mycompany.archivos_secuenciales;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class VC_File {
    private RandomAccessFile raf;
    private String path = "C:\\Proyecto\\vc.txt";

    public void guardar(vehiculo_cliente vc) {
        try {
            raf = new RandomAccessFile(path, "rw");
            raf.seek(raf.length()); // Move to the end of the file
            raf.writeUTF(vc.getIdUsuario());
            raf.writeUTF(vc.getIdCliente());
        } catch (IOException e) {
            System.out.println("Error al guardar el cliente");
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo");
            }
        }
    }

    public void eliminar(vehiculo_cliente vc) {
        RandomAccessFile tempFile = null;
        try {
            raf = new RandomAccessFile(path, "r");
            tempFile = new RandomAccessFile("C:\\Proyecto\\temp.txt", "rw");

            while (true) {
                String us = raf.readUTF();
                String id = raf.readUTF();

                if (!id.equals(vc.getIdCliente()) || !us.equals(vc.getIdUsuario())) {
                    tempFile.writeUTF(us);
                    tempFile.writeUTF(id);
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de clientes");
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
                if (tempFile != null) {
                    tempFile.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo");
            }
        }

        // Rename temp file to original file
        try {
            File temp = new File("C:\\Proyecto\\temp.txt");
            File original = new File(path);
            if (original.delete()) {
                temp.renameTo(original);
            } else {
                System.out.println("Error al eliminar el archivo original");
            }
        } catch (Exception e) {
            System.out.println("Error al renombrar el archivo");
        }
    }

    public void editar(vehiculo_cliente vc) {
        RandomAccessFile tempFile = null;
        try {
            raf = new RandomAccessFile(path, "r");
            tempFile = new RandomAccessFile("C:\\Proyecto\\temp.txt", "rw");

            while (true) {
                String us = raf.readUTF();
                String id = raf.readUTF();

                if (id.equals(vc.getIdCliente()) && us.equals(vc.getIdUsuario())) {
                    tempFile.writeUTF(vc.getIdUsuario());
                    tempFile.writeUTF(vc.getIdCliente());
                } else {
                    tempFile.writeUTF(us);
                    tempFile.writeUTF(id);
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de clientes");
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
                if (tempFile != null) {
                    tempFile.close();
                }
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo");
            }
        }

        // Rename temp file to original file
        try {
            File temp = new File("C:\\Proyecto\\temp.txt");
            File original = new File(path);
            if (original.delete()) {
                temp.renameTo(original);
            } else {
                System.out.println("Error al eliminar el archivo original");
            }
        } catch (Exception e) {
            System.out.println("Error al renombrar el archivo");
        }
    }
}

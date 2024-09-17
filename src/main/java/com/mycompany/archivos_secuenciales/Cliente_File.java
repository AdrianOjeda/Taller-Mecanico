package com.mycompany.archivos_secuenciales;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Cliente_File {
    private static final String PATH = "C:\\Proyecto\\clientes.txt"; // Static final for constant path

    public int getMaxId() {
        int maxId = -1;

        try (DataInputStream read = new DataInputStream(new FileInputStream(PATH))) {
            while (true) {
                int us = read.readInt();
                int id = read.readInt();
                read.readUTF(); // Skip name
                read.readUTF(); // Skip apellidoPaterno
                read.readUTF(); // Skip apellidoMaterno

                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (EOFException e) {
            // End of file
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de clientes");
        }

        // Check for missing IDs
        for (int i = 0; i <= maxId; i++) {
            boolean idEncontrado = false;

            try (DataInputStream read = new DataInputStream(new FileInputStream(PATH))) {
                while (true) {
                    int us = read.readInt();
                    int id = read.readInt();
                    read.readUTF(); // Skip name
                    read.readUTF(); // Skip apellidoPaterno
                    read.readUTF(); // Skip apellidoMaterno

                    if (id == i) {
                        idEncontrado = true;
                        break;
                    }
                }
            } catch (EOFException e) {
                // End of file
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de clientes");
            }

            if (!idEncontrado) {
                return i;
            }
        }

        return maxId + 1;
    }

    public void guardar(cliente cliente) {
        try (DataOutputStream write = new DataOutputStream(new FileOutputStream(PATH, true))) {
            write.writeInt(cliente.getIdUsuario());
            write.writeInt(cliente.getId());
            write.writeUTF(cliente.getNombre());
            write.writeUTF(cliente.getApellidoPaterno());
            write.writeUTF(cliente.getApellidoMaterno());
        } catch (IOException e) {
            System.out.println("Error al guardar el cliente");
        }
    }

    public cliente buscar(int id) {
        cliente cliente = null;

        try (DataInputStream read = new DataInputStream(new FileInputStream(PATH))) {
            while (true) {
                int us = read.readInt();
                int idCliente = read.readInt();
                String nombre = read.readUTF();
                String apellidoPaterno = read.readUTF();
                String apellidoMaterno = read.readUTF();

                if (idCliente == id) {
                    cliente = new cliente();
                    cliente.setIdUsuario(us);
                    cliente.setId(idCliente);
                    cliente.setNombre(nombre);
                    cliente.setApellidoPaterno(apellidoPaterno);
                    cliente.setApellidoMaterno(apellidoMaterno);
                    break;
                }
            }
        } catch (EOFException e) {
            // End of file
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de clientes");
        }

        return cliente;
    }

    public void eliminar(cliente cliente) {
        try (DataInputStream read = new DataInputStream(new FileInputStream(PATH));
             DataOutputStream write = new DataOutputStream(new FileOutputStream("C:\\Proyecto\\temp.txt"))) {

            while (true) {
                int us = read.readInt();
                int id = read.readInt();
                String nombre = read.readUTF();
                String apellidoPaterno = read.readUTF();
                String apellidoMaterno = read.readUTF();

                if (id != cliente.getId()) {
                    write.writeInt(us);
                    write.writeInt(id);
                    write.writeUTF(nombre);
                    write.writeUTF(apellidoPaterno);
                    write.writeUTF(apellidoMaterno);
                }
            }
        } catch (EOFException e) {
            // End of file
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de clientes");
        }

        // Rename temp file to original file
        try {
            java.io.File temp = new java.io.File("C:\\Proyecto\\temp.txt");
            java.io.File original = new java.io.File(PATH);
            if (original.delete()) {
                temp.renameTo(original);
            } else {
                System.out.println("Error deleting the original file");
            }
        } catch (Exception e) {
            System.out.println("Error renaming the file");
        }
    }

    public void editar(cliente cliente) {
        try (RandomAccessFile file = new RandomAccessFile(PATH, "r");
             RandomAccessFile tempFile = new RandomAccessFile("C:\\Proyecto\\temp.txt", "rw")) {

            while (true) {
                try {
                    int us = file.readInt();
                    int id = file.readInt();
                    String nombre = file.readUTF();
                    String apellidoPaterno = file.readUTF();
                    String apellidoMaterno = file.readUTF();

                    if (id == cliente.getId()) {
                        tempFile.writeInt(cliente.getIdUsuario());
                        tempFile.writeInt(cliente.getId());
                        tempFile.writeUTF(cliente.getNombre());
                        tempFile.writeUTF(cliente.getApellidoPaterno());
                        tempFile.writeUTF(cliente.getApellidoMaterno());
                    } else {
                        tempFile.writeInt(us);
                        tempFile.writeInt(id);
                        tempFile.writeUTF(nombre);
                        tempFile.writeUTF(apellidoPaterno);
                        tempFile.writeUTF(apellidoMaterno);
                    }
                } catch (EOFException e) {
                    break; // End of file
                }
            }
        } catch (IOException e) {
            System.out.println("Error editing the client");
        }

        // Rename temp file to original file
        try {
            java.io.File temp = new java.io.File("C:\\Proyecto\\temp.txt");
            java.io.File original = new java.io.File(PATH);
            if (original.delete()) {
                temp.renameTo(original);
            } else {
                System.out.println("Error deleting the original file");
            }
        } catch (Exception e) {
            System.out.println("Error renaming the file");
        }
    }
}

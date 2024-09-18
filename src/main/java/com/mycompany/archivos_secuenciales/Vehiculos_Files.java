package com.mycompany.archivos_secuenciales;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Vehiculos_Files {

    private RandomAccessFile file;
    private String path = "C:\\Proyecto\\vehiculos.txt";

    public void Guardar(Vehiculos vcs) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(path, "rw")) {
            // Move to the end of the file
            file.seek(file.length());

            file.writeUTF(vcs.getCliente() != null ? vcs.getCliente() : "");
            file.writeInt(vcs.getId_vehiculo());
            file.writeUTF(vcs.getMatricula() != null ? vcs.getMatricula() : "");
            file.writeUTF(vcs.getMarca() != null ? vcs.getMarca() : "");
            file.writeUTF(vcs.getModelo() != null ? vcs.getModelo() : "");
            file.writeUTF(vcs.getFecha() != null ? vcs.getFecha() : "");
            file.writeUTF(vcs.getColor() != null ? vcs.getColor() : "");
            file.writeUTF(vcs.getNota() != null ? vcs.getNota() : "");
        } catch (IOException ex) {
            ex.printStackTrace(); // Improved error handling
            throw ex; // Re-throw exception to handle it at a higher level
        }
    }

    public Vehiculos BuscarIdVehiculo(Vehiculos vcs) throws IOException {
        Vehiculos aux = null;
        int id;
        String cliente, matricula, marca, modelo, fecha, color, nota;
        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                cliente = file.readUTF();
                id = file.readInt();
                matricula = file.readUTF();
                marca = file.readUTF();
                modelo = file.readUTF();
                fecha = file.readUTF();
                color = file.readUTF();
                nota = file.readUTF();
                if (vcs.getId_vehiculo() == id) {
                    aux = new Vehiculos();
                    aux.setCliente(cliente);
                    aux.setId_vehiculo(id);
                    aux.setMatricula(matricula);
                    aux.setMarca(marca);
                    aux.setModelo(modelo);
                    aux.setFecha(fecha);
                    aux.setColor(color);
                    aux.setNota(nota);
                    break; // Exit the loop when found
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } finally {
            if (file != null) {
                file.close();
            }
        }
        return aux;
    }

    public Vehiculos BuscarMatricula(Vehiculos vcs) throws IOException {
        Vehiculos aux = null;
        int id;
        String cliente, matricula, marca, modelo, fecha, color, nota;
        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                cliente = file.readUTF();
                id = file.readInt();
                matricula = file.readUTF();
                marca = file.readUTF();
                modelo = file.readUTF();
                fecha = file.readUTF();
                color = file.readUTF();
                nota = file.readUTF();
                if (vcs.getMatricula().equals(matricula)) {
                    aux = new Vehiculos();
                    aux.setCliente(cliente);
                    aux.setId_vehiculo(id);
                    aux.setMatricula(matricula);
                    aux.setMarca(marca);
                    aux.setModelo(modelo);
                    aux.setFecha(fecha);
                    aux.setColor(color);
                    aux.setNota(nota);
                    break; // Exit the loop when found
                }
            }
        } catch (EOFException e) {
            // End of file reached
        } finally {
            if (file != null) {
                file.close();
            }
        }
        return aux;
    }

    public void Editar_Vehiculo(Vehiculos vcs) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(path, "rw")) {
            long pos = 0;
            while (true) {
                file.seek(pos);

                String cliente = file.readUTF();
                int id = file.readInt();
                String matricula = file.readUTF();
                String marca = file.readUTF();
                String modelo = file.readUTF();
                String fecha = file.readUTF();
                String color = file.readUTF();
                String nota = file.readUTF();

                if (vcs.getId_vehiculo() == id) {
                    file.seek(pos);
                    file.writeUTF(vcs.getCliente() != null ? vcs.getCliente() : "");
                    file.writeInt(vcs.getId_vehiculo());
                    file.writeUTF(vcs.getMatricula() != null ? vcs.getMatricula() : "");
                    file.writeUTF(vcs.getMarca() != null ? vcs.getMarca() : "");
                    file.writeUTF(vcs.getModelo() != null ? vcs.getModelo() : "");
                    file.writeUTF(vcs.getFecha() != null ? vcs.getFecha() : "");
                    file.writeUTF(vcs.getColor() != null ? vcs.getColor() : "");
                    file.writeUTF(vcs.getNota() != null ? vcs.getNota() : "");
                    break; // Exit loop after editing
                }

                pos = file.getFilePointer(); // Update position for next record
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // Improved error handling
            throw ex; // Re-throw exception to handle it at a higher level
        }
    }


    public void Eliminar_Vehiculos(Vehiculos vcs) throws IOException {
        // Read all records into memory
        Object[][] datos = new Object[8][100];
        Object[][] eliminar = new Object[8][100];
        int i = 0, z = 0, j = 0;

        try {
            file = new RandomAccessFile(path, "r");
            while (true) {
                eliminar[0][i] = file.readUTF();
                eliminar[1][i] = file.readInt();
                eliminar[2][i] = file.readUTF();
                eliminar[3][i] = file.readUTF();
                eliminar[4][i] = file.readUTF();
                eliminar[5][i] = file.readUTF();
                eliminar[6][i] = file.readUTF();
                eliminar[7][i] = file.readUTF();
                i++;
            }
        } catch (EOFException e) {
            // End of file reached
        } finally {
            if (file != null) {
                file.close();
            }
        }

        // Filter out the record to be deleted
        for (z = 0; z < i; z++) {
            if ((int) eliminar[1][z] != vcs.getId_vehiculo()) {
                datos[0][j] = eliminar[0][z];
                datos[1][j] = eliminar[1][z];
                datos[2][j] = eliminar[2][z];
                datos[3][j] = eliminar[3][z];
                datos[4][j] = eliminar[4][z];
                datos[5][j] = eliminar[5][z];
                datos[6][j] = eliminar[6][z];
                datos[7][j] = eliminar[7][z];
                j++;
            }
        }

        // Write the remaining records back to file
        try {
            file = new RandomAccessFile(path, "rw");
            for (int k = 0; k < j; k++) {
                file.writeUTF((String) datos[0][k]);
                file.writeInt((int) datos[1][k]);
                file.writeUTF((String) datos[2][k]);
                file.writeUTF((String) datos[3][k]);
                file.writeUTF((String) datos[4][k]);
                file.writeUTF((String) datos[5][k]);
                file.writeUTF((String) datos[6][k]);
                file.writeUTF((String) datos[7][k]);
            }
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }

    public int getMax() {
        int maxId = -1; // Initialize with -1 to handle empty file case
        try (RandomAccessFile file = new RandomAccessFile(path, "r")) {
            while (true) {
                String cliente = file.readUTF();
                int id = file.readInt();
                String matricula = file.readUTF();
                String marca = file.readUTF();
                String modelo = file.readUTF();
                String fecha = file.readUTF();
                String color = file.readUTF();
                String nota = file.readUTF();

                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (EOFException e) {
            // End of file reached, continue with maxId calculation
        } catch (IOException e) {
            System.out.println("Error reading the file");
        }

        return maxId + 1; // Return the next available ID
    }


}

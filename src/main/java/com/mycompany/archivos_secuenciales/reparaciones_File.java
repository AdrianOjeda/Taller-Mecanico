package com.mycompany.archivos_secuenciales;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author brand
 */

public class reparaciones_File {

    private RandomAccessFile file;
    private final String path = "C:\\Proyecto\\reparaciones.txt";

    public reparaciones_File() throws IOException {
        file = new RandomAccessFile(path, "rw");
    }

    // Guardar new reparacion
    public void Guardar(reparaciones rep) throws IOException {
        file.seek(file.length()); // Move to the end of the file
        file.writeInt(rep.getId_ve());
        file.writeInt(rep.getId_pi());
        file.writeInt(rep.getId_re());
        file.writeUTF(rep.getFalla());
        file.writeInt(rep.getId_contrl());
        file.writeUTF(rep.getFecha_e());
        file.writeUTF(rep.getFecha_s());
    }

    // Buscar reparacion by id_re
    public reparaciones BuscarReparacion(reparaciones rep) throws IOException {
        reparaciones aux = null;
        file.seek(0); // Start from the beginning of the file
        while (true) {
            try {
                int id_ve = file.readInt();
                int id_pi = file.readInt();
                int id_re = file.readInt();
                String falla = file.readUTF();
                int id_contrl = file.readInt();
                String fecha_e = file.readUTF();
                String fecha_s = file.readUTF();

                if (rep.getId_re() == id_re) {
                    aux = new reparaciones();
                    aux.setId_ve(id_ve);
                    aux.setId_pi(id_pi);
                    aux.setId_re(id_re);
                    aux.setFalla(falla);
                    aux.setId_contrl(id_contrl);
                    aux.setFecha_e(fecha_e);
                    aux.setFecha_s(fecha_s);
                    break;
                }
            } catch (EOFException e) {
                break; // End of file reached
            }
        }
        return aux;
    }

    // Edit existing reparacion
    public void Editar(reparaciones rep) throws IOException {
        // Temporary file for updating
        String tempPath = "C:\\Proyecto\\temp_reparaciones.txt";
        RandomAccessFile tempFile = new RandomAccessFile(tempPath, "rw");

        file.seek(0);
        while (true) {
            try {
                long pos = file.getFilePointer();
                int id_ve = file.readInt();
                int id_pi = file.readInt();
                int id_re = file.readInt();
                String falla = file.readUTF();
                int id_contrl = file.readInt();
                String fecha_e = file.readUTF();
                String fecha_s = file.readUTF();

                if (rep.getId_re() == id_re) {
                    tempFile.writeInt(rep.getId_ve());
                    tempFile.writeInt(rep.getId_pi());
                    tempFile.writeInt(rep.getId_re());
                    tempFile.writeUTF(rep.getFalla());
                    tempFile.writeInt(rep.getId_contrl());
                    tempFile.writeUTF(rep.getFecha_e());
                    tempFile.writeUTF(rep.getFecha_s());
                } else {
                    tempFile.writeInt(id_ve);
                    tempFile.writeInt(id_pi);
                    tempFile.writeInt(id_re);
                    tempFile.writeUTF(falla);
                    tempFile.writeInt(id_contrl);
                    tempFile.writeUTF(fecha_e);
                    tempFile.writeUTF(fecha_s);
                }
            } catch (EOFException e) {
                break; // End of file reached
            }
        }

        file.close();
        tempFile.close();

        // Replace original file with updated file
        file = new RandomAccessFile(path, "rw");
        tempFile = new RandomAccessFile(tempPath, "rw");
        file.setLength(0); // Clear the original file

        tempFile.seek(0);
        while (true) {
            try {
                file.writeInt(tempFile.readInt());
                file.writeInt(tempFile.readInt());
                file.writeInt(tempFile.readInt());
                file.writeUTF(tempFile.readUTF());
                file.writeInt(tempFile.readInt());
                file.writeUTF(tempFile.readUTF());
                file.writeUTF(tempFile.readUTF());
            } catch (EOFException e) {
                break; // End of file reached
            }
        }

        tempFile.close();
        file.close();
    }

    // Get maximum ID
    public int getMax() throws IOException {
        int maxId = -1;
        file.seek(0);
        while (true) {
            try {
                int id = file.readInt();
                file.readInt(); // Skip id_pi
                int readId = file.readInt();
                file.readUTF(); // Skip falla
                file.readInt(); // Skip id_contrl
                file.readUTF(); // Skip fecha_e
                file.readUTF(); // Skip fecha_s

                if (readId > maxId) {
                    maxId = readId;
                }
            } catch (EOFException e) {
                break; // End of file reached
            }
        }

        return maxId + 1;
    }

    // Eliminar reparacion by rep
    public void Eliminar_Reparacion(reparaciones rep) throws IOException {
        // Temporary file for deleting
        String tempPath = "C:\\Proyecto\\temp_reparaciones.txt";
        RandomAccessFile tempFile = new RandomAccessFile(tempPath, "rw");

        file.seek(0);
        while (true) {
            try {
                long pos = file.getFilePointer();
                int id_ve = file.readInt();
                int id_pi = file.readInt();
                int id_re = file.readInt();
                String falla = file.readUTF();
                int id_contrl = file.readInt();
                String fecha_e = file.readUTF();
                String fecha_s = file.readUTF();

                if (rep.getId_re() != id_re) {
                    tempFile.writeInt(id_ve);
                    tempFile.writeInt(id_pi);
                    tempFile.writeInt(id_re);
                    tempFile.writeUTF(falla);
                    tempFile.writeInt(id_contrl);
                    tempFile.writeUTF(fecha_e);
                    tempFile.writeUTF(fecha_s);
                }
            } catch (EOFException e) {
                break; // End of file reached
            }
        }

        file.close();
        tempFile.close();

        // Replace original file with updated file
        file = new RandomAccessFile(path, "rw");
        tempFile = new RandomAccessFile(tempPath, "rw");
        file.setLength(0); // Clear the original file

        tempFile.seek(0);
        while (true) {
            try {
                file.writeInt(tempFile.readInt());
                file.writeInt(tempFile.readInt());
                file.writeInt(tempFile.readInt());
                file.writeUTF(tempFile.readUTF());
                file.writeInt(tempFile.readInt());
                file.writeUTF(tempFile.readUTF());
                file.writeUTF(tempFile.readUTF());
            } catch (EOFException e) {
                break; // End of file reached
            }
        }

        tempFile.close();
        file.close();
    }
}
